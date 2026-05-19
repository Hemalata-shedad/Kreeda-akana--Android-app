package com.kreedaankana.utils

object Constants {
    // Firebase paths
    const val FB_CHALLENGES = "challenges"
    const val FB_BOOKINGS = "bookings"
    const val FB_SCORES = "scores"

    // DataStore keys
    const val PREF_TEAM_NAME = "team_name"
    const val PREF_CAPTAIN = "captain"
    const val PREF_SPORT = "sport"
    const val PREF_ONBOARDED = "onboarded"

    // Time slots (6 AM – 8 PM, 2-hour blocks)
    val TIME_SLOTS = listOf(
        "06:00 - 08:00",
        "08:00 - 10:00",
        "10:00 - 12:00",
        "12:00 - 14:00",
        "14:00 - 16:00",
        "16:00 - 18:00",
        "18:00 - 20:00"
    )

    // Sports
    val SPORTS = listOf("Cricket", "Volleyball", "Football", "Kabaddi", "Kho-Kho")

    // Challenge statuses
    const val STATUS_OPEN = "OPEN"
    const val STATUS_ACCEPTED = "ACCEPTED"
    const val STATUS_DECLINED = "DECLINED"
    const val STATUS_COUNTER = "COUNTER"
}
