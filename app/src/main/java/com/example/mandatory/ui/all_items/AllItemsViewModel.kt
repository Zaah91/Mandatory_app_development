package com.example.mandatory.ui.all_items

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mandatory.ui.repository.Items
import com.example.mandatory.ui.repository.ItemsRepository

class AllItemsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is 'all items' Fragment"
    }
    // val text: LiveData<String> = _text
}

class ItemsViewModel : ViewModel() {
    private val repository = ItemsRepository()
    val itemsLiveData: LiveData<List<Items>> = repository.itemsLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    val updateMessageLiveData: LiveData<String> = repository.updateMessageLiveData

    init {
        reload()
    }

    fun reload() {
        repository.getItems()
    }

    operator fun get(index: Int): Items? {
        return itemsLiveData.value?.get(index)
    }

    fun add(items: Items) {
        repository.add(items)
    }

    fun delete(id: Int) {
        repository.delete(id)
    }

    fun sortByDescription() {
        repository.sortByDescription()
    }

    fun sortByDescriptionDescending() {
        repository.sortByDescriptionDescending()
    }

    fun sortByPrice() {
        repository.sortByPrice()
    }

    fun sortByPriceDescending() {
        repository.sortByPriceDescending()
    }

    fun filterByTitle(description: String) {
        repository.filterByDescription(description)
    }
}