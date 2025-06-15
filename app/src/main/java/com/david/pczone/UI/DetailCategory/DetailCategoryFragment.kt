package com.david.pczone.UI.DetailCategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.david.pczone.Adapter.OnClickProduct
import com.david.pczone.Adapter.ProductApater
import com.david.pczone.databinding.FragmentDetailCategoryBinding

class DetailCategoryFragment : Fragment(), OnClickProduct {

    private lateinit var binding: FragmentDetailCategoryBinding
    private val args: DetailCategoryFragmentArgs by navArgs()
    private val viewModel: DetailCategoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailCategoryBinding.inflate(inflater, container, false)

        binding.recyclerViewProductos.layoutManager = GridLayoutManager(requireContext(), 2)

        viewModel.products.observe(viewLifecycleOwner) { products ->
            binding.recyclerViewProductos.adapter = ProductApater(products, this)
        }

        viewModel.categoryName.observe(viewLifecycleOwner) { name ->
            binding.textCategoria.text = name
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.clearError()
            }
        }

        viewModel.loadCategoryName(args.id)
        viewModel.loadProducts(args.id)

        return binding.root
    }

    override fun onClickProduct(id: Int) {
        val action = DetailCategoryFragmentDirections.actionDetailCategoryFragmentToDetailProductFragment(id)
        findNavController().navigate(action)
    }
}
