package com.david.pczone.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.RecyclerView
import com.david.pczone.Models.OrderItem
import com.david.pczone.Models.Product
import com.david.pczone.R
import com.david.pczone.databinding.ItemDetailpurchaseBinding

class PurchaseDetailAdapter : RecyclerView.Adapter<PurchaseDetailAdapter.ViewHolder>() {

    private lateinit var context: Context
    private var items: List<Pair<OrderItem, Product>> = emptyList()

    fun updateList(newList: List<Pair<OrderItem, Product>>) {
        items = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_detailpurchase, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemDetailpurchaseBinding.bind(view)
        fun bind(data: Pair<OrderItem, Product>) {
            val (item, product) = data
            binding.textNombreProducto.text = product.name
            binding.textCantidadProducto.text = "Cantidad: ${item.quantity}"

            val fullUrl = "http://192.168.1.140:8000${product.image_url}"
            Glide.with(context)
                .load(fullUrl)
                .placeholder(R.drawable.ryzen)
                .into(binding.imageProducto)
        }
    }
}