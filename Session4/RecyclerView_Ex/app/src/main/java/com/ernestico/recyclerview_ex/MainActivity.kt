package com.ernestico.recyclerview_ex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<RecyclerView>(R.id.rv_listImgs).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)

            val cats = listOf(R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4)
            adapter = FeedAdapter(cats) {
                Toast.makeText(context, "position = $it", Toast.LENGTH_SHORT).show()
            }
        }

    }
}