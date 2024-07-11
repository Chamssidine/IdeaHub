package com.solodev.ideahub.ui.screen.goalScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.solodev.ideahub.R

@Composable
fun GoalScree(
  modifier: Modifier = Modifier,
) {

}

@Composable
fun GoalItem(
    modifier: Modifier,
    title: String = "My Goal",
    creationDate: String = "01/01/2023",
    hasCompleted: Boolean = false,
    onCompleted: () -> Unit = {},
    onDelete: () -> Unit = {},
    onOpen: () -> Unit = {},

){

    ElevatedCard(
        modifier = modifier.fillMaxWidth()
    )  {
        Row(
            modifier = modifier
        ) {
            Column {
                Text(text = title,style = MaterialTheme.typography.labelMedium)
                Text(text = creationDate,style = MaterialTheme.typography.labelSmall)
            }
            if (hasCompleted) {
                Text(text = "Completed",style = MaterialTheme.typography.labelSmall)
            }
            ElevatedCard(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small)))
            {
                IconButton(onClick = {}) {
                    Icon(painter = painterResource(
                        id = R.drawable.open_in_new_24px),
                        contentDescription = "icon_share",
                        tint = MaterialTheme.colorScheme.primary

                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GoalItemPreview() {
    GoalItem(modifier = Modifier)
}