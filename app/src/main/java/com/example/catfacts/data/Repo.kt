package com.example.catfacts.data

import com.example.catfacts.helper.Item
import com.google.gson.annotations.SerializedName

data class Repo(
    @SerializedName("name")
    var name: String = "",

    @SerializedName("description")
    var description: String = "",

    @SerializedName("stargazers_count")
    var stargazersCount: String = "",

    @SerializedName("watchers_count")
    var watchersCount: String = "",

    @SerializedName("language")
    var language: String = "",

    @SerializedName("forks_count")
    var forksCount: String = "",

    @SerializedName("html_url")
    var htmlUrl: String = ""
) : Item