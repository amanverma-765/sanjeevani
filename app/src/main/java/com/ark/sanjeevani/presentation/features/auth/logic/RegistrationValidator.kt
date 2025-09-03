package com.ark.sanjeevani.presentation.features.auth.logic

import com.ark.sanjeevani.domain.enums.LoginRole
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object RegistrationValidator {

    fun validateName(name: String): String? {
        return when {
            name.isBlank() -> "Name is required"
            name.length < 2 -> "Name must be at least 2 characters"
            name.length > 50 -> "Name must be less than 50 characters"
            !name.matches(Regex("^[a-zA-Z\\s]+$")) -> "Name should only contain letters and spaces"
            else -> null
        }
    }

    fun validateDateOfBirth(dateOfBirth: String): String? {
        return when {
            dateOfBirth.isBlank() -> "Date of birth is required"
            !isValidDate(dateOfBirth) -> "Please select a valid date"
            !isValidAge(dateOfBirth) -> "Age must be between 13 and 120 years"
            else -> null
        }
    }

    fun validateGender(gender: String): String? {
        return when {
            gender.isBlank() -> "Gender selection is required"
            else -> null
        }
    }

    fun validateMobileNumber(mobileNumber: String): String? {
        return when {
            mobileNumber.isBlank() -> "Mobile number is required"
            mobileNumber.length != 10 -> "Mobile number must be exactly 10 digits"
            !mobileNumber.matches(Regex("^[6-9][0-9]{9}$")) -> "Please enter a valid Indian mobile number"
            else -> null
        }
    }

    fun validateState(state: String): String? {
        return when {
            state.isBlank() -> "State selection is required"
            else -> null
        }
    }

    fun validateCity(city: String): String? {
        return when {
            city.isBlank() -> "City selection is required"
            else -> null
        }
    }

    fun validateRole(role: LoginRole?): String? {
        return when {
            role == null -> "Please select your role"
            else -> null
        }
    }

    fun validateTermsAcceptance(accepted: Boolean): String? {
        return when {
            !accepted -> "You must accept the terms and conditions"
            else -> null
        }
    }

    private fun isValidDate(dateString: String): Boolean {
        return try {
            val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            format.isLenient = false
            format.parse(dateString)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun isValidAge(dateOfBirth: String): Boolean {
        return try {
            val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val birthDate = format.parse(dateOfBirth)
            val currentDate = Calendar.getInstance().time

            val calendar = Calendar.getInstance()
            calendar.time = birthDate!!
            val birthYear = calendar.get(Calendar.YEAR)

            calendar.time = currentDate
            val currentYear = calendar.get(Calendar.YEAR)

            val age = currentYear - birthYear
            age in 13..120
        } catch (e: Exception) {
            false
        }
    }
}
