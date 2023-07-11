package com.example.abha_create_verify.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class VerifyAadhaarOTPResp(
    @SerializedName("txnId") val transactionId: String,
    @SerializedName("photo") val photoUrl: String,
    @SerializedName("name") val fullName: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("birthdate") val birthdate: String,
    @SerializedName("careOf") val careOf: String,
    @SerializedName("house") val house: String,
    @SerializedName("street") val street: String,
    @SerializedName("villageTownCity") val villageTownCity: String,
    @SerializedName("district") val district: String,
    @SerializedName("state") val state: String,
    @SerializedName("pincode") val pincode: String
) : Serializable
