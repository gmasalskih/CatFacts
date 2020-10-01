package com.example.catfacts.states

import com.example.catfacts.helper.Item

sealed class ResponseStatus {
    class Success(val items: List<Item>) : ResponseStatus()
    class Error(val errMsg: String) : ResponseStatus()
}