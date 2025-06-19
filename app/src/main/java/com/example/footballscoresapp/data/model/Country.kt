//Country.kt
package com.example.footballscoresapp.data.model

data class CountryResponse(
    val response: List<Country>
)

data class Country(
    val name: String,
    val code: String?,
    val flag: String?
)
