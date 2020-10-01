package com.example.catfacts.states

import androidx.compose.runtime.*
import com.example.catfacts.helper.Item

data class MainScreenState(
    val items: List<Item> = listOf(),
    val selectedUser: String = "",
    val isErrorOccurs: Boolean = false,
    val isLoading: Boolean = false,
    val errMsg: String =""
)

sealed class SideEffect {
    data class Do(val text: String) : SideEffect()
}