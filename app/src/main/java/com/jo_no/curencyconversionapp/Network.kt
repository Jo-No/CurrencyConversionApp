package com.jo_no.curencyconversionapp

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

object Network {

    private const val base = "https://hiring.revolut.codes/"
    fun <S> createService(serviceClass: Class<S>, baseUrl: String = base): S {

        val client = OkHttpClient.Builder()
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

        return retrofit.create(serviceClass)
    }
}
