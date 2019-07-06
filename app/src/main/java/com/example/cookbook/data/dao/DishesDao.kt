package com.example.cookbook.data.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.cookbook.data.model.DishesModel


@Dao
abstract class DishesDao {
//    suspend fun insertDishes(dishes: DishesModel.Dishes) {
//        val steps = dishes.RecipeInstructions
//        val ingredients = dishes.Ingredients
//        for (i in steps!!.indices) {
//            steps.get(i).DishesID=dishes.DishesID!!
//        }
//        for (i in ingredients!!.indices) {
//            ingredients.get(i).DishesID=dishes.DishesID!!
//        }
//        _insertDishes(dishes)
//
//        _insertSteps(steps)
//        _insertIngredients(ingredients)
//    }

    suspend fun insertAllRecipes(recipeList: List<DishesModel.Recipe>) {

        for(recipe in recipeList) {
//            val steps = dishes.RecipeInstructions
//            val ingredients = dishes.Ingredients
//            for (i in steps!!.indices) {
//                steps.get(i).DishesID = dishes.DishesID!!
//            }
//            for (i in ingredients!!.indices) {
//                ingredients.get(i).DishesID = dishes.DishesID!!
//            }
            _insertSteps(recipe.RecipeInstructions)
            _insertIngredients(recipe.Ingredients)
            _insertDishes(recipe.Dishes)
            _insertAuthors(recipe.Authors)
            _insertTags(recipe.Tags)
        }
    }

//    fun getDishes(id: Int): LiveData<DishesModel.Recipe> {
//        val dishes = _getDishes(id)
//        val steps = _getSteps(id)
//        val ingredients = _getIngredients(id)
//        dishes.value!!.RecipeInstructions=steps
//        dishes.value!!.Ingredients=ingredients
//        return dishes
//    }

//    fun getAllDishes(): LiveData<List<DishesModel.Dishes>> {
//
//         val Dishes = _getAllDishes()
//         if (Dishes.value == null ) return  Dishes
//         for (i in Dishes.value!!.indices){
//             val RecipeInstructions = _getSteps(Dishes.value!![i].DishesID!!)
//             val Ingredients = _getIngredients(Dishes.value!![i].DishesID!!)
//
//             Dishes.value!![i].RecipeInstructions=RecipeInstructions
//             Dishes.value!![i].Ingredients=Ingredients
//         }
//         return Dishes
//     }


    @Query("SELECT * FROM Dishes") @Transaction
    abstract fun getAllRecipe(): DataSource.Factory<Int, DishesModel.Recipe>

    @Query("SELECT * FROM Dishes where DishesID =:ID") @Transaction
    abstract fun getRecipe(ID:Int ): LiveData<DishesModel.Recipe>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun _insertDishes(dishes: DishesModel.Dishes)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun _insertAuthors(authors: List<DishesModel.Author>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun _insertTags(tags: List<DishesModel.Tag>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun _insertSteps(recipeInstructions: List<DishesModel.RecipeInstruction>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun _insertIngredients(ingredients: List<DishesModel.Ingredient>)

//    @Query("SELECT * FROM Dishes")
//    abstract fun getAllDishes(): LiveData<List<DishesModel.Dishes>>
    @Query("SELECT * FROM Dishes") @Transaction
    abstract fun getAllDishes(): DataSource.Factory<Int, DishesModel.Dishes>

    @Query("SELECT * FROM Dishes WHERE DishesID =:ID")
    abstract fun getDishes(ID: Int): LiveData<DishesModel.Dishes>

    @Query("SELECT * FROM Dishes Where Favorite =:isFav") @Transaction
    abstract fun getAllFav(isFav:Boolean): DataSource.Factory<Int, DishesModel.Dishes>

    @Query("SELECT * FROM Dishes WHERE  Dishes.Name LIKE :searchStr OR (Dishes.DishesID IN (SELECT Ingredient.DishesID FROM Ingredient WHERE  Ingredient.Name LIKE :searchStr))")
    abstract fun getSearchDishes(searchStr: String): DataSource.Factory<Int, DishesModel.Dishes>

    @Query("SELECT * FROM RecipeInstruction WHERE DishesID =:ID")
    abstract fun _getSteps(ID: Int): List<DishesModel.RecipeInstruction>
    @Query("SELECT * FROM Ingredient WHERE DishesID =:ID")
    abstract fun _getIngredients(ID: Int): List<DishesModel.Ingredient>

    @Update
    abstract suspend fun updateDishes(dishes: DishesModel.Dishes)

    @Query("DELETE FROM Dishes")
    abstract suspend fun deleteAll()
}