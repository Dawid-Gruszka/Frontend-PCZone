package com.david.pczone.UI.DetailPurchase

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.pczone.API.TokenManager
import com.david.pczone.Adapter.PurchaseDetailAdapter
import com.david.pczone.R
import com.david.pczone.UI.DetailProduct.DetailProductFragmentArgs
import com.david.pczone.databinding.FragmentDetailPurchaseBinding

class DetailPurchaseFragment : Fragment() {

    private lateinit var binding: FragmentDetailPurchaseBinding
    private lateinit var adapter: PurchaseDetailAdapter
    private val viewModel: DetailPurchaseViewModel by viewModels()
    private val args: DetailPurchaseFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailPurchaseBinding.inflate(inflater, container, false)
        binding.recyclerDetalleItems.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val orderId = args.id
        val token = TokenManager.getToken(requireContext())
        adapter = PurchaseDetailAdapter()
        binding.recyclerDetalleItems.adapter = adapter

        if (token != null) {
            viewModel.loadOrderDetail(orderId, token)
        }

        viewModel.order.observe(viewLifecycleOwner) { order ->
            if (order != null) {
                binding.textIdPedido.text = "ID Pedido: #${order.id}"
            }
            if (order != null) {
                binding.textFechaPedido.text = "Fecha: ${order.created_at}"
            }
            if (order != null) {
                binding.textTotalPedido.text = "Total: ${order.total_price}€"
            }
        }

        viewModel.itemsConProducto.observe(viewLifecycleOwner) { listaCombinada ->
            adapter.updateList(listaCombinada)
        }
    }
}
