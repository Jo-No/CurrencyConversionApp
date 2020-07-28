package com.jo_no.curencyconversionapp

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.math.RoundingMode

fun Double.toThreeDecimalPlaces(): Double {
    return this.toBigDecimal().setScale(3, RoundingMode.HALF_EVEN).toDouble()
}

fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, onEmitted: (T) -> Unit) {
    observe(lifecycleOwner, Observer<T> {
        onEmitted(it)
    })
}