package com.david.pczone.UI.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.pczone.API.RetrofitInstance
import com.david.pczone.Adapter.CategoryAdapter
import com.david.pczone.Adapter.OnClickCategory
import com.david.pczone.Adapter.OnClickProduct
import com.david.pczone.Adapter.ProductApater
import com.david.pczone.R
import com.david.pczone.databinding.FragmentHomeBinding


class HomeFragment : Fragment(), OnClickProduct, OnClickCategory {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.imagePublicidad.setImageResource(R.drawable.publicidad)
        binding.imagePublicidad2.setImageResource(R.drawable.redes)

        val factory = HomeViewModelFactory(RetrofitInstance.api)
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.loading.observe(viewLifecycleOwner) { showLoading(it) }

        viewModel.categorias.observe(viewLifecycleOwner) { categorias ->
            binding.recyclerViewCategorias.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = CategoryAdapter(categorias, this@HomeFragment)
            }
        }

        viewModel.productos.observe(viewLifecycleOwner) { productos ->
            binding.recyclerViewProductos.apply {
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = ProductApater(productos, this@HomeFragment)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            errorMsg?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoading(show: Boolean) {
        binding.loadingView.visibility = if (show) View.VISIBLE else View.GONE
        binding.contentView.visibility = if (show) View.INVISIBLE else View.VISIBLE
    }

    override fun onClickProduct(id: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailProductFragment(id)
        findNavController().navigate(action)
    }

    override fun onClickCategory(id: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailCategoryFragment(id)
        findNavController().navigate(action)
    }
}
