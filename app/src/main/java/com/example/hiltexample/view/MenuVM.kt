package com.example.hiltexample.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiltexample.base.BaseVM
import com.example.hiltexample.data.Resource
import com.example.hiltexample.data.Status
import com.example.hiltexample.model.Country
import com.example.hiltexample.repository.MenuRepository
import com.github.ajalt.timberkt.e
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@HiltViewModel
@ExperimentalCoroutinesApi
class MenuVM @Inject constructor(
    private val menuRepository: MenuRepository,
) : BaseVM() {

    val apiView: MutableLiveData<Resource<List<Country>>> = MutableLiveData()

    fun getCovidView(
        summary: String,
    ): MutableLiveData<Resource<List<Country>>> {
        viewModelScope.launch {
            menuRepository.getCovid(summary).collect {
                if (it.status == Status.SUCCESS) {
                    e { "Success View Model" }
                    apiView.postValue(it)
                } else if (it.status == Status.ERROR) {
                    e { "error : ${it.throwable}" }
                }
            }
        }
        return apiView
    }

}