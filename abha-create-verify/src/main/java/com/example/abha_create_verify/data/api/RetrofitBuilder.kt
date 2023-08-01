package com.example.abha_create_verify.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.Interceptor
import java.util.concurrent.TimeUnit

    object RetrofitBuilder {
        var BASE_URL = ""
        private const val HEADER_AUTHORIZATION = "Authorization"
        var AUTH_TOKEN = ""

        private var retrofit: Retrofit? = null

        private fun createRetrofitInstance(): Retrofit {
            val okHttpClient = OkHttpClient.Builder().apply {
                addInterceptor(
                    Interceptor { chain ->
                        val builder = chain.request().newBuilder()
                        builder.header(HEADER_AUTHORIZATION, "Bearer $AUTH_TOKEN")
                        readTimeout(10, TimeUnit.SECONDS)
                        connectTimeout(5, TimeUnit.SECONDS)
                        return@Interceptor chain.proceed(builder.build())
                    }
                )

            }.build()

            retrofit =  Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit!!
        }

        // Custom getter for apiService to handle dynamic BASE_URL
        val apiService: ApiService
            get() {
                if (retrofit == null || BASE_URL != retrofit!!.baseUrl().toString()) {
                    retrofit = createRetrofitInstance()
                }
                return retrofit!!.create(ApiService::class.java)
            }
}