package com.ark.sanjeevani.domain.enums

import kotlinx.serialization.Serializable

@Serializable
enum class HospitalType(val text: String) {
    GOV("Government Hospitals"),
    PRIVATE("Private Hospitals")
}