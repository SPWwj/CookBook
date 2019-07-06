package com.example.cookbook.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cookbook.R
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

import android.app.AlertDialog
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cookbook.utils.ActivityUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.toolbar.*
import androidx.core.view.MenuItemCompat
import androidx.core.view.MenuItemCompat.expandActionView
import android.app.PendingIntent.getActivity





class MainActivity : AppCompatActivity() {
    private val adapter by lazy { SearchFragment.adapter }

    private var disposable: Disposable? = null
    private lateinit var searchView :SearchView
    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar()
        setupBottomNavigation()
        if (savedInstanceState == null) {
            setupViewFragment()
        }


    }
    private fun setupViewFragment() {
        val discoverMoviesFragment = DiscoverFragment.newInstance()
        ActivityUtils.replaceFragmentInActivity(
            supportFragmentManager, discoverMoviesFragment, R.id.fragment_container
        )
    }


    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        val searchItem = menu.findItem(R.id.menu_search)
        if(searchItem != null){
            searchView = searchItem.actionView as SearchView
            val editText = searchView.findViewById<EditText>(R.id.search_src_text)
            editText.hint = resources.getString(R.string.search_string)

            searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(menuItem: MenuItem): Boolean {
                    return true
                }

                override fun onMenuItemActionCollapse(menuItem: MenuItem): Boolean {
                    supportFragmentManager.popBackStackImmediate()
                    return true
                }
            })

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if(query!!.isNotEmpty()){
                        viewModel.getSearchItems("%$query%").observe(this@MainActivity, Observer(adapter::submitList))

                    }else{
                        viewModel.getAllDishes.observe(this@MainActivity, Observer(adapter::submitList))

                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    if(newText!!.isNotEmpty()){

                        viewModel.getSearchItems("%$newText%").observe(this@MainActivity, Observer(adapter::submitList))

                    }else{
                        viewModel.getAllDishes.observe(this@MainActivity, Observer(adapter::submitList))


                    }
                    return true
                }

            })
        }




        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.itemId) {
            R.id.menu_search -> {
                ActivityUtils.replaceFragmentInActivityWithBackStack(
                    supportFragmentManager, SearchFragment.newInstance(),
                    R.id.fragment_container
                )
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(idToolbar)
        supportActionBar!!.run { setDisplayShowTitleEnabled(false) };

    }

    private fun setupBottomNavigation() {
        bnvMain.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_discover -> {
                    idToolbar.collapseActionView()
                    ActivityUtils.replaceFragmentInActivity(
                        supportFragmentManager, DiscoverFragment.newInstance(),
                        R.id.fragment_container
                    )
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_favorites -> {
                    idToolbar.collapseActionView()
                    ActivityUtils.replaceFragmentInActivity(
                        supportFragmentManager, FavoriteFragment.newInstance(),
                        R.id.fragment_container
                    )
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }
}