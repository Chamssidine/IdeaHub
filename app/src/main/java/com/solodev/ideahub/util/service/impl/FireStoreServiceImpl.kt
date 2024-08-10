package com.solodev.ideahub.util.service.impl

import CommunityCategoryUiState
import GroupItemUiState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.solodev.ideahub.model.CommunityCategory
import com.solodev.ideahub.model.GroupItemData
import com.solodev.ideahub.util.service.FireStoreService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireStoreServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FireStoreService {

    override suspend fun createGroup(
        categoryId: String,
        groupName: String,
        groupDescription: String
    ): Result<Unit> {
        return try {
            val groupId = firestore.collection("groups").document().id
            val newGroup = GroupItemData(
                groupId = groupId,
                groupName = groupName,
                groupDescription = groupDescription,
                groupImage = "", // Placeholder for group image
                memberList = emptyList()
            )
            firestore.collection("groups").document(groupId).set(newGroup).await()
            // Optionally, add the group to its category
            addGroupToCategory(categoryId, newGroup)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createGroupCategory(
        categoryName: String,
        categoryImage: String
    ): Result<Unit> {
        return try {
            val newCategory = mapOf(
                "categoryName" to categoryName,
                "categoryImage" to categoryImage,
                "memberCount" to 0,
                "groupList" to emptyList<GroupItemData>()
            )
            firestore.collection("communityCategories").add(newCategory).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun joinGroup(groupId: String, userId: String): Result<Unit> {
        return try {
            val groupDocRef = firestore.collection("groups").document(groupId)

            firestore.runTransaction { transaction ->
                val groupSnapshot = transaction.get(groupDocRef)
                val members = groupSnapshot.get("memberList") as? List<String> ?: emptyList()

                if (!members.contains(userId)) {
                    val updatedMembers = members + userId
                    transaction.update(groupDocRef, "memberList", updatedMembers)
                }
            }.await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun likeGroup(groupId: String, userId: String): Result<Unit> {
        return try {
            val groupDocRef = firestore.collection("groups").document(groupId)
            val userLikesRef = firestore.collection("userLikes").document(userId)

            firestore.runTransaction { transaction ->
                val groupSnapshot = transaction.get(groupDocRef)
                val isLiked = groupSnapshot.getBoolean("isLiked") ?: false
                transaction.update(groupDocRef, "isLiked", !isLiked)
                transaction.update(userLikesRef, groupId, !isLiked)
            }.await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addToFavorites(groupId: String, userId: String): Result<Unit> {
        return try {
            val userFavoritesRef = firestore.collection("userFavorites").document(userId)

            firestore.runTransaction { transaction ->
                val userFavoritesSnapshot = transaction.get(userFavoritesRef)
                val isFavorite = userFavoritesSnapshot.getBoolean(groupId) ?: false
                transaction.update(userFavoritesRef, groupId, !isFavorite)
            }.await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCommunityCategories(): Result<List<CommunityCategoryUiState>> {
        return try {
            val result = firestore.collection("communityCategories").get().await()
            val categories = result.documents.map { document ->
                val category = document.toObject<CommunityCategory>() ?: return@map null
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
            }.filterNotNull()

            Result.success(categories)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun addGroupToCategory(
        categoryId: String,
        newGroup: GroupItemData
    ) {
        try {
            val categoryRef = firestore.collection("communityCategories").document(categoryId)
            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(categoryRef)
                val groupList = snapshot.get("groupList") as? List<GroupItemData> ?: emptyList()
                val updatedGroupList = groupList + newGroup
                transaction.update(categoryRef, "groupList", updatedGroupList)
            }.await()
        } catch (e: Exception) {
            // Handle the exception if needed
        }
    }
}
