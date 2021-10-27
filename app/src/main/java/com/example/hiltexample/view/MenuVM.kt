package com.example.hiltexample.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiltexample.data.Resource
import com.example.hiltexample.data.Status
import com.example.hiltexample.model.Country
import com.example.hiltexample.repository.MenuRepository
import com.github.ajalt.timberkt.e
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MenuVM @Inject constructor(
    private val menuRepository: MenuRepository
): ViewModel() {

    val apiView :MutableLiveData<List<Country>> = MutableLiveData()

    fun getCovidView(
        summary :String
    ) : MutableLiveData<List<Country>> {
        viewModelScope.launch {
            menuRepository.getCovid(summary).collect {
                if (it.status == Status.SUCCESS){
                    e { "Success View Model" }
                    apiView.postValue(it.data)
                }else if (it.status == Status.ERROR){
                    e { "error : ${it.throwable}" }
                }
            }
        }
        return apiView
    }
}