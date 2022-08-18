package com.harbourspace.unsplash.ui.legacy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import coil.load
import com.harbourspace.unsplash.R
import com.harbourspace.unsplash.utils.EXTRA_UNSPLASH_IMAGE_URL

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val url = if (intent.extras?.containsKey(EXTRA_UNSPLASH_IMAGE_URL) == true) {
            intent.extras!!.getString(EXTRA_UNSPLASH_IMAGE_URL)
        } else {
            finish()
            return
        }

        findViewById<ImageView>(R.id.iv_preview).load(url) {
            crossfade(true)
            placeholder(R.drawable.ic_placeholder)
        }
    }
}