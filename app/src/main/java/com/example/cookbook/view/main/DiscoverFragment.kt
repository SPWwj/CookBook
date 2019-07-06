package com.example.cookbook.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cookbook.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_discover.*

class DiscoverFragment : Fragment() {
    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }
    private val adapter by lazy { MainViewAdapter() }

    //2
    companion object {

        fun newInstance(): DiscoverFragment {
            return DiscoverFragment()
        }
    }

    //3
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_discover,
            container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.loadDishes()
        setupAdapter()
        setupRefresh()
    }

    private fun setupRefresh() {
        //setting an setOnRefreshListener on the SwipeDownLayout
        pullToRefreshDiscover.setOnRefreshListener {
            //                dialog.show()
            viewModel.loadDishes()
            //adapter!!.notifyDataSetChanged()
            pullToRefreshDiscover.isRefreshing = false
        }

    }

    private fun setupAdapter() {
        rv_discover.layoutManager = GridLayoutManager(activity,resources.getInteger(R.integer.span_count))
        rv_discover.adapter=adapter
        viewModel.getAllDishes.observe(this, Observer(adapter::submitList))

    }

}