package com.jo_no.curencyconversionapp

import com.jo_no.curencyconversionapp.models.CurrencyRate

class ConversionHelper {

    fun convert(baseCurrencyStart: CurrencyRate, baseCurrencyEdited: String, currencyList: ArrayList<CurrencyRate>): ArrayList<CurrencyRate> {
        val conversion = baseCurrencyStart.rate.toDouble()/baseCurrencyEdited.toDouble()
        currencyList.map {
            it.rate.toDouble()/conversion
        }
        return currencyList
    }
}