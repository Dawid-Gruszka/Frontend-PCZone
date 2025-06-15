package com.david.pczone.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.david.pczone.API.RetrofitInstance
import com.david.pczone.Models.CartItems
import com.david.pczone.Models.Product
import com.david.pczone.R
import com.david.pczone.databinding.ItemFavProductBinding

class FavAdapter(private var items: List<Product>, private val onFavClick: (Product) -> Unit,private val onCartClick: (CartItems) -> Unit, private val listener: OnClickProduct) : RecyclerView.Adapter<FavAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_fav_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favItem = items[position]
        holder.bind(favItem)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemFavProductBinding.bind(view)
        val userApi = RetrofitInstance.api
        fun bind(item: Product) {
            binding.textNombre.text = item.name
            binding.textPrecio.text = "${item.price}€"

            binding.iconFavorito.setOnClickListener {
                onFavClick(item)
            }
            binding.iconCart.setOnClickListener {
                val cartItems = CartItems(item, 1)
                onCartClick(cartItems)
            }

            if (!item.image_url.isNullOrEmpty()) {
                val fullUrl = "http://192.168.1.140:8000${item.image_url}"
                Glide.with(context)
                    .load(fullUrl)
                    .into(binding.imageProducto)
            } else {
                binding.imageProducto.setImageResource(R.drawable.ryzen)
            }

            binding.cardFav.setOnClickListener {
                listener.onClickProduct(item.id)
            }
        }
    }
}