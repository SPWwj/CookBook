package com.example.cookbook.network

import com.example.cookbook.data.model.DishesModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface DishesApiService {

    @GET("mlist.json")
    fun getDishes(): Observable<DishesModel.DishesReposnse>

    companion object {
        fun create(): DishesApiService {

            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl("https://cookbook-21f51.firebaseio.com")
                    .build()

            return retrofit.create(DishesApiService::class.java)
        }
    }

}