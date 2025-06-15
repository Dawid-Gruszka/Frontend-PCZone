package com.david.pczone.UI.Cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.pczone.API.RetrofitInstance
import com.david.pczone.API.TokenManager
import com.david.pczone.Adapter.CartAdapter
import com.david.pczone.Adapter.OnClickProduct
import com.david.pczone.Models.CartItems
import com.david.pczone.databinding.FragmentShoppingCartBinding

class ShoppingCartFragment : Fragment(), OnClickProduct {

    private lateinit var binding: FragmentShoppingCartBinding
    private lateinit var viewModel: ShoppingCartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingCartBinding.inflate(inflater, container, false)

        val factory = ShoppingCartViewModelFactory(RetrofitInstance.api)
        viewModel = ViewModelProvider(this, factory)[ShoppingCartViewModel::class.java]

        binding.button2.setOnClickListener {
            val action = ShoppingCartFragmentDirections.actionShoppingCartFragmentToOrdersFragment()
            findNavController().navigate(action)
        }

        setupObservers()
        loadCart()

        return binding.root
    }

    private fun loadCart() {
        val token = TokenManager.getToken(requireContext())
        if (token != null) {
            viewModel.loadCart(token)
        } else {
            showToast("Sesión no iniciada")
            checkCart()
        }
    }

    private fun setupObservers() {
        viewModel.cartItems.observe(viewLifecycleOwner) { items ->
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = items?.let {
                    CartAdapter(
                        it,
                        { item -> onRemoveClick(item) },
                        { item -> onAddClick(item) },
                        this@ShoppingCartFragment
                    )
                }
            }
        }

        viewModel.totalPrice.observe(viewLifecycleOwner) { price ->
            binding.textEditPrecio.text = "${price}€"
        }

        viewModel.isCartEmpty.observe(viewLifecycleOwner) { isEmpty ->
            if (isEmpty) {
                checkCart()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            errorMsg?.let { showToast(it) }
        }
    }

    private fun onAddClick(cartItem: CartItems) {
        val token = TokenManager.getToken(requireContext())
        if (token != null) {
            viewModel.updateCart(token, cartItem.product.id, 1)
        } else {
            showToast("Sesión no iniciada")
        }
    }

    private fun onRemoveClick(cartItem: CartItems) {
        val token = TokenManager.getToken(requireContext())
        if (token != null) {
            viewModel.updateCart(token, cartItem.product.id, -1)
        } else {
            showToast("Sesión no iniciada")
        }
    }

    private fun checkCart() {
        binding.button2.isEnabled = false
        binding.button2.text = "Carrito Vacío"
        binding.textEditPrecio.visibility = View.GONE
        binding.textView10.visibility = View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onClickProduct(id: Int) {
        val action = ShoppingCartFragmentDirections.actionShoppingCartFragmentToDetailProductFragment(id)
        findNavController().navigate(action)
    }
}
