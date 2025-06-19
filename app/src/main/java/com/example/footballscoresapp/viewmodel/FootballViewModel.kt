package com.example.footballscoresapp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballscoresapp.data.model.Country
import com.example.footballscoresapp.data.model.LeagueData
import com.example.footballscoresapp.data.model.Match
import com.example.footballscoresapp.data.network.RetrofitInstance
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FootballViewModel : ViewModel() {

    // Pełna lista meczów, pobrana z API
    private var matchesRaw = listOf<Match>()

    // Lista meczów po filtracji - widoczna dla UI
    var matches by mutableStateOf<List<Match>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    var leagues by mutableStateOf<List<LeagueData>>(emptyList())

    var countries by mutableStateOf<List<Country>>(emptyList())

    private var _selectedLeague by mutableStateOf<LeagueData?>(null)
    var selectedLeague: LeagueData?
        get() = _selectedLeague
        set(value) {
            _selectedLeague = value
            filterMatches()
        }

    private var _selectedCountry by mutableStateOf<Country?>(null)
    var selectedCountry: Country?
        get() = _selectedCountry
        set(value) {
            _selectedCountry = value
            filterMatches()
        }

    var errorMessage by mutableStateOf<String?>(null)

    val filteredLeagues: List<LeagueData>
        get() = if (selectedCountry == null) {
            leagues
        } else {
            leagues.filter { it.country.name == selectedCountry?.name }
        }

    init {
        val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getTodayMatches(today)
                matchesRaw = response.response

                leagues = RetrofitInstance.api.getLeagues().response
                countries = RetrofitInstance.api.getCountries().response

                filterMatches()
            } catch (e: Exception) {
                errorMessage = "Błąd: ${e.message}"
                Log.e("API", "Błąd pobierania: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    // Metoda filtrująca mecze wg wybranej ligi i kraju
    private fun filterMatches() {
        matches = matchesRaw.filter { match ->
            val leagueMatches = selectedLeague?.league?.id?.let { it == match.league.id } ?: true
            val countryMatches = selectedCountry?.name?.let { it == match.league.country } ?: true
            leagueMatches && countryMatches
        }
    }
}
