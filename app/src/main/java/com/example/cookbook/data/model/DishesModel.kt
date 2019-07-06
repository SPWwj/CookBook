package com.example.cookbook.data.model

import androidx.room.*
import java.io.Serializable

object DishesModel {
    @Entity(primaryKeys= [ "ID", "DishesID"] )
    data class RecipeInstruction (
        var ID :Int,
        var DishesID :Int,
        var Step :String,
        var Highlight :Boolean,
        var Image :String?
    ): Serializable
    @Entity(primaryKeys= [ "ID", "DishesID"] )
    data class Ingredient (
        var ID :Int,
        var Highlight :Boolean,
        var DishesID :Int,
        var Image : String?,
        var Name :String,
        var Main : Boolean?,
        var Side : Boolean?,
        var Spices : Boolean?,
        var Quantity :String? ): Serializable

    @Entity(primaryKeys= [ "ID", "DishesID" ] )
    data class Tag (
        var ID :Int,
        var DishesID :Int,
        var Tag : String
    ): Serializable

    @Entity(primaryKeys= [ "ID", "DishesID" ] )
    data class Author (
        var ID :Int,
        var DishesID :Int,
        var Name : String,
        var Type : String?
    ): Serializable

    data class Recipe(
        @Relation(parentColumn = "DishesID", entityColumn = "DishesID", entity = RecipeInstruction::class)
        var RecipeInstructions: List<RecipeInstruction>,
        @Relation(parentColumn = "DishesID", entityColumn = "DishesID", entity = Ingredient::class)
        var Ingredients: List<Ingredient>,
        @Embedded
        var Dishes :Dishes,
        @Relation(parentColumn = "DishesID", entityColumn = "DishesID", entity = Tag::class)
        var Tags :List<Tag>,
        @Relation(parentColumn = "DishesID", entityColumn = "DishesID", entity = Author::class)
        var Authors :List<Author>

    ) :Serializable

    @Entity
    data class Dishes  (
        var Description : String?,
        var Tips : String?,
        var Name : String,
        var Image : String?,
        var Video : String?,
        var Favorite :Boolean,
        @PrimaryKey var DishesID :Int
    ): Serializable


    data class DishesResponse(var mList:List<Recipe>)

}

