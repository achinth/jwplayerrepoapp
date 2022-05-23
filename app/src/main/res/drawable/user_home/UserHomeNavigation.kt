package com.yiptv.android.presentation.user_home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yiptv.android.presentation.navigation.AppScreen
import com.yiptv.android.presentation.player.PlayerScreen
import com.yiptv.android.presentation.radio_channels.RadioChannelsScreen
import com.yiptv.android.presentation.tv_channels.TvChannelsScreen
import com.yiptv.android.presentation.user_options.UserOptionsScreen
import com.yiptv.android.presentation.vod_titles.VodTitlesScreen

@Composable
fun UserHomeNavigation(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(navController = navController, startDestination = AppScreen.TvChannelsScreen.name) {
        composable(AppScreen.TvChannelsScreen.name) {
            TvChannelsScreen(
                navController = navController,
                paddingValues = paddingValues
            )
        }
        composable(AppScreen.VodTitlesScreen.name) {
            VodTitlesScreen(
                navController = navController,
                paddingValues = paddingValues
            )
        }
        composable(AppScreen.RadioChannelsScreen.name) {
            RadioChannelsScreen(
                navController = navController,
                paddingValues = paddingValues
            )
        }
        composable(AppScreen.PlayerScreen.name + "/{id}/{type}") {
            PlayerScreen(
                navController = navController,
                paddingValues = paddingValues
            )
        }
        composable(AppScreen.UserOptionsScreen.name) {
            UserOptionsScreen(
                navController = navController,
                paddingValues = paddingValues
            )
        }
    }
}