package com.sue.notes.ui.write_note

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.sue.notes.R
import com.sue.notes.databinding.FragmentWriteNoteBinding
import com.sue.notes.domain.model.Note
import com.sue.notes.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class WriteNoteFragment: BaseFragment<WriteNoteViewModel, FragmentWriteNoteBinding>() {
    override val viewModel: WriteNoteViewModel by viewModels()

    override fun getViewBinding(): FragmentWriteNoteBinding = FragmentWriteNoteBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() = with(binding){
        backButton.setOnClickListener {
            popBack()
        }

        saveButton.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() = with(binding){
        if(titleEditText.text.isNullOrEmpty() || contentEditText.text.isNullOrEmpty()) {
            Toast.makeText(requireContext(), R.string.enter_title_and_content, Toast.LENGTH_SHORT).show()
        }else {
            viewModel.saveNote(
                title = titleEditText.text.toString(),
                content = contentEditText.text.toString()
            )
        }
    }

    override fun observeData() = viewModel.state.observe(viewLifecycleOwner){ state ->
        when(state) {
            is WriteNoteState.Success -> {
                handleSuccess(state.note)
            }
            WriteNoteState.Save -> {
                handleSave()
            }
        }
    }

    private fun handleSuccess(note: Note?) = with(binding){
        note?.let { note ->
            titleEditText.setText(note.title)
            contentEditText.setText(note.content)
        }
    }

    private fun handleSave() {
        Toast.makeText(requireContext(), R.string.complete_save, Toast.LENGTH_SHORT).show()
        popBack()
    }
}