package com.example.abha_creation.data.model

import com.google.gson.annotations.SerializedName

data class CreateABHAResp(
    @SerializedName("healthIdNumber") val healthIdNumber: String,
    @SerializedName("mobile") val mobile: String
)
