package com.jo_no.curencyconversionapp

import java.math.RoundingMode

fun Double.toThreeDecimalPlaces(): Double {
    return this.toBigDecimal().setScale(3, RoundingMode.HALF_EVEN).toDouble()
}