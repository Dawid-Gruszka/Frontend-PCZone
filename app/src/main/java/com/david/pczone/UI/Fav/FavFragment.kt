package com.david.pczone.UI.Fav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.findNavController
import com.david.pczone.Adapter.FavAdapter
import com.david.pczone.Adapter.OnClickProduct
import com.david.pczone.Models.CartItems
import com.david.pczone.Models.Product
import com.david.pczone.API.TokenManager
import com.david.pczone.databinding.FragmentFavBinding

class FavFragment : Fragment(), OnClickProduct {

    private lateinit var binding: FragmentFavBinding
    private val viewModel: FavViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavBinding.inflate(inflater, container, false)

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val token = TokenManager.getToken(requireContext())

        token?.let {
            viewModel.loadFav(it)
        } ?: run {
            Toast.makeText(requireContext(), "Sesión no iniciada", Toast.LENGTH_SHORT).show()
        }

        viewModel.favItems.observe(viewLifecycleOwner) { favItems ->
            recyclerView.adapter = FavAdapter(favItems,
                onFavClick = { product -> onFavClick(product) },
                onCartClick = { cartItem -> onCartClick(cartItem) },
                listener = this
            )
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.clearMessages()
            }
        }

        viewModel.infoMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.clearMessages()
            }
        }

        return binding.root
    }

    private fun onFavClick(product: Product) {
        val token = TokenManager.getToken(requireContext())
        token?.let {
            viewModel.deleteFav(it, product.id)
        } ?: Toast.makeText(requireContext(), "Sesión no iniciada", Toast.LENGTH_SHORT).show()
    }

    private fun onCartClick(cartItem: CartItems) {
        val token = TokenManager.getToken(requireContext())
        token?.let {
            viewModel.addToCart(it, cartItem.product.id)
        } ?: Toast.makeText(requireContext(), "Sesión no iniciada", Toast.LENGTH_SHORT).show()
    }

    override fun onClickProduct(id: Int) {
        val action = FavFragmentDirections.actionFavFragmentToDetailProductFragment(id)
        findNavController().navigate(action)
    }
}
