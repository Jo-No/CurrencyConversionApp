package com.jo_no.curencyconversionapp

import android.util.Log
import io.reactivex.Flowable
import retrofit2.adapter.rxjava2.Result

object RxUtils {
    val logTag = this.javaClass.simpleName

    fun <T> networkTask(observable: Flowable<Result<T>>): Flowable<T> {
        return observable
            .map {
                emitResponse(it)
            }
    }

    private fun <T> emitResponse(result: Result<T>): T? {
        return result.response()?.body()

//            Log.d("Network error", "😡 response failed")

    }
}