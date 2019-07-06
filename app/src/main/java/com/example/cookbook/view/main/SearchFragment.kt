package com.example.cookbook.view.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cookbook.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_discover.*
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {
    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    //2
    companion object {
        val adapter by lazy { MainViewAdapter() }

        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    //3
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
//        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        val view = inflater.inflate(
            R.layout.fragment_search,
            container, false)

        return view
    }
    private fun setupAdapter() {
        rv_search.layoutManager = GridLayoutManager(activity,resources.getInteger(R.integer.span_count))
        rv_search.adapter=adapter
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val searchItem = menu.findItem(R.id.menu_search)
        if(searchItem != null){
            val searchView = searchItem.actionView as SearchView
            val editText = searchView.findViewById<EditText>(R.id.search_src_text)
            editText.hint = resources.getString(R.string.search_string)

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if(query!!.isNotEmpty()){
//                        bnvMain.selectedItemId = R.id.action_discover;
//                        viewModel.getSearchItems("%$query%").observe(this@MainActivity, Observer(adapter::submitList))

                    }else{
//                        bnvMain.selectedItemId = R.id.action_discover;
//                        viewModel.getAllDishes.observe(this@MainActivity, Observer(adapter::submitList))

                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    if(newText!!.isNotEmpty()){
                        Log.d("FragmentTest" ,"Fire")
//                        bnvMain.selectedItemId = R.id.action_discover;
//                        viewModel.getSearchItems("%$newText%").observe(this@MainActivity, Observer(adapter::submitList))

                    }else{
//                        bnvMain.selectedItemId = R.id.action_discover;
//                        viewModel.getAllDishes.observe(this@MainActivity, Observer(adapter::submitList))
//
                        Log.d("FragmentTest" ,"Empty")

                    }
                    return true
                }

            })
        }

    }




}