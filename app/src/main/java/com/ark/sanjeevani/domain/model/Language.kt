package com.ark.sanjeevani.domain.model

data class Language(
    val id: Int,
    val name: String,
    val selected: Boolean
)

val mockLanguages = listOf(
    Language(id = 2, name = "English", selected = false),
    Language(id = 1, name = "हिन्दी", selected = false),
    Language(id = 3, name = "ગુજરાતી", selected = false),
    Language(id = 4, name = "മലയാളം", selected = false),
    Language(id = 5, name = "भोजपुरी", selected = false),
    Language(id = 6, name = "தமிழ்", selected = false),
    Language(id = 7, name = "తెలుగు", selected = false),
    Language(id = 8, name = "मराठी", selected = false),
    Language(id = 9, name = "বাংলা", selected = false),
    Language(id = 10, name = "ਪੰਜਾਬੀ", selected = false),
    Language(id = 11, name = "ಕನ್ನಡ", selected = false),
    Language(id = 12, name = "ଓଡ଼ିଆ", selected = false),
    Language(id = 13, name = "অসমীয়া", selected = false),
    Language(id = 14, name = "اردو", selected = false),
    Language(id = 15, name = "कोंकणी", selected = false),
    Language(id = 16, name = "संस्कृतम्", selected = false),
    Language(id = 17, name = "سنڌي", selected = false),
    Language(id = 18, name = "डोगरी", selected = false),
    Language(id = 19, name = "کٲشُر", selected = false),
    Language(id = 20, name = "मैथिली", selected = false)
)

