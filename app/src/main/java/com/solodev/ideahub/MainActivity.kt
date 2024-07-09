package com.solodev.ideahub

import UserThreadHistoryScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.solodev.ideahub.ui.screen.IdeaHubScreen
import com.solodev.ideahub.ui.screen.ThreadScreen.ThreadScreen
import com.solodev.ideahub.ui.screen.goalCreationScreen.CreateGoalButton
import com.solodev.ideahub.ui.screen.goalCreationScreen.DialogContent
import com.solodev.ideahub.ui.screen.goalCreationScreen.GoalCreationScreen
import com.solodev.ideahub.ui.screen.login.LoginScreen
import com.solodev.ideahub.ui.screen.userThreadScreen.UserThreadScreen
import com.solodev.ideahub.ui.screen.welcomeScreen.WelcomeScreen
import com.solodev.ideahub.ui.theme.IdeaHubTheme

@ExperimentalMaterial3Api
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
                    DialogContent()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IdeaHubTheme {
        LoginScreen()
    }
}