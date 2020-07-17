package com.jo_no.curencyconversionapp.network

import com.jo_no.curencyconversionapp.models.CurrencyResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.adapter.rxjava2.Result

interface Service {

    @GET("api/android/latest")
    fun getCurrencies(
        @Query("base") base: String
    ) : Flowable<Result<CurrencyResponse>>
}
