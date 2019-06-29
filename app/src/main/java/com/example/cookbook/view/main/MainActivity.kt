package com.example.cookbook.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import com.example.cookbook.data.model.DishesModel
import com.example.cookbook.R
import com.example.cookbook.network.DishesApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.layout_loading_dialog.*


class MainActivity : AppCompatActivity() {

    private var disposable: Disposable? = null
    private lateinit var dialog :AlertDialog
    private var adapter: MainViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        val builder = AlertDialog.Builder(this@MainActivity)
//        builder.setCancelable(false) // if you want user to wait for some process to finish,
//        builder.setView(loading_layout)
//        builder.setCancelable(true)
//        dialog = builder.create()

        val model = ViewModelProviders.of(this).get(MainViewModel::class.java)
//        dialog.show()
        model.loadDishes()
        model.getAllRecipe.observe(this, Observer<List<DishesModel.Recipe>>{ recipe ->
            // update UI
            generateDataList(recipe)
//            dialog.dismiss()
        })


        //setting an setOnRefreshListener on the SwipeDownLayout
        pullToRefresh.setOnRefreshListener(object : androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
//                dialog.show()
                model.loadDishes()
                //adapter!!.notifyDataSetChanged()
                pullToRefresh.setRefreshing(false)
            }
        })
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private fun generateDataList(RecipeList: List<DishesModel.Recipe>?) {
        adapter = RecipeList?.let { MainViewAdapter(this, it) }
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@MainActivity)
        rv_main.layoutManager =layoutManager
        rv_main.adapter=adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}