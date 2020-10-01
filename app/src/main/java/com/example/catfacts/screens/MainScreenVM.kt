package com.example.catfacts.screens

import androidx.lifecycle.ViewModel
import com.babylon.orbit2.*
import com.babylon.orbit2.coroutines.transformSuspend
import com.babylon.orbit2.viewmodel.container
import com.example.catfacts.api.Service
import com.example.catfacts.data.User
import com.example.catfacts.states.MainScreenState
import com.example.catfacts.states.ResponseStatus
import com.example.catfacts.states.SideEffect

class MainScreenVM(private val api: Service) : ContainerHost<MainScreenState, SideEffect>,
    ViewModel() {

    override val container = container<MainScreenState, SideEffect>(MainScreenState())

    fun getUsers() = orbit {
        reduce {
            state.copy(isLoading = true, items = listOf(), selectedUser = "")
        }.transformSuspend() {
            api.getUsers()
        }.reduce {
            if (event.isSuccessful) {
                state.copy(
                    items = event.body() ?: listOf(),
                    isLoading = false,
                    isErrorOccurs = false,
                    errMsg = "",
                    selectedUser = ""
                )
            } else {
                state.copy(
                    items = listOf(),
                    selectedUser = "",
                    isLoading = true,
                    isErrorOccurs = true,
                    errMsg = event.message()
                )
            }
        }
    }

    fun getRepos(user: User) = orbit {
        reduce {
            state.copy(isLoading = true, selectedUser = user.login)
        }.transformSuspend {
            try {
                val res = api.getReposOfUser(login = user.login)
                if (res.isSuccessful) ResponseStatus.Success(res.body() ?: listOf())
                else ResponseStatus.Error(res.message())
            } catch (e: Exception) {
                ResponseStatus.Error(e.message ?: "")
            }
        }.reduce {
            when (event) {
                is ResponseStatus.Success -> {
                    state.copy(
                        items = (event as ResponseStatus.Success).items,
                        isLoading = false,
                        isErrorOccurs = false,
                        errMsg = "",
                        selectedUser = user.login
                    )
                }
                is ResponseStatus.Error -> {
                    state.copy(
                        errMsg = (event as ResponseStatus.Error).errMsg,
                        selectedUser = user.login,
                        isErrorOccurs = true,
                        items = listOf(),
                        isLoading = true
                    )
                }
            }
        }
    }
}