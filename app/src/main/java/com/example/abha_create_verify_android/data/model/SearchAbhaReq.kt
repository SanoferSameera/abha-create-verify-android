package com.example.abha_create_verify_android.data.model

import com.google.gson.annotations.SerializedName

data class SearchAbhaReq(
    @SerializedName("healthId")
    val abhaId: String
)
