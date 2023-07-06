package com.example.abha_create_verify_android.data.model

data class ErrorResponse(
    val code: String,
    val message: String,
    val details: List<ErrorDetail>
)

data class ErrorDetail(
    val message: String,
    val code: String,
    val attribute: Attribute
)

data class Attribute(
    val key: String,
    val value: String
)