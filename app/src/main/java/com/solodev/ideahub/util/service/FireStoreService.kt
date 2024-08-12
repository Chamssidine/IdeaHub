package com.solodev.ideahub.util.service

import com.solodev.ideahub.model.GroupItemData
import com.solodev.ideahub.ui.screen.popularGroup.CommunityCategoryUiState

interface FireStoreService {
    suspend fun createGroup(
        categoryId: String,
        groupName: String,
        groupDescription: String
    ): Result<Unit>

    suspend fun createGroupCategory(
        categoryName: String,
        categoryImage: String,
        categoryId: String,
    ): Result<Unit>

    suspend fun joinGroup(groupId: String, userId: String): Result<Unit>

    suspend fun likeGroup(groupId: String, userId: String): Result<Unit>

    suspend fun addToFavorites(groupId: String, userId: String): Result<Unit>

    suspend fun getCommunityCategories(): Result<List<CommunityCategoryUiState>>
    suspend fun addGroupToCategory(categoryId: String, newGroup: GroupItemData): Result<Unit>
}
