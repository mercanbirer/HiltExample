package com.example.hiltexample.repository

import com.example.hiltexample.api.CovidApi
import com.example.hiltexample.data.Resource
import com.example.hiltexample.model.Country
import com.github.ajalt.timberkt.e
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.lang.Exception
import javax.inject.Inject

class MenuRepository @Inject constructor(
    private val covidApi: CovidApi,
) {

    fun getCovid(
        summary: String,
    ): Flow<Resource<List<Country>>> {
        return flow {
            emit(Resource.loading(null))
            val covid = covidApi.getCovid19(summary)
            e { "loading" }
            emit(Resource.success(covid))
        }.retryWhen { cause, _ ->
            (cause is Exception).also {
                if (it) delay(5000)
            }
        }.catch {
            emit(Resource.error(it.localizedMessage, null, it))
        }.flowOn(Dispatchers.IO)
    }
}