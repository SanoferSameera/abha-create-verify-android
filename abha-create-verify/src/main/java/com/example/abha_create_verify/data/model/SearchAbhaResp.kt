package com.example.abha_create_verify.data.model

import com.google.gson.annotations.SerializedName

data class SearchAbhaResp(
    @SerializedName("authMethods")
    val authModes: List<String>,
    @SerializedName("status")
    val status: String
)
