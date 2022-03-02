package com.sue.notes.ui.main

import androidx.lifecycle.viewModelScope
import com.sue.notes.ui.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel: BaseViewModel() {
    override fun fetchData(): Job = viewModelScope.launch{

    }
}