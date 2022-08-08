package com.ernestico.unsplash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.compose.material.Card
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
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.ernestico.unsplash.data.UnsplashApiProvider
import com.ernestico.unsplash.data.cb.UnsplashResult
import com.ernestico.unsplash.model.UnsplashItem
import com.ernestico.unsplash.ui.theme.UnsplashTheme

class MainActivity : ComponentActivity(), UnsplashResult {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val provider = UnsplashApiProvider()
        provider.fetchImages(this)
        
//        var cats = listOf<Cat>(
//            Cat(R.drawable.img1),
//            Cat(R.drawable.img2),
//            Cat(R.drawable.img3),
//            Cat(R.drawable.img4),
//            Cat(R.drawable.img5),
//            Cat(R.drawable.img6),
//            Cat(R.drawable.img7),
//        )
//        setContent {
//            MaterialTheme {
//                ImageList(cats)
//            }
//        }
    }

    override fun onDataFetchedSuccess(images: List<UnsplashItem>) {
        setContent {
            MaterialTheme {
                DataList(data = images)
            }
        }
    }

    override fun onDataFetchedFailed() {
//        TODO("Not yet implemented")
    }
}

@Composable
fun DataList(data: List<UnsplashItem>) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        items(data) {
            image -> DataItem(image)

        }
    }
}

@Composable
fun DataItem(image : UnsplashItem) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(vertical = 10.dp),
    ) {
        Card(
            modifier = Modifier
                .clip(RoundedCornerShape(percent = 10))
                .fillMaxSize(),
            backgroundColor = Color.LightGray
        ) { }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(10.dp)
        ) {
            Text(
                text = image.user.name,
            )
            Text(text = "Likes: ${image.likes}")
        }
    }
}


@Composable
fun ImageList(cats: List<Cat>) {
    LazyColumn {
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

