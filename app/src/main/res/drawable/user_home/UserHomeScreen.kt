package com.yiptv.android.presentation.user_home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yiptv.android.R
import com.yiptv.android.presentation.common.components.AppLogo
import com.yiptv.android.presentation.navigation.AppScreen
import com.yiptv.android.presentation.navigation.navigateToUserHomeScreen
import com.yiptv.jwplayerrepoapp.AppScreen
import com.yiptv.jwplayerrepoapp.R

@Composable
fun UserHomeScreen(
    navController: NavController,
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
            contentDescription = stringResource(id = R.string.user_home_menu),
        )
        AppLogo(36.dp)
        Image(
            modifier = Modifier.clickable {
                navigateToUserHomeScreen(navController, AppScreen.UserOptionsScreen.name)
            },
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_user),
            contentDescription = stringResource(id = R.string.user_home_user_options),
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
                    navigateToUserHomeScreen(navController, item.route)
                }
            )
        }
    }
}
