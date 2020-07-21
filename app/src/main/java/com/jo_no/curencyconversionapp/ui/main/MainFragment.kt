package com.jo_no.curencyconversionapp.ui.main

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.jo_no.curencyconversionapp.ConversionHelper
import com.jo_no.curencyconversionapp.R
import com.jo_no.curencyconversionapp.di.DaggerAppComponent
import com.jo_no.curencyconversionapp.models.CurrencyRate
import javax.inject.Inject

// TODO Dagger
// TODO Navigation
class MainFragment : Fragment(), ClickInterface {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CurrencyAdapter

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
    }

    override fun makeConversion(
        item: CurrencyRate,
        value: String,
        listItems: ArrayList<CurrencyRate>
    ) {
        // TODO: inject helper and use without freezing
        val helper = ConversionHelper()
        val convertedList = helper.convert(item, value, listItems)
        adapter.listItems = convertedList
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.currencies.removeObservers(this)
        viewModel.dispose()
    }
}

fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, onEmitted: (T) -> Unit) {
    observe(lifecycleOwner, Observer<T> {
        onEmitted(it)
    })
}