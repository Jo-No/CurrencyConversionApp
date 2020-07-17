package com.jo_no.curencyconversionapp.ui.main

import com.jo_no.curencyconversionapp.models.CurrencyResponse
import com.jo_no.curencyconversionapp.network.Network
import com.jo_no.curencyconversionapp.network.RxUtils
import com.jo_no.curencyconversionapp.network.Service
import io.reactivex.Flowable

interface MainRepo {
    fun getCurrencyRates(): Flowable<CurrencyResponse>
}

class MainRepoImpl(
    private val service: Service = Network.createService(
        Service::class.java)
): MainRepo {
    override fun getCurrencyRates(): Flowable<CurrencyResponse> {
        return RxUtils.networkTask(service.getCurrencies("EUR"))
    }
}
