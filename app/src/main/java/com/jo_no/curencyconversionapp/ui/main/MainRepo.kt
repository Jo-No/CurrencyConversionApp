package com.jo_no.curencyconversionapp.ui.main

import com.jo_no.curencyconversionapp.models.CurrencyResponse
import com.jo_no.curencyconversionapp.network.RxUtils
import com.jo_no.curencyconversionapp.network.Service
import io.reactivex.Flowable

interface MainRepo {
    fun getCurrencyRates(): Flowable<CurrencyResponse>
}

class MainRepoImpl constructor(private val service: Service) : MainRepo {
    override fun getCurrencyRates(): Flowable<CurrencyResponse> {
        return RxUtils.networkTask(service.getCurrencies("EUR"))
    }
}
