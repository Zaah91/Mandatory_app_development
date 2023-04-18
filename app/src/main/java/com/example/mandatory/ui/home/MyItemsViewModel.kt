package com.example.mandatory.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyItemsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is 'my items' Fragment"
    }
    val text: LiveData<String> = _text
}