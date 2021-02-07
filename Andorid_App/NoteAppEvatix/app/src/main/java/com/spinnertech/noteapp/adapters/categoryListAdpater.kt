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
import com.spinnertech.noteapp.models.CategoryItem

class categoryListAdpater(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CategoryItem>() {

        override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {

            return oldItem.cat_id == newItem.cat_id
        }

        override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {

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

    fun submitList(list: List<CategoryItem>) {
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

        fun bind(item: CategoryItem) = with(itemView) {

            itemView.setOnClickListener {
                interaction?.onCategoryItemSelected(adapterPosition, item)
            }


            dot.backgroundTintList = ColorStateList.valueOf(Color.parseColor(item.color))
            title.text = item.name
           desc.text = "${item.counter} Tasks"


        }
    }

    interface Interaction {
        fun onCategoryItemSelected(position: Int, item: CategoryItem)
    }
}
