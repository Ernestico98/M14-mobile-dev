package com.ernestico.composeexample

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ernestico.composeexample.ui.theme.ComposeExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                        .padding(40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center

                )
                {
                    Text(
                        text = "This is a showcase app using Jetpack Compose with the Unsplash API",
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier.width(250.dp)
                    )

                    Spacer(modifier = Modifier.padding(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.android),
                            contentDescription = "Android icon",
                            modifier = Modifier.width(80.dp).height(80.dp)
                        )

                        Spacer(modifier = Modifier.padding(10.dp))

                        Image(
                            painter = painterResource(id = R.drawable.kotlin),
                            contentDescription = "Kotlin icon",
                            modifier = Modifier.width(80.dp).height(80.dp)
                        )
                    }
                }

            }
        }
    }
}
