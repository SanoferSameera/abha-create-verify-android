package com.example.abha_sample.data.model

import com.google.gson.annotations.SerializedName

data class GenerateMobileOTPResp(
    @SerializedName("mobileLinked")
    val mobileLinked: String
)
