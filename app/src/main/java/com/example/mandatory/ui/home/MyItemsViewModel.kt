package com.example.mandatory.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mandatory.ui.repository.Items
import com.example.mandatory.ui.repository.ItemsRepository

class MyItemsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is 'my items' Fragment"
    }
    val text: LiveData<String> = _text


}

class myItemsViewModel : ViewModel(){
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
}