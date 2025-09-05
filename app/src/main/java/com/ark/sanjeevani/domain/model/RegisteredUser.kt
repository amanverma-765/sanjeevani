package com.ark.sanjeevani.domain.model

import com.ark.sanjeevani.domain.enums.Gender
import com.ark.sanjeevani.domain.enums.LoginRole


data class RegisteredUser(
    val createdAt: String,
    val name: String,
    val email: String,
    val phone: String,
    val avatar: String?,
    val countryCode: String,
    val role: LoginRole,
    val dob: String,
    val gender: Gender,
    val state: String,
    val city: String
) {
    fun getFirstName(): String {
        return name.split(" ").firstOrNull() ?: name
    }
}