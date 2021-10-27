package com.example.hiltexample.api

import com.example.hiltexample.model.Country
import retrofit2.http.GET
import retrofit2.http.Query

interface CovidApi {
    //https://api.covid19api.com/summary

    @GET("covid")
    suspend fun getCovid19(
        @Query("covid") api : String,
    ): List<Country>
}