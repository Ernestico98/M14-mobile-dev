package com.harbourspace.unsplash.ui.compose.collections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.harbourspace.unsplash.ui.compose.AddUnsplashImage
import com.harbourspace.unsplash.model.UnsplashCollection

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AddCollectionsContent(
    unsplashCollections: List<UnsplashCollection>,
    onOpenDetailsActivity: (String) -> Unit
) {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        val pagerState = rememberPagerState(
            initialPage = 0
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            count = unsplashCollections.size
        ) { page ->

            val item = unsplashCollections[page]

            AddUnsplashImage(
                url = item.cover_photo.urls.regular,
                description = item.title,
                author = item.user.username,
                onOpenDetailsActivity = onOpenDetailsActivity
            )
        }

        if (pagerState.pageCount > 1) {
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}