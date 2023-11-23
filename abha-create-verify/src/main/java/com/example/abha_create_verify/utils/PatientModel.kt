package com.example.abha_create_verify.utils

import com.google.gson.annotations.SerializedName
data class Patient(
    @SerializedName("abhaAddress")
    var abhaAddress: String? = "",
    @SerializedName("abhaNumber")
    var abhaNumber: String? = "",
    @SerializedName("address")
    var address: String? = "",
    @SerializedName("dateOfBirth")
    var dateOfBirth: String? = "",
    @SerializedName("firstName")
    var firstName: String? = "",
    @SerializedName("gender")
    var gender: String? = "",
    @SerializedName("lastName")
    var lastName: String? = "",
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("phoneNumber")
    var phoneNumber: String? = "",
    @SerializedName("villageTownCity")
    var villageTownCity: String? = "",
    @SerializedName("aadhaarNumber")
    var aadhaarNumber: String? = "",
    @SerializedName("postalCode")
    var postalCode: String? = ""
)

data class PatientDemographics(
    @SerializedName("healthId")
    var abhaAddress: String? = "",
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("gender")
    var gender: String? = "",
    @SerializedName("dateOfBirth")
    var dateOfBirth: String? = "",
    @SerializedName("phoneNumber")
    var phoneNumber: String? = ""
)