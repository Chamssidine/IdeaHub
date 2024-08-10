import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.solodev.ideahub.model.CommunityCategory
import com.solodev.ideahub.model.GroupItemData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
    val categoryName: String,
    val categoryImage: String?,
    val memberCount: Int,
    val groupList: List<GroupItemUiState> = emptyList()
)

data class PopularGroupUiState(
    val communityCategories: List<CommunityCategoryUiState> = emptyList()
)

class PopularGroupViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val _uiState = MutableStateFlow(PopularGroupUiState())
    private  val _groupItemUIState = MutableStateFlow(GroupItemUiState("","",""))
    val uiState: StateFlow<PopularGroupUiState> = _uiState.asStateFlow()
    val groupItemUIState: StateFlow<GroupItemUiState> = _groupItemUIState.asStateFlow()

    private fun loadGroups() {
        viewModelScope.launch {
            firestore.collection("communityCategories")
                .get()
                .addOnSuccessListener { result ->
                    val categories = result.map { document ->
                        val category = document.toObject<CommunityCategory>()
                        CommunityCategoryUiState(
                            categoryName = category.categoryName,
                            categoryImage = category.categoryImage,
                            memberCount = category.memberCount,
                            groupList = category.groupList.map { group ->
                                GroupItemUiState(
                                    groupId = group.groupId,
                                    groupName = group.groupName,
                                    groupDescription = group.groupDescription
                                )
                            }
                        )
                    }
                    _uiState.value = PopularGroupUiState(communityCategories = categories)
                }
        }
    }

    fun joinGroup(groupId: String) {
        viewModelScope.launch {
            val userId = getCurrentUserId()
            val groupDocRef = firestore.collection("groups").document(groupId)

            firestore.runTransaction { transaction ->
                val groupSnapshot = transaction.get(groupDocRef)
                val members = groupSnapshot.get("memberList") as? List<String> ?: emptyList()

                if (!members.contains(userId)) {
                    val updatedMembers = members + userId
                    transaction.update(groupDocRef, "memberList", updatedMembers)
                }
            }.addOnSuccessListener {
                updateGroupState(groupId) { it.copy(isJoined = true) }
            }
        }
    }

    fun likeGroup(groupId: String) {
        updateGroupState(groupId) { it.copy(isLiked = !it.isLiked) }
    }

    fun addToFavorites(groupId: String) {
        updateGroupState(groupId) { it.copy(isFavorite = !it.isFavorite) }
    }

    private fun createGroup(
        categoryId: String,
        groupName: String,
        groupDescription: String
    ) {
        viewModelScope.launch {
            val groupId = firestore.collection("groups").document().id // Generate a new group ID
            val newGroup = GroupItemData(
                groupId = groupId,
                groupName = groupName,
                groupDescription = groupDescription,
                groupImage = "", // Placeholder for group image
                memberList = emptyList()
            )

            firestore.collection("groups")
                .document(groupId)
                .set(newGroup)
                .addOnSuccessListener {
                    // Update the UI state after successfully creating the group
                    addGroupToCategory(categoryId, newGroup)
                    Log.d("GroupCreation", "Group created successfully: $groupId")
                }
                .addOnFailureListener { exception ->
                    Log.e("GroupCreation", "Failed to create group", exception)
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

    init {
        loadGroups()
    }
     fun checkInputs(): Boolean {
         return  validateInputs()
     }
    fun createGroup(){
        createGroup(
            categoryId = System.currentTimeMillis().toString(),
            groupName = _groupItemUIState.value.groupName,
            groupDescription = _groupItemUIState.value.groupDescription
        )
    }
    fun updateDescription(it: String) {
        _groupItemUIState.update {
            state -> state.copy(
            groupDescription = it
            )
        }
    }
    fun updateGroupName(it: String) {
        _groupItemUIState.update {
                state -> state.copy(
            groupName = it
        )
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
}
