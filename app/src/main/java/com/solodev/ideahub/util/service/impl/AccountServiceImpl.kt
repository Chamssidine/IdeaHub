package com.solodev.ideahub.util.service.impl

import com.google.firebase.auth.FirebaseAuth
import com.solodev.ideahub.util.service.AccountService
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(private val auth: FirebaseAuth):AccountService {
    override suspend fun authenticate(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun sendRecoveryEmail(email: String) {
        TODO("Not yet implemented")
    }

    override suspend fun createAnonymousAccount() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAccount() {
        TODO("Not yet implemented")
    }

    override suspend fun signOut() {
        TODO("Not yet implemented")
    }

}