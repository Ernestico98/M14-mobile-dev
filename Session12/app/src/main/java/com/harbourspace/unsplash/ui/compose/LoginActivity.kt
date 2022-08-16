package com.harbourspace.unsplash.ui.compose

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.harbourspace.unsplash.ui.compose.ui.theme.HarbourUnsplashTheme
import com.harbourspace.unsplash.ui.compose.MainComposeActivity

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, MainComposeActivity::class.java))

//        val auth = Firebase.auth
//        if (auth.currentUser == null) {
//            startActivity(Intent(this, MainComposeActivity::class.java))
//            finish()
//        }

        setContent {
            HarbourUnsplashTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        val username = remember{ mutableStateOf("") }
                        val password = remember{ mutableStateOf("")}

                        TextField(
                            value = username.value,
                            onValueChange = {
                                username.value = it
                            },
                            label = { Text(text = "Username")}
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        
                        TextField(
                            value = password.value,
                            onValueChange = {
                                password.value = it
                            },
                            label = { Text(text = "Password")},
                            visualTransformation = PasswordVisualTransformation()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row() {
                            Button(onClick = {
                                Login(username.value, password.value)
                            }) {
                                Text(text = "Login")
                            }
                            Spacer(modifier = Modifier.width(10.dp))

                            Button(onClick = {
                                Register(username.value, password.value)
                            }) {
                                Text(text = "Register")
                            }
                        }
                    }
                }
            }
        }
    }

    fun Login(username: String, password: String) {
        val auth = Firebase.auth
        auth.signInWithEmailAndPassword(
            username,
            password
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                startActivity(Intent(this, MainComposeActivity::class.java))
                finish()
            } else {
                Log.d("LOGIN_VIEW", "Authentication failed. Error: ${it.exception}")
            }
        }
    }
    fun Register(username: String, password: String) {
        val auth = Firebase.auth
        auth.createUserWithEmailAndPassword(
            username,
            password
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                startActivity(Intent(this, MainComposeActivity::class.java))
                finish()
            } else {
                Log.d("LOGIN_VIEW", "Registration failed. Error: ${it.exception}")
            }
        }
    }
}

