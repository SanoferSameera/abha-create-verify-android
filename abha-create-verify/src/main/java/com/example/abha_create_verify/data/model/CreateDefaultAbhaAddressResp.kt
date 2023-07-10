package com.example.abha_create_verify.data.model

import com.google.gson.annotations.SerializedName

data class CreateDefaultAbhaAddressResp(
    @SerializedName("healthId") val abhaAddress: String,
)
