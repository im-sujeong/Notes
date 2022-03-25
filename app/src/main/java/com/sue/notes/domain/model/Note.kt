package com.sue.notes.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Note(
    val title: String,
    val content: String,
    val createdAt: Long,
    val isFixed: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
): Parcelable
