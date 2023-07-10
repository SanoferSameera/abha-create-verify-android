package com.example.abha_create_verify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.abha_create_verify.data.model.CreateAbhaAddressReq
import com.example.abha_create_verify.data.model.ErrorResponse
import com.example.abha_create_verify.data.model.GenerateAadhaarOTPReq
import com.example.abha_create_verify.data.model.GenerateMobileOTPReq
import com.example.abha_create_verify.data.model.VerifyOTPReq
import com.example.abha_create_verify.data.repository.MainRepository
import com.example.abha_create_verify.utils.Resource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import okhttp3.ResponseBody

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {


    private fun handleErrorResponse(errorBody: ResponseBody?) : String {
        val gson = Gson()
        val jsonString = errorBody?.string()
        val error = gson.fromJson(jsonString, ErrorResponse::class.java)
        return if(error?.details?.isNotEmpty() == true) error.details[0].message else "Error Occurred!"
    }


    fun generateAadhaarOtp(generateAadhaarOTPReq: GenerateAadhaarOTPReq) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val res = mainRepository.generateAadhaarOtp(generateAadhaarOTPReq)
            if(res.code() == 202)
                res.body()?.let {
                    emit(Resource.success(data = it))
                }
            else {
               val errorMessage = res.errorBody()?.let { handleErrorResponse(it) }!!
                emit(Resource.error(data = null, message = errorMessage))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun verifyAadhaarOtp(verifyOTPReq: VerifyOTPReq) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val res = mainRepository.verifyAadhaarOtp(verifyOTPReq)
            if(res.code() == 202)
                res.body()?.let {
                    emit(Resource.success(data = it))
                }
            else {
                val errorMessage = res.errorBody()?.let { handleErrorResponse(it) }!!
                emit(Resource.error(data = null, message = errorMessage))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun checkAndGenerateMobileOtp(generateMobileOTPReq: GenerateMobileOTPReq) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val res = mainRepository.checkAndGenerateMobileOtp(generateMobileOTPReq)
            if(res.code() == 202)
                res.body()?.let {
                    emit(Resource.success(data = it))
                }
            else {
                val errorMessage = res.errorBody()?.let { handleErrorResponse(it) }!!
                emit(Resource.error(data = null, message = errorMessage))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun verifyMobileOtp(verifyOTPReq: VerifyOTPReq) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val res = mainRepository.verifyMobileOtp(verifyOTPReq)
            if(res.code() == 202)
                res.body()?.let {
                    emit(Resource.success(data = it))
                }
            else {
                val errorMessage = res.errorBody()?.let { handleErrorResponse(it) }!!
                emit(Resource.error(data = null, message = errorMessage))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun createHealthIdByAadhaarOtp() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val res = mainRepository.createHealthIdByAdhaarOtp()
            if(res.code() == 202)
                res.body()?.let {
                    emit(Resource.success(data = it))
                }
            else {
                val errorMessage = res.errorBody()?.let { handleErrorResponse(it) }!!
                emit(Resource.error(data = null, message = errorMessage))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun createAbhaAddress(createAbhaAddressReq : CreateAbhaAddressReq) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val res = mainRepository.createAbhaAddress(createAbhaAddressReq)
            if(res.code() == 202)
                res.body()?.let {
                    emit(Resource.success(data = it))
                }
            else {
                val errorMessage = res.errorBody()?.let { handleErrorResponse(it) }!!
                emit(Resource.error(data = null, message = errorMessage))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun createDefaultAbhaAddress() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val res = mainRepository.createDefaultAbhaAddress()
            if(res.code() == 202)
                res.body()?.let {
                    emit(Resource.success(data = it))
                }
            else {
                val errorMessage = res.errorBody()?.let { handleErrorResponse(it) }!!
                emit(Resource.error(data = null, message = errorMessage))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


}