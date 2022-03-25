package com.sue.notes.extensions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
internal fun Long.formattedDate(format: String = "yyyy.MM.dd HH:mm"): String {
    return SimpleDateFormat(format).format(Date(this))
}