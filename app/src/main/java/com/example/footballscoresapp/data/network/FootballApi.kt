//FootballApi
package com.example.footballscoresapp.data.network

import com.example.footballscoresapp.data.model.CountryResponse
import com.example.footballscoresapp.data.model.LeagueResponse
import com.example.footballscoresapp.data.model.MatchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FootballApi {
    @GET("fixtures")
    suspend fun getTodayMatches(@Query("date") date: String): MatchResponse

    @GET("leagues")
    suspend fun getLeagues(): LeagueResponse

    @GET("countries")
    suspend fun getCountries(): CountryResponse
}
