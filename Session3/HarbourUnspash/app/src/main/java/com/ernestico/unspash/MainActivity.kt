package com.ernestico.unspash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPause() {
        super.onPause()

        Toast.makeText(applicationContext, "OnPause!", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()

        Toast.makeText(applicationContext, "OnResume!", Toast.LENGTH_SHORT).show()
    }


}