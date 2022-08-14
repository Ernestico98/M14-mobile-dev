package com.ernestico.weather

import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ernestico.weather.data.GeoApiProvider
import com.ernestico.weather.data.WeatherApiProvider
import com.ernestico.weather.data.cb.GeoResult
import com.ernestico.weather.data.cb.WeatherResult
import com.ernestico.weather.data.geo_response.GeoDataItem
import com.ernestico.weather.data.weather_response.WeatherData
import com.google.android.gms.location.FusedLocationProviderClient

private val TAG = "MAIN_VIEW_MODEL"

class MainViewModel : ViewModel(), WeatherResult, GeoResult {

    private val _geoResponse = MutableLiveData<List<GeoDataItem>?>()
    val geoResponse: LiveData<List<GeoDataItem>?> = _geoResponse

    private val _weatherResponse = MutableLiveData<WeatherData?>()
    val weatherResponse: LiveData<WeatherData?> = _weatherResponse

    private val _latitude = MutableLiveData<Double?>()
    val latitude: LiveData<Double?> = _latitude

    private val _longitude = MutableLiveData<Double?>()
    val longitude: LiveData<Double?> = _longitude

    private val _useLocation = MutableLiveData<Boolean>()
    val useLocation: LiveData<Boolean> = _useLocation

    private val _topBarText = MutableLiveData<String>()
    val topBarText : LiveData<String> = _topBarText

    init {
        _geoResponse.value = null
        _weatherResponse.value = null
        _latitude.value = null
        _longitude.value = null
        _useLocation.value = true
        _topBarText.value = ""
    }

    private val weatherProvider by lazy {
        WeatherApiProvider()
    }

    private val geoProvider by lazy {
        GeoApiProvider()
    }

    fun fetchWeather(lon: Double, lat: Double) {
        weatherProvider.fetchWeather(lon = lon, lat = lat, cb = this)
    }

    fun fetchGeo(place : String) {
        geoProvider.fetchGeo(place = place, cb = this)
    }

    fun setLocation(lon: Double, lat: Double) {
        _longitude?.value = lon
        _latitude?.value = lat
    }

    fun setTopBarText(text : String) {
        _topBarText.value = text
    }

    fun setGeoData(geo : List<GeoDataItem>? ) {
        _geoResponse.value = geo
    }

    fun setUseLocation(useLoc : Boolean) {
        _useLocation.value = useLoc
    }

    override fun onWeatherFetchedSuccess(weather: WeatherData) {
        Log.d(TAG, "weather fetch $weather")
        _weatherResponse.value = weather
    }

    override fun onWeatherFetchedFailed() {
        Log.d(TAG, "weather fetch failed")
    }

    override fun onGeoFetchedSuccess(geo: List<GeoDataItem>) {
        Log.d(TAG, "geo fetched $geo")
        _geoResponse.value = geo
    }

    override fun onGeoFetchedFailed() {
        Log.d(TAG, "geo fetch failed")
    }


}