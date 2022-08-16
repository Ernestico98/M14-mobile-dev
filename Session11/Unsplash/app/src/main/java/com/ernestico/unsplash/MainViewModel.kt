package com.ernestico.unsplash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ernestico.unsplash.databaseModel.UnsplashModel

import com.ernestico.unsplash.databaseModel.UnsplashRepository
import com.ernestico.unsplash.model.UnsplashItem

class MainViewModel(
    private val repository: UnsplashRepository
) : ViewModel() {
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

    fun getImagesFromDatabase(): LiveData<List<UnsplashModel>> {
        return repository.allUnsplash
    }

    fun addImage(image : UnsplashModel) {
        repository.insert(image)
    }

    fun incrementClickCounter(index: Int) : Unit {
        val temp = _clickCounter.value?.toMutableList() ?: mutableListOf()
        temp[index] += 1
        _clickCounter.value = temp
    }
}

class MainViewModelFactory(
    private val repository: UnsplashRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
