package com.ernestico.unsplash

import android.util.Log
import android.util.MutableBoolean
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ernestico.unsplash.model.UnsplashItem

class MainViewModel : ViewModel() {
    private val _unsplashItems = MutableLiveData<List<UnsplashItem>>()
    val unsplashItems : LiveData<List<UnsplashItem>> = _unsplashItems

    private val _clickCounter = MutableLiveData<List<Int>>()
    val clickCounter : LiveData<List<Int>> = _clickCounter

    fun initValues(imgs: List<UnsplashItem>) {
        _unsplashItems.value = imgs
        _clickCounter.value = MutableList<Int>(imgs.size){0}
    }

    fun getClicks(index: Int) : Int {
        return _clickCounter.value?.get(index) ?: 0
    }

    fun incrementClickCounter(index: Int) : Unit {
        val temp = _clickCounter.value?.toMutableList() ?: mutableListOf()
        temp[index] += 1
        _clickCounter.value = temp
    }
}

