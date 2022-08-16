package com.harbourspace.unsplash.ui.compose.images

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.harbourspace.unsplash.ui.compose.AddUnsplashImage
import com.harbourspace.unsplash.ui.compose.AddUserInputAction
import com.harbourspace.unsplash.model.UnsplashItem

@Composable
fun AddImagesContent(
    unsplashItems: List<UnsplashItem>,
    onSearchAction: (String) -> Unit,
    onOpenDetailsActivity: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        item {
            AddUserInputAction(
                onSearchAction = {
                    onSearchAction(it)
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        items(unsplashItems) {

            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                AddUnsplashImage(
                    url = it.urls.regular,
                    description = it.description ?: "",
                    author = it.user.username,
                    onOpenDetailsActivity = onOpenDetailsActivity
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}