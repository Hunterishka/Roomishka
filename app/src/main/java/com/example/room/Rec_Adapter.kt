package com.example.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.room.databinding.RecyclerviewBinding
import com.example.room.model.Note

class Rec_Adapter : ListAdapter<Note, Rec_Adapter.MyViewHolder>(diffutill) {

    class MyViewHolder(val binding: RecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)

    private var listener: OnItemClickListener? = null

    companion object {
        val diffutill = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.txtTitle.text = item.title
        holder.binding.txtDescription.text = item.description
        holder.binding.btnDelete.setOnClickListener {
            listener?.delete(note = item)
        }
        holder.binding.btnUpdate.setOnClickListener {
            listener?.update(note = item)
        }
    }

    interface OnItemClickListener {
        fun delete(note: Note)
        fun update(note: Note)

    }

    fun onItemClickListener(onItemClickListener: OnItemClickListener) {
        listener = onItemClickListener
    }
}