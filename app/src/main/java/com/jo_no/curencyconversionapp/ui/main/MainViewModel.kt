package com.jo_no.curencyconversionapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jo_no.curencyconversionapp.models.CurrencyRate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val repo: MainRepo = MainRepoImpl()
) : ViewModel() {

    val logTag = this.javaClass.simpleName

    private val disposable = CompositeDisposable()

    private val _currencies = MutableLiveData<ArrayList<CurrencyRate>>()
    val currencies: LiveData<ArrayList<CurrencyRate>> = _currencies


    fun getCurrencyRates() {
        disposable.add(
            repo.getCurrencyRates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { res ->
                        _currencies.value = res.rates.map {
                            CurrencyRate(
                                it.key,
                                it.value.toDouble()
                            )
                        } as ArrayList<CurrencyRate>
                    },
                    {
                        Log.e(logTag, it.localizedMessage)
                    },
                    {
                        _currencies.value?.add(0, CurrencyRate("EUR", 1.0))
                    })
        )
    }

    fun dispose() {
        disposable.dispose()
    }
}