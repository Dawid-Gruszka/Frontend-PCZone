package com.david.pczone.UI.Purchase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.pczone.API.TokenManager
import com.david.pczone.Adapter.OnClickProduct
import com.david.pczone.Adapter.PurchaseAdapter
import com.david.pczone.databinding.FragmentPurchaseBinding
import kotlinx.coroutines.launch

class PurchaseFragment : Fragment(), OnClickProduct {

    private lateinit var binding: FragmentPurchaseBinding
    private val viewModel: PurchaseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPurchaseBinding.inflate(inflater, container, false)

        setupObservers()

        lifecycleScope.launch {
            val token = TokenManager.getToken(requireContext())
            if (token != null) {
                viewModel.loadOrders(token)
            } else {
                Toast.makeText(requireContext(), "Sesión no iniciada", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun setupObservers() {
        viewModel.orders.observe(viewLifecycleOwner) { pedidos ->
            if (pedidos != null) {
                binding.recyclerViewPurchase.layoutManager = LinearLayoutManager(requireContext())
                binding.recyclerViewPurchase.adapter = PurchaseAdapter(pedidos, this)
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClickProduct(id: Int) {
        val action = PurchaseFragmentDirections.actionPurchaseFragmentToDetailPurchaseFragment(id)
        findNavController().navigate(action)
    }
}
