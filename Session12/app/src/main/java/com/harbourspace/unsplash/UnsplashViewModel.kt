package com.harbourspace.unsplash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harbourspace.unsplash.data.UnsplashApiProvider
import com.harbourspace.unsplash.data.cb.UnsplashResult
import com.harbourspace.unsplash.model.UnsplashCollection
import com.harbourspace.unsplash.model.UnsplashItem
import com.harbourspace.unsplash.ui.Message

private const val TAG = "UnsplashViewModel"

class UnsplashViewModel: ViewModel(), UnsplashResult {

    private val _unsplashItems = MutableLiveData<List<UnsplashItem>>()
    val unsplashItems: LiveData<List<UnsplashItem>> = _unsplashItems

    private val _unsplashCollections = MutableLiveData<List<UnsplashCollection>>()
    val unsplashCollections: LiveData<List<UnsplashCollection>> = _unsplashCollections

    private val _messages = MutableLiveData<List<Message>?>()
    val messages : LiveData<List<Message>?> = _messages

    fun setMessages(msgs : List<Message>) {
        _messages.value = msgs
    }

    init {
        _messages.value = null
    }

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val provider by lazy {
        UnsplashApiProvider()
    }

    fun fetchImages() {
        provider.fetchImages(this)
    }

    fun forceFetchImages() {
        _unsplashItems.value = emptyList()
        provider.fetchImages(this)
    }

    fun searchImages(keyword: String) {
        provider.searchImages(keyword, this)
    }

    fun fetchCollections() {
        provider.fetchCollections(this)
    }

    override fun onDataFetchedSuccess(images: List<UnsplashItem>) {
        Log.d(TAG, "onDataFetchedSuccess | Received ${images.size} images")
        _unsplashItems.value = images
    }

    override fun onCollectionsFetchedSuccess(collections: List<UnsplashCollection>) {
        Log.d(TAG, "onCollectionsFetchedSuccess | Received ${collections.size} images")
        _unsplashCollections.value = collections
    }

    override fun onDataFetchedFailed() {
        Log.d(TAG, "onDataFetchedFailed | Unable to retrieve images")
        _error.value = true
    }
}