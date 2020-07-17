package com.jo_no.curencyconversionapp.ui.main

import com.jo_no.curencyconversionapp.models.CurrencyRate

interface ClickInterface {
    fun startChecking()
    fun stopChecking()
    fun makeConversion(
        item: CurrencyRate,
        value: String,
        listItems: ArrayList<CurrencyRate>
    )
}
