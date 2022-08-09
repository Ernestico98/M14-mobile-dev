package com.ernestico.unsplash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.MainThread
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.ernestico.unsplash.ui.theme.UnsplashTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var cats = listOf<Cat>(
            Cat(R.drawable.img1),
            Cat(R.drawable.img2),
            Cat(R.drawable.img3),
            Cat(R.drawable.img4),
            Cat(R.drawable.img5),
            Cat(R.drawable.img6),
            Cat(R.drawable.img7),
        )
        setContent {
            MaterialTheme {
                ImageList(cats)
            }
        }

    }
}


@Composable
fun ImageList(cats: List<Cat>) {
    LazyColumn() {
        items(cats) { cat ->
            CatItem(cat)
        }
    }
}

@Composable
fun CatItem(cat: Cat) {
    val context = LocalContext.current
    Image(
        painter = painterResource(id = cat.img),
        contentDescription = stringResource(id = R.string.cat_picture_desc),
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                val intent = Intent(context, DetailView::class.java)
                intent.putExtra("cat", cat.img)
                context.startActivity(intent)
            },
        contentScale = ContentScale.Crop,
    )
}

