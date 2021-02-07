package com.spinnertech.noteapp.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.spinnertech.noteapp.R
import com.spinnertech.noteapp.models.TaskItem

class taskListAdpater(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TaskItem>() {

        override fun areItemsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {

            return oldItem.task_id == newItem.task_id
        }

        override fun areContentsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {

            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return viewholder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_task,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is viewholder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<TaskItem>) {
        differ.submitList(list)
    }

    class viewholder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.title)
        val desc = itemView.findViewById<TextView>(R.id.desc)
        val dot = itemView.findViewById<View>(R.id.dotView)

        fun bind(item: TaskItem) = with(itemView) {

            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }


            dot.backgroundTintList = ColorStateList.valueOf(Color.parseColor(item.category.color))
            title.text = item.task_title
            desc.text = item.description


        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: TaskItem)
    }
}
