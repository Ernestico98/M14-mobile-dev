package com.ernestico.unsplash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ernestico.unsplash.data.UnsplashApiProvider
import com.ernestico.unsplash.data.cb.UnsplashResult
import com.ernestico.unsplash.model.Result
import com.ernestico.unsplash.model.UnsplashItem

class MainActivity : ComponentActivity(), UnsplashResult {

    lateinit var viewModel: MainViewModel
    lateinit var provider: UnsplashApiProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        provider = UnsplashApiProvider()
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
        viewModel.initValues(images)

        setContent {
            val state = viewModel.clickCounter.observeAsState()
            MaterialTheme {
                DataList(state, images, viewModel, this@MainActivity)
            }
        }
    }

    override fun onDataFetchedFailed() {
//        TODO("Not yet implemented")
    }
}

@Composable
fun DataList(state: State<List<Int>?>, images : List<UnsplashItem>, viewModel : MainViewModel, ctx : MainActivity) {
    val context = LocalContext.current

    Column( modifier = Modifier.padding(horizontal = 10.dp) ) {
        val textValue = remember { mutableStateOf("") }

        Row (
            Modifier.padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = textValue.value,
                onValueChange = { value ->
                    textValue.value = value
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .weight(1f),
                maxLines = 1
            )

            IconButton(
                onClick = {
                    ctx.provider.searchImages(textValue.value, cb = ctx)
                },
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Icon(Icons.Filled.Search, contentDescription = "Search icon")
            }
        }

        LazyColumn() {
            itemsIndexed(images) { index, image ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(vertical = 10.dp)
                        .clickable {
                            viewModel.incrementClickCounter(index)

                            val intent = Intent(context, DetailView::class.java)
                            intent.putExtra("image", image)
                            context.startActivity(intent)
                        }
                ) {
                    Card(
                        modifier = Modifier
                            .clip(RoundedCornerShape(percent = 10))
                            .fillMaxSize(),
                        backgroundColor = Color.LightGray
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(image.urls?.regular)
                                    .build()
                            ),
                            contentDescription = "Image description",
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop,
                        )
                    }
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(10.dp)
                    ) {
                        image.user?.name?.let { Text(text = it) }
                        Text(text = "Likes: ${image.likes}")
                        Text(text = "Amount of clicks: ${state.value?.get(index)}")
                    }
                }
            }
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

