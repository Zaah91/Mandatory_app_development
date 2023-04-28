package com.example.mandatory.ui.repository

import retrofit2.Call
import retrofit2.http.*

interface ItemsService {
    @GET("salesItems")
    fun getAllItems(): Call<List<Items>>

    @GET("SalesItems/{itemId}")
    fun getItemById(@Path("itemId") itemId: Int): Call<Items>

    @POST("SalesItems")
    fun saveItem(@Body items: Items): Call<Items>

    @DELETE("SalesItems/{id}")
    fun deleteItem(@Path("id") id: Int): Call<Items>

    @PUT("SalesItems/{id}")
    fun updateItem(@Path("id") id: Int, @Body item: Items): Call<Items>

}