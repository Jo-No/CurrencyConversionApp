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
                            CurrencyRate(it.key, it.value.toFloat())
                        } as ArrayList<CurrencyRate>
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

    fun dispose() {
        disposable.dispose()
    }
}