package com.sue.notes.ui.note.dialog

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sue.notes.R
import com.sue.notes.databinding.DialogNoteMoreBinding

class NoteMoreDialog(
    context: Context,
    private val isFixed: Boolean,
    val onFixClick: () -> Unit,
    val onDeleteClick: () -> Unit,
): BottomSheetDialog(context, R.style.BottomSheetDialogTheme) {
    lateinit var binding: DialogNoteMoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogNoteMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window?.attributes?.let {
            it.height = WindowManager.LayoutParams.MATCH_PARENT
            it.width = WindowManager.LayoutParams.MATCH_PARENT

            window?.attributes = it
        }

        initViews()
    }

    private fun initViews() = with(binding){
        fixButton.setOnClickListener {
            onFixClick()
            dismiss()
        }

        deleteButton.setOnClickListener {
            onDeleteClick()
            dismiss()
        }

        fixButton.setText(
            if( isFixed ) {
                R.string.top_fix_cancel
            }else {
                R.string.top_fix
            }
        )
    }
}