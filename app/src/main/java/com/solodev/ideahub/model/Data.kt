package com.solodev.ideahub.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.solodev.ideahub.R
import com.solodev.ideahub.ui.screen.dayplanScreen.DayPlanScreen
import com.solodev.ideahub.ui.screen.dayplanScreen.DayPlanViewModel
import com.solodev.ideahub.ui.screen.goalScreen.ActiveDiscussionSection
import com.solodev.ideahub.ui.screen.goalScreen.GoalScreen
import com.solodev.ideahub.ui.screen.goalScreen.GoalScreenViewModel
import com.solodev.ideahub.ui.screen.popularGroup.PopularGroupScreen
import com.solodev.ideahub.ui.screen.popularGroup.PopularGroupViewModel
import com.solodev.ideahub.ui.screen.threadScreen.GeneralThreadListContent
import com.solodev.ideahub.ui.screen.threadScreen.ThreadScreen
import kotlinx.serialization.Serializable

import java.util.UUID

data class BottomNavigationItem (
    val title: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    val route: String,
    val hasNews: Boolean,

    )
data class GoalScreenTabItem(
    @StringRes val title: Int,
    val selected: Boolean,
    val showItem: Boolean = false,
    val screenContent: @Composable () -> Unit = {}
)
data class CommunityTabItem(
    val title: String,
    val selected: Boolean,
)

@Serializable
data class ThreadItem(
    var threadId: String = UUID.randomUUID().toString(),
    val threadTitle: String = "",
    val threadDescription: String = "",
    val threadImage: String = "",
    val threadDate: String = "",
    val contributionCount: Int = 0,
    val category: String = "",
    val userProfile: UserProfile = UserProfile(
        name = "",
        biography = "",
        profileImage = "",
        publicationTime = 0,
        personalStatistics = PersonalStatistics(0, 0),
        userSettings = UserSettings(false, "", ""),
    )
)

data class CommunityCategory(
    var categoryName: String,
    val categoryImage: String,
    val categoryId: String ="",
    val memberCount: Int,
    val groupList: List<GroupItemData> = emptyList(),
    ) {
    constructor() : this("", "",    "",0,emptyList())
}

data class GroupItemData (
    val groupId: String = UUID.randomUUID().toString(),
    val groupName: String,
    val groupDescription: String,
    val groupImage: String,
    val memberList: List<User> = emptyList(),
) {
    constructor() : this("", "",    "","",emptyList())
}

data class ConversationMessage(
    val aiMessage: AiMessage,
    val userMessage: UserMessage,
    val discussionTitle: String
)

data class AiMessage(
    val message: String,
    val time: String
)

data class UserMessage(
    val message: String,
    val time: String
)

@Serializable
data class UserProfile(
    val name: String,
    val biography: String,
    val profileImage: String,
    val publicationTime: Int,
    val personalStatistics: PersonalStatistics,
    val userSettings: UserSettings
)

@Serializable
data class PersonalStatistics(
    val reachedGoalPercentage: Int,
    val contributions: Int
)

@Serializable
data class UserSettings(
    val notificationsEnabled: Boolean,
    val selectedLanguage: String,
    val selectedTheme: String
)


val aiMessage1 = AiMessage(
    message = "Hello! How can I assist you today?",
    time = "2024-07-25T10:00:00"
)

val userMessage1 = UserMessage(
    message = "I need help with my project.",
    time = "2024-07-25T10:01:00"
)

val conversationMessage1 = ConversationMessage(
    aiMessage = aiMessage1,
    userMessage = userMessage1,
    discussionTitle = "Project Assistance"
)

val aiMessage2 = AiMessage(
    message = "Sure! What specifically do you need help with?",
    time = "2024-07-25T10:02:00"
)

val userMessage2 = UserMessage(
    message = "I am having trouble with data structuring.",
    time = "2024-07-25T10:03:00"
)

val conversationMessage2 = ConversationMessage(
    aiMessage = aiMessage2,
    userMessage = userMessage2,
    discussionTitle = "Data Structuring"
)

val conversationMessages = listOf(conversationMessage1, conversationMessage2)

// Données de statistiques personnelles
val personalStatistics = PersonalStatistics(
    reachedGoalPercentage = 60, // Pourcentage d'objectifs atteints
    contributions = 30 // Nombre de contributions
)

// Données de paramètres utilisateur
val userSettings = UserSettings(
    notificationsEnabled = true, // Notifications activées
    selectedLanguage = "English", // Langue sélectionnée
    selectedTheme = "Light" // Thème sélectionné
)

