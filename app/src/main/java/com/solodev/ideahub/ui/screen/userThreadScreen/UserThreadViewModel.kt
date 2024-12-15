package com.solodev.ideahub.ui.screen.userThreadScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodev.ideahub.data.ThreadItemRepositoryImpl
import com.solodev.ideahub.model.ThreadItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserThreadViewModel @Inject constructor (
    private val dataHolder: ThreadItemRepositoryImpl
): ViewModel() {

    private val _threadItem = MutableStateFlow<ThreadItem?>(null)
    val threadItem: StateFlow<ThreadItem?> = _threadItem.asStateFlow()


    fun addThreadItem(threadItem: ThreadItem) {
       viewModelScope.launch {
           dataHolder.insertThreadItem(threadItem)
       }
    }
    init {
        dataHolder.initialize()
    }
    // Fetch the ThreadItem by ID
    fun fetchThreadItem(id: String) {
        _threadItem.value = dataHolder.getThreadItemById(id)
    }
}

