package com.harbourspace.unsplash.ui.compose

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.harbourspace.unsplash.UnsplashViewModel
import com.harbourspace.unsplash.ui.Message

private val firestore = Firebase.firestore
private val COLLECTION_MODULE = "Messages"

fun fetchMessages(viewModel: UnsplashViewModel) {
    val docs = firestore.collection(COLLECTION_MODULE).orderBy("timeStamp")
    docs.addSnapshotListener { snapshot, e ->
        if (snapshot?.documents?.isNotEmpty() == true) {
            val messages = mutableListOf<Message>()
            for (document in snapshot.documents) {
                messages.add(
                    Message(
                        message = document.get("message").toString(),
                        timeStamp = document.get("timeStamp") as Timestamp,
                        username = document.get("username").toString()
                    )
                )
            }

            viewModel.setMessages(messages)
        }
        Log.d("MYTAG", "${snapshot!!.documents.size}")
    }
}

@Composable
fun MessageItem(message : String, username : String) {
    Row(
        modifier = Modifier
            .background(color = Color.DarkGray)
            .padding(15.dp)

    ) {
        Column() {
            Text(text = message)
            Text(text = username)
        }
    }
}

@Composable
fun MessageView(unsplashViewModel : UnsplashViewModel) {
    val messages = unsplashViewModel.messages.observeAsState()
    fetchMessages(viewModel = unsplashViewModel)

    val text = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {

        Row() {
            TextField(value = text.value, onValueChange = {
                text.value = it
            })
            Button(onClick = {
                val msg = Message(
                    message = text.value,
                    username = "ernestico",
                    timeStamp = Timestamp.now()
                )
                text.value = ""
                firestore.collection(COLLECTION_MODULE).document()
                    .set(msg)
                    .addOnSuccessListener { fetchMessages(unsplashViewModel) }
                    .addOnFailureListener {
                        Log.d(
                            "VIEWMESSAGE",
                            "Unable to write to firestore. Error: $it"
                        )
                    }
            }) {
                Text(text = "send")
            }
        }
        Column(
            modifier = Modifier.fillMaxSize()
        )
        {
            if (messages.value != null) {
                for (item in messages.value!!) {
                    MessageItem(message = item.message, username = item.username)
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
    }
}