// Données de profil utilisateur
val userProfile = UserProfile(
    name = "CHRISTOPHER",
    biography = "Biographie",
    publicationTime = 20,
    profileImage = "https://example.com/path/to/profile_image.png", // URL de l'image de profil
    personalStatistics = personalStatistics,
    userSettings = userSettings
)


val threadItems = listOf(
    ThreadItem(
        threadTitle = "Understanding Kotlin Coroutines",
        threadDescription = "A comprehensive guide to using coroutines in Kotlin for asynchronous programming.ajsjahsjhgdjhgajsdhgjsahdgjsashgdjhagdjsahgdjhagsjdhgsajdhgajsdgj",
        threadImage = "https://example.com/images/kotlin_coroutines.png",
        threadDate = "2023-06-15",
        contributionCount = 45,
        category = "Technology",
        userProfile = UserProfile(
            name = "John Doe",
            biography = "Software Engineer with a passion for Kotlin.",
            profileImage = "https://example.com/images/john_doe.png",
            personalStatistics = PersonalStatistics(60, 45),
            userSettings = UserSettings(true, "English", "Light"),
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
            biography = "Android Developer and UI/UX Designer.",
            profileImage = "https://example.com/images/jane_smith.png",
            personalStatistics = PersonalStatistics(70, 30),
            userSettings = UserSettings(true, "English", "Dark"),
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
            biography = "Mobile App Developer with expertise in Firebase.",
            profileImage = "https://example.com/images/alice_johnson.png",
            personalStatistics = PersonalStatistics(80, 20),
            userSettings = UserSettings(false, "French", "Light"),
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
            biography = "Senior Android Developer specializing in Dagger and Hilt.",
            profileImage = "https://example.com/images/bob_brown.png",
            personalStatistics = PersonalStatistics(50, 25),
            userSettings = UserSettings(true, "Spanish", "Dark"),
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
            biography = "Kotlin Enthusiast and Contributor.",
            profileImage = "https://example.com/images/charlie_davis.png",
            personalStatistics = PersonalStatistics(75, 35),
            userSettings = UserSettings(true, "English", "Light"),
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
            biography = "Environmental Scientist focused on renewable energy.",
            profileImage = "https://example.com/images/dana_lee.png",
            personalStatistics = PersonalStatistics(65, 50),
            userSettings = UserSettings(true, "English", "Dark"),
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
            biography = "Economist researching climate change impacts.",
            profileImage = "https://example.com/images/evan_green.png",
            personalStatistics = PersonalStatistics(85, 40),
            userSettings = UserSettings(true, "French", "Light"),
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
            biography = "Medical Researcher at a leading university.",
            profileImage = "https://example.com/images/fiona_white.png",
            personalStatistics = PersonalStatistics(90, 60),
            userSettings = UserSettings(false, "German", "Dark"),
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
            biography = "AI Researcher and Developer.",
            profileImage = "https://example.com/images/grace_miller.png",
            personalStatistics = PersonalStatistics(95, 70),
            userSettings = UserSettings(true, "Japanese", "Light"),
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
            biography = "Astrophysicist working with space agencies.",
            profileImage = "https://example.com/images/hank_williams.png",
            personalStatistics = PersonalStatistics(100, 80),
            userSettings = UserSettings(true, "English", "Dark"),
            publicationTime = 9
        )
    )
)





// Création de plusieurs utilisateurs
val user1 = User(name = "Alice", photo = "alice_photo_url", contributionCount = 10)
val user2 = User(name = "Bob", photo = "bob_photo_url", contributionCount = 5)
val user3 = User(name = "Charlie", photo = "charlie_photo_url", contributionCount = 3)
val user4 = User(name = "David", photo = "david_photo_url", contributionCount = 7)
val user5 = User(name = "Eve", photo = "eve_photo_url", contributionCount = 2)
val user6 = User(name = "Frank", photo = "frank_photo_url", contributionCount = 4)
val user7 = User(name = "Grace", photo = "grace_photo_url", contributionCount = 6)
val user8 = User(name = "Hank", photo = "hank_photo_url", contributionCount = 1)

// Création de groupes pour la catégorie "Alimentation and Nutrition"
val nutritionGroup1 = GroupItemData(
    groupName = "Healthy Eating",
    groupDescription = "Discussions about healthy eating habits.",
    groupImage = "https://pixabay.com/fr/photos/le-jeu-parc-amusement-nuageux-1715803/",
    memberList = listOf(user1, user2)
)

val nutritionGroup2 = GroupItemData(
    groupName = "Vegan Recipes",
    groupDescription = "Sharing vegan recipes and tips.",
    groupImage = "https://pixabay.com/fr/users/marandap-7632346/?utm_source=link-attribution&utm_medium=referral&utm_campaign=image&utm_content=5247958",
    memberList = listOf(user3, user4)
)

// Création de groupes pour la catégorie "Personal Development"
val personalDevGroup1 = GroupItemData(
    groupName = "Mindfulness",
    groupDescription = "Practices and discussions on mindfulness.",
    groupImage = "https://pixabay.com/fr/users/kadirkritik-2019309/?utm_source=link-attribution&utm_medium=referral&utm_campaign=image&utm_content=1244649",
    memberList = listOf(user5, user6)
)

val personalDevGroup2 = GroupItemData(
    groupName = "Time Management",
    groupDescription = "Tips and tricks for better time management.",
    groupImage = "https://pixabay.com/fr/users/noel_bauza-2019050/?utm_source=link-attribution&utm_medium=referral&utm_campaign=image&utm_content=1197755",
    memberList = listOf(user7, user8)
)

// Création de groupes pour la catégorie "Technologies and Innovation"
val techGroup1 = GroupItemData(
    groupName = "AI and Machine Learning",
    groupDescription = "Latest trends and discussions on AI.",
    groupImage = "https://pixabay.com/fr/photos/le-jeu-parc-amusement-nuageux-1715803/",
    memberList = listOf(user1, user3)
)

val techGroup2 = GroupItemData(
    groupName = "Blockchain",
    groupDescription = "Exploring blockchain technology.",
    groupImage = "https://pixabay.com/fr/photos/le-jeu-parc-amusement-nuageux-1715803/",
    memberList = listOf(user2, user4)
)

// Création de groupes pour la catégorie "Personal Finance"
val financeGroup1 = GroupItemData(
    groupName = "Investing",
    groupDescription = "Tips and strategies for investing.",
    groupImage = "https://pixabay.com/fr/photos/le-jeu-parc-amusement-nuageux-1715803/",
    memberList = listOf(user5, user7)
)

val financeGroup2 = GroupItemData(
    groupName = "Budgeting",
    groupDescription = "How to budget effectively.",
    groupImage = "https://pixabay.com/fr/photos/le-jeu-parc-amusement-nuageux-1715803/",
    memberList = listOf(user6, user8)
)

// Création des catégories avec les groupes associés
val communityCategories = listOf(
    CommunityCategory(
        categoryName = "Alimentation and Nutrition",
        categoryImage = "https://pixabay.com/fr/photos/alimentation-saine-healthy-eating-1238255/",
        memberCount = nutritionGroup1.memberList.size + nutritionGroup2.memberList.size,
        groupList = listOf(nutritionGroup1, nutritionGroup2)
    ),
    CommunityCategory(
        categoryName = "Personal Development",
        categoryImage = "https://pixabay.com/fr/photos/d%c3%a9veloppement-personnel-1207839/",
        memberCount = personalDevGroup1.memberList.size + personalDevGroup2.memberList.size,
        groupList = listOf(personalDevGroup1, personalDevGroup2)
    ),
    CommunityCategory(
        categoryName = "Technologies and Innovation",
        categoryImage = "https://pixabay.com/fr/photos/technologies-innovation-id%c3%a9es-1239581/",
        memberCount = techGroup1.memberList.size + techGroup2.memberList.size,
        groupList = listOf(techGroup1, techGroup2)
    ),
    CommunityCategory(
        categoryName = "Personal Finance",
        categoryImage = "https://pixabay.com/fr/photos/finances-personnelles-budget-1239484/",
        memberCount = financeGroup1.memberList.size + financeGroup2.memberList.size,
        groupList = listOf(financeGroup1, financeGroup2)
    )
)

val groupItemData = listOf(
    nutritionGroup1,
    nutritionGroup2,
    personalDevGroup1,
    personalDevGroup2,
    techGroup1,
    techGroup2,
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

enum class PrivacyLevel {
    PUBLIC, PRIVATE, PROTECTED, NONE
}

data class Privacy(
   val privacy: MutableMap<PrivacyLevel, String>
)

val privacy = Privacy(
    privacy = mutableMapOf(
        PrivacyLevel.PUBLIC to "Anyone can join and view the group.",
        PrivacyLevel.PRIVATE to "Only invited members can join and view the group.",
        PrivacyLevel.PROTECTED to "The group is visible to everyone, but only invited members can join."
    )
)