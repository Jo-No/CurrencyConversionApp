package com.jo_no.curencyconversionapp.ui.main

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.jo_no.curencyconversionapp.ConversionHelper
import com.jo_no.curencyconversionapp.R
import com.jo_no.curencyconversionapp.di.DaggerAppComponent
import com.jo_no.curencyconversionapp.models.CurrencyRate
import com.jo_no.curencyconversionapp.observe
import javax.inject.Inject

class MainFragment : Fragment(), ClickInterface {
    val logTag = this.javaClass.simpleName

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CurrencyAdapter

    private var newValue: String = "1.0"

    val handler = Handler()
    var keepChecking = true

    val delay = 1000L
    private val runnable = object : Runnable {
        override fun run() {
            if (keepChecking) {
                viewModel.getCurrencyRates()
                handler.postDelayed(this, delay)
            }
        }
    }
    lateinit var imm: InputMethodManager

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var viewModel: MainViewModel

    private lateinit var helper: ConversionHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.main_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val component = DaggerAppComponent.builder().build()
        component.injectIntoFragment(this)

        imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        recyclerView = view.findViewById(R.id.currency_list_recycler)
        viewModel.getCurrencyRates()

        adapter = CurrencyAdapter(this)
        recyclerView.adapter = adapter

        startChecking()
    }

    override fun startChecking() {
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
        keepChecking = true
        viewModel.currencies.observe(this) {
            if (keepChecking) {
                adapter.listItems = it
                adapter.notifyDataSetChanged()
            }
        }
        handler.postDelayed(runnable, delay)
    }

    override fun stopChecking() {
        keepChecking = false
        helper = ConversionHelper(viewModel.currencies.value?: arrayListOf())
    }

    override fun makeConversion(
        item: CurrencyRate,
        value: String,
        listItems: ArrayList<CurrencyRate>
    ) {
        if (imm.isAcceptingText && !keepChecking) {
            val convertedList = helper.convert(item, value, listItems)
            try {
                adapter.listItems = convertedList
                adapter.notifyItemRangeChanged(1, listItems.size)
            } catch (e: Exception) {
                Log.e(logTag, e.localizedMessage ?: "Error updating recyclerview")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.currencies.removeObservers(this)
        viewModel.dispose()
    }
}