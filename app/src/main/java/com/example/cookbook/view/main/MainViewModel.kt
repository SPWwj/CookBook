package com.example.cookbook.view.main

import android.app.Application
import androidx.lifecycle.*
import com.example.cookbook.data.RecipeRepo
import com.example.cookbook.data.dao.DishesRoomDatabase
import com.example.cookbook.data.model.DishesModel
import com.example.cookbook.network.DishesApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecipeRepo
    var getAllDishes: LiveData<PagedList<DishesModel.Dishes>>
    var isLoading = MutableLiveData<Boolean>()

    init {
        val dishesDao = DishesRoomDatabase.getDatabase(application, viewModelScope).dishesDao()
        repository = RecipeRepo(dishesDao)
        getAllDishes = repository.getAllDishes
    }
    fun getSearchItems(searchStr:String): LiveData<PagedList<DishesModel.Dishes>>{
        return repository.getSearchDishes(searchStr)
    }

    fun getFavItems(isFav:Boolean): LiveData<PagedList<DishesModel.Dishes>>{
        return repository.getFavDishes(isFav)
    }


    fun updateDishes(dishes: DishesModel.Dishes) = viewModelScope.launch {
        repository.updateDishes(dishes)
    }

    fun insertAll(recipeList: List<DishesModel.Recipe>) = viewModelScope.launch {
        repository.insertAll(recipeList)
    }


    fun getRecipe(id: Int):LiveData<DishesModel.Recipe> {
        return repository.getRecipe(id)
    }
    fun getSteps(id: Int):List<DishesModel.RecipeInstruction> {
       return repository.getSteps(id)
    }
    fun getIngredients(id: Int): List<DishesModel.Ingredient>{
       return repository.getIngredients(id)
    }

    private var disposable: Disposable? = null
    private val dishesApiService by lazy {
        DishesApiService.create()
    }

    fun loadDishes() {
        isLoading.value=true
        disposable = dishesApiService.getDishes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    insertAll(result.mList)
                    isLoading.setValue(false);
                },
                {
                    isLoading.setValue(false);

                }
            )
    }



}

