package com.example.cookbook.view.main

import android.app.AlertDialog
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.example.cookbook.R
import com.example.cookbook.data.RecipeRepo
import com.example.cookbook.data.dao.DishesRoomDatabase
import com.example.cookbook.data.model.DishesModel
import com.example.cookbook.network.DishesApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecipeRepo
    val getAllRecipe: LiveData<List<DishesModel.Recipe>>

    init {
        val dishesDao = DishesRoomDatabase.getDatabase(application, viewModelScope).dishesDao()
        repository = RecipeRepo(dishesDao)
        getAllRecipe = repository.getAllRecipe
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertAll(dishesList: List<DishesModel.Dishes>) = viewModelScope.launch {
        repository.insertAll(dishesList)
    }
    fun insert(dishes: DishesModel.Dishes) = viewModelScope.launch {
        repository.insert(dishes)
    }

    fun getSteps(id: Int):List<DishesModel.Step> {
       return repository.getSteps(id)
    }
    fun getIngredients(id: Int): List<DishesModel.Ingredient>{
       return repository.getIngredients(id)
    }
//    fun getFullDetail(dishesList: List<DishesModel.Dishes>): Job = viewModelScope.launch  {
//        for (i in dishesList.indices){
//            val ingredients = getIngredients(dishesList[i].dishesId!!)
//             val steps = getSteps(dishesList[i].dishesId!!)
//
//            dishesList[i].steps=getSteps(dishesList[i].dishesId!!)
//            dishesList[i].ingredients=ingredients
//         }
//        dishesList
//    }


    private var disposable: Disposable? = null
    private val dishesApiService by lazy {
        DishesApiService.create()
    }

    fun loadDishes() {
        disposable = dishesApiService.getDishes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    //dialog.dismiss()
                    //insertAll(result.mList)
//                    allDishes.value=result.mList
                    insertAll(result.mList)

                },
                {
                    //                    dialog.dismiss()
//                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                }
            )
    }



}

