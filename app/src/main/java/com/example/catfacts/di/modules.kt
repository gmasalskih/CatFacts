package com.example.catfacts.di

import coil.ImageLoader
import com.example.catfacts.api.ApiProvider
import com.example.catfacts.screens.MainScreenVM
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val providersModule = module {
    single { ApiProvider.getAPI() }
    single { ImageLoader(androidApplication()) }
}

val mainScreenModule = module {
    viewModel { MainScreenVM(get()) }
}

