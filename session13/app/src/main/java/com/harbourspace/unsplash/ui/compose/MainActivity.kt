package com.harbourspace.unsplash.ui.compose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.harbourspace.unsplash.ui.legacy.DetailsActivity
import com.harbourspace.unsplash.R
import com.harbourspace.unsplash.UnsplashViewModel
import com.harbourspace.unsplash.data.repository.AppPreferences
import com.harbourspace.unsplash.ui.compose.collections.AddCollectionsContent
import com.harbourspace.unsplash.ui.compose.images.AddImagesContent
import com.harbourspace.unsplash.ui.compose.navigation.BottomNavigationScreens
import com.harbourspace.unsplash.model.UnsplashCollection
import com.harbourspace.unsplash.model.UnsplashItem
import com.harbourspace.unsplash.ui.compose.animations.AddLoadingAnimation
import com.harbourspace.unsplash.ui.compose.theme.colorDarkPalette
import com.harbourspace.unsplash.ui.compose.theme.colorLightPalette
import com.harbourspace.unsplash.ui.compose.theme.typography
import com.harbourspace.unsplash.utils.EXTRA_UNSPLASH_IMAGE_URL
import com.harbourspace.unsplash.utils.isDarkThemeEnabled

private enum class Tab(@StringRes val tab: Int) {
    HOME(R.string.main_tab_images),
    COLLECTIONS(R.string.main_tab_collections)
}

class MainComposeActivity : AppCompatActivity() {

    private lateinit var appPreferences: AppPreferences

    private val unsplashViewModel: UnsplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appPreferences = AppPreferences(this)

        unsplashViewModel.fetchImages()
        unsplashViewModel.fetchCollections()

        setContent {

            val unsplashItems = unsplashViewModel.unsplashItems.observeAsState()
            val unsplashCollections = unsplashViewModel.unsplashCollections.observeAsState()

            unsplashViewModel.error.observe(this) {
                Toast.makeText(baseContext, R.string.main_unable_to_fetch_images, Toast.LENGTH_SHORT).show()
            }

            val isDarkTheme = remember { mutableStateOf(appPreferences.getDarkTheme()) }
            appPreferences.setDarkTheme(isDarkTheme.value)

            MaterialTheme(
                colors = if(isDarkTheme.value) colorDarkPalette else colorLightPalette,
                typography = typography
            ) {
//                MessageView(unsplashViewModel = unsplashViewModel)
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        AddBottomBarNavigation(
                            navController = navController,
                            isDarkTheme = isDarkTheme
                        )
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = it.calculateBottomPadding())
                    ) {
                        AddNavigationContent(
                            unsplashViewModel = unsplashViewModel,
                            navController = navController,
                            isDarkTheme = isDarkTheme,
                            unsplashItems = unsplashItems.value ?: emptyList(),
                            unsplashCollections = unsplashCollections.value ?: emptyList(),
                            onRefresh = { unsplashViewModel.forceFetchImages() },
                            onSearchAction = { keyword -> unsplashViewModel.searchImages(keyword) },
                            onOpenDetailsActivity = { url -> openDetailsActivity(url) }
                        )
                    }
                }
             }
        }
    }

    private fun openDetailsActivity(url: String) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(EXTRA_UNSPLASH_IMAGE_URL, url)

        startActivity(intent)
    }
}

@Composable
fun AddTopTabNavigation(
    unsplashItems: List<UnsplashItem>,
    unsplashCollections: List<UnsplashCollection>,
    onRefresh: () -> Unit,
    onSearchAction: (String) -> Unit,
    onOpenDetailsActivity: (String) -> Unit
) {
    val selected = remember { mutableStateOf(0) }

    Column {

        val actions = listOf(Tab.HOME, Tab.COLLECTIONS)
        TabRow(
            selectedTabIndex = selected.value,
            modifier = Modifier.height(48.dp),
            indicator = @Composable { tabPositions: List<TabPosition> ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selected.value])
                )
            }
        ) {
            actions.forEachIndexed { index, title ->

                Tab(
                    selected = selected.value == index,
                    onClick = {
                        selected.value = index
                    }
                ) {
                    Text(
                        text = stringResource(id = Tab.values()[index].tab),
                        style = typography.h1
                    )
                }
            }
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Transparent,
            content = {
                OnTabSelected(
                    selected = selected,
                    unsplashItems = unsplashItems,
                    unsplashCollections = unsplashCollections,
                    onRefresh = onRefresh,
                    onSearchAction = onSearchAction,
                    onOpenDetailsActivity = onOpenDetailsActivity
                )
            }
        )
    }
}

