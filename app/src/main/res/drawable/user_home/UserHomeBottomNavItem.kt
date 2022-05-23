package com.yiptv.android.presentation.user_home

import com.yiptv.android.R
import com.yiptv.android.presentation.common.App
import com.yiptv.android.presentation.navigation.AppScreen

sealed class UserHomeBottomNavItem(var title: String, var icon: Int, var route: String) {
    object TV : UserHomeBottomNavItem(
        App.appResources.getString(R.string.user_home_tv),
        R.drawable.ic_tv,
        AppScreen.TvChannelsScreen.name
    )

    object VOD : UserHomeBottomNavItem(
        App.appResources.getString(R.string.user_home_vod),
        R.drawable.ic_vod,
        AppScreen.VodTitlesScreen.name
    )

    object Radio : UserHomeBottomNavItem(
        App.appResources.getString(R.string.user_home_radio),
        R.drawable.ic_radio,
        AppScreen.RadioChannelsScreen.name
    )
}