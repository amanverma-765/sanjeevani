package com.ark.sanjeevani.data.dto

import com.ark.sanjeevani.domain.enums.Gender
import com.ark.sanjeevani.domain.enums.LoginRole
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisteredUserDto(
    @SerialName("created_at")
    val createdAt: String,
    val name: String,
    val email: String,
    val phone: String,
    val avatar: String?,
    @SerialName("country_code")
    val countryCode: String,
    val role: LoginRole,
    val dob: String,
    val gender: Gender,
    val state: String,
    val city: String
)