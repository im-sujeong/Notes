package com.sue.notes.widget

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.AppCompatImageView
import com.sue.notes.R

class CustomFloatingButton: AppCompatImageView {
    companion object {
        const val SHOW_BUTTON = 100
        const val SHOW_TIME: Long = 1000
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            when(msg.what) {
                SHOW_BUTTON -> {
                    show()
                }
            }
        }
    }

    private var isStartHideAnim = false

    fun hide() {
        handler.removeMessages(SHOW_BUTTON)

        if( !isStartHideAnim ) {
            isStartHideAnim = true
            isEnabled = false
            startAnimation(AnimationUtils.loadAnimation(context, R.anim.floating_button_hide_anim))
        }

        handler.sendMessageDelayed(handlerMessage(SHOW_BUTTON), SHOW_TIME)
    }

    fun show() {
        isStartHideAnim = false
        isEnabled = true
        startAnimation(AnimationUtils.loadAnimation(context, R.anim.floating_button_show_anim))
    }

    private fun handlerMessage(status: Int): Message {
        return Message().apply {
            what = status
        }
    }
}