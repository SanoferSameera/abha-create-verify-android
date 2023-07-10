package com.example.abha_sample.data.api

import com.example.abha_sample.data.model.CreateAbhaAddressReq
import com.example.abha_sample.data.model.GenerateAadhaarOTPReq
import com.example.abha_sample.data.model.GenerateMobileOTPReq
import com.example.abha_sample.data.model.VerifyOTPReq

class ApiHelper(private val apiService: ApiService) {

    suspend fun generateAadhaarOtp(generateAadhaarOTPReq: GenerateAadhaarOTPReq) = apiService.generateAadhaarOtp(generateAadhaarOTPReq)

    suspend fun verifyAadhaarOtp(verifyOTPReq: VerifyOTPReq) = apiService.verifyAadhaarOtp(verifyOTPReq)

    suspend fun checkAndGenerateMobileOtp(generateMobileOTPReq: GenerateMobileOTPReq) = apiService.checkAndGenerateMobileOtp(generateMobileOTPReq)

    suspend fun verifyMobileOtp(verifyOTPReq: VerifyOTPReq) = apiService.verifyMobileOtp(verifyOTPReq)

    suspend fun createHealthIdByAdhaarOtp() = apiService.createHealthIdByAdhaarOtp()

    suspend fun createAbhaAddress(createAbhaAddressReq : CreateAbhaAddressReq) = apiService.createAbhaAddress(createAbhaAddressReq)

    suspend fun createDefaultAbhaAddress() = apiService.createDefaultAbhaAddress()
}