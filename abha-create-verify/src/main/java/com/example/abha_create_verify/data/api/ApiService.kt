package com.example.abha_create_verify.data.api

import com.example.abha_create_verify.data.model.AuthInitReq
import com.example.abha_create_verify.data.model.ConfirmOtpReq
import com.example.abha_create_verify.data.model.VerifyAbhaPatient
import com.example.abha_create_verify.data.model.CreateABHAResp
import com.example.abha_create_verify.data.model.CreateAbhaAddressReq
import com.example.abha_create_verify.data.model.CreateDefaultAbhaAddressResp
import com.example.abha_create_verify.data.model.GenerateAadhaarOTPReq
import com.example.abha_create_verify.data.model.GenerateAadhaarOTPResp
import com.example.abha_create_verify.data.model.GenerateMobileOTPReq
import com.example.abha_create_verify.data.model.GenerateMobileOTPResp
import com.example.abha_create_verify.data.model.SearchAbhaReq
import com.example.abha_create_verify.data.model.SearchAbhaResp
import com.example.abha_create_verify.data.model.VerifyAadhaarOTPResp
import com.example.abha_create_verify.data.model.VerifyOTPReq
import com.example.abha_create_verify.utils.PatientDemographics
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

    @POST("hiprovider/v2/search/searchHealthIdToLogin")
    suspend fun searchAbhaId(@Body requestBody: SearchAbhaReq): Response<SearchAbhaResp>

    @POST("hiprovider/v2/auth/init")
    suspend fun authInit(@Body requestBody: AuthInitReq): Response<Unit>

    @POST("hiprovider/v2/hip/confirmOTP")
    suspend fun confirmOtp(@Body requestBody: ConfirmOtpReq): Response<VerifyAbhaPatient>

    @POST("hiprovider/v0.5/hip/ndhm-demographics")
    suspend fun addPatientDemographics(@Body requestBody: PatientDemographics): Response<Unit>

}