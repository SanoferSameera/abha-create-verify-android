package com.example.abha_create_verify.data.model

import com.google.gson.annotations.SerializedName

data class AuthInitReq(
    @SerializedName("healthId")
    val abhaId: String,
    @SerializedName("authMethod")
    val authMode: String
)
