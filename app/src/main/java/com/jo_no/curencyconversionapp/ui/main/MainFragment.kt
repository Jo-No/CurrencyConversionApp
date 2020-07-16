package com.jo_no.curencyconversionapp.ui.main

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.jo_no.curencyconversionapp.R


class MainFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CurrencyAdapter

    val handler = Handler()
    var keepChecking = true

    val delay = 1000L
    val runnable = object : Runnable {
        override fun run() {
            if (keepChecking) {
                viewModel.getCurrencyRates()
                handler.postDelayed(this, delay)
            }
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.main_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        recyclerView = view.findViewById(R.id.currency_list_recycler)
        viewModel.getCurrencyRates()

        adapter = CurrencyAdapter()
        adapter.stopCheckingCallback = ::stopChecking
        recyclerView.adapter = adapter

        startChecking()

        viewModel.currencies.observe(this) {
            if (keepChecking) {
                adapter.listItems = it
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun startChecking() {
        handler.postDelayed(runnable, delay)
    }

    private fun stopChecking() {
        handler.removeCallbacks(runnable)
        keepChecking = false
    }
}

fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, onEmitted: (T) -> Unit) {
    observe(lifecycleOwner, Observer<T> {
        onEmitted(it)
    })
}