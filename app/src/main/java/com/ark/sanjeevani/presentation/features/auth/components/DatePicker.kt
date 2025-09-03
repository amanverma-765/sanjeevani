package com.ark.sanjeevani.presentation.features.auth.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeHolder: String,
    label: String
) {
    var openDialog by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val datePickerState = rememberDatePickerState()

    if (openDialog) {
        DatePickerDialog(
            onDismissRequest = { openDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            onValueChange(sdf.format(Date(millis)))
                        }
                        openDialog = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { openDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = { },
            enabled = false,
            readOnly = true,
            placeholder = {
                Text(
                    text = placeHolder,
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    focusManager.clearFocus()
                    openDialog = true
                }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = OutlinedTextFieldDefaults.colors().unfocusedIndicatorColor,
                disabledTextColor =  OutlinedTextFieldDefaults.colors().focusedTextColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    focusManager.clearFocus()
                    openDialog = true
                }
        )
    }
}