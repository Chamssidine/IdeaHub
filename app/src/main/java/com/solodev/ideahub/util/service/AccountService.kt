package com.solodev.ideahub.util.service

interface AccountService {

    suspend fun authenticate(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun createAnonymousAccount()
    suspend fun deleteAccount()
    suspend fun signOut()

    suspend fun register(email: String, password: String, name: String)
}