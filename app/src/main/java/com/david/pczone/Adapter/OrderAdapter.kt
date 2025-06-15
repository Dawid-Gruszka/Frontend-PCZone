package com.david.pczone.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.david.pczone.databinding.ItemCartProductBinding
import com.david.pczone.Models.CartItems
import com.david.pczone.Models.Product
import com.david.pczone.R
import com.david.pczone.databinding.ItemOrderProductBinding

class OrderAdapter(private var items: List<CartItems>, private val listener: OnClickProduct) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_order_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = items[position]
        holder.bind(cartItem)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemOrderProductBinding.bind(view)

        fun bind(item: CartItems) {
            binding.imageProducto.setImageResource(R.drawable.ryzen)
            binding.textTituloProducto.text = item.product.name

            binding.cardView.setOnClickListener {
                listener.onClickProduct(item.product.id)
            }

            if (!item.product.image_url.isNullOrEmpty()) {
                val fullUrl = "http://192.168.1.140:8000${item.product.image_url}"
                Glide.with(context)
                    .load(fullUrl)
                    .into(binding.imageProducto)
            } else {
                binding.imageProducto.setImageResource(R.drawable.ryzen)
            }
        }
    }
}

