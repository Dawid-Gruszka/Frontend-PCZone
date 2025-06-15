package com.david.pczone.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.david.pczone.Models.Order
import com.david.pczone.R
import com.david.pczone.databinding.ItemPurchaseBinding

class PurchaseAdapter(private var items: List<Order>, private val listener: OnClickProduct) : RecyclerView.Adapter<PurchaseAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_purchase, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderItem = items[position]
        holder.bind(orderItem)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemPurchaseBinding.bind(view)

        fun bind(item: Order) {
            binding.textIdOrder.text = "Pedido #${item.id}"
            binding.textPrice.text = "Total: ${item.total_price}€"
            binding.textDate.text = "Fecha: ${item.created_at}"

            binding.cardView.setOnClickListener {
                listener.onClickProduct(item.id)
            }
        }
    }
}

