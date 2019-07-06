package com.example.cookbook.data

import androidx.lifecycle.LiveData
import androidx.paging.Config
import androidx.paging.toLiveData

import com.example.cookbook.data.dao.DishesDao
import com.example.cookbook.data.model.DishesModel

class RecipeRepo(private val dishesDao: DishesDao) {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    //val getAllDishes: LiveData<List<DishesModel.Dishes>> = dishesDao.getAllDishes()
    //val getAllRecipe: LiveData<List<DishesModel.Recipe>> = dishesDao.getAllRecipe()
    //val getAllRecipe: LiveData<List<DishesModel.Recipe>> = dishesDao.getAllRecipe()
    val getAllRecipe =
        dishesDao.getAllRecipe().toLiveData(pageSize = 50)


    fun getRecipe(id: Int): LiveData<DishesModel.Recipe> = dishesDao.getRecipe(id)
//    val getAllDishes: LiveData<List<DishesModel.Dishes>> = dishesDao.getAllDishes()
    val getAllDishes=  dishesDao.getAllDishes().toLiveData(
    Config(
        /**
         * A good page size is a value that fills at least a screen worth of content on a large
         * device so the User is unlikely to see a null item.
         * You can play with this constant to observe the paging behavior.
         * <p>
         * It's possible to vary this with list device size, but often unnecessary, unless a
         * user scrolling on a large device is expected to scroll through items more quickly
         * than a small device, such as when the large device uses a grid layout of items.
         */
        pageSize = 10,

        /**
         * If placeholders are enabled, PagedList will report the full size but some items might
         * be null in onBind method (PagedListAdapter triggers a rebind when data is loaded).
         * <p>
         * If placeholders are disabled, onBind will never receive null but as more pages are
         * loaded, the scrollbars will jitter as new pages are loaded. You should probably
         * disable scrollbars if you disable placeholders.
         */
        enablePlaceholders = true,

        /**
         * Maximum number of items a PagedList should hold in memory at once.
         * <p>
         * This number triggers the PagedList to start dropping distant pages as more are loaded.
         */
        maxSize = 100)
)


    fun getSearchDishes(searchStr:String)=  dishesDao.getSearchDishes(searchStr).toLiveData(
        Config(
            pageSize = 10,
            enablePlaceholders = true,
            maxSize = 100)
    )
    fun getFavDishes(isFav:Boolean)=  dishesDao.getAllFav(isFav).toLiveData(
        Config(
            pageSize = 10,
            enablePlaceholders = true,
            maxSize = 100)
    )

    suspend fun updateDishes(dishes:DishesModel.Dishes) = dishesDao.updateDishes(dishes)

    fun getSteps (id:Int): List<DishesModel.RecipeInstruction> = dishesDao._getSteps(id)
    fun getIngredients(id:Int) :List<DishesModel.Ingredient> = dishesDao._getIngredients(id)

    // The suspend modifier tells the compiler that this must be called from a
    // coroutine or another suspend function.
    // This ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
//    suspend fun insertAll(dishesList: List<DishesModel.Dishes>) {
//        dishesDao.insertAllDishes(dishesList)
//    }
    suspend fun insertAll(recipeList: List<DishesModel.Recipe>) {
        dishesDao.insertAllRecipes(recipeList)
    }
//    suspend fun insert(dishes: DishesModel.Dishes) {
//        dishesDao.insertDishes(dishes)
//    }
}