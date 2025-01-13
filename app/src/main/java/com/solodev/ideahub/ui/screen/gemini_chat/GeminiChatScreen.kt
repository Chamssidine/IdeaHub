package com.solodev.ideahub.ui.screen.gemini_chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.solodev.ideahub.R
import com.solodev.ideahub.model.ConversationMessage
import com.solodev.ideahub.model.conversationMessages
import com.solodev.ideahub.ui.screen.CustomSearchBar
import com.solodev.ideahub.ui.screen.components.CommentSectionInput

@Composable
fun GeminiChatScreen(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Box(modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))){
            CustomSearchBar()
        }
        LazyColumn(
            modifier  = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        ){
            items(conversationMessages.size) { index ->
                ConversionMessageItem(
                    conversationMessage = conversationMessages[index]
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)))
        {
//            CommentSectionInput(
//                modifier = modifier.fillMaxWidth(),
//                focusRequester = FocusRequester()
//            )
        }


    }
}

@Composable
fun ConversionMessageItem(
    modifier: Modifier = Modifier,
    conversationMessage: ConversationMessage
){
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Message(message = conversationMessage.userMessage.message)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
        Message(message = conversationMessage.aiMessage.message, isAiMessage = true)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
    }
}

@Composable
fun Message(
    modifier: Modifier = Modifier,
    message: String,
    isAiMessage: Boolean = false
) {
        Row(

            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ){
            if(!isAiMessage)
            {
                Spacer(modifier = modifier.weight(1f))
                Card(
                    modifier = modifier.wrapContentSize(),
                    shape = MaterialTheme.shapes.medium,
                ){
                    Box(modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)))
                    {
                        Text(text =  message)
                    }
                }

            }
            else
            {
                Card(
                    modifier = modifier.wrapContentSize(),
                    shape = MaterialTheme.shapes.medium,
                ){
                    Box(modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)))
                    {
                        Text(text =  message)
                    }
                }
                Spacer(modifier = modifier.weight(1f))
            }



        }


}
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun GeminiChatScreenPreview(){
    GeminiChatScreen()
}