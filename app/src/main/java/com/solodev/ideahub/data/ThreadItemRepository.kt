package com.solodev.ideahub.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.solodev.ideahub.model.Comment
import com.solodev.ideahub.model.ThreadItem
import com.solodev.ideahub.model.threadItems
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

const val  TAG = "ThreadImpl"
interface ThreadItemRepository {
    suspend fun insertThreadItem(threadItem: ThreadItem)
    suspend fun deleteThreadItem(threadItem: ThreadItem)
    suspend fun updateThreadItem(threadItem: ThreadItem):Result<Unit>
    suspend fun  createThreadItem(threadItem: ThreadItem):Result<Unit>
    fun getThreadItemById(id: String): ThreadItem?
    fun getAllThreadItems(): Flow<List<ThreadItem>>
    fun getSelectedItem(): ThreadItem?
    fun setSelectedItem(threadItem: ThreadItem)
    suspend fun addNewComment(comment: Comment, threadId: String ):Result<Unit>
    fun listenForComments(
        postId: ThreadItem,
        onCommentsChanged: (Result<ThreadItem>) -> Unit
    )

}

class ThreadItemRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ThreadItemRepository {

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



    override suspend fun insertThreadItem(threadItem: ThreadItem) {
        addThreadItem(threadItem)

    }
    override fun setSelectedItem(threadItem: ThreadItem) {
        _selectedItem = threadItem
    }

    override suspend fun addNewComment(comment: Comment, threadId: String): Result<Unit>{
        return try {
             val query = firestore.collection("threads").whereEqualTo("threadId",threadId).get().await()
             if(query.isEmpty)
                 return Result.failure(Exception("Thread not found"))

             val threadDoc = query.documents.first()
             val threadRef = threadDoc.reference

             firestore.runTransaction { transaction ->
                 val threadSnapshot = transaction.get(threadRef)
                 val threads = threadSnapshot.toObject(ThreadItem::class.java)
                 val comments = threads?.comments?.toMutableList() ?: mutableListOf()
                 comments.add(comment)

                 transaction.update(threadRef, "comments", comments)

             }.await()

            Result.success(Unit)
         }catch (e: Exception){
             return  Result.failure(e)
         }
    }

    override fun getSelectedItem(): ThreadItem? {
        return _selectedItem
    }
    override suspend fun deleteThreadItem(threadItem: ThreadItem) {
        TODO("Not yet implemented")
    }

    override suspend fun updateThreadItem(threadItem: ThreadItem): Result<Unit> {
        return try {
            val query = firestore.collection("threads").whereEqualTo("threadId", threadItem.threadId).get().await()

            // Check if the query returned any documents
            if (query.isEmpty) {
                return Result.failure(Exception("Thread not found"))
            }

            val threadDoc = query.documents.first()
            val threadRef = threadDoc.reference

            // Run Firestore transaction to replace the entire thread document
            firestore.runTransaction { transaction ->
                val threadSnapshot = transaction.get(threadRef)
                val currentThread = threadSnapshot.toObject(ThreadItem::class.java)
                    ?: throw Exception("Thread not found in the transaction")

                // If currentThread is null, return failure

                // Replace the entire thread document with the new ThreadItem object
                transaction.set(threadRef, threadItem)  // Overwrite the entire document with the new data

            }.await()

            Result.success(Unit)
        } catch (e: Exception) {
            // Log the error and return failure
            Log.e("FirestoreError", "Failed to update thread: ${e.message}", e)
            Result.failure(e)
        }
    }


    override suspend fun createThreadItem(threadItem: ThreadItem): Result<Unit> {
        return try {
            Log.d(TAG, "Début de createThreadItem")
            firestore.collection("threads").add(threadItem).await()
            Log.d(TAG, "Thread ajouté avec succès")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Erreur lors de l'ajout du thread : ${e.message}", e)
            Result.failure(e)
        }
    }


    override fun getThreadItemById(id: String): ThreadItem? {
        return _threadItems.find { it.threadId == id }
    }

    override fun getAllThreadItems(): Flow<List<ThreadItem>> {
        return flow {

            emit(emptyList())


            val snapshot = firestore.collection("threads").get().await()


            val threadItems = snapshot.documents.mapNotNull { document ->
                document.toObject(ThreadItem::class.java)
            }
            Log.d(TAG, "Thread items fetched from Firestore: $threadItems")

            emit(threadItems)
        }.catch { e ->

            emit(emptyList())
            Log.d(TAG,"Error fetching threads: ${e.message}")
        }
    }


    override fun listenForComments(
        postId: ThreadItem,
        onCommentsChanged: (Result<ThreadItem>) -> Unit
    ) {
        val query = firestore.collection("threads")
            .whereEqualTo("threadId", postId.threadId)

        query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("Firestore", "Error fetching thread: ${error.message}")
                onCommentsChanged(Result.failure(error))
                return@addSnapshotListener
            }

            if (snapshot == null || snapshot.isEmpty) {
                Log.e("Firestore", "No matching thread found.")
                onCommentsChanged(Result.success(ThreadItem()))
                return@addSnapshotListener
            }


            val thread = snapshot.documents.firstOrNull()
            val threadObject =  thread?.toObject<ThreadItem>()
         

            Log.d("Firestore", "Fetched comments: $threadObject")
            onCommentsChanged(Result.success(threadObject!!))



        }
    }



}