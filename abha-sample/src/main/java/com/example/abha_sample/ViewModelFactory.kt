package com.example.abha_sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.abha_sample.data.api.ApiHelper
import com.example.abha_sample.data.repository.MainRepository

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}