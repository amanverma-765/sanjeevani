package com.ark.sanjeevani.presentation.features.tabs.logic


fun createTabDestination(tab: TabDestinations): String  {
    return when(tab) {
        TabDestinations.History -> "history"
        TabDestinations.Home -> "ride"
    }
}

fun getTabDestination(tab: String?): TabDestinations {
    return when(tab) {
        "history" -> TabDestinations.History
        "ride" -> TabDestinations.Home
        else -> TabDestinations.Home
    }
}
