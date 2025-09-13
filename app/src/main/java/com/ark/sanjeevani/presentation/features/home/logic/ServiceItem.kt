package com.ark.sanjeevani.presentation.features.home.logic

import androidx.compose.ui.graphics.Color
import com.ark.sanjeevani.R

data class ServiceItem(
    val id: Int,
    val title: String,
    val description: String,
    val icon: Int,
    val color: Color
)

val serviceItems = listOf(
    ServiceItem(
        1,
        "Doctor",
        "Book Appointments with doctors worldwide",
        R.drawable.doctor_outlined_icon,
        Color(0xFF6366F1) // Modern Indigo - Professional and trustworthy
    ),
    ServiceItem(
        2,
        "Physiotherapist",
        "Book Appointments with Physiotherapists worldwide",
        R.drawable.physiotherapist_icon,
        Color(0xFF10B981) // Emerald Green - Health and vitality
    ),
    ServiceItem(
        3,
        "Dietitian",
        "Book Appointments with Dietitian worldwide",
        R.drawable.dietitian_icon,
        Color(0xFF06B6D4) // Cyan - Fresh and clean
    ),
    ServiceItem(
        4,
        "Medical Stores",
        "Get list of medical stores nearby",
        R.drawable.medical_store_icon,
        Color(0xFF8B5CF6) // Purple - Premium and reliable
    ),
    ServiceItem(
        5,
        "Labs",
        "Get List of medical labs nearby",
        R.drawable.lab_icon,
        Color(0xFF3B82F6) // Blue - Scientific and precise
    ),
    ServiceItem(
        6,
        "Blood Banks",
        "Get List of blood banks nearby",
        R.drawable.blood_bank_icon,
        Color(0xFFEF4444) // Red - Medical emergency and blood
    )
)