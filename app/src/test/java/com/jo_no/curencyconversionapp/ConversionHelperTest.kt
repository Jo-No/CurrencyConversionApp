package com.jo_no.curencyconversionapp

import com.jo_no.curencyconversionapp.models.CurrencyRate
import org.junit.Test

class ConversionHelperTest {

    private val currencyList = arrayListOf(
        CurrencyRate("GBP", 1.4),
        CurrencyRate("USD", 1.9),
        CurrencyRate("SEK", 5.2),
        CurrencyRate("HUF", 3.2),
        CurrencyRate("EUR", 1.0)
    )

    private val doubledConversionList = arrayListOf(
        CurrencyRate("GBP", 2.8),
        CurrencyRate("USD", 3.8),
        CurrencyRate("SEK", 10.4),
        CurrencyRate("HUF", 6.4),
        CurrencyRate("EUR", 2.0)
    )

    private val halvedConversionList = arrayListOf(
        CurrencyRate("GBP", 0.7),
        CurrencyRate("USD", 0.95),
        CurrencyRate("SEK", 2.6),
        CurrencyRate("HUF", 1.6),
        CurrencyRate("EUR", 0.5)
    )

    private val conversionHelper = ConversionHelper(currencyList)

    @Test
    fun whenTopValueDoubles_AllOtherValuesDouble() {
        val testList = conversionHelper.convert(CurrencyRate("GBP", 1.4), "2.8", currencyList)
        assert(testList[1].rate == doubledConversionList[1].rate)
        assert(testList[2].rate == doubledConversionList[2].rate)
        assert(testList[3].rate == doubledConversionList[3].rate)
        assert(testList[4].rate == doubledConversionList[4].rate)
    }

    @Test
    fun whenTopValueHalves_AllOtherValuesHalve() {
        val testList = conversionHelper.convert(CurrencyRate("GBP", 1.4), "0.7", currencyList)
        assert(testList[1].rate == halvedConversionList[1].rate)
        assert(testList[2].rate == halvedConversionList[2].rate)
        assert(testList[3].rate == halvedConversionList[3].rate)
        assert(testList[4].rate == halvedConversionList[4].rate)
    }
}