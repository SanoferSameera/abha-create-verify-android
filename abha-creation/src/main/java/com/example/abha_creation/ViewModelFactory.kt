package com.example.abha_creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.abha_creation.data.api.ApiHelper
import com.example.abha_creation.data.repository.MainRepository

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}