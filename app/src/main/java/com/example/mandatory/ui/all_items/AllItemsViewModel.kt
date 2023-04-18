package com.example.mandatory.ui.all_items

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AllItemsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is 'all items' Fragment"
    }
    val text: LiveData<String> = _text
}