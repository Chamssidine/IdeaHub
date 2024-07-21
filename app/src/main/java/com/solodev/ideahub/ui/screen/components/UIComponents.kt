package com.solodev.ideahub.ui.screen.components

import androidx.annotation.DrawableRes
import com.solodev.ideahub.R


data class BottomNavigationItem (
    val title: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    val route: String,
    val hasNews: Boolean,

    )

data class CommunityTabItem(
    val title: String,
    val selected: Boolean,
)

data class GroupItemData    (
    val groupName: String,
    val groupDescription: String,
    val groupeImage: String,
)


val groupItemData = listOf(
    GroupItemData(
        groupName = "Science",
        groupDescription = "This is a group description",
        groupeImage = "https://pixabay.com/fr/users/kadirkritik-2019309/?utm_source=link-attribution&utm_medium=referral&utm_campaign=image&utm_content=1244649\">Kadir Kritik</a> de <a href=\"https://pixabay.com/fr//?utm_source=link-attribution&utm_medium=referral&utm_campaign=image&utm_content=1244649"
    ),
    GroupItemData(
        groupName = "Maths",
        groupDescription = "This is a group description",
        groupeImage = "https://pixabay.com/fr/users/marandap-7632346/?utm_source=link-attribution&utm_medium=referral&utm_campaign=image&utm_content=5247958\">Mario Aranda</a> de <a href=\"https://pixabay.com/fr//?utm_source=link-attribution&utm_medium=referral&utm_campaign=image&utm_content=5247958"    ),
    GroupItemData(
        groupName = "Physics",
        groupDescription = "This is a group description",
        groupeImage ="https://pixabay.com/fr/users/zerpixelt-7250091/?utm_source=link-attribution&utm_medium=referral&utm_campaign=image&utm_content=3079904\">zerpixelt</a> de <a href=\"https://pixabay.com/fr//?utm_source=link-attribution&utm_medium=referral&utm_campaign=image&utm_content=3079904"
    ),
    GroupItemData(
        groupName = "Astronomy",
        groupDescription = "This is a group description",
        groupeImage ="https://pixabay.com/fr/users/noel_bauza-2019050/?utm_source=link-attribution&utm_medium=referral&utm_campaign=image&utm_content=1197755\">Noel Bauza</a> de <a href=\"https://pixabay.com/fr//?utm_source=link-attribution&utm_medium=referral&utm_campaign=image&utm_content=1197755"
    ),

)
val communityTabItems = listOf(
    CommunityTabItem(
        title = "Your Group",
        selected = true
    ),
    CommunityTabItem(
        title = "Explore",
        selected = false
    ),
    CommunityTabItem(
        title = "Active Discussion",
        selected = false
    ),
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
        selectedIcon = R.drawable.baseline_auto_awesome_black_24,
        unselectedIcon = R.drawable.outline_auto_awesome_24,
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