package com.yiptv.jwplayerrepoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jwplayer.pub.api.configuration.PlayerConfig
import com.jwplayer.pub.api.license.LicenseUtil
import com.jwplayer.pub.api.media.playlists.PlaylistItem
import com.jwplayer.pub.view.JWPlayerView
import com.yiptv.jwplayerrepoapp.ui.theme.JWPlayerRepoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JWPlayerRepoAppTheme {
                    StartApp()
            }
        }
    }
}

@Composable
fun StartApp() {
    Scaffold { paddingValues ->
        UserHomeScreen(paddingValues)
    }
}

@Composable
fun UserHomeScreen(
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(bottom = paddingValues.calculateBottomPadding()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val userHomeNavController = rememberNavController()
        Scaffold(
            topBar = { TopBar(navController = userHomeNavController) },
            bottomBar = { BottomBar(navController = userHomeNavController) }
        ) { paddingValues ->
            UserHomeNavigation(userHomeNavController, paddingValues)
        }
    }
}

@Composable
fun TopBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.primary)
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Image(
            modifier = Modifier.clickable {
                // TODO
            },
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_menu),
            contentDescription = null,
        )
        Image(
            modifier = Modifier.clickable {
                navController.navigate(AppScreen.UserOptionsScreen.name)
            },
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_user),
            contentDescription = null,
        )
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val items = listOf(
        UserHomeBottomNavItem.TV,
        UserHomeBottomNavItem.VOD,
        UserHomeBottomNavItem.Radio,
    )
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 9.sp,
                    )
                },
                selectedContentColor = MaterialTheme.colors.onPrimary,
                unselectedContentColor = MaterialTheme.colors.onPrimary.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
                }
            )
        }
    }
}

@Composable
fun UserHomeNavigation(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(navController = navController, startDestination = AppScreen.TvChannelsScreen.name) {
        composable(AppScreen.TvChannelsScreen.name) {
            PlayerScreen(
                navController = navController,
                paddingValues = paddingValues
            )
        }
        composable(AppScreen.VodTitlesScreen.name) {
            PlayerScreen(
                navController = navController,
                paddingValues = paddingValues
            )
        }
        composable(AppScreen.RadioChannelsScreen.name) {
            PlayerScreen(
                navController = navController,
                paddingValues = paddingValues
            )
        }
        composable(AppScreen.PlayerScreen.name) {
            PlayerScreen(
                navController = navController,
                paddingValues = paddingValues
            )
        }
        composable(AppScreen.UserOptionsScreen.name) {
            PlayerScreen(
                navController = navController,
                paddingValues = paddingValues
            )
        }
    }
}

sealed class UserHomeBottomNavItem(var title: String, var icon: Int, var route: String) {
    object TV : UserHomeBottomNavItem(
        "TV",
        R.drawable.ic_tv,
        AppScreen.TvChannelsScreen.name
    )

    object VOD : UserHomeBottomNavItem(
        "VOD",
        R.drawable.ic_vod,
        AppScreen.VodTitlesScreen.name
    )

    object Radio : UserHomeBottomNavItem(
        "Radio",
        R.drawable.ic_radio,
        AppScreen.RadioChannelsScreen.name
    )
}

enum class AppScreen {
    TvChannelsScreen,
    VodTitlesScreen,
    RadioChannelsScreen,
    PlayerScreen,
    UserOptionsScreen
}

@Composable
fun PlayerScreen(
    navController: NavController,
    paddingValues: PaddingValues,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(bottom = paddingValues.calculateBottomPadding()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VideoPlayer("https://yiptv-hls-gametoon.unitedteleports.tv/YIP/Gametoon.m3u8")
    }
}

@Composable
fun VideoPlayer(videoUrl: String?) {
    LicenseUtil().setLicenseKey(LocalContext.current, "<Add your license here>")
    val playerView = JWPlayerView(LocalContext.current)
    val player = playerView.player
    val playlistItem = PlaylistItem.Builder()
        .file(videoUrl)
        .build()
    val playlist: MutableList<PlaylistItem> = ArrayList()
    playlist.add(playlistItem)
    val config = PlayerConfig.Builder()
        .playlist(playlist)
        .build()
    player.setup(config)
    player.play()

    AndroidView(
        modifier = Modifier.height(300.dp),
        factory = {
            playerView
        }
    )

    DisposableEffect(key1 = playerView) {
        onDispose {
            player.stop()
        }
    }
}