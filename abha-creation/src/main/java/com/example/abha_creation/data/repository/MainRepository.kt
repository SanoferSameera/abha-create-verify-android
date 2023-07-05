package com.example.abha_creation.data.repository

import com.example.abha_creation.data.api.ApiHelper
import com.example.abha_creation.data.model.CreateAbhaAddressReq
import com.example.abha_creation.data.model.GenerateAadhaarOTPReq
import com.example.abha_creation.data.model.GenerateMobileOTPReq
import com.example.abha_creation.data.model.VerifyOTPReq

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun generateAadhaarOtp(generateAadhaarOTPReq: GenerateAadhaarOTPReq) = apiHelper.generateAadhaarOtp(generateAadhaarOTPReq)

    suspend fun verifyAadhaarOtp(verifyOTPReq: VerifyOTPReq) = apiHelper.verifyAadhaarOtp(verifyOTPReq)

    suspend fun checkAndGenerateMobileOtp(generateMobileOTPReq: GenerateMobileOTPReq) = apiHelper.checkAndGenerateMobileOtp(generateMobileOTPReq)

    suspend fun verifyMobileOtp(verifyOTPReq: VerifyOTPReq) = apiHelper.verifyMobileOtp(verifyOTPReq)

    suspend fun createHealthIdByAdhaarOtp() = apiHelper.createHealthIdByAdhaarOtp()

    suspend fun createAbhaAddress(createAbhaAddressReq : CreateAbhaAddressReq) = apiHelper.createAbhaAddress(createAbhaAddressReq)

    suspend fun createDefaultAbhaAddress() = apiHelper.createDefaultAbhaAddress()
}