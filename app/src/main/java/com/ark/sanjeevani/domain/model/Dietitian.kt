package com.ark.sanjeevani.domain.model


data class Dietitian(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val avatar: String,
    val experienceMonths: Int,
) {
    val experienceYears: Int
        get() = experienceMonths / 12
}