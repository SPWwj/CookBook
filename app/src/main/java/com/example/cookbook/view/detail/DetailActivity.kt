package com.example.cookbook.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

import com.example.cookbook.R
import com.example.cookbook.data.model.DishesModel
import com.example.cookbook.utils.Constants
import com.example.cookbook.view.main.MainViewAdapter
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.wstonedev.xcollector.view.details.cast.StepAdapter
import com.wstonedev.xcollector.view.details.episode.IngredientAdapter
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.partial_details_info.*

class DetailActivity : AppCompatActivity() {

   private lateinit var recipe : DishesModel.Recipe
    private var stepAdapter: StepAdapter? = null
    private var ingredientAdapter: IngredientAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val i = intent
        recipe =i.getSerializableExtra(Constants.DISHES_DETAIL) as DishesModel.Recipe

        //setup toolbar
        setupToolbar()

        //disheName.text = dishes.name
        val builder = Picasso.Builder(this)
        builder.downloader(OkHttp3Downloader(this))
        builder.build().load(recipe.dishes.name)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(coverImage)

        dishes_discription.text=recipe.dishes.description
        dishes_remark.text=recipe.dishes.remark
        generateDataListStep(recipe.steps)
        generateDataListIngredient(recipe.ingredients)
        //Toast.makeText(this,dishes.ingredients!!.firstOrNull()!!.name.toString() , Toast.LENGTH_LONG).show()

    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private fun generateDataListStep(dataList: List<DishesModel.Step>?) {
        stepAdapter = dataList?.let { StepAdapter(this, it) }
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@DetailActivity)
        rv_step.layoutManager =layoutManager
        rv_step.adapter=stepAdapter
    }
    /*Method to generate List of data using RecyclerView with custom adapter*/
    private fun generateDataListIngredient(dataList: List<DishesModel.Ingredient>?) {
        ingredientAdapter = dataList?.let { IngredientAdapter(this, it) }
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@DetailActivity)
        rv_ingredient.layoutManager =layoutManager
        rv_ingredient.adapter=ingredientAdapter
    }

    private fun setupToolbar() {
        val toolbar = toolbar
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            handleCollapsedToolbarTitle()
        }
    }

    private fun handleCollapsedToolbarTitle() {
        appbar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                // verify if the toolbar is completely collapsed and set the movie name as the title
                if (scrollRange + verticalOffset == 0) {
                    collapsing_toolbar.title = recipe.dishes.name
                    isShow = true
                } else if (isShow) {
                    // display an empty string when toolbar is expanded
                    collapsing_toolbar.title = " "
                    isShow = false
                }
            }
        })
    }

}
