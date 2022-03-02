package com.sue.notes.di

import com.sue.notes.ui.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {
    single { Dispatchers.IO }

    //ViewModel
    viewModel { MainViewModel() }
}