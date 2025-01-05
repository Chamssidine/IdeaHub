package com.solodev.ideahub.ui.screen.userThreadScreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodev.ideahub.data.ThreadItemRepository
import com.solodev.ideahub.model.Comment
import com.solodev.ideahub.model.ThreadItem
import com.solodev.ideahub.model.UserProfile
import com.solodev.ideahub.util.Tools
import com.solodev.ideahub.util.UserHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class UserThreadViewModel @Inject constructor (
    private val dataHolder: ThreadItemRepository
): ViewModel() {

    private val _threadItemUiState = MutableStateFlow(ThreadUIState())
    val threadItemUiState: StateFlow<ThreadUIState> = _threadItemUiState.asStateFlow()

    private val _comments = MutableStateFlow(CommentUIState())
    val comments: StateFlow<CommentUIState> = _comments.asStateFlow()

    fun createThread(threadItem: ThreadItem){
        viewModelScope.launch {
            dataHolder.insertThreadItem(threadItem)
        }
    }

    private fun initialize() {

        viewModelScope.launch {
            dataHolder.getAllThreadItems().collect {
                    threadItems -> _threadItemUiState.value = ThreadUIState(threadItems)

            }

        }
    }
    init {

        initialize()
    }
    fun selectThread(threadItem: ThreadItem) {

        _threadItemUiState.update { state ->
            state.copy(selectedThread = threadItem)

        }

    }

    fun getSelectedItem(): ThreadItem {
        return _threadItemUiState.value.selectedThread
    }



    fun onCommentTyping(commentText: String){

        _comments.update {
            state -> state.copy(commentText = commentText)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun publishComment(threadItem: ThreadItem, commentText: String) {
        val index = _threadItemUiState.value.threadItems.indexOf(threadItem)
        if (index == -1) {
            Log.e("PublishComment", "ThreadItem not found!")
            return
        }

        val newComment = Comment(
            commentDate = Tools.getCurrentDate(),
            commentText = commentText,
            userProfile = UserHandler.getCurrentuser()
        )

        val updatedThreadItems = _threadItemUiState.value.threadItems.toMutableList()
        val contributionCount = updatedThreadItems[index].contributionCount
        val updatedThread = updatedThreadItems[index].copy(
            contributionCount = contributionCount+1,
            comments = (listOf(newComment) + updatedThreadItems[index].comments).toMutableList()
        )



        updatedThreadItems[index] = updatedThread


        _threadItemUiState.update { state ->
            state.copy(
                threadItems = updatedThreadItems,
                selectedThread = updatedThread // Update selectedThread as well
            )
        }

        viewModelScope.launch {
            dataHolder.addNewComment(newComment, threadId = threadItem.threadId)
            dataHolder.updateThreadItem(updatedThread)
        }
    }




}

data class CommentUIState(
    val commentText: String = "",
    val isTyping: Boolean = false,
    val userProfile: UserProfile = UserProfile(),
)
data class ThreadUIState(
    val threadItems: List<ThreadItem> = emptyList(),
    val selectedThread: ThreadItem = ThreadItem()

)