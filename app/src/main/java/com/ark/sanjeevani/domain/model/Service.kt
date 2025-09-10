package com.ark.sanjeevani.domain.model

import androidx.compose.ui.graphics.Color
import com.ark.sanjeevani.R

data class Service(
    val id: Int,
    val title: String,
    val description: String,
    val icon: Int,
    val color: Color
)

val mockServices = listOf(
    Service(
        1,
        "Doctor",
        "Book Appointments with doctors worldwide",
        R.drawable.doctor_outlined_icon,
        Color(0xFFBB86FC) // Vibrant Purple
    ),
    Service(
        2,
        "Physiotherapist",
        "Book Appointments with Physiotherapists worldwide",
        R.drawable.physiotherapist_icon,
        Color(0xFFFFB74D) // Vibrant Orange
    ),
    Service(
        3,
        "Dietitian",
        "Book Appointments with Dietitian worldwide",
        R.drawable.dietitian_icon,
        Color(0xFF4DB6AC) // Teal Green
    ),
    Service(
        4,
        "Medical Stores",
        "Get list of medical stores nearby",
        R.drawable.medical_store_icon,
        Color(0xFF64B5F6) // Light Blue
    ),
    Service(
        5,
        "Labs",
        "Get List of medical labs nearby",
        R.drawable.lab_icon,
        Color(0xFFFF8A65) // Coral/Peach
    ),
    Service(
        6,
        "Blood Banks",
        "Get List of blood banks nearby",
        R.drawable.blood_bank_icon,
        Color(0xFFFF5252) // Bright Red
    ),
    Service(
        7,
        "Blood Banks",
        "Get List of blood banks nearby",
        R.drawable.blood_bank_icon,
        Color(0xFFFF5252) // Bright Red
    ),
    Service(
        8,
        "Blood Banks",
        "Get List of blood banks nearby",
        R.drawable.blood_bank_icon,
        Color(0xFFFF5252) // Bright Red
    )
)
