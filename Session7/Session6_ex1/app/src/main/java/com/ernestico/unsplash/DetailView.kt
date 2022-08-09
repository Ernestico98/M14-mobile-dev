package com.ernestico.unsplash

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ernestico.unsplash.model.UnsplashItem
import com.ernestico.unsplash.ui.theme.UnsplashTheme

class DetailView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var image = intent.getParcelableExtra<UnsplashItem>("image")
        if (image != null) {
            Log.d("KKK", "${image.urls?.regular}")
        }

        setContent {
            MaterialTheme {
                if (image != null) {
                    Detail(image)
                }
            }
        }
    }
}

//@Preview
@Composable
fun Detail(image: UnsplashItem) {
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .padding(bottom = 20.dp)) {

        Box() {
            Image(
//                painter = painterResource(id = R.drawable.img1),

                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image.urls?.regular)
                        .build()
                ),
                contentDescription = "contentImage",
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
                    text = "Barcelona, Spain",
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
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = stringResource(id = R.string.user_picture_desc),
                    modifier = Modifier.clip(RoundedCornerShape(percent = 50))
                )
                
                Text(
                    text = stringResource(id = R.string.user_name),
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
                Text(text = stringResource(id = R.string.camera_model), Modifier.weight(0.5f))
                Text(text = stringResource(id = R.string.aperture_value), Modifier.weight(0.5f))
            }

            Row(Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)) {
                Text(text = stringResource(id = R.string.focal_length), Modifier.weight(0.5f), fontWeight = FontWeight.Bold)
                Text(text = stringResource(id = R.string.shutter_speed), Modifier.weight(0.5f), fontWeight = FontWeight.Bold)
            }
            Row(Modifier.padding(horizontal = 20.dp)) {
                Text(text = stringResource(id = R.string.focal_length_value), Modifier.weight(0.5f))
                Text(text = stringResource(id = R.string.shutter_speed_value), Modifier.weight(0.5f))
            }

            Row(Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)) {
                Text(text = stringResource(id = R.string.iso), Modifier.weight(0.5f), fontWeight = FontWeight.Bold)
                Text(text = stringResource(id = R.string.dimensions), Modifier.weight(0.5f), fontWeight = FontWeight.Bold)
            }
            Row(Modifier.padding(horizontal = 20.dp)) {
                Text(text = stringResource(id = R.string.iso_value), Modifier.weight(0.5f))
                Text(text = stringResource(id = R.string.dimensions_value), Modifier.weight(0.5f))
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
                    text = stringResource(id = R.string.views_values),
                    Modifier.weight(0.33f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(id = R.string.downloads_values),
                    Modifier.weight(0.33f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(id = R.string.likes_values),
                    Modifier.weight(0.33f),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(10.dp))
            Divider()

            Row(Modifier.padding(top = 10.dp)) {
                Box(Modifier.clip(RoundedCornerShape(percent = 50))) {
                    Text(
                        text = "barcelona",
                        modifier = Modifier
                            .background(Color.Gray)
                            .padding(vertical = 3.dp, horizontal = 5.dp)
                    )
                }

                Spacer(Modifier.width(7.dp))

                Box(Modifier.clip(RoundedCornerShape(percent = 50))) {
                    Text(
                        text = "spain",
                        modifier = Modifier
                            .background(Color.Gray)
                            .padding(vertical = 3.dp, horizontal = 5.dp)
                    )
                }
            }
        }
    }
}