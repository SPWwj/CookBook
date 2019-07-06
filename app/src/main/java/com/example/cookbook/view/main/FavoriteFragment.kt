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
import kotlinx.android.synthetic.main.fragment_discover.*
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment() {
    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }
    private val adapter by lazy { MainViewAdapter() }

    //2
    companion object {

        fun newInstance(): FavoriteFragment {
            return FavoriteFragment()
        }
    }

    //3
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorite,
            container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupAdapter()
    }


    private fun setupAdapter() {
        rv_favorite.layoutManager = GridLayoutManager(activity,resources.getInteger(R.integer.span_count))
        rv_favorite.adapter=adapter
        viewModel.getFavItems(true).observe(this, Observer(adapter::submitList))

    }

}