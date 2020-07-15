package com.jo_no.curencyconversionapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val repo: MainRepo = MainRepoImpl()
) : ViewModel() {

    val logTag = this.javaClass.simpleName

    private val disposable = CompositeDisposable()

    private val _eurRate = MutableLiveData<CurrencyRate>()
    val eurRate: LiveData<CurrencyRate> = _eurRate

    private val _cadRate = MutableLiveData<CurrencyRate>()
    val cadRate: LiveData<CurrencyRate> = _cadRate

    private val _sekRate = MutableLiveData<CurrencyRate>()
    val sekRate: LiveData<CurrencyRate> = _sekRate

    private val _usdRate = MutableLiveData<CurrencyRate>()
    val usdRate: LiveData<CurrencyRate> = _usdRate

    fun getCurrencyRates() {
        disposable.add(
            repo.getCurrencyRates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response ->
                        _eurRate.value = CurrencyRate("EUR", response.rates.eur)
                        _usdRate.value = CurrencyRate("USD", response.rates.usd)
                        _sekRate.value = CurrencyRate("SEK", response.rates.sek)
                        _cadRate.value = CurrencyRate("CAD", response.rates.cad)
                        Log.d(logTag, "OnNext!")
                    },
                    {
                        Log.e(logTag, it.localizedMessage)
                    },
                    {
                        Log.d(logTag, "Complete!")
                    })
        )
    }
}