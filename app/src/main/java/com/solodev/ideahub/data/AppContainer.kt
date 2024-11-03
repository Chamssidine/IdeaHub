package com.solodev.ideahub.data

import android.content.Context


interface  AppContainer {
    val GoalsRepository: GoalsRepository
}
class AppDataContainer(private val context: Context) : AppContainer {
    override val GoalsRepository: GoalsRepository by lazy {
        OfflineGoalsRepository(
            GoalDataBase.getDataBase(context).goalItemDao()
        )
    }

}