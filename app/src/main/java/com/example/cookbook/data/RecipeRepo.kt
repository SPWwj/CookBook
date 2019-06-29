package com.example.cookbook.data

import androidx.lifecycle.LiveData
import com.example.cookbook.data.dao.DishesDao
import com.example.cookbook.data.model.DishesModel

class RecipeRepo(private val dishesDao: DishesDao) {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    //val getAllDishes: LiveData<List<DishesModel.Dishes>> = dishesDao.getAllDishes()
    val getAllRecipe: LiveData<List<DishesModel.Recipe>> = dishesDao.getAllRecipe()
    fun getSteps (id:Int): List<DishesModel.Step> = dishesDao._getSteps(id)
    fun getIngredients(id:Int) :List<DishesModel.Ingredient> = dishesDao._getIngredients(id)

    // The suspend modifier tells the compiler that this must be called from a
    // coroutine or another suspend function.
    // This ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    suspend fun insertAll(dishesList: List<DishesModel.Dishes>) {
        dishesDao.insertAllDishes(dishesList)
    }
    suspend fun insert(dishes: DishesModel.Dishes) {
        dishesDao.insertDishes(dishes)
    }
}