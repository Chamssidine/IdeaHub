package com.solodev.ideahub.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.solodev.ideahub.R

@ExperimentalMaterial3Api
@Composable
fun SocialMediaSection(modifier: Modifier = Modifier)
{
    Row(modifier.wrapContentWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_medium)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SocialMediaIcon(icon = R.drawable.gmail)
        SocialMediaIcon(icon = R.drawable.facebook)
        SocialMediaIcon(icon = R.drawable.twitter__1_)
    }

}

@ExperimentalMaterial3Api
@Composable
fun SocialMediaIcon(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int
){
    ElevatedCard(
        onClick = {  },
        modifier = modifier
            .size(dimensionResource(id = R.dimen.icon_size_medium))
    ) {

        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small))
        )
    }


}
