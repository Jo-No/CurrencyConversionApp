package com.jo_no.curencyconversionapp

import android.util.Log
import com.jo_no.curencyconversionapp.models.CurrencyRate

class ConversionHelper(private val listOfPausedRates: ArrayList<CurrencyRate> = arrayListOf()) {

    fun convert(baseCurrency: CurrencyRate, valueEntered: String, currencyList: ArrayList<CurrencyRate>): ArrayList<CurrencyRate> {
        Log.d("JOSEPHINE", "🎾️")

        val savedCurrencyRate = listOfPausedRates.find {
            it.currency == baseCurrency.currency
        }

        val conversionFactor = valueEntered.toDouble()/savedCurrencyRate!!.rate

        val x = currencyList.map {
            if (it.currency!=baseCurrency.currency) {
                val y = listOfPausedRates.find { fromPaused ->
                    it.currency == fromPaused.currency
                }
                y?.let {
                    CurrencyRate(it.currency, y.rate * conversionFactor)
                }
            } else {
                it
            }
        }

        return x as ArrayList<CurrencyRate>
    }
}
