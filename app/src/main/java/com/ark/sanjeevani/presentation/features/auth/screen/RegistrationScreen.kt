package com.ark.sanjeevani.presentation.features.auth.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.ark.sanjeevani.R
import com.ark.sanjeevani.domain.enums.LoginRole
import com.ark.sanjeevani.presentation.features.auth.components.UserSelectionCard
import com.ark.sanjeevani.presentation.features.auth.components.CityDropdownField
import com.ark.sanjeevani.presentation.features.auth.components.DatePickerField
import com.ark.sanjeevani.presentation.features.auth.components.GenderDropdownField
import com.ark.sanjeevani.presentation.features.auth.components.ProfileImage
import com.ark.sanjeevani.presentation.features.auth.components.RegistrationTextField
import com.ark.sanjeevani.presentation.features.auth.components.StateDropdownField


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    onRoleSelection: (LoginRole) -> Unit,
    onCreateAccountClick: () -> Unit
) {
    var loginRole by remember { mutableStateOf<LoginRole?>(null) }
    var name by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var selectedState by remember { mutableStateOf("") }
    var selectedCity by remember { mutableStateOf("") }
    var acceptTerms by remember { mutableStateOf(false) }
    var selectedRole by remember { mutableStateOf("") }

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

            ProfileImage(onClick = {})

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                UserSelectionCard(
                    title = "User",
                    subTitle = "Track your health\n& consult experts",
                    icon = R.drawable.user_icon,
                    onClick = { onRoleSelection(LoginRole.INDIVIDUAL) },
                    selected = loginRole == LoginRole.INDIVIDUAL,
                    modifier = Modifier.weight(1f)
                )

                UserSelectionCard(
                    title = "Health Specialist",
                    subTitle = "Manage patients\n& share expertise",
                    icon = R.drawable.doctor_icon,
                    onClick = { onRoleSelection(LoginRole.DOCTOR) },
                    selected = loginRole == LoginRole.DOCTOR,
                    modifier = Modifier.weight(1f)
                )
            }

            // Name Field
            RegistrationTextField(
                value = name,
                onValueChange = { name = it },
                placeHolder = "Enter your full name",
                label = "Full Name"
            )

            // Date of Birth Field
            DatePickerField(
                value = dateOfBirth,
                onValueChange = { dateOfBirth = it },
                placeHolder = "Select your date of birth",
                label = "Date of Birth"
            )

            // Gender Selection
            GenderDropdownField(
                selectedGender = selectedGender,
                onGenderSelected = { selectedGender = it },
                label = "Gender"
            )

            // Mobile Number Field
            RegistrationTextField(
                value = mobileNumber,
                onValueChange = { newValue ->
                    val filteredValue = newValue.filter { it.isDigit() }.take(10)
                    mobileNumber = filteredValue
                },
                placeHolder = "Enter 10-digit mobile number",
                label = "Mobile Number",
                prefix = "+91 ",
                keyboardType = KeyboardType.Phone
            )

            // State Selection
            StateDropdownField(
                selectedState = selectedState,
                onStateSelected = { selectedState = it },
                label = "State"
            )

            // City Selection
            CityDropdownField(
                selectedCity = selectedCity,
                onCitySelected = { selectedCity = it },
                label = "City"
            )

            // Terms and Conditions
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = acceptTerms,
                    onCheckedChange = { acceptTerms = it }
                )
                Spacer(modifier = Modifier.width(8.dp))
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

            // Submit Button
            Button(
                onClick = onCreateAccountClick,
                enabled = acceptTerms &&
                    name.isNotBlank() &&
                    dateOfBirth.isNotBlank() &&
                    selectedGender.isNotBlank() &&
                    mobileNumber.isNotBlank() &&
                    selectedState.isNotBlank() &&
                    selectedCity.isNotBlank() &&
                    selectedRole.isNotBlank(),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text = "Create Account",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
