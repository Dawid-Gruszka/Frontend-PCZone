package com.david.pczone.UI.Orders

import OrdersViewModel
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.pczone.API.TokenManager
import com.david.pczone.Adapter.OnClickProduct
import com.david.pczone.Adapter.OrderAdapter
import com.david.pczone.databinding.FragmentOrdersBinding
import kotlinx.coroutines.launch

class OrdersFragment : Fragment(), OnClickProduct {

    private lateinit var binding: FragmentOrdersBinding
    private val viewModel: OrdersViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)
        setupObservers()

        lifecycleScope.launch {
            val token = TokenManager.getToken(requireContext())
            if (token != null) {
                viewModel.loadCart(token)
                viewModel.loadUser(token)

                binding.btnPagar.setOnClickListener {
                    if (validarDatosTarjeta()) return@setOnClickListener
                    viewModel.crearPedido(token)
                }
            } else {
                Toast.makeText(requireContext(), "Sesión no iniciada", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }

        return binding.root
    }

    private fun validarDatosTarjeta(): Boolean {
        val numeroTarjeta = binding.editNumeroTarjeta.text.toString().trim()
        val titular = binding.editTitular.text.toString().trim()
        val fecha = binding.editFecha.text.toString().trim()
        val cvv = binding.editCVV.text.toString().trim()

        if (!numeroTarjeta.matches(Regex("^\\d{16}$"))) {
            Toast.makeText(requireContext(), "Número de tarjeta inválido", Toast.LENGTH_SHORT).show()
            return true
        }

        if (titular.isEmpty()) {
            Toast.makeText(requireContext(), "Nombre del titular requerido", Toast.LENGTH_SHORT)
                .show()
            return true
        }

        if (!fecha.matches(Regex("^(0[1-9]|1[0-2])/\\d{2}$"))) {
            Toast.makeText(
                requireContext(),
                "Fecha inválida. Usa formato MM/AA",
                Toast.LENGTH_SHORT
            ).show()
            return true
        }

        if (!cvv.matches(Regex("^\\d{3,4}$"))) {
            Toast.makeText(requireContext(), "CVV inválido", Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }

    private fun setupObservers() {
        viewModel.cartItems.observe(viewLifecycleOwner) { cesta ->
            if (cesta != null) {
                val precioTotal = cesta.sumOf { it.product.price * it.quantity }
                binding.textTotalPrecio.text = "$precioTotal€"

                binding.recyclerResumen.apply {
                    layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    adapter = OrderAdapter(cesta, this@OrdersFragment)
                }
            }
        }

        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding.editDireccion.setText(user.address)
        }

        viewModel.orderCreated.observe(viewLifecycleOwner) { order ->
            order?.let {
                Toast.makeText(requireContext(), "Pedido realizado con ID: ${it.order_id}", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClickProduct(id: Int) {
        val action = OrdersFragmentDirections.actionOrdersFragmentToDetailProductFragment(id)
        findNavController().navigate(action)
    }
}
