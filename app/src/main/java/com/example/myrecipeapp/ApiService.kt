package com.example.myrecipeapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


//Usando essa base de URL, sempre que fomos usar outro GET por exemplo, o retrofit já sabe qual URL usar
private val retrofit =
    Retrofit.Builder()
        .baseUrl("https://www.themealdb.com/api/json/v1/1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

//Obtendo os endpoints da nossa URL base
val recipeService = retrofit.create(Apiservice::class.java)

interface Apiservice {
    //Obtendo os dados da API de Categories
    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse
}