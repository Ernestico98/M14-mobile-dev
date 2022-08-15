package com.ernestico.unsplash

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ernestico.unsplash.data.UnsplashApiProvider
import com.ernestico.unsplash.data.cb.UnsplashResult
import com.ernestico.unsplash.model.PhotoDetails
import com.ernestico.unsplash.model.UnsplashItem

private const val TAG = "DETAIL_VIEW"

class DetailView : ComponentActivity(), UnsplashResult {

    private lateinit var provider: UnsplashApiProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val imageId = intent.getStringExtra("imageId")

        provider = UnsplashApiProvider()
        if (imageId != null) {
            provider.getPhotoDetails(imageId, this)
        }
    }

    override fun onDataFetchedSuccess(images: List<UnsplashItem>) {
//        TODO("Not yet implemented")
    }

    override fun onDataFetchedFailed() {
//        TODO("Not yet implemented")
    }

    override fun onDetailsFetchedSuccess(image: PhotoDetails) {
        Log.d(TAG, "image details | $image")

        setContent {
            MaterialTheme {
                Detail(image)
            }
        }
    }
}

//@Preview
@Composable
fun Detail(image: PhotoDetails) {
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .padding(bottom = 20.dp)) {

        Box {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image.urls?.regular)
                        .build()
                ),
                contentDescription = image.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop,
            )

            Row(modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(10.dp)
            ) {
                Icon(
                    Icons.Filled.LocationOn,
                    tint = Color.White,
                    contentDescription = "location icon",
                )
                Text(
                    text = "${image.location?.city  ?: "No available location data"} ${
                        if (image.location?.city == null) {
                            ""
                        } else {
                            ", " + image.location.country
                        }
                    }",
                    textAlign = TextAlign.End,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(image.user?.profile_image?.large)
                            .build()
                    ),
                    contentDescription = "User picture",
                    modifier = Modifier
                        .clip(RoundedCornerShape(percent = 50))
                        .size(50.dp)
                )

                Text(
                    text = image.user?.name ?: "<No User name description>",
                    modifier = Modifier.padding(horizontal = 5.dp),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.width(50.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.download),
                        contentDescription = "Download",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                }

                Spacer(Modifier.size(ButtonDefaults.IconSpacing))

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.width(50.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.favorite),
                        contentDescription = "Favorite",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                }

                Spacer(Modifier.size(ButtonDefaults.IconSpacing))

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.width(50.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.bookmark),
                        contentDescription = "bookmark",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Divider()

            Row(Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)) {
                Text(text = stringResource(id = R.string.camera), Modifier.weight(0.5f), fontWeight = FontWeight.Bold)
                Text(text = stringResource(id = R.string.aperture), Modifier.weight(0.5f), fontWeight = FontWeight.Bold)
            }
            Row(Modifier.padding(horizontal = 20.dp)) {
                Text(text = image.exif?.name ?: "No data available", Modifier.weight(0.5f))
                Text(text = image.exif?.aperture ?: "No data available", Modifier.weight(0.5f))
            }

            Row(Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)) {
                Text(text = stringResource(id = R.string.focal_length), Modifier.weight(0.5f), fontWeight = FontWeight.Bold)
                Text(text = stringResource(id = R.string.shutter_speed), Modifier.weight(0.5f), fontWeight = FontWeight.Bold)
            }
            Row(Modifier.padding(horizontal = 20.dp)) {
                Text(text = image.exif?.focal_length ?: "No data available" , Modifier.weight(0.5f))
                Text(text = image.exif?.exposure_time ?: "No data available", Modifier.weight(0.5f))
            }

            Row(Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)) {
                Text(text = stringResource(id = R.string.iso), Modifier.weight(0.5f), fontWeight = FontWeight.Bold)
                Text(text = stringResource(id = R.string.dimensions), Modifier.weight(0.5f), fontWeight = FontWeight.Bold)
            }
            Row(Modifier.padding(horizontal = 20.dp)) {
                Text(text = "${image.exif?.iso ?: """No data available"""}", Modifier.weight(0.5f))
                Text(text = "${image.height} x ${image.width}", Modifier.weight(0.5f))
            }

            Spacer(modifier = Modifier.height(10.dp))
            Divider()

            Row(
                Modifier.padding(top = 10.dp, start = 40.dp, end = 40.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.views),
                    Modifier.weight(0.33f),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(id = R.string.downloads),
                    Modifier.weight(0.33f),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(id = R.string.likes),
                    Modifier.weight(0.33f),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
            Row(
                Modifier.padding(horizontal = 40.dp),

            ) {
                Text(
                    text = if (image.views == null) {
                        "No data"
                    } else {
                        "${image.views}"
                    },
                    Modifier.weight(0.33f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "${image.downloads ?: "No data"}",
                    Modifier.weight(0.33f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = if (image.likes == null) {
                        "No data"
                    } else {
                        "${image.likes}"
                    },
                    Modifier.weight(0.33f),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(10.dp))
            Divider()

            LazyRow(
                modifier = Modifier.padding(top = 10.dp)
            ) {

                itemsIndexed(image.tags ?: emptyList()) { index, tag ->
                    Box(Modifier.clip(RoundedCornerShape(percent = 50))) {
                        Text(
                            text = tag.title!!,
                            modifier = Modifier
                                .background(Color.Gray)
                                .padding(vertical = 3.dp, horizontal = 5.dp)
                        )
                    }

                    if (index + 1 != image.tags!!.size)
                        Spacer(Modifier.width(7.dp))
                }
            }
        }
    }
}