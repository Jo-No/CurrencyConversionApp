package com.jo_no.curencyconversionapp

import com.jo_no.curencyconversionapp.models.CurrencyRate

class ConversionHelper(private val listOfPausedRates: ArrayList<CurrencyRate> = arrayListOf()) {

    fun convert(baseCurrency: CurrencyRate, valueEntered: String, currencyList: ArrayList<CurrencyRate>): ArrayList<CurrencyRate> {
        val savedCurrencyRate = listOfPausedRates.find {
            it.currency == baseCurrency.currency
        }

        val conversionFactor = valueEntered.toDouble()/savedCurrencyRate!!.rate

        val newList = currencyList.map { currencyRate ->
            if (currencyRate.currency!=baseCurrency.currency) {
                val item = listOfPausedRates.find { fromPaused ->
                    currencyRate.currency == fromPaused.currency
                }
                item?.let {
                    CurrencyRate(it.currency, item.rate * conversionFactor)
                }
            } else {
                currencyRate
            }
        }
        return newList as ArrayList<CurrencyRate>
    }
}
