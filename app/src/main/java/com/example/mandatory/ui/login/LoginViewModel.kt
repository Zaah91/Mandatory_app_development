package com.example.mandatory.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    val email: MutableLiveData<String> = MutableLiveData()
    private val _text = MutableLiveData<String>().apply {
        value = "This is 'login' Fragment"
    }
    val text: LiveData<String> = _text
}