package com.sue.notes.ui.note.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sue.notes.databinding.ItemNotesBinding
import com.sue.notes.domain.model.Note
import com.sue.notes.extensions.formattedDate

class NoteAdapter(
    val onItemClick: (Note) -> Unit,
    val onMoreClick: (Note) -> Unit
): ListAdapter<Note, NoteAdapter.ViewHolder>(diffUtil) {
    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder(
        private val binding: ItemNotesBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Note) = with(binding){
            root.setOnClickListener {
                onItemClick(model)
            }

            moreButton.setOnClickListener {
                onMoreClick(model)
            }

            titleTextView.text = model.title
            noteTextView.text = model.content
            dateTextView.text = model.createdAt.formattedDate()

            fixImageView.isVisible = model.isFixed
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            currentList[position]
        )
    }
}