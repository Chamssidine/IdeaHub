package com.solodev.ideahub.util.service.module

import com.solodev.ideahub.data.ThreadItemRepository
import com.solodev.ideahub.data.ThreadItemRepositoryImpl
import com.solodev.ideahub.util.service.AccountService
import com.solodev.ideahub.util.service.FireStoreService
import com.solodev.ideahub.util.service.impl.AccountServiceImpl
import com.solodev.ideahub.util.service.impl.FireStoreServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService
    @Binds
    abstract  fun provideFireStoreService(impl: FireStoreServiceImpl): FireStoreService


}