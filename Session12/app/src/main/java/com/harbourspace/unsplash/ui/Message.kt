package com.harbourspace.unsplash.ui

import com.google.firebase.Timestamp

data class Message(val message : String, val username : String, val timeStamp: Timestamp)