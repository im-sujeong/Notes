package com.sue.notes.ui.detail_note

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sue.notes.databinding.FragmentDetailBinding
import com.sue.notes.domain.model.Note
import com.sue.notes.extensions.formattedDate
import com.sue.notes.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class DetailNoteFragment: BaseFragment<DetailNoteViewModel, FragmentDetailBinding>() {
    override val viewModel: DetailNoteViewModel by viewModels()

    override fun getViewBinding(): FragmentDetailBinding = FragmentDetailBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() = with(binding){
        editButton.setOnClickListener {
            findNavController().navigate(
                DetailNoteFragmentDirections.actionDetailNoteFragmentToWriteNoteFragment(viewModel.getNote())
            )
        }
    }

    override fun observeData() = viewModel.state.observe(viewLifecycleOwner){ state ->
        when(state) {
            is DetailNoteState.Success -> handleSuccess(state.note)
        }
    }

    private fun handleSuccess(note: Note?) = with(binding){
        note?.let {
            dateTextView.text = note.createdAt.formattedDate()
            titleTextView.text = note.title
            contentTextView.text = note.content
        }
    }
}