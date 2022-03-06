package com.podium.papermenu.ui.foodmenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListingMenuViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Food Menu"
    }
    val text: LiveData<String> = _text
}