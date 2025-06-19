//League.kt
package com.example.footballscoresapp.data.model

data class LeagueResponse(
    val response: List<LeagueData>
)

data class LeagueData(
    val league: LeagueInfo,
    val country: LeagueCountry
)

data class LeagueInfo(
    val id: Int,
    val name: String,
    val logo: String
)

data class LeagueCountry(
    val name: String,
    val code: String?,
    val flag: String?
)
