package com.ark.sanjeevani.utils

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.GetCredentialInterruptedException
import androidx.credentials.exceptions.GetCredentialProviderConfigurationException
import androidx.credentials.exceptions.GetCredentialUnsupportedException
import androidx.credentials.exceptions.NoCredentialException
import com.ark.sanjeevani.BuildConfig
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.UUID

/**
 * Data class representing Google OAuth authentication result
 */
data class GoogleOAuthResult(
    val idToken: String,
    val hashedNonce: String
)

/**
 * Sealed class representing different types of OAuth errors
 */
sealed class GoogleOAuthError(open val message: String, val cause: Throwable? = null) {
    data class UserCancelled(val exception: GetCredentialCancellationException) :
        GoogleOAuthError("Sign-in cancelled", exception)

    data class NetworkError(val exception: GetCredentialException) :
        GoogleOAuthError("Network error, please try again", exception)

    data class ConfigurationError(val exception: GetCredentialProviderConfigurationException) :
        GoogleOAuthError("Sign-in configuration error", exception)

    data class UnsupportedError(val exception: GetCredentialUnsupportedException) :
        GoogleOAuthError("Sign-in not supported on this device", exception)

    data class InterruptedError(val exception: GetCredentialInterruptedException) :
        GoogleOAuthError("Sign-in was interrupted", exception)

    data class NoCredentialError(val exception: NoCredentialException) :
        GoogleOAuthError("No sign-in credentials found", exception)

    data class TokenParsingError(val exception: GoogleIdTokenParsingException) :
        GoogleOAuthError("Authentication token error", exception)

    data class UnknownError(val exception: Exception) :
        GoogleOAuthError("Sign-in failed", exception)

    data class InvalidConfiguration(override val message: String) :
        GoogleOAuthError(message)
}

/**
 * Initiates Google OAuth authentication process with improved error handling
 *
 * @param context Android context
 * @param filterByAuthorizedAccounts Whether to filter by authorized accounts only
 * @return Result containing either GoogleOAuthResult or GoogleOAuthError
 */
suspend fun initiateGoogleOAuth(
    context: Context,
    filterByAuthorizedAccounts: Boolean = false
): Result<GoogleOAuthResult> = withContext(Dispatchers.IO) {

    try {
        // Validate configuration
        if (BuildConfig.googleClientId.isBlank()) {
            return@withContext Result.failure(
                Exception(GoogleOAuthError.InvalidConfiguration("Google Client ID is not configured").message)
            )
        }

        val credManager = CredentialManager.create(context)

        // Generate secure nonce
        val (rawNonce, hashedNonce) = generateSecureNonce()

        // Build Google ID option
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(filterByAuthorizedAccounts)
            .setServerClientId(BuildConfig.googleClientId)
            .setNonce(hashedNonce)
            .setAutoSelectEnabled(true)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        // Execute credential request
        val result = credManager.getCredential(
            request = request,
            context = context
        )

        // Parse the credential
        val credential = result.credential
        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
        val googleIdToken = googleIdTokenCredential.idToken

        // Validate token is not empty
        if (googleIdToken.isBlank()) {
            return@withContext Result.failure(
                Exception(GoogleOAuthError.TokenParsingError(
                    GoogleIdTokenParsingException(RuntimeException("Received empty ID token"))
                ).message)
            )
        }

        Result.success(GoogleOAuthResult(googleIdToken, hashedNonce))

    } catch (e: GetCredentialCancellationException) {
        Result.failure(Exception(GoogleOAuthError.UserCancelled(e).message, e))
    } catch (e: GetCredentialProviderConfigurationException) {
        Result.failure(Exception(GoogleOAuthError.ConfigurationError(e).message, e))
    } catch (e: GetCredentialUnsupportedException) {
        Result.failure(Exception(GoogleOAuthError.UnsupportedError(e).message, e))
    } catch (e: GetCredentialInterruptedException) {
        Result.failure(Exception(GoogleOAuthError.InterruptedError(e).message, e))
    } catch (e: NoCredentialException) {
        Result.failure(Exception(GoogleOAuthError.NoCredentialError(e).message, e))
    } catch (e: GoogleIdTokenParsingException) {
        Result.failure(Exception(GoogleOAuthError.TokenParsingError(e).message, e))
    } catch (e: GetCredentialException) {
        Result.failure(Exception(GoogleOAuthError.NetworkError(e).message, e))
    } catch (e: Exception) {
        Result.failure(Exception(GoogleOAuthError.UnknownError(e).message, e))
    }
}

/**
 * Generates a cryptographically secure nonce for OAuth
 *
 * @return Pair of raw nonce and SHA-256 hashed nonce
 */
private fun generateSecureNonce(): Pair<String, String> {
    // Use SecureRandom for better security instead of UUID
    val secureRandom = SecureRandom()
    val randomBytes = ByteArray(32) // 256 bits
    secureRandom.nextBytes(randomBytes)

    val rawNonce = randomBytes.joinToString("") { "%02x".format(it) }

    // Hash the nonce using SHA-256
    val messageDigest = MessageDigest.getInstance("SHA-256")
    val hashedBytes = messageDigest.digest(rawNonce.toByteArray())
    val hashedNonce = hashedBytes.joinToString("") { "%02x".format(it) }

    return Pair(rawNonce, hashedNonce)
}

/**
 * Alternative nonce generation using UUID (less secure but simpler)
 */
private fun generateUUIDNonce(): Pair<String, String> {
    val rawNonce = UUID.randomUUID().toString()
    val messageDigest = MessageDigest.getInstance("SHA-256")
    val hashedBytes = messageDigest.digest(rawNonce.toByteArray())
    val hashedNonce = hashedBytes.joinToString("") { "%02x".format(it) }

    return Pair(rawNonce, hashedNonce)
}

/**
 * Extension function to handle OAuth results in UI components
 */
fun Result<GoogleOAuthResult>.handleOAuthResult(
    onSuccess: (GoogleOAuthResult) -> Unit,
    onError: (String) -> Unit
) {
    fold(
        onSuccess = onSuccess,
        onFailure = { exception ->
            onError(exception.message ?: "Unknown authentication error")
        }
    )
}