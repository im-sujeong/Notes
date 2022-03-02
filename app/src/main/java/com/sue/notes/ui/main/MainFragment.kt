package com.sue.notes.ui.main

import com.sue.notes.databinding.FragmentMainBinding
import com.sue.notes.ui.base.BaseFragment
import org.koin.android.ext.android.inject

internal class MainFragment: BaseFragment<MainViewModel, FragmentMainBinding>() {
    override val viewModel by inject<MainViewModel>()

    override fun getViewBinding(): FragmentMainBinding = FragmentMainBinding.inflate(layoutInflater)

    override fun observeData() {

    }
}