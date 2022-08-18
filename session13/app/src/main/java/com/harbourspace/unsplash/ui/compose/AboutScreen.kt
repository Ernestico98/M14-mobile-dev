package com.harbourspace.unsplash.ui.compose

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.harbourspace.unsplash.R
import com.harbourspace.unsplash.ui.compose.theme.typography

@Composable
fun AboutScreen(
    isDarkTheme: MutableState<Boolean>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = stringResource(id = R.string.about_dark_theme),
                style = typography.h2
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Switch(
                    checked = isDarkTheme.value,
                    onCheckedChange = {
                        isDarkTheme.value = !isDarkTheme.value
                    }
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = stringResource(id = R.string.about_text),
                style = typography.h1
            )

            Spacer(modifier = Modifier.height(75.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_android),
                    contentDescription = stringResource(id = R.string.description_android),
                    modifier = Modifier.size(75.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_kotlin),
                    contentDescription = stringResource(id = R.string.description_kotlin),
                    modifier = Modifier.size(75.dp)
                )
            }

            val context = LocalContext.current
            Button(onClick = {
                Firebase.auth.signOut()
            }) {
                Text(text = "Logout")
            }
        }
    }
}