package com.ark.sanjeevani.presentation.features.individual.home.logic

import androidx.compose.ui.graphics.Color
import com.ark.sanjeevani.R

data class ServiceItem(
    val type: ServiceType,
    val title: String,
    val description: String,
    val icon: Int,
    val color: Color
)

enum class ServiceType() {
    DOCTOR,
    PHYSIOTHERAPIST,
    DIETITIAN,
    MEDICAL_STORE,
    LABS,
    BLOOD_BANKS
}

val serviceItems = listOf(
    ServiceItem(
        ServiceType.DOCTOR,
        "Doctor",
        "Book Appointments with doctors worldwide",
        R.drawable.doctor_outlined_icon,
        Color(0xFF6366F1) // Modern Indigo - Professional and trustworthy
    ),
    ServiceItem(
        ServiceType.PHYSIOTHERAPIST,
        "Physiotherapist",
        "Book Appointments with Physiotherapists worldwide",
        R.drawable.physiotherapist_icon,
        Color(0xFF10B981) // Emerald Green - Health and vitality
    ),
    ServiceItem(
        ServiceType.DIETITIAN,
        "Dietitian",
        "Book Appointments with Dietitian worldwide",
        R.drawable.dietitian_icon,
        Color(0xFF06B6D4) // Cyan - Fresh and clean
    ),
    ServiceItem(
        ServiceType.MEDICAL_STORE,
        "Medical Stores",
        "Get list of medical stores nearby",
        R.drawable.medical_store_icon,
        Color(0xFF8B5CF6) // Purple - Premium and reliable
    ),
    ServiceItem(
        ServiceType.LABS,
        "Labs",
        "Get List of medical labs nearby",
        R.drawable.lab_icon,
        Color(0xFF3B82F6) // Blue - Scientific and precise
    ),
    ServiceItem(
        ServiceType.BLOOD_BANKS,
        "Blood Banks",
        "Get List of blood banks nearby",
        R.drawable.blood_bank_icon,
        Color(0xFFEF4444) // Red - Medical emergency and blood
    )
)