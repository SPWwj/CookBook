package com.example.cookbook.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cookbook.data.model.DishesModel








@Dao
abstract class DishesDao {
    suspend fun insertDishes(dishes: DishesModel.Dishes) {
        val steps = dishes.steps
        val ingredients = dishes.ingredients
        for (i in steps!!.indices) {
            steps.get(i).dishesId=dishes.dishesId!!
        }
        for (i in ingredients!!.indices) {
            ingredients.get(i).dishesId=dishes.dishesId!!
        }
        _insertDishes(dishes)

        _insertSteps(steps)
        _insertIngredients(ingredients)
    }

    suspend fun insertAllDishes(dishesList: List<DishesModel.Dishes>) {

        for(dishes in dishesList) {
            val steps = dishes.steps
            val ingredients = dishes.ingredients
            for (i in steps!!.indices) {
                steps.get(i).dishesId = dishes.dishesId!!
            }
            for (i in ingredients!!.indices) {
                ingredients.get(i).dishesId = dishes.dishesId!!
            }
            _insertSteps(steps)
            _insertIngredients(ingredients)
            _insertDishes(dishes)
        }
    }

    fun getDishes(id: Int): LiveData<DishesModel.Dishes> {
        val dishes = _getDishes(id)
        val steps = _getSteps(id)
        val ingredients = _getIngredients(id)
        dishes.value!!.steps=steps
        dishes.value!!.ingredients=ingredients
        return dishes
    }

//    fun getAllDishes(): LiveData<List<DishesModel.Dishes>> {
//
//         val dishes = _getAllDishes()
//         if (dishes.value == null ) return  dishes
//         for (i in dishes.value!!.indices){
//             val steps = _getSteps(dishes.value!![i].dishesId!!)
//             val ingredients = _getIngredients(dishes.value!![i].dishesId!!)
//
//             dishes.value!![i].steps=steps
//             dishes.value!![i].ingredients=ingredients
//         }
//         return dishes
//     }


    @Query("SELECT * FROM Dishes")@Transaction
    abstract fun getAllRecipe(): LiveData<List<DishesModel.Recipe>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun _insertDishes(dishes: DishesModel.Dishes)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun _insertSteps(steps: List<DishesModel.Step>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun _insertIngredients(ingredients: List<DishesModel.Ingredient>)

    @Query("SELECT * FROM Dishes")
    abstract fun _getAllDishes(): LiveData<List<DishesModel.Dishes>>

    @Query("SELECT * FROM Dishes WHERE dishesId =:id")
    abstract fun _getDishes(id: Int): LiveData<DishesModel.Dishes>

    @Query("SELECT * FROM Step WHERE dishesId =:id")
    abstract fun _getSteps(id: Int): List<DishesModel.Step>
    @Query("SELECT * FROM Ingredient WHERE dishesId =:id")
    abstract fun _getIngredients(id: Int): List<DishesModel.Ingredient>
    @Query("DELETE FROM dishes")
    abstract suspend fun deleteAll()
}