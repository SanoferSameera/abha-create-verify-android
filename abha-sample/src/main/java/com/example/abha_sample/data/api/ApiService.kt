package com.example.abha_sample.data.api

import com.example.abha_sample.data.model.CreateABHAResp
import com.example.abha_sample.data.model.CreateAbhaAddressReq
import com.example.abha_sample.data.model.CreateDefaultAbhaAddressResp
import com.example.abha_sample.data.model.GenerateAadhaarOTPReq
import com.example.abha_sample.data.model.GenerateAadhaarOTPResp
import com.example.abha_sample.data.model.GenerateMobileOTPReq
import com.example.abha_sample.data.model.GenerateMobileOTPResp
import com.example.abha_sample.data.model.VerifyAadhaarOTPResp
import com.example.abha_sample.data.model.VerifyOTPReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("hiprovider/v2/registration/aadhaar/generateOtp")
    suspend fun generateAadhaarOtp(@Body requestBody: GenerateAadhaarOTPReq): Response<GenerateAadhaarOTPResp>

    @POST("hiprovider/v2/registration/aadhaar/verifyOTP")
    suspend fun verifyAadhaarOtp(@Body requestBody: VerifyOTPReq): Response<VerifyAadhaarOTPResp>

    @POST("hiprovider/v2/registration/aadhaar/checkAndGenerateMobileOTP")
    suspend fun checkAndGenerateMobileOtp(@Body requestBody: GenerateMobileOTPReq): Response<GenerateMobileOTPResp>

    @POST("hiprovider/v2/registration/aadhaar/verifyMobileOTP")
    suspend fun verifyMobileOtp(@Body requestBody: VerifyOTPReq) : Response<Unit>

    @POST("hiprovider/v2/registration/aadhaar/createHealthIdByAdhaar")
    suspend fun createHealthIdByAdhaarOtp(): Response<CreateABHAResp>

    @POST("hiprovider/v2/account/phr-linked")
    suspend fun createAbhaAddress(@Body requestBody: CreateAbhaAddressReq): Response<Unit>

    @GET("hiprovider/v1/account/update/phr-address")
    suspend fun createDefaultAbhaAddress(): Response<CreateDefaultAbhaAddressResp>

}