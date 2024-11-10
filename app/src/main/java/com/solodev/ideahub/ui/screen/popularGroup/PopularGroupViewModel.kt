package com.solodev.ideahub.ui.screen.popularGroup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodev.ideahub.model.CommunityCategory
import com.solodev.ideahub.model.GroupItemData
import com.solodev.ideahub.model.communityCategories
import com.solodev.ideahub.util.service.FireStoreService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class PopularGroupViewModel @Inject constructor(
    private val fireStoreService: FireStoreService
): ViewModel() {

    private val _uiState = MutableStateFlow(PopularGroupUiState())
    private val _categoryUIState = MutableStateFlow(CommunityCategoryUiState())
    private  val _groupItemUIState = MutableStateFlow(GroupItemUiState("", "", ""))

    val uiState: StateFlow<PopularGroupUiState> = _uiState.asStateFlow()
    val groupItemUIState: StateFlow<GroupItemUiState> = _groupItemUIState.asStateFlow()
    val categoryUIState: StateFlow<CommunityCategoryUiState> = _categoryUIState.asStateFlow()

    init {
        //val communities = communityCategories
        //CreateMock(communities)
        loadGroups()
        Log.d("PopularGroupScreen", "com.solodev.ideahub.ui.screen.popularGroup.PopularGroupViewModel initialized")
    }

    private fun CreateMock(communities: List<CommunityCategory>) {

        viewModelScope.launch {
            try {
                communities.forEach { community -> fireStoreService.createGroupCategory(
                    community.categoryName,
                    community.categoryImage,
                    System.currentTimeMillis().toString(),
                    community.groupList

                     )
                    Log.d("PopularGroupScreen  ", "List: ${community.groupList.size}" )
                }

            }catch (
                ex: Exception
            ) {
                Log.e("PopularGroupScreen", "Failed to create category: ${ex.message}", ex.cause )
            }
        }
    }
    private fun loadGroups() {
        viewModelScope.launch {
            try {
                val result = fireStoreService.getCommunityCategories()
                if (result.isSuccess) {
                    val categories = result.getOrNull() ?: emptyList()
                    _uiState.value = PopularGroupUiState(communityCategories = categories.filter { item ->
                        item.groupList.isNotEmpty()

                    })
                    Log.d("PopularGroupScreen", "Groups loaded successfully")
                    Log.d("PopularGroupScreen", "Groups: ${categories.size}")
                    Log.d("PopularGroupScreen", "Groups: ${categories[0].categoryId}")
                } else {
                    val error = result.exceptionOrNull()
                    Log.e("PopularGroupScreen", "Error loading groups", error)
                }
            } catch (e: Exception) {
                Log.e("PopularGroupScreen", "Error loading groups", e)
            }
        }
    }


    fun joinGroup(groupId: String) {
        viewModelScope.launch {
            try {
                val userId = getCurrentUserId()
                fireStoreService.joinGroup(groupId, userId)
                updateGroupState(groupId) { it.copy(isJoined = true) }
            } catch (e: Exception) {
                Log.e("PopularGroupScreen", "Error joining group", e)
            }
        }
    }


    fun createGroup() {
            viewModelScope.launch {
                try {
                    val categoryId = _uiState.value.selectedCategory.categoryId ?: ""
                    val groupName = _groupItemUIState.value.groupName
                    val groupDescription = _groupItemUIState.value.groupDescription

                    Log.d("PopularGroupScreen", "Group created successfully")

                    addGroupToCategory(
                        categoryId = categoryId,
                        newGroup = GroupItemData(
                            groupId = categoryId,
                            groupName = groupName,
                            groupDescription = groupDescription,
                            groupImage = "",
                            memberList = emptyList()
                        )
                    )
                } catch (e: Exception) {
                    Log.e("PopularGroupScreen", "Failed to create group", e)
                }
            }

    }

    private fun addGroupToCategory(categoryId: String, newGroup: GroupItemData) {
        viewModelScope.launch {
            try {
                val result = fireStoreService.addGroupToCategory(categoryId, newGroup)
                if (result.isSuccess) {
                    val updatedCategories = _uiState.value.communityCategories.map { category ->
                        if (category.categoryId == categoryId) {
                            val updatedGroupList = category.groupList + GroupItemUiState(
                                groupId = newGroup.groupId,
                                groupName = newGroup.groupName,
                                groupDescription = newGroup.groupDescription
                            )
                            category.copy(groupList = updatedGroupList)
                        } else {
                            category
                        }
                    }
                    _uiState.value = _uiState.value.copy(communityCategories = updatedCategories)
                    Log.d("PopularGroupScreen", "Group added to category successfully")
                }
                else {
                    Log.e("PopularGroupScreen", "Failed to add group to category ")
                }
            } catch (
                ex: Exception
            ) {
                Log.e("PopularGroupScreen", "Failed to add group to category: ${ex.message}", ex.cause )
            }



        }
    }


    private fun updateGroupState(groupId: String, update: (GroupItemUiState) -> GroupItemUiState) {
        val updatedCategories = _uiState.value.communityCategories.map { category ->
            val updatedGroups = category.groupList.map { group ->
                if (group.groupId == groupId) {
                    update(group)
                } else {
                    group
                }
            }
            category.copy(groupList = updatedGroups)
        }
        _uiState.value = _uiState.value.copy(communityCategories = updatedCategories)
    }

    private fun getCurrentUserId(): String {
        // Implement logic to retrieve the current user's ID (e.g., FirebaseAuth.currentUser?.uid)
        return "currentUserId"
    }

    fun checkInputs(): Boolean {
        return validateInputs()
    }

    fun updateDescription(it: String) {
        _groupItemUIState.update { state ->
            state.copy(groupDescription = it)
        }
    }

    fun updateGroupName(it: String) {
        _groupItemUIState.update { state ->
            state.copy(groupName = it)
        }
    }

    private fun validateInputs(): Boolean {
        val state = _groupItemUIState.value
        Log.d("PopularGroupScreen", "Slected Category : ${_uiState.value
            .selectedCategory.categoryId}")
        return when {
            state.groupName.isBlank() -> {
                _groupItemUIState.update {
                    it.copy(
                        hasError = true,
                        errorMessage = "The title is empty"
                    )
                }
                false
            }

            state.groupDescription.isBlank() -> {
                _groupItemUIState.update {
                    it.copy(
                        hasError = true,
                        errorMessage = "The description is empty"
                    )
                }
                false
            }

            _uiState.value.selectedCategory.categoryId?.isBlank() ?: true -> {
                _groupItemUIState.update {
                    it.copy(
                        hasError = true,
                        errorMessage = "The category is empty"
                    )
                }
                false
            }

            else -> {
                _groupItemUIState.update {
                    it.copy(
                        hasError = false,
                        errorMessage = null
                    )
                }
                true
            }
        }
    }

    fun createCategory() {
        Log.d("PopularGroupScreen", "Category created: ${_categoryUIState.value.categoryName}")
        if (categoryUIState.value.categoryName.isNotBlank()) {
            val category = CommunityCategoryUiState(
                categoryName = categoryUIState.value.categoryName,
                categoryId = categoryUIState.value.categoryId,
                categoryImage = "",
                memberCount = 0,
                groupList = emptyList()
            )
            _uiState.update {
                currentState ->
                currentState.copy(
                    communityCategories = currentState.communityCategories + category
                )
            }
            viewModelScope.launch {
                try {
                    fireStoreService.createGroupCategory(
                        _categoryUIState.value.categoryName,
                        categoryId = _categoryUIState.value.categoryId,
                        categoryImage = "",
                        groupList = emptyList()
                    )
                    Log.d("PopularGroupScreen", "Category created: ${_categoryUIState.value.categoryName}")
                }catch (
                    ex: Exception
                ) {
                    Log.e("PopularGroupScreen", "Failed to create category: ${ex.message}", ex.cause )
                }

            }

            Log.d("PopularGroupScreen", "Category created: ${_uiState.value.selectedCategory.toString()}")
        }
    }

    fun updateCategoryNameOnCreate(it: String) {
        _categoryUIState.update { state ->
            state.copy(
                categoryName = it,
                categoryId = System.currentTimeMillis().toString()
            )
        }
        Log.d("PopularGroupScreen", "Category created: ${_categoryUIState.value.categoryId}")
    }

    fun updateSelectedCategoryOnGroupCreation(category: CommunityCategoryUiState) {
        _uiState.update {
            state -> state.copy(
                selectedCategory = category
            )
        }
        Log.d("PopularGroupScreen", "Slected Category : ${_uiState.value.selectedCategory.categoryName}")
        Log.d("PopularGroupScreen", "Category created: ${_uiState.value.selectedCategory.categoryId}")
    }
}


data class GroupItemUiState(
    val groupId: String,
    val groupName: String,
    val groupDescription: String,
    val isLiked: Boolean = false,
    val isJoined: Boolean = false,
    val isFavorite: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null
)

data class CommunityCategoryUiState(
    val categoryName: String = "",
    val categoryImage: String? = null,
    val categoryId: String="",
    val memberCount: Int = 0,
    val groupList: List<GroupItemUiState> = emptyList()
)

data class PopularGroupUiState(
    val communityCategories: List<CommunityCategoryUiState> = emptyList(),
    val selectedCategory: CommunityCategoryUiState = CommunityCategoryUiState()

)