package com.sue.notes.extensions

import android.content.res.Resources

internal fun Float.dpToPx() : Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}