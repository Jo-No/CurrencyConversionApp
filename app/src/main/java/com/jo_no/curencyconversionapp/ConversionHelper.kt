package com.jo_no.curencyconversionapp

import com.jo_no.curencyconversionapp.models.CurrencyRate

class ConversionHelper {

    fun convert(baseCurrencyStart: CurrencyRate, baseCurrencyEdited: String, currencyList: ArrayList<CurrencyRate>): ArrayList<CurrencyRate> {
        val conversion = baseCurrencyStart.rate/baseCurrencyEdited.toDouble()
        val convertedList = arrayListOf<CurrencyRate>()
        for (i in currencyList) {
            if (i.currency!=baseCurrencyStart.currency) {
                convertedList.add(CurrencyRate(i.currency, i.rate/conversion))
            }
        }
        return currencyList
    }
}