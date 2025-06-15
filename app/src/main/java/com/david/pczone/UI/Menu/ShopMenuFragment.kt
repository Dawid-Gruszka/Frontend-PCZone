package com.david.pczone.UI.Menu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.core.view.marginStart
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.pczone.Adapter.CategoryMenuAdapter
import com.david.pczone.Adapter.OnClickCategory
import com.david.pczone.Adapter.OnClickProduct
import com.david.pczone.Adapter.ProductApater
import com.david.pczone.databinding.FragmentShopMenuBinding

class ShopMenuFragment : Fragment(), OnClickCategory, OnClickProduct {

    private lateinit var binding: FragmentShopMenuBinding
    private val viewModel: ShopMenuViewModel by viewModels()

    private lateinit var categoryAdapter: CategoryMenuAdapter
    private lateinit var productAdapter: ProductApater

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopMenuBinding.inflate(inflater, container, false)

        val recyclerView = binding.recyclerViewMenu
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        categoryAdapter = CategoryMenuAdapter(emptyList(), this)
        productAdapter = ProductApater(emptyList(), this)

        recyclerView.adapter = categoryAdapter

        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            categoryAdapter.updateList(categories)
            if (binding.searchView.query.isEmpty()) {
                recyclerView.adapter = categoryAdapter
            }
        }

        viewModel.filteredProducts.observe(viewLifecycleOwner) { products ->
            productAdapter.updateList(products)
            if (binding.searchView.query.isNotEmpty()) {
                recyclerView.adapter = productAdapter
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.loadCategories()
        viewModel.loadAllProducts()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText.orEmpty()
                if (query.isBlank()) {
                    binding.recyclerViewMenu.layoutManager = LinearLayoutManager(requireContext())
                    binding.recyclerViewMenu.adapter = categoryAdapter
                    binding.cartCardView.setCardBackgroundColor(0xFFFFFFFF.toInt()) // Blanco
                } else {
                    viewModel.filterProducts(query)
                    binding.recyclerViewMenu.layoutManager = GridLayoutManager(requireContext(), 2)
                    binding.recyclerViewMenu.adapter = productAdapter
                    binding.cartCardView.setCardBackgroundColor(android.graphics.Color.TRANSPARENT)
                }
                return true
            }
        })

        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
            binding.searchView.requestFocus()
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val searchEditText = binding.searchView.findViewById<android.widget.EditText>(androidx.appcompat.R.id.search_src_text)
            imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT)
        }
        return binding.root
    }


    override fun onClickCategory(id: Int) {
        val action = ShopMenuFragmentDirections.actionShopMenuFragmentToDetailCategoryFragment(id)
        findNavController().navigate(action)
    }

    override fun onClickProduct(id: Int) {
        val action = ShopMenuFragmentDirections.actionShopMenuFragmentToDetailProductFragment(id)
        findNavController().navigate(action)
    }
}
