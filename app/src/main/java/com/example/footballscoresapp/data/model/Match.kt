//Match.kt
package com.example.footballscoresapp.data.model

data class MatchResponse(
    val response: List<Match>
)

data class Match(
    val fixture: Fixture,
    val league: League,
    val teams: Teams,
    val goals: Goals,
    val score: Score
)

data class Fixture(
    val id: Int,
    val referee: String?,      // może być null
    val timezone: String,
    val date: String,          // ISO format, np. "2020-02-06T14:00:00+00:00"
    val timestamp: Long,
    val periods: Periods,
    val venue: Venue,
    val status: Status
)

data class Periods(
    val first: Long?,
    val second: Long?
)

data class Venue(
    val id: Int?,
    val name: String?,
    val city: String?
)

data class Status(
    val long: String,
    val short: String,
    val elapsed: Int?,
    val extra: Any?           // lub inny typ jeśli znasz
)

data class League(
    val id: Int,
    val name: String,
    val country: String,
    val logo: String,
    val flag: String?,
    val season: Int,
    val round: String
)

data class Teams(
    val home: Team,
    val away: Team
)

data class Team(
    val id: Int,
    val name: String,
    val logo: String,
    val winner: Boolean?
)

data class Goals(
    val home: Int?,
    val away: Int?
)

data class Score(
    val halftime: HalfFullExtraPenalty,
    val fulltime: HalfFullExtraPenalty,
    val extratime: HalfFullExtraPenalty,
    val penalty: HalfFullExtraPenalty
)

data class HalfFullExtraPenalty(
    val home: Int?,
    val away: Int?
)
