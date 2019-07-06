package com.example.cookbook.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.cookbook.R
import com.example.cookbook.data.model.DishesModel
import com.example.cookbook.utils.Constants
import com.example.cookbook.view.main.MainViewModel
import com.google.android.material.appbar.AppBarLayout
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.wstonedev.xcollector.view.details.cast.StepAdapter
import com.wstonedev.xcollector.view.details.episode.IngredientAdapter
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.partial_details_info.*






class DetailActivity : AppCompatActivity() {

    private lateinit var stepAdapter: StepAdapter
    private lateinit var ingredientAdapter: IngredientAdapter
    lateinit var recipe : DishesModel.Recipe
    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }
    private var loaded :Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val i = intent
        val id =i.getIntExtra(Constants.DISHES_DETAIL,-1)
        viewModel.getRecipe(id).observe(this, Observer<DishesModel.Recipe>{ recipe ->
            //setup toolbar
            setupToolbar(recipe)
            this.recipe=recipe
            // update UI
            dishes_name.text =recipe.Dishes.Name


            if(recipe.Dishes.Description== "") {
                dishes_description.visibility= View.GONE
                dishes_description_title.visibility= View.GONE
            }
            if(recipe.Dishes.Tips== "") {
                dishes_tips.visibility = View.GONE
                dishes_tips_title.visibility= View.GONE

            }

            dishes_description.text=recipe.Dishes.Description
            dishes_tips.text=recipe.Dishes.Tips
            generateDataListStep(recipe.RecipeInstructions)
            generateDataListIngredient(recipe.Ingredients)

            if(!loaded) {
                val builder = Picasso.Builder(this)
                builder.downloader(OkHttp3Downloader(this))
                builder.build().load(recipe.Dishes.Image)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(cover_image)
                loaded =true
            }
        })


    }
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val checkable = menu.findItem(R.id.menu_fav)
        checkable.isChecked = recipe.Dishes.Favorite
        if(checkable.isChecked) {
            checkable.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_red_24dp)
        }
        else{
            checkable.icon= ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_black_24dp)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_fav -> {
                recipe.Dishes.Favorite = !item.isChecked

                viewModel.updateDishes(recipe.Dishes)
                item.isChecked = recipe.Dishes.Favorite

                return true
            }
            else -> return false
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail,menu)


        return super.onCreateOptionsMenu(menu)
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private fun generateDataListStep(dataList: List<DishesModel.RecipeInstruction>) {
        stepAdapter = StepAdapter(this, dataList)
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@DetailActivity)
        rv_step.layoutManager =layoutManager
        rv_step.adapter=stepAdapter
    }
    /*Method to generate List of data using RecyclerView with custom adapter*/
    private fun generateDataListIngredient(dataList: List<DishesModel.Ingredient>) {
        ingredientAdapter = IngredientAdapter(this, dataList)
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@DetailActivity)
        rv_ingredient.layoutManager =layoutManager
        rv_ingredient.adapter=ingredientAdapter
    }

    private fun setupToolbar(recipe: DishesModel.Recipe) {
        val toolbar = toolbar
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            handleCollapsedToolbarTitle(recipe)
        }
    }

    private fun handleCollapsedToolbarTitle(recipe:DishesModel.Recipe) {
        appbar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                // verify if the toolbar is completely collapsed and set the movie Name as the title
                if (scrollRange + verticalOffset == 0) {
                    collapsing_toolbar.title = recipe.Dishes.Name
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

