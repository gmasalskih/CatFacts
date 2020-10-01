package com.example.catfacts.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.lifecycleScope
import com.example.catfacts.states.MainScreenState
import com.example.catfacts.ui.Screen
import com.example.catfacts.ui_settings.CatFactsTheme
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreen : AppCompatActivity() {

    private val vm: MainScreenVM by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            vm.container.stateFlow.collect { state -> render(state = state) }
        }
    }

    private fun render(state: MainScreenState) {
        setContent {
            CatFactsTheme {
                Screen(
                    items = state.items,
                    selectedUser = state.selectedUser,
                    isErrorOccurs = state.isErrorOccurs,
                    errMsg = state.errMsg,
                    isLoading = state.isLoading,
                    getUsersCallback = vm::getUsers,
                    getReposCallback = vm::getRepos,
                )
            }
        }
    }
}