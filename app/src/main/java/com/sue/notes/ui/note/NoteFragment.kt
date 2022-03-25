package com.sue.notes.ui.note

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import android.widget.PopupWindow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sue.notes.R
import com.sue.notes.databinding.FragmentNoteBinding
import com.sue.notes.databinding.PopupNoteSortBinding
import com.sue.notes.domain.model.Note
import com.sue.notes.domain.util.NoteOrder
import com.sue.notes.extensions.dpToPx
import com.sue.notes.ui.base.BaseFragment
import com.sue.notes.ui.dialog.AlertDialog
import com.sue.notes.ui.note.adapter.NoteAdapter
import com.sue.notes.ui.note.dialog.NoteMoreDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class NoteFragment: BaseFragment<NoteViewModel, FragmentNoteBinding>() {
    override val viewModel: NoteViewModel by viewModels()

    override fun getViewBinding(): FragmentNoteBinding = FragmentNoteBinding.inflate(layoutInflater)

    private val noteAdapter by lazy {
        NoteAdapter(
            onItemClick = {
                goToNoteDetail(it)
            },
            onMoreClick = {
                showMoreDialog(it)
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        notesRecyclerView.adapter = noteAdapter

        searchCloseButton.setOnClickListener {
            clearSearchEditText()

            motionLayout.transitionToStart()
        }

        searchEditText.addTextChangedListener {
            viewModel.searchNote(it.toString())
        }

        searchEditText.setOnEditorActionListener { v, id, _ ->
            if( id == EditorInfo.IME_ACTION_SEARCH ) {
                hideKeyboard(v)
            }
            true
        }

        addButton.setOnClickListener {
            clearSearchEditText()

            findNavController().navigate(
                NoteFragmentDirections.actionMainFragmentToWriteNoteFragment(null)
            )
        }

        sortButton.setOnClickListener {
            showSortMenu(it)
        }

        nestedScrollView.setOnScrollChangeListener { _, _, _, _, oldScrollY ->
            if( oldScrollY > 0 ) {
                addButton.hide()
            }
        }

        sortTypeButton.setOnClickListener {
            viewModel.orderNotes((it as CheckBox).isChecked)
        }
    }

    private fun clearSearchEditText() = with(binding){
        searchEditText.setText("")
        searchEditText.clearFocus()
        hideKeyboard(searchEditText)
    }

    private fun goToNoteDetail(note: Note) {
        findNavController().navigate(
            NoteFragmentDirections.actionMainFragmentToDetailNoteFragment(note.id)
        )
    }

    private fun showMoreDialog(note: Note) {
        NoteMoreDialog(
            requireContext(),
            note.isFixed,
            onFixClick = {
                viewModel.fixNote(note)
            },
            onDeleteClick = {
                AlertDialog(
                    context = requireContext(),
                    title = R.string.delete,
                    message = R.string.delete_message,
                    leftButtonText = R.string.ok,
                    onLeftButtonClickListener = {
                        viewModel.delete(note)
                    },
                    rightButtonText = R.string.cancel
                ).show()
            }
        ).show()
    }

    private fun showSortMenu(v: View) {
        val menuView = layoutInflater.inflate(R.layout.popup_note_sort, ConstraintLayout(requireContext()), false)

        val popupWindow = PopupWindow(menuView, 62f.dpToPx(), ConstraintLayout.LayoutParams.WRAP_CONTENT)
            .apply {
                isFocusable = true
                isTouchable = true
                isOutsideTouchable = true

                showAsDropDown(v)
            }

        PopupNoteSortBinding.bind(menuView).apply {
            sortDateButton.setOnClickListener {
                viewModel.orderNotes(NoteOrder.Date())
                popupWindow.dismiss()
            }

            sortTitleButton.setOnClickListener {
                viewModel.orderNotes(NoteOrder.Title())
                popupWindow.dismiss()
            }
        }
    }

    override fun observeData() = viewModel.state.observe(viewLifecycleOwner){ state ->
        when(state) {
            is NoteState.Success -> {
                handleSuccess(state)
            }
        }
    }

    private fun handleSuccess(state: NoteState.Success) = with(binding){
        emptyTextView.isVisible = state.notes.isEmpty()
        noteAdapter.submitList(state.notes)

        sortButton.setText(
            when(state.noteOrder) {
                is NoteOrder.Date -> {
                    R.string.sort_date
                }
                is NoteOrder.Title -> {
                    R.string.sort_title
                }
            }
        )
    }
}