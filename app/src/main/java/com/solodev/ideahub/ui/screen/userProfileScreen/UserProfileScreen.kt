package com.solodev.ideahub.ui.screen.userProfileScreen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.solodev.ideahub.R
import com.solodev.ideahub.ui.screen.components.userProfile

@Composable
fun UserProfileScreen(
    modifier: Modifier = Modifier,

){
    val userProfile = userProfile
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))) {
            UserInfos(
                name = userProfile.name,
                biography = userProfile.biography,
                profileImage = userProfile.profileImage
            )
        }
        Box(modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)))
        {
            UserPersonalStats(
                reachedGoal = userProfile.personalStatistics.reachedGoalPercentage,
                contribution = userProfile.personalStatistics.contributions
            )
        }
        Box(modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)))
        {
            UserSettings()
        }
    }
}

@Composable
fun UserInfos(
    modifier: Modifier = Modifier,
    name: String,
    biography: String,
    profileImage: String,
){
    Column(
        modifier = modifier
    ) {
        Text(
             stringResource(id = R.string.user_profile_information),
             modifier = modifier.fillMaxWidth()
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.spacing_small)))
        ElevatedCard (modifier = modifier.fillMaxWidth()){
            Row (
                modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Box(modifier = modifier.size(120.dp))
                {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(profileImage)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.loading_img),
                        contentDescription = stringResource(R.string.description),
                        error = painterResource(R.drawable.ic_broken_image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                }
                Spacer(modifier = modifier.weight(1f))
                Column(modifier = modifier) {
                    Text(
                        name,
                        style = MaterialTheme.typography.displayMedium
                    )
                    Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.spacing_small)))
                    Text(
                        biography,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

    }

}

@Composable
fun UserPersonalStats(
    modifier: Modifier = Modifier,
    reachedGoal: Int,
    contribution: Int,
    onTaskAndGoalCliked: ()-> Unit = {},
    onCommunityCliked: ()-> Unit = {}
){
    Column(
        modifier = modifier.fillMaxWidth(),
    ){
        Text(
            stringResource(id = R.string.personal_stats)
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
        ElevatedCard(modifier = modifier.fillMaxWidth()
        ) {
            Column(
                modifier = modifier.
                padding(dimensionResource(id = R.dimen.padding_medium))
            ){
                Text(
                    text = stringResource(
                        id = R.string.user_profile_progression
                    ),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ){
                    Icon(
                        painter = painterResource
                            (id = R.drawable.flag_circle_filled_24px),
                        contentDescription = "icon_goal"
                    )
                    Spacer(modifier = modifier.width(dimensionResource(id = R.dimen.spacing_small)))
                    Text(
                        text = stringResource(id = R.string.user_reached_goal),
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(text = "$reachedGoal %")
                    Spacer(modifier = modifier.weight(1f))
                    Button(onClick = onTaskAndGoalCliked) {
                        Text(
                            stringResource(id = R.string.tab_goal_and_task)
                        )
                    }

                }
                Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ){
                    Icon(
                        painter = painterResource
                            (id = R.drawable.diversity_3_24px),
                        contentDescription = "icon_contribution"
                    )
                    Spacer(modifier = modifier.width(dimensionResource(id = R.dimen.spacing_small)))
                    Text(text = "$contribution contributions")
                    Spacer(modifier = modifier.weight(1f))
                    Button(onClick = onCommunityCliked) {
                        Text(
                            stringResource(id = R.string.nav_community)
                        )
                    }

                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSettings(
    modifier: Modifier = Modifier,
    onNotificationClicked: () -> Unit = {},
    onLanguageSelect: () -> Unit = {},
    onThemeSelect: () -> Unit = {},

){
    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = stringResource(id = R.string.user_settings))
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_medium)))
        ElevatedCard(modifier.fillMaxWidth()) {
            Column(
                modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ElevatedCard(
                    onClick = onNotificationClicked
                ) {
                    Row(
                        modifier.padding(dimensionResource(id = R.dimen.spacing_medium)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.notifications_filled_24px),
                            contentDescription = "icon_notification"
                        )
                        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_small)))
                        Column(
                            modifier = modifier.fillMaxWidth()
                        ){
                            Text(
                                text = stringResource(id = R.string.user_notifications),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = stringResource(id = R.string.user_notifications_sound),
                                style = MaterialTheme.typography.bodySmall
                            )

                        }
                    }
                }
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_medium)))
                ElevatedCard(
                    onClick = onLanguageSelect
                ) {
                    Row(
                        modifier.padding(dimensionResource(id = R.dimen.spacing_medium)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.language_24px),
                            contentDescription = "icon_language"
                        )
                        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_small)))
                        Column(
                            modifier = modifier.fillMaxWidth()
                        ){
                            Text(
                                text = stringResource(id = R.string.user_languages),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = stringResource(id = R.string.user_languages_select),
                                style = MaterialTheme.typography.bodySmall
                            )

                        }
                    }
                }
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_medium)))
                ElevatedCard(
                    onClick = onThemeSelect
                ) {
                    Row(
                        modifier.padding(dimensionResource(id = R.dimen.spacing_medium)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.palette_24px),
                            contentDescription = "icon_palette"
                        )
                        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_small)))
                        Column(
                            modifier = modifier.fillMaxWidth()
                        ){
                            Text(
                                text = stringResource(id = R.string.user_theme),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = stringResource(id = R.string.user_theme_select),
                                style = MaterialTheme.typography.bodySmall
                            )

                        }
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun UserProfileScreenPreview(

){
    UserProfileScreen()
}