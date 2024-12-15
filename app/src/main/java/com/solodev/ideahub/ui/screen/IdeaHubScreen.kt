 package com.solodev.ideahub.ui.screen

import MailConfirmationScreen
import UserThreadHistoryScreen
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.solodev.ideahub.R
import com.solodev.ideahub.model.GoalScreenTabItem
import com.solodev.ideahub.model.ThreadItem
import com.solodev.ideahub.model.bottomNavigationItems
import com.solodev.ideahub.ui.ViewModels.ScreenManagerVM
import com.solodev.ideahub.ui.screen.communityScreen.CommunityScreen
import com.solodev.ideahub.ui.screen.dayplanScreen.DayPlanScreen
import com.solodev.ideahub.ui.screen.dayplanScreen.DayPlanViewModel
import com.solodev.ideahub.ui.screen.gemini_chat.GeminiChatScreen
import com.solodev.ideahub.ui.screen.goalCreationScreen.GoalCreationScreen
import com.solodev.ideahub.ui.screen.goalScreen.ActiveDiscussionSection
import com.solodev.ideahub.ui.screen.goalScreen.GoalScreen
import com.solodev.ideahub.ui.screen.goalScreen.GoalScreenViewModel
import com.solodev.ideahub.ui.screen.login.LoginScreen
import com.solodev.ideahub.ui.screen.popularGroup.CreateGroupConfirmationScreen
import com.solodev.ideahub.ui.screen.popularGroup.CreateGroupConfirmationScreenPreview
import com.solodev.ideahub.ui.screen.popularGroup.CreateGroupScreen
import com.solodev.ideahub.ui.screen.popularGroup.PopularGroupScreen
import com.solodev.ideahub.ui.screen.popularGroup.PopularGroupViewModel
import com.solodev.ideahub.ui.screen.sign_up.SignUpScreen
import com.solodev.ideahub.ui.screen.threadScreen.GeneralThreadListContent
import com.solodev.ideahub.ui.screen.threadScreen.ThreadItem
import com.solodev.ideahub.ui.screen.userProfileScreen.UserProfileScreen
import com.solodev.ideahub.ui.screen.userThreadScreen.UserThreadScreen
import com.solodev.ideahub.ui.screen.userThreadScreen.UserThreadViewModel
import com.solodev.ideahub.ui.screen.welcomeScreen.WelcomeScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import kotlin.reflect.typeOf


 @ExperimentalMaterial3Api
@Composable
fun IdeaHubScreen(
    modifier: Modifier = Modifier,
    screenManagerVM: ScreenManagerVM = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
) {
    val uiState by screenManagerVM.uiState.collectAsState()

    var selectedItem by rememberSaveable { mutableStateOf(0) }

    val goalTabItems = listOf(
        GoalScreenTabItem(
            title = R.string.general_thread,
            selected = true,
            showItem = true,
            screenContent = { GeneralThreadListContent(
                modifier = Modifier
                    .height(
                        1000.dp
                    )
                    .fillMaxWidth(),
                onThreadClick = { threadItem ->
                    navController.navigate("${Routes.UserThreadScreen.name}/${threadItem.threadId}")
                }

            ) }
        ),
        GoalScreenTabItem(
            title = R.string.goal_screen_tab_item_1,
            selected = false,
            showItem = false,
            screenContent = { GoalScreen(goalScreenViewModel = hiltViewModel<GoalScreenViewModel>()) }
        ),
        GoalScreenTabItem(
            title = R.string.goal_screen_tab_item_2,
            selected = false,
            showItem = false,
            screenContent = { DayPlanScreen(viewModel = hiltViewModel<DayPlanViewModel>()) }
        ),
        GoalScreenTabItem(
            title = R.string.goal_screen_tab_item_3,false, true,
            screenContent = { PopularGroupScreen(
                viewModel = hiltViewModel<PopularGroupViewModel>(),
                onCreateGroupButtonCliked = {
                    navController.navigate(Routes.CreateGroup.name)
                }
            ) }
        ),
        GoalScreenTabItem(
            R.string.goal_screen_tab_item_4,false, true,
            screenContent = {
                ActiveDiscussionSection()
            }
        ),

        )
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            if(uiState.showBottomNavigationBar)
            {
                NavigationBar(
                    modifier = modifier.height(60.dp)
                ){
                    bottomNavigationItems.forEachIndexed { index, bottomNavigationItem ->

                        NavigationBarItem(
                            selected = selectedItem == index
                            ,
                            onClick = {
                                selectedItem = index
                                navController.navigate(bottomNavigationItem.route)
                            },
                            icon = {
                                BadgedBox(
                                    badge = {
                                        if(bottomNavigationItem.hasNews)
                                        {
                                            Badge()
                                        }
                                    }
                                ) {

                                    Icon(
                                        painter = if(selectedItem == index) painterResource(id = bottomNavigationItem.selectedIcon) else painterResource(id = bottomNavigationItem.unselectedIcon),
                                        contentDescription = bottomNavigationItem.title
                                    )

                                }
                            }
                        )
                    }
                }
            }

        }
    ) {
            innerPadding ->
        val innerPadding = innerPadding
        NavHost(
            navController = navController,
            startDestination = uiState.startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.SignUp.name) {
                SignUpScreen(
                    onSignUpButtonClicked = {
                        if(uiState.isFirstLaunch)
                            navController.navigate(Routes.Welcome.name)
                        else
                            navController.navigate(Routes.Home.name) },
                    onLoginButtonClicked = {navController.navigate(Routes.Login.name)}
                )

            }
            composable(Routes.Login.name) {
                LoginScreen(
                    onLoginButtonClicked = {
                        navController.navigate(Routes.Home.name)
                        screenManagerVM.showBottomNavigationBar(true)
                    },
                    onForgotPasswordButtonClicked = {

                    },
                    onSignUpButtonClicked = {navController.navigate(Routes.SignUp.name)}
                )
            }
            composable(Routes.MailConfirmation.name) {
                MailConfirmationScreen(
                    onConfirmButtonClicked = {navController.navigate(Routes.Login.name)},
                    onChangeEmailClicked = {navController.navigate(Routes.SignUp.name)}
                )
            }

            composable(Routes.Community.name){
                CommunityScreen()

            }
            composable(Routes.Gemini.name){
                GeminiChatScreen()
            }
            composable(Routes.Profile.name){
                UserProfileScreen()
            }
            composable(Routes.Home.name){
                HomeScreen(
                    goalTabItems
                )
            }
            composable(Routes.GoalCreation.name) {
                GoalCreationScreen(
                    goalScreenViewModel = hiltViewModel<GoalScreenViewModel>(),
                    onGoalCreated = {
                        Log.d("IdeaHubScreen", "Goal created callback")
                        screenManagerVM.showBottomNavigationBar(true)
                        navController.navigate(Routes.Home.name)
                        Log.d("IdeaHubScreen", "Navigating to GoalAndTasks")
                    }
                )
            }
            composable(Routes.Welcome.name){
                WelcomeScreen(
                    onLetsGoClicked = {

                        navController.navigate(Routes.GoalCreation.name)

                    }
                )
            }
            composable(Routes.Thread.name){
                GoalScreen(
                    goalScreenViewModel = hiltViewModel<GoalScreenViewModel>(),
                )
            }
            composable(Routes.ThreadHistory.name) {

                    UserThreadHistoryScreen()
            }
            composable(
                Routes.CreateGroup.name) {
                CreateGroupScreen(
                    onNextButtonCLicked = {
                        navController.navigate(Routes.ConfirmCreateGoal.name)

                    },
                    onBackPress = {
                        navController.navigateUp()
                    }
                )
            }
            composable(Routes.ConfirmCreateGoal.name) {
                CreateGroupConfirmationScreen(
                    onBackPress = {
                        navController.navigateUp()
                    }
                )
            }
            composable(
                route = "${Routes.UserThreadScreen.name}/{threadId}",
                arguments = listOf(navArgument("threadId") { type = NavType.StringType })
            ) { backStackEntry ->
                val threadId = backStackEntry.arguments?.getString("threadId")
                val viewModel: UserThreadViewModel = hiltViewModel()

                // Fetch the thread item when the screen is shown
                LaunchedEffect(threadId) {
                    threadId?.let { viewModel.fetchThreadItem(it) }
                }

                UserThreadScreen(threadItem = viewModel.getSelectedItem()!!)
            }


        }

    }
}