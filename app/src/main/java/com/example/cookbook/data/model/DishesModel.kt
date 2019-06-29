package com.example.cookbook.data.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import java.io.Serializable

object DishesModel {
    @Entity(primaryKeys= [ "id", "dishesId" ] )
    data class Step (
        var id :Int,
        var dishesId :Int,
        var content :String ,
        var image :String?
    ): Serializable
    @Entity(primaryKeys= [ "id", "dishesId" ] )
    data class Ingredient (
        var id :Int,
        var dishesId :Int,
        var image : String? ,
        var name :String ,
        var quantity :String? ): Serializable

    data class Recipe(
        @Relation(parentColumn = "dishesId", entityColumn = "dishesId", entity = Step::class)
        var steps: List<Step>,
        @Relation(parentColumn = "dishesId", entityColumn = "dishesId", entity = Ingredient::class)
        var ingredients: List<Ingredient>?,
        @Embedded
        var dishes :Dishes

    ) :Serializable

    @Entity
    data class Dishes  (
        var description : String?=null,
        var remark : String?=null,
        var name : String?= null,
        var image : String?=null,
        @Ignore
        var ingredients :List<Ingredient> ?=null,
        @Ignore
        var steps : List<Step>?=null,
        var video : String?=null,
        @PrimaryKey var dishesId :Int? =null
    ): Serializable


    data class DishesReposnse(var mList:List<Dishes>)

}