@Composable
fun OnTabSelected(
    selected: MutableState<Int>,
    unsplashItems: List<UnsplashItem>,
    unsplashCollections: List<UnsplashCollection>,
    onRefresh: () -> Unit,
    onSearchAction: (String) -> Unit,
    onOpenDetailsActivity: (String) -> Unit
) {
    when(selected.value) {
        Tab.HOME.ordinal -> {

            if (unsplashItems.isEmpty()) {
                AddLoadingAnimation()
                return
            }

            val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { onRefresh() },
                indicator = { state, trigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = trigger,
                        scale = true,
                    )
                }
            ) {
                AddImagesContent(
                    unsplashItems = unsplashItems,
                    onSearchAction = onSearchAction,
                    onOpenDetailsActivity = onOpenDetailsActivity
                )
            }
        }

        Tab.COLLECTIONS.ordinal -> {
            if (unsplashItems.isEmpty()) {
                AddLoadingAnimation()
                return
            }

            AddCollectionsContent(
                unsplashCollections = unsplashCollections,
                onOpenDetailsActivity = onOpenDetailsActivity
            )
        }
    }
}

@Composable
fun AddBottomBarNavigation(
    navController: NavHostController,
    isDarkTheme: MutableState<Boolean>
) {

    val items = listOf(
        BottomNavigationScreens.Home,
        BottomNavigationScreens.Favourites,
        BottomNavigationScreens.About,
        BottomNavigationScreens.Chat
    )

    val selectedIndex = remember { mutableStateOf(0) }

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary,
    ) {

        items.forEachIndexed { index, screen ->

            val isSelected = selectedIndex.value == index

            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = screen.drawResId),
                        contentDescription = stringResource(id = screen.stringResId),
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        stringResource(id = screen.stringResId),
                        style = typography.subtitle1
                    )
                },
                selected = isSelected,
                unselectedContentColor = if(isDarkTheme.value) Color.LightGray else Color.DarkGray,
                alwaysShowLabel = true,
                onClick = {
                    if (!isSelected) {
                        selectedIndex.value = index
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun AddNavigationContent(
    unsplashViewModel: UnsplashViewModel,
    navController: NavHostController,
    isDarkTheme: MutableState<Boolean>,
    unsplashItems: List<UnsplashItem>,
    unsplashCollections: List<UnsplashCollection>,
    onRefresh: () -> Unit,
    onSearchAction: (String) -> Unit,
    onOpenDetailsActivity: (String) -> Unit
) {
    NavHost(navController, startDestination = BottomNavigationScreens.Home.route) {
        composable(BottomNavigationScreens.Home.route) {
            AddTopTabNavigation(
                unsplashItems = unsplashItems,
                unsplashCollections = unsplashCollections,
                onRefresh = onRefresh,
                onSearchAction = onSearchAction,
                onOpenDetailsActivity = onOpenDetailsActivity
            )
        }

        composable(BottomNavigationScreens.Favourites.route) {
            AddFavouritesScreen()
        }

        composable(BottomNavigationScreens.About.route) {
            AboutScreen(
                isDarkTheme = isDarkTheme
            )
        }

        composable(BottomNavigationScreens.Chat.route) {
            MessageView(
                unsplashViewModel
            )
        }
    }
}

@Composable
fun AddUserInputAction(
    onSearchAction: (String) -> Unit
) {
    val search = remember { mutableStateOf("") }

    OutlinedTextField(
        value = search.value,
        onValueChange = { value ->
            search.value = value
        },
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = stringResource(id = R.string.main_search_hint),
                style = typography.h1,
                maxLines = 1
            )
        },
        leadingIcon = {
            val image = painterResource(id = R.drawable.ic_search)
            val description = stringResource(R.string.description_search)

            Image(
                painter = image,
                contentDescription = description
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions {
            onSearchAction(search.value)
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.LightGray
        )
    )
}

@Composable
fun AddUnsplashImage(
    url: String,
    author: String,
    description: String,
    onOpenDetailsActivity: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                onOpenDetailsActivity(url)
            },
        backgroundColor = Color.Transparent
    ) {

        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .build()
        )

        Image(
            painter = painter,
            contentDescription = stringResource(id = R.string.description_image_preview),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.Bottom
        ) {

            Text(
                text = description,
                style = typography.h2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = author,
                style = typography.h3,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}