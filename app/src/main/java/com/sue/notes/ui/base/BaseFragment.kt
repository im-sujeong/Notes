package com.sue.notes.ui.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.sue.notes.ui.HostActivity
import kotlinx.coroutines.Job

internal abstract class BaseFragment<VM: BaseViewModel, VB: ViewBinding> : Fragment(){
    abstract val viewModel: VM

    protected lateinit var binding: VB

    abstract fun getViewBinding(): VB

    protected lateinit var fetchJob: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchJob = viewModel.fetchData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    abstract fun observeData()

    override fun onDestroyView() {
        super.onDestroyView()

        if( ::fetchJob.isInitialized && fetchJob.isActive ) {
            fetchJob.cancel()
        }
    }

    fun hideKeyboard(view: View) {
        activity?.let {
            if( it is HostActivity ) {
                it.hideKeyboard(view)
            }
        }
    }

    fun popBack() {
        findNavController().popBackStack()
    }
}