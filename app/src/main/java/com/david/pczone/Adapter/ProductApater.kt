package com.david.pczone.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.david.pczone.Models.Product
import com.david.pczone.R
import com.david.pczone.databinding.ItemProductBinding

class ProductApater(private var items: List<Product>, private val listener: OnClickProduct) : RecyclerView.Adapter<ProductApater.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favItem = items[position]
        holder.bind(favItem)
    }

    override fun getItemCount(): Int = items.size

    fun updateList(list: List<Product>) {
        items = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemProductBinding.bind(view)
        fun bind(item: Product) {
            binding.textProductName.text = item.name
            binding.textProductPrice.text = "${item.price}€"

            if (!item.image_url.isNullOrEmpty()) {
                val fullUrl = "http://192.168.1.140:8000${item.image_url}"
                Glide.with(context)
                    .load(fullUrl)
                    .into(binding.imageProducto)
            } else {
                binding.imageProducto.setImageResource(R.drawable.ryzen)
            }

            binding.cardItemProduct.setOnClickListener {
                listener.onClickProduct(item.id)
            }

            if (item.quantity == 0) {
                binding.textOutOfStock.visibility = View.VISIBLE
                binding.cardItemProduct.isEnabled = false
            } else {
                binding.textOutOfStock.visibility = View.GONE
            }
        }

    }
}