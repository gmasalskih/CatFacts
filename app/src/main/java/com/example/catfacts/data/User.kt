package com.example.catfacts.data

import com.example.catfacts.helper.Item
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login")
    var login: String = "",

    @SerializedName("avatar_url")
    var avatarUrl: String = ""
) : Item