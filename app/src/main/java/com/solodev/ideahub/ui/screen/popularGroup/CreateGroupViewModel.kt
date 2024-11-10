package com.solodev.ideahub.ui.screen.popularGroup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateGroupViewModel @Inject constructor(): ViewModel()  {


}


data class GroupCreationUISte(
    val groupName: String = "",
    val groupDescription: String = "",
)