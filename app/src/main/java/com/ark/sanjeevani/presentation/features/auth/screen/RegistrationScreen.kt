package com.ark.sanjeevani.presentation.features.auth.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.ark.sanjeevani.R
import com.ark.sanjeevani.domain.enums.LoginRole
import com.ark.sanjeevani.presentation.components.LoadingDialog
import com.ark.sanjeevani.presentation.features.auth.components.UserSelectionCard
import com.ark.sanjeevani.presentation.features.auth.components.CityDropdownField
import com.ark.sanjeevani.presentation.features.auth.components.DatePickerField
import com.ark.sanjeevani.presentation.features.auth.components.GenderDropdownField
import com.ark.sanjeevani.presentation.features.auth.components.ProfileImage
import com.ark.sanjeevani.presentation.features.auth.components.RegistrationTextField
import com.ark.sanjeevani.presentation.features.auth.components.StateDropdownField
import com.ark.sanjeevani.presentation.features.auth.logic.reg.RegistrationUiEvent
import com.ark.sanjeevani.presentation.features.auth.logic.reg.RegistrationViewModel
import com.ark.sanjeevani.utils.toastShort
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    viewModel: RegistrationViewModel = koinViewModel(),
    onRegCompleted: () -> Unit,
    onUserNotAuthenticated: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    if (uiState.isLoading) LoadingDialog()

    // Handle error messages
    LaunchedEffect(uiState.errorMsg, uiState.authErrorMsg, uiState.registrationError) {
        uiState.errorMsg?.let {
            context.toastShort(it)
            viewModel.onEvent(RegistrationUiEvent.ClearErrorMsg)
        }
        uiState.authErrorMsg?.let {
            context.toastShort(it)
            viewModel.onEvent(RegistrationUiEvent.ClearErrorMsg)
            onUserNotAuthenticated()
        }
        uiState.registrationError?.let {
            context.toastShort(it)
        }
    }

    // Check for existing user when userInfo is available
    LaunchedEffect(uiState.userInfo) {
        uiState.userInfo?.let { userInfo ->
            viewModel.onEvent(RegistrationUiEvent.GetRegisteredUser(userInfo.email))
        }
    }

    // Handle navigation after successful registration or existing user found
    LaunchedEffect(uiState.isRegistrationComplete) {
        if (uiState.isRegistrationComplete) {
            onRegCompleted()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Register Your Account",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState())
                .imePadding()
        ) {

            ProfileImage(
                onClick = {},
                imageUrl = uiState.userInfo?.profileUrl
            )

            // Role Selection
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                UserSelectionCard(
                    title = "User",
                    subTitle = "Track your health\n& consult experts",
                    icon = R.drawable.user_icon,
                    onClick = { viewModel.onEvent(RegistrationUiEvent.UpdateRole(LoginRole.INDIVIDUAL)) },
                    selected = uiState.selectedRole == LoginRole.INDIVIDUAL,
                    modifier = Modifier.weight(1f)
                )

                UserSelectionCard(
                    title = "Health Specialist",
                    subTitle = "Manage patients\n& share expertise",
                    icon = R.drawable.doctor_icon,
                    onClick = { viewModel.onEvent(RegistrationUiEvent.UpdateRole(LoginRole.HS)) },
                    selected = uiState.selectedRole == LoginRole.HS,
                    modifier = Modifier.weight(1f)
                )
            }

            // Show role error
            uiState.roleError?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            // Name Field
            RegistrationTextField(
                value = uiState.name,
                onValueChange = { viewModel.onEvent(RegistrationUiEvent.UpdateName(it)) },
                placeHolder = "Enter your full name",
                label = "Full Name",
                isError = uiState.nameError != null,
                errorMessage = uiState.nameError
            )

            // Date of Birth Field
            DatePickerField(
                value = uiState.dateOfBirth,
                onValueChange = { viewModel.onEvent(RegistrationUiEvent.UpdateDateOfBirth(it)) },
                placeHolder = "Select your date of birth",
                label = "Date of Birth",
                isError = uiState.dateOfBirthError != null,
                errorMessage = uiState.dateOfBirthError
            )

            // Gender Selection
            GenderDropdownField(
                selectedGender = uiState.selectedGender,
                onGenderSelected = { viewModel.onEvent(RegistrationUiEvent.UpdateGender(it)) },
                label = "Gender",
                isError = uiState.genderError != null,
                errorMessage = uiState.genderError
            )

            // Mobile Number Field
            RegistrationTextField(
                value = uiState.mobileNumber,
                onValueChange = { viewModel.onEvent(RegistrationUiEvent.UpdateMobileNumber(it)) },
                placeHolder = "Enter 10-digit mobile number",
                label = "Mobile Number",
                prefix = "+91 ",
                keyboardType = KeyboardType.Phone,
                isError = uiState.mobileNumberError != null,
                errorMessage = uiState.mobileNumberError
            )

            // State Selection
            StateDropdownField(
                selectedState = uiState.selectedState,
                onStateSelected = { viewModel.onEvent(RegistrationUiEvent.UpdateState(it)) },
                label = "State",
                isError = uiState.stateError != null,
                errorMessage = uiState.stateError,
                stateOptions = uiState.states
            )

            // City Selection
            CityDropdownField(
                selectedCity = uiState.selectedCity,
                onCitySelected = { viewModel.onEvent(RegistrationUiEvent.UpdateCity(it)) },
                label = "City",
                isError = uiState.cityError != null,
                errorMessage = uiState.cityError,
                cityOptions = uiState.cities
            )

            // Terms and Conditions
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = uiState.acceptTerms,
                        onCheckedChange = {
                            viewModel.onEvent(
                                RegistrationUiEvent.UpdateTermsAcceptance(
                                    it
                                )
                            )
                        }
                    )
                    Text(
                        text = "I accept the ",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = "Terms and Conditions",
                        style = MaterialTheme.typography.bodyMedium,
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable { /* Handle terms click */ }
                    )
                }

                // Show terms error
                uiState.termsError?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                    )
                }
            }

            // Submit Button
            Button(
                onClick = {
                    viewModel.onEvent(RegistrationUiEvent.SubmitForm)
                },
                enabled = !uiState.isLoading,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
                Text(
                    text = if (uiState.isLoading) "Creating Account..." else "Create Account",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}