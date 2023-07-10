package com.example.abha_sample.data.model

import com.google.gson.annotations.SerializedName

data class GenerateAadhaarOTPResp(
    @SerializedName("mobileNumber")
    val mobileNumber: String
)
