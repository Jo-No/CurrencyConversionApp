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
        recyclerView.adapter = adapter

        val handler = Handler()
        val delay = 1000L
        var count = 0
        handler.postDelayed(object : Runnable {
            override fun run() {
                viewModel.getCurrencyRates()
                count++
                handler.postDelayed(this, delay)
            }
        }, delay)

        viewModel.currencies.observe(this) {
            adapter.listItems = it
            adapter.notifyDataSetChanged()
        }
    }
}

fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, onEmitted: (T) -> Unit) {
    observe(lifecycleOwner, Observer<T> {
        onEmitted(it)
    })
}