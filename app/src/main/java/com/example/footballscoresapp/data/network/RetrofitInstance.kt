//RetrofitInstance.kt
package com.example.footballscoresapp.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-RapidAPI-Key", "47d3b60504da99511b179023d8ab4027")
                .addHeader("X-RapidAPI-Host", "v3.football.api-sports.io")
                .build()
            chain.proceed(request)
        }
        .build()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://v3.football.api-sports.io/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)
        .build()

    val api: FootballApi = retrofit.create(FootballApi::class.java)
}
