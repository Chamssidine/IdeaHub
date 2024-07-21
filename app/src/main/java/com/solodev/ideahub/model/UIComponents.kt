package com.solodev.ideahub.model

import androidx.annotation.DrawableRes
import com.solodev.ideahub.R


data class BottomNavigationItem (
    val title: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    val route: String,
    val hasNews: Boolean,

    )
val bottomNavigationItems = listOf(
    BottomNavigationItem(
        title = "Home",
        selectedIcon = R.drawable.house_24px_filled,
        unselectedIcon = R.drawable.house_24px_outlined,
        route = "home",
        hasNews = false
    ),
    BottomNavigationItem(
        title = "Community",
        selectedIcon = R.drawable.diversity_2_24px_filled,
        unselectedIcon = R.drawable.diversity_2_24px_outlined,
        route = "community",
        hasNews = false
    ),
    BottomNavigationItem(
        title = "Gemini",
        selectedIcon = R.drawable.arcticons__google_gemini,
        unselectedIcon = R.drawable.arcticons__google_gemini_filled,
        route = "gemini",
        hasNews = false
    ),
    BottomNavigationItem(
        title = "Profile",
        selectedIcon = R.drawable.account_circle_24px_filled,
        unselectedIcon = R.drawable.account_circle_24px_outlined,
        route = "profile",
        hasNews = false
    ),

)