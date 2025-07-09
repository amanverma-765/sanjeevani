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
        "Book Appointments with doctors world wide",
        R.drawable.doctor_icon,
        Color(0xFF725CAD)
    ),
    Service(
        2,
        "Physiotherapist",
        "Book Appointments with Physiotherapists world wide",
        R.drawable.physiotherapist_icon,
        Color(0xFFE63E6D)
    ),
    Service(
        3,
        "Dietitian",
        "Book Appointments with Dietitian world wide",
        R.drawable.dietitian_icon,
        Color(0xFF725CAD)
    ),
    Service(
        4,
        "Medical Stores",
        "Get list of medical stores nearby",
        R.drawable.medical_store_icon,
        Color(0xFFE63E6D)
    ),
    Service(
        5,
        "Labs",
        "Get List of medical labs nearby",
        R.drawable.lab_icon,
        Color(0xFF725CAD)
    ),
    Service(
        6,
        "Blood Banks",
        "Get List of blood banks nearby",
        R.drawable.blood_bank_icon,
        Color(0xFFE63E6D)
    )
)