package com.jo_no.curencyconversionapp.ui.main

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jo_no.curencyconversionapp.R
import com.jo_no.curencyconversionapp.models.CurrencyRate
import com.jo_no.curencyconversionapp.toThreeDecimalPlaces
import java.util.*

class CurrencyAdapter(val clickInterface: ClickInterface) :
    RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    var listItems: ArrayList<CurrencyRate> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_layout, parent, false)
        return CurrencyViewHolder(view)
    }

    override fun getItemCount(): Int = listItems.size

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(listItems[position])
    }

    inner class CurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val flag = view.findViewById<TextView>(R.id.flag)
        private val shortTitle = view.findViewById<TextView>(R.id.currency_short_title)
        private val longTitle = view.findViewById<TextView>(R.id.currency_long_title)
        private val currencyValue = view.findViewById<EditText>(R.id.currency_edit)

        fun bind(item: CurrencyRate) {
            shortTitle.text = item.currency
            longTitle.text = getLongTitle(item.currency)
            currencyValue.text = Editable.Factory.getInstance().newEditable(item.rate.toThreeDecimalPlaces().toString())
            flag.text = getFlagImage(item.currency)

            currencyValue.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    clickInterface.stopChecking()
                    v.performClick()
                }
            }
            currencyValue.setOnClickListener {
                if (adapterPosition>0) {
                    moveToTop(adapterPosition)
                }
            }
            if (currencyValue.text.isNotEmpty()) {
                (currencyValue as EditText).addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

                    override fun afterTextChanged(s: Editable) {
                        if (s.isNotEmpty()) {
                            clickInterface.makeConversion(item, s.toString(), listItems)
                        Log.d("JOSEPHINE", "🦄 converting...")
                        }
                    }
                })
            }
        }
    }

    fun moveToTop(startPosition: Int) {
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
            "EUR" -> "Euro"
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