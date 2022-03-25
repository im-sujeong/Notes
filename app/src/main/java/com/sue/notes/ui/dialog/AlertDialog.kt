package com.sue.notes.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import com.sue.notes.databinding.DialogAlertBinding

class AlertDialog(
    context: Context,
    @StringRes private val title: Int? = null,
    @StringRes private val message: Int? = null,
    @StringRes private val leftButtonText: Int? = null,
    private val onLeftButtonClickListener: () -> Unit = { },
    @StringRes private val rightButtonText: Int? = null,
    private val onRightButtonClickListener: () -> Unit = { }
) : Dialog(context) {
    private lateinit var binding: DialogAlertBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogAlertBinding.inflate(layoutInflater)

        setContentView(binding.root)

        window?.attributes?.let {
            it.width = WindowManager.LayoutParams.MATCH_PARENT
            it.height = WindowManager.LayoutParams.WRAP_CONTENT

            window?.attributes = it
        }

        initViews()
    }

    private fun initViews() = with(binding) {
        title?.let {
            titleTextView.setText(title)
        }

        message?.let {
            messageTextView.setText(message)
        }

        leftButtonText?.let {
            leftButton.isVisible = true
            leftButton.setText(leftButtonText)
        }

        leftButton.setOnClickListener {
            onLeftButtonClickListener()
            dismiss()
        }

        rightButtonText?.let {
            rightButton.isVisible = true
            rightButton.setText(rightButtonText)
        }

        rightButton.setOnClickListener {
            onRightButtonClickListener()
            dismiss()
        }
    }
}