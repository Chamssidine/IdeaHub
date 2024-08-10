import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.solodev.ideahub.model.CommunityCategory
import com.solodev.ideahub.model.GroupItemData
import com.solodev.ideahub.util.service.FireStoreService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

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
    val memberCount: Int = 0,
    val groupList: List<GroupItemUiState> = emptyList()
)

data class PopularGroupUiState(
    val communityCategories: List<CommunityCategoryUiState> = emptyList()
)

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
        loadGroups()
        Log.d("PopularGroupScreen", "PopularGroupViewModel initialized")
    }

    private fun loadGroups() {
        viewModelScope.launch {
            try {
                val result = fireStoreService.getCommunityCategories()
                if (result.isSuccess) {
                    val categories = result.getOrNull() ?: emptyList()
                    _uiState.value = PopularGroupUiState(communityCategories = categories)
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

    fun likeGroup(groupId: String) {
        updateGroupState(groupId) { it.copy(isLiked = !it.isLiked) }
    }

    fun addToFavorites(groupId: String) {
        updateGroupState(groupId) { it.copy(isFavorite = !it.isFavorite) }
    }

    fun createGroup() {
        if (validateInputs()) {
            viewModelScope.launch {
                try {
                    val categoryId = System.currentTimeMillis().toString()
                    val groupName = _groupItemUIState.value.groupName
                    val groupDescription = _groupItemUIState.value.groupDescription

                    fireStoreService.createGroup(categoryId, groupName, groupDescription)
                    Log.d("PopularGroupScreen", "Group created successfully")

                    addGroupToCategory(
                        categoryId = categoryId,
                        newGroup = GroupItemData(
                            groupId = categoryId,
                            groupName = groupName,
                            groupDescription = groupDescription,
                            groupImage = "", // Placeholder for group image
                            memberList = emptyList()
                        )
                    )
                } catch (e: Exception) {
                    Log.e("PopularGroupScreen", "Failed to create group", e)
                }
            }
        }
    }

    private fun addGroupToCategory(categoryId: String, newGroup: GroupItemData) {
        val updatedCategories = _uiState.value.communityCategories.map { category ->
            if (category.categoryName == categoryId) {
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
        return when {
            state.groupName.isBlank() -> {
                _groupItemUIState.update { state ->
                    state.copy(
                        hasError = true,
                        errorMessage = "The title is empty"
                    )
                }
                false
            }

            state.groupDescription.isBlank() -> {
                _groupItemUIState.update { state ->
                    state.copy(
                        hasError = true,
                        errorMessage = "The description is empty"
                    )
                }
                false
            }

            else -> {
                _groupItemUIState.update { state ->
                    state.copy(
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
                categoryImage = "",
                memberCount = 0,
                groupList = emptyList()
            )
            _uiState.update { currentState ->
                currentState.copy(
                    communityCategories = currentState.communityCategories + category
                )
            }
            Log.d("PopularGroupScreen", "Category created: ${category.categoryName}")
        }
    }

    fun updateCategoryNameOnCreate(it: String) {
        _categoryUIState.update { state ->
            state.copy(categoryName = it)
        }
        Log.d("PopularGroupScreen", "Category created: ${_categoryUIState.value.categoryName}")
    }
}


val community = CommunityCategory("", "", 0,)