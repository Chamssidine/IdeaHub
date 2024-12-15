package com.solodev.ideahub.data

import com.solodev.ideahub.model.ThreadItem
import com.solodev.ideahub.model.threadItems
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

interface ThreadItemRepository {
    suspend fun insertThreadItem(threadItem: ThreadItem)
    suspend fun deleteThreadItem(threadItem: ThreadItem)
    suspend fun updateThreadItem(threadItem: ThreadItem)
    fun getThreadItemById(id: String): ThreadItem?
    fun getAllThreadItems(): List<ThreadItem>

}

class ThreadItemRepositoryImpl() : ThreadItemRepository {

    private var _threadItems: MutableList<ThreadItem> = threadItems.toMutableList()
    private  var _selectedItem: ThreadItem? = null

    // Flow to observe the data changes
    private val _threadItemsFlow = MutableStateFlow<List<ThreadItem>>(emptyList())
    val threadItemsFlow: StateFlow<List<ThreadItem>> = _threadItemsFlow

    // Add a ThreadItem to the data holder
    private fun addThreadItem(Item: ThreadItem) {
        _threadItems.add(Item)
        _threadItemsFlow.value = _threadItems
    }

    fun initialize() {
        _threadItems = threadItems.toMutableList()
        for (item in _threadItems) {
            item.threadId = UUID.randomUUID().toString()
        }
    }

    override suspend fun insertThreadItem(threadItem: ThreadItem) {
        addThreadItem(threadItem)
    }
    fun setSelectedItem(threadItem: ThreadItem) {
        _selectedItem = threadItem
    }
    fun getSelectedItem(): ThreadItem? {
        return _selectedItem
    }
    override suspend fun deleteThreadItem(threadItem: ThreadItem) {
        TODO("Not yet implemented")
    }

    override suspend fun updateThreadItem(threadItem: ThreadItem) {
        TODO("Not yet implemented")
    }

    override fun getThreadItemById(id: String): ThreadItem? {
        return _threadItems.find { it.threadId == id }
    }

    override fun getAllThreadItems(): List<ThreadItem> {
        return _threadItems
    }

}