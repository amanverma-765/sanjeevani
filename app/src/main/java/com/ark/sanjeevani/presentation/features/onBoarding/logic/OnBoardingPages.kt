package com.ark.sanjeevani.presentation.features.onBoarding.logic

import com.ark.sanjeevani.R


data class OnBoardingPage(
    val imageRes: Int,
    val title: String,
    val description: String
)

val onBoardingPages = listOf(
    OnBoardingPage(
        imageRes = R.drawable.doc_1,
        title = "Meet Doctors Online",
        description = "Connect with Specialized Doctors Online for Convenient and Comprehensive Medical Consultations."
    ),
    OnBoardingPage(
        imageRes = R.drawable.doc_2,
        title = "Book Appointments",
        description = "Easily Schedule Appointments with Your Preferred Healthcare Providers at Your Convenience."
    ),
    OnBoardingPage(
        imageRes = R.drawable.doc_3,
        title = "Health Records",
        description = "Securely Store and Access Your Health Records Anytime, Anywhere for Better Care."
    )
)