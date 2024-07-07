@file:OptIn(ExperimentalMaterial3Api::class)

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solodev.ideahub.R
import com.solodev.ideahub.ui.screen.CustomSearchBar


@Composable
fun UserThreadHistoryScreen(
    onOpenThread: (Int) -> Unit = {},
    onDeletedThread: (Int) -> Unit = {},
) {


    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CustomSearchBar()
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
        MyThreadsSection()
        Spacer(modifier = Modifier.height(16.dp))
        ContributedThreadsSection()
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
        TotalContributionsSection()
    }
}


@Composable
fun MyThreadsSection() {
    SectionHeader(title = stringResource(id = R.string.my_threads))
    repeat(3) {
        ThreadCard()
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
    }
    SeeAllButton()
}

@Composable
fun ContributedThreadsSection(onOpenThread: (Int) -> Unit = {},) {
    SectionHeader(title = stringResource(id = R.string.contributed_threads))
    repeat(2) {
        ThreadCard(onOpenThread)
        Spacer(modifier = Modifier.height(8.dp))
    }
    SeeAllButton()
}

@Composable
fun TotalContributionsSection() {

    ElevatedCard()
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(stringResource(id = R.string.total_contributions), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(stringResource(id = R.string.total_contributions_message))
            Text(stringResource(id = R.string.points))
            Text(stringResource(id = R.string.last_contribution))
            Text(stringResource(id = R.string.objectives))
            Text(stringResource(id = R.string.contribution_rate))
        }
    }


}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small))
    )
}

@Composable
fun ThreadCard(
    onOpenThread: (Int) -> Unit = {},
    onDeletedThread: (Int) -> Unit = {},
) {

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text(stringResource(id = R.string.name), fontWeight = FontWeight.Bold)
                Text(stringResource(id = R.string.title))
            }
            Column{
                Text(stringResource(id = R.string.contributions), fontWeight = FontWeight.Bold)
                Row(
                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small), top = dimensionResource(id = R.dimen.padding_small)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    ElevatedCard(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small)))
                    {
                        IconButton(onClick = {onOpenThread(1)}) {
                            Icon(painter = painterResource(
                                id = R.drawable.open_in_new_24px),
                                contentDescription = "icon_share",
                                tint = MaterialTheme.colorScheme.primary

                            )
                        }
                    }

                    ElevatedCard(
                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small))

                    )
                    {
                        IconButton(onClick = { onDeletedThread }) {
                            Icon(
                                painter = painterResource(id = R.drawable.delete_forever_24px),
                                contentDescription = "icon_more",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                }

            }

        }
    }



}

@Composable
fun SeeAllButton() {
    TextButton(onClick = {}) {
        Text(stringResource(id = R.string.see_all))
    }
}

@Composable
@Preview(showBackground = true)
fun UserThreadHistoryScreenPreview() {
    UserThreadHistoryScreen()
}