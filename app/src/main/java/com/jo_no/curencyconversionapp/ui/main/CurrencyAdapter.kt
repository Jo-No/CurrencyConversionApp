package com.jo_no.curencyconversionapp.ui.main

import android.R.attr.data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jo_no.curencyconversionapp.R
import java.util.*
import kotlin.collections.ArrayList


class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    var listItems: ArrayList<CurrencyRate> = arrayListOf()
    lateinit var stopCheckingCallback: () -> Unit
    lateinit var startCheckingCallback: () -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_layout, parent, false)
        return CurrencyViewHolder(view)
    }

    override fun getItemCount(): Int = listItems.size

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(listItems[position], position)
    }

    inner class CurrencyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val flag = view.findViewById<TextView>(R.id.flag)
        private val shortTitle = view.findViewById<TextView>(R.id.currency_short_title)
        private val longTitle = view.findViewById<TextView>(R.id.currency_long_title)
        private val currencyValue = view.findViewById<EditText>(R.id.currency_edit)

        fun bind(item: CurrencyRate, position: Int) {
            shortTitle.text = item.currency
            longTitle.text = getLongTitle(item.currency)
            currencyValue.text.clear()
            currencyValue.text.insert(0, item.rate.toString())
            flag.text = getFlagImage(item.currency)

            currencyValue.setOnClickListener {
                stopCheckingCallback.invoke()
            }
            currencyValue.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    moveToTop(adapterPosition)
                }
            }
            currencyValue.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    startCheckingCallback.invoke()
                }
                true
            }
        }
    }

    fun moveToTop(startPosition: Int){
        if (startPosition != 0) {
            for (i in startPosition downTo 1) {
                Collections.swap(listItems, i, i - 1)
            }
        }
        notifyItemMoved(startPosition, 0)
    }

    fun getFlagImage(short: String): String {
        return when (short) {
            "EUR" -> "🇪🇺"
            "AUD" -> "🇦🇺"
            "BGN" -> "🇧🇬"
            "BRL" -> "🇧🇷"
            "CAD" -> "🇨🇦"
            "CHF" -> "🇨🇭"
            "CNY" -> "🇨🇳"
            "CZK" -> "🇨🇿"
            "DKK" -> "🇩🇰"
            "GBP" -> "🇬🇧"
            "HKD" -> "🇭🇰"
            "HRK" -> "🇭🇷"
            "HUF" -> "🇭🇺"
            "IDR" -> "🇮🇩"
            "ILS" -> "🇮🇱"
            "INR" -> "🇮🇳"
            "ISK" -> "🇮🇸"
            "JPY" -> "🇯🇵"
            "KRW" -> "🇰🇷"
            "MXN" -> "🇲🇽"
            "MYR" -> "🇲🇾"
            "NOK" -> "🇳🇴"
            "NZD" -> "🇳🇿"
            "PHP" -> "🇵🇭"
            "PLN" -> "🇵🇱"
            "RON" -> "🇷🇴"
            "RUB" -> "🇷🇺"
            "SEK" -> "🇸🇪"
            "SGD" -> "🇸🇬"
            "THB" -> "🇹🇭"
            "USD" -> "🇺🇸"
            "ZAR" -> "🇿🇦"
            else -> "😥"
        }
    }

    fun getLongTitle(short: String): String {
        return when (short) {
            "AUD" -> "Australian Dollar"
            "BGN" -> "Bulgarian Lev"
            "BRL" -> "Brazilian Real"
            "CAD" -> "Canadian Dollar"
            "CHF" -> "Swiss Franc"
            "CNY" -> "Chinese Yuan"
            "CZK" -> "Czech Koruna"
            "DKK" -> "Danish Krone"
            "GBP" -> "British Pound"
            "HKD" -> "Hong Kong Dollar"
            "HRK" -> "Croatian Kuna"
            "HUF" -> "Hungarian Forint"
            "IDR" -> "Indonesian Rupiah"
            "ILS" -> "Israeli New Shekel"
            "INR" -> "Indian Rupee"
            "ISK" -> "Icelandic Króna"
            "JPY" -> "Japanese Yen"
            "KRW" -> "South Korean Won"
            "MXN" -> "Mexican Peso"
            "MYR" -> "Malaysian Ringgit"
            "NOK" -> "Norwegian Krone"
            "NZD" -> "New Zealand Dollar"
            "PHP" -> "Philippine Peso"
            "PLN" -> "Poland Złoty"
            "RON" -> "Romanian Leu"
            "RUB" -> "Russian Ruble"
            "SEK" -> "Swedish Krona"
            "SGD" -> "Singapore Dollar"
            "THB" -> "Thai Baht"
            "USD" -> "US Dollar"
            "ZAR" -> "South African Rand"
            else -> "Unknown currency 😥"
        }
    }
}