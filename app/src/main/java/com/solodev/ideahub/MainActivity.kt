package com.solodev.ideahub
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.solodev.ideahub.ui.screen.IdeaHubScreen
import com.solodev.ideahub.ui.screen.communityScreen.CommunityScreen
import com.solodev.ideahub.ui.screen.gemini_chat.GeminiChatScreen
import com.solodev.ideahub.ui.screen.goalCreationScreen.GoalCreationScreen
import com.solodev.ideahub.ui.screen.goalCreationScreen.GoalCreationViewModel
import com.solodev.ideahub.ui.screen.goalScreen.GoalScreen
import com.solodev.ideahub.ui.screen.userProfileScreen.UserProfileScreen
import com.solodev.ideahub.ui.theme.IdeaHubTheme
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IdeaHubTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GoalCreationScreen(goalCreationViewModel = GoalCreationViewModel())
                }
            }
        }
    }
}


@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IdeaHubTheme {
        IdeaHubScreen()
    }
}