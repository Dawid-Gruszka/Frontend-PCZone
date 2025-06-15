package com.david.pczone.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.david.pczone.Models.Category
import com.david.pczone.R
import com.david.pczone.databinding.ItemCategoryBinding

class CategoryAdapter(private var items: List<Category>,private val listener: OnClickCategory) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favItem = items[position]
        holder.bind(favItem)
    }

    override fun getItemCount(): Int = items.size


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemCategoryBinding.bind(view)
        fun bind(item: Category) {
            binding.textCategory.text = item.name
            binding.imageCategory.setImageResource(R.drawable.tarjetasgraficas)

            binding.itemCategory.setOnClickListener {
                listener.onClickCategory(item.id)
            }

            if (!item.image_url.isNullOrEmpty()) {
                val fullUrl = "http://192.168.1.140:8000${item.image_url}"
                Glide.with(context)
                    .load(fullUrl)
                    .into(binding.imageCategory)
            } else {
                binding.imageCategory.setImageResource(R.drawable.ryzen)
            }
        }
    }
}