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

data class UserProfile(
    val name: String,
    val image: String,
    val publicationTime: Int
)

data class ThreadItem(
    val threadTitle: String,
    val threadDescription: String,
    val threadImage: String,
    val threadDate: String,
    val contributionCount: Int,
    val category: String,
    val userProfile: UserProfile
)


val threadItems = listOf(
    ThreadItem(
        threadTitle = "Understanding Kotlin Coroutines",
        threadDescription = "A comprehensive guide to using coroutines in Kotlin for asynchronous programming.",
        threadImage = "https://example.com/images/kotlin_coroutines.png",
        threadDate = "2023-06-15",
        contributionCount = 45,
        category = "Technology",
        userProfile = UserProfile(
            name = "John Doe",
            image = "https://example.com/images/john_doe.png",
            publicationTime = 2
        )
    ),
    ThreadItem(
        threadTitle = "Mastering Jetpack Compose",
        threadDescription = "Learn how to build beautiful UI with Jetpack Compose in Android development.",
        threadImage = "https://example.com/images/jetpack_compose.png",
        threadDate = "2023-07-21",
        contributionCount = 30,
        category = "Technology",
        userProfile = UserProfile(
            name = "Jane Smith",
            image = "https://example.com/images/jane_smith.png",
            publicationTime = 5
        )
    ),
    ThreadItem(
        threadTitle = "Exploring Firebase Authentication",
        threadDescription = "An in-depth look at integrating Firebase Authentication into your Android apps.",
        threadImage = "https://example.com/images/firebase_auth.png",
        threadDate = "2023-08-10",
        contributionCount = 20,
        category = "Technology",
        userProfile = UserProfile(
            name = "Alice Johnson",
            image = "https://example.com/images/alice_johnson.png",
            publicationTime = 10
        )
    ),
    ThreadItem(
        threadTitle = "Effective Dependency Injection with Hilt",
        threadDescription = "Tips and tricks for using Hilt to manage dependencies in your Android projects.",
        threadImage = "https://example.com/images/hilt.png",
        threadDate = "2023-09-05",
        contributionCount = 25,
        category = "Technology",
        userProfile = UserProfile(
            name = "Bob Brown",
            image = "https://example.com/images/bob_brown.png",
            publicationTime = 8
        )
    ),
    ThreadItem(
        threadTitle = "Advanced Kotlin Features",
        threadDescription = "Explore advanced features of Kotlin such as DSLs, type-safe builders, and more.",
        threadImage = "https://example.com/images/advanced_kotlin.png",
        threadDate = "2023-10-01",
        contributionCount = 35,
        category = "Technology",
        userProfile = UserProfile(
            name = "Charlie Davis",
            image = "https://example.com/images/charlie_davis.png",
            publicationTime = 3
        )
    ),
    ThreadItem(
        threadTitle = "The Future of Renewable Energy",
        threadDescription = "A discussion on the latest advancements in renewable energy technologies.",
        threadImage = "https://example.com/images/renewable_energy.png",
        threadDate = "2023-11-10",
        contributionCount = 50,
        category = "Science",
        userProfile = UserProfile(
            name = "Dana Lee",
            image = "https://example.com/images/dana_lee.png",
            publicationTime = 1
        )
    ),
    ThreadItem(
        threadTitle = "Economic Impacts of Climate Change",
        threadDescription = "Analyzing how climate change affects global economies and financial markets.",
        threadImage = "https://example.com/images/climate_change.png",
        threadDate = "2023-12-05",
        contributionCount = 40,
        category = "Economy",
        userProfile = UserProfile(
            name = "Evan Green",
            image = "https://example.com/images/evan_green.png",
            publicationTime = 6
        )
    ),
    ThreadItem(
        threadTitle = "Breakthroughs in Medical Research",
        threadDescription = "A look at the latest breakthroughs and innovations in medical research.",
        threadImage = "https://example.com/images/medical_research.png",
        threadDate = "2024-01-20",
        contributionCount = 60,
        category = "Health",
        userProfile = UserProfile(
            name = "Fiona White",
            image = "https://example.com/images/fiona_white.png",
            publicationTime = 4
        )
    ),
    ThreadItem(
        threadTitle = "Innovations in Artificial Intelligence",
        threadDescription = "Exploring the latest trends and breakthroughs in AI technologies.",
        threadImage = "https://example.com/images/ai_innovation.png",
        threadDate = "2024-02-10",
        contributionCount = 70,
        category = "Technology",
        userProfile = UserProfile(
            name = "Grace Miller",
            image = "https://example.com/images/grace_miller.png",
            publicationTime = 7
        )
    ),
    ThreadItem(
        threadTitle = "New Frontiers in Space Exploration",
        threadDescription = "Discussion on the latest missions and technologies in space exploration.",
        threadImage = "https://example.com/images/space_exploration.png",
        threadDate = "2024-03-05",
        contributionCount = 80,
        category = "Science",
        userProfile = UserProfile(
            name = "Hank Williams",
            image = "https://example.com/images/hank_williams.png",
            publicationTime = 9
        )
    )
)


val groupItemData = listOf(
    GroupItemData(
        groupName = "Science",
        groupDescription = "This is a group description",
        groupeImage = "https://pixabay.com/fr/users/kadirkritik-2019309/?utm_source=link-attribution&utm_medium=referral&utm_campaign=image&utm_content=1244649"
    ),
    GroupItemData(
        groupName = "Maths",
        groupDescription = "This is a group description",
        groupeImage = "https://pixabay.com/fr/users/marandap-7632346/?utm_source=link-attribution&utm_medium=referral&utm_campaign=image&utm_content=5247958"
    ),
    GroupItemData(
        groupName = "Physics",
        groupDescription = "This is a group description",
        groupeImage ="https://pixabay.com/fr/photos/le-jeu-parc-amusement-nuageux-1715803/"
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