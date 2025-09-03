package com.ark.sanjeevani.presentation.features.auth.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties

@Composable
fun CityDropdownField(
    modifier: Modifier = Modifier,
    selectedCity: String,
    onCitySelected: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    var expanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val cityOptions = listOf(
        // Major Metropolitan Cities
        "Mumbai", "Delhi", "Bangalore", "Hyderabad", "Ahmedabad", "Chennai",
        "Kolkata", "Surat", "Pune", "Jaipur", "Lucknow", "Kanpur", "Nagpur",
        "Indore", "Thane", "Bhopal", "Visakhapatnam", "Pimpri", "Patna", "Vadodara",

        // Additional Major Cities
        "Agra", "Amritsar", "Aurangabad", "Coimbatore", "Faridabad", "Ghaziabad",
        "Gurgaon", "Guwahati", "Hubli", "Jabalpur", "Kochi", "Kota", "Kozhikode",
        "Ludhiana", "Madurai", "Mangalore", "Meerut", "Mysore", "Nashik", "Noida",

        // State Capitals and Important Cities
        "Agartala", "Aizawl", "Allahabad", "Amravati", "Asansol", "Bareilly",
        "Belgaum", "Bhavnagar", "Bhilai", "Bhubaneswar", "Bikaner", "Bilaspur",
        "Chandigarh", "Cuttack", "Dehradun", "Dhanbad", "Durgapur", "Erode",
        "Gandhinagar", "Gangtok", "Gorakhpur", "Gulbarga", "Guntur", "Gwalior",

        // Regional Centers
        "Haldwani", "Haridwar", "Hisar", "Howrah", "Imphal", "Itanagar",
        "Jalandhar", "Jammu", "Jamnagar", "Jamshedpur", "Jodhpur", "Junagadh",
        "Kakinada", "Kalaburagi", "Kannur", "Karnal", "Kohima", "Kollam",
        "Korba", "Kurnool", "Latur", "Malappuram", "Malegaon", "Mathura",

        // Growing Cities
        "Moradabad", "Muzaffarnagar", "Muzaffarpur", "Nanded", "Navi Mumbai",
        "Nellore", "Panipat", "Patiala", "Puducherry", "Raipur", "Rajahmundry",
        "Rajkot", "Ranchi", "Rourkela", "Saharanpur", "Salem", "Sangli",
        "Shimla", "Siliguri", "Solapur", "Srinagar", "Thiruvananthapuram",

        // Tier 2 Cities
        "Thrissur", "Tiruchirapalli", "Tirunelveli", "Tirupati", "Tiruppur",
        "Tumkur", "Udaipur", "Ujjain", "Ulhasnagar", "Vadodara", "Vapi",
        "Varanasi", "Vasai", "Vellore", "Vijayawada", "Warangal", "Yamuna Nagar"
    ).sorted()

    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Box {
            OutlinedTextField(
                value = selectedCity,
                onValueChange = { },
                readOnly = true,
                enabled = false,
                placeholder = {
                    Text(
                        text = "Select your city",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                trailingIcon = {
                    IconButton(onClick = {
                        focusManager.clearFocus()
                        expanded = !expanded
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowDown,
                            contentDescription = "Dropdown",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                shape = RoundedCornerShape(12.dp),
                isError = isError,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = if (isError) MaterialTheme.colorScheme.error else OutlinedTextFieldDefaults.colors().unfocusedIndicatorColor,
                    disabledTextColor = OutlinedTextFieldDefaults.colors().focusedTextColor
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        focusManager.clearFocus()
                        expanded = !expanded
                    }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                properties = PopupProperties(
                    focusable = true,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                ),
                modifier = Modifier
                    .height(250.dp)
                    .padding(vertical = 4.dp)
            ) {
                cityOptions.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        onClick = {
                            onCitySelected(option)
                            expanded = false
                            focusManager.clearFocus()
                        },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }
        }

        // Show error message
        errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }
    }
}