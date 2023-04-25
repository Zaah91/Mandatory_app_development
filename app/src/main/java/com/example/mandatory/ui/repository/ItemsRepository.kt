package com.example.mandatory.ui.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ItemsRepository {

    private val url = "https://anbo-salesitems.azurewebsites.net/api/"

    private val itemsService: ItemsService
    val itemsLiveData: MutableLiveData<List<Items>> = MutableLiveData<List<Items>>()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val updateMessageLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create()) // GSON
            .build()
        itemsService = build.create(ItemsService::class.java)
        getItems()
    }

    fun getItems() {
        itemsService.getAllItems().enqueue(object : Callback<List<Items>> {
            override fun onResponse(
                call: retrofit2.Call<List<Items>>,
                response: retrofit2.Response<List<Items>>
            ) {
                if (response.isSuccessful) {
                    val b: List<Items>? = response.body()
                    itemsLiveData.postValue(b!!)
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("Line 42 errorcode", message)
                }
            }


            override fun onFailure(call: retrofit2.Call<List<Items>>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("Line 49 errorcode", t.message!!)
            }

        })
    }

    fun add(items: Items) {
        itemsService.saveItem(items).enqueue(object : Callback<Items> {
            override fun onResponse(
                call: retrofit2.Call<Items>,
                response: retrofit2.Response<Items>
            ) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "Added: " + response.body()) //TODO omskriv tag
                    updateMessageLiveData.postValue("Added: " + response.body())
                    getItems()
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("Line 69 errorcode", message)
                }
            }

            override fun onFailure(call: retrofit2.Call<Items>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!) //TODO omskriv tag
            }
        })
    }

    fun delete(id: Int) {
        itemsService.deleteItem(id).enqueue(object : Callback<Items> {
            override fun onResponse(
                call: retrofit2.Call<Items>,
                response: retrofit2.Response<Items>
            ) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "Updated: " + response.body()) //TODO omskriv tag
                    updateMessageLiveData.postValue("Deleted: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message) //TODO omskriv tag
                }
            }

            override fun onFailure(call: retrofit2.Call<Items>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!) //TODO omskriv tag
            }
        })
    }

    fun sortByDescription() {
        itemsLiveData.value = itemsLiveData.value?.sortedBy { it.description }
    }

    fun sortByDescriptionDescending() {
        itemsLiveData.value = itemsLiveData.value?.sortedByDescending { it.description }
    }

    fun sortByPrice() {
        itemsLiveData.value = itemsLiveData.value?.sortedBy { it.price }
    }

    fun sortByPriceDescending() {
        itemsLiveData.value = itemsLiveData.value?.sortedByDescending { it.price }
    }

    fun filterByDescription(title: String) {
        if (title.isBlank()) {
            getItems()
        } else {
            itemsLiveData.value =
                itemsLiveData.value?.filter { item -> item.description.contains(title) }
            // TODO: bug fix: booksLiveData.value keeps getting smaller for each filter
        }
    }
}