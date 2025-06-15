package com.david.pczone.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.david.pczone.Models.Category
import com.david.pczone.R
import com.david.pczone.databinding.ItemCategoryBinding
import com.david.pczone.databinding.ItemCategoryMenuBinding

class CategoryMenuAdapter(private var items: List<Category>, private val listener: OnClickCategory) : RecyclerView.Adapter<CategoryMenuAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_category_menu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favItem = items[position]
        holder.bind(favItem)
    }

    override fun getItemCount(): Int = items.size

    fun updateList(newList: List<Category>) {
        items = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemCategoryMenuBinding.bind(view)
        fun bind(item: Category) {
            binding.itemText.text = item.name

            binding.itemLayout.setOnClickListener {
                listener.onClickCategory(item.id)
            }
        }
    }
}