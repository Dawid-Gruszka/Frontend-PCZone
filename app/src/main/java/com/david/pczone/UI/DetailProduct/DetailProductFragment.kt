package com.david.pczone.UI.DetailProduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.david.pczone.API.TokenManager
import com.david.pczone.R
import com.david.pczone.databinding.FragmentDetailProductBinding

class DetailProductFragment : Fragment() {

    private lateinit var binding: FragmentDetailProductBinding
    private val viewModel: DetailProductViewModel by viewModels()
    private val args: DetailProductFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailProductBinding.inflate(inflater, container, false)

        val token = TokenManager.getToken(requireContext())
        viewModel.loadProduct(args.id, token)

        viewModel.product.observe(viewLifecycleOwner) { product ->
            product?.let {
                binding.TextName.text = it.name
                binding.TextPrice.text = "${it.price}€"
                binding.TextSpecification.text = it.description

                if (!it.image_url.isNullOrEmpty()) {
                    val fullUrl = "http://192.168.1.140:8000${it.image_url}"
                    Glide.with(this)
                        .load(fullUrl)
                        .into(binding.productImage)
                } else {
                    binding.productImage.setImageResource(R.drawable.tarjetasgraficas)
                }
            }
        }

        viewModel.isFavorite.observe(viewLifecycleOwner) { favorite ->
            if (favorite) {
                binding.favButton.setImageResource(R.drawable.baseline_favorite_black)
            } else {
                binding.favButton.setImageResource(R.drawable.outline_favorite_24)
            }
        }

        viewModel.message.observe(viewLifecycleOwner) { msg ->
            msg?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.clearMessage()
            }
        }

        binding.addToCartButton.setOnClickListener {
            if (token == null) {
                Toast.makeText(requireContext(), "No estás logueado", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.addToCart(token, args.id)
            }
        }

        binding.favButton.setOnClickListener {
            if (token == null) {
                Toast.makeText(requireContext(), "No estás logueado", Toast.LENGTH_SHORT).show()
            } else {
                val currentlyFavorite = viewModel.isFavorite.value ?: false
                viewModel.toggleFavorite(token, args.id, currentlyFavorite)
            }
        }

        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        return binding.root
    }
}
