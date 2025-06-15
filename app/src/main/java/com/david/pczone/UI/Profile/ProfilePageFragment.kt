package com.david.pczone.UI.Profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.david.pczone.API.TokenManager
import com.david.pczone.R
import com.david.pczone.databinding.FragmentProfilePageBinding

class ProfilePageFragment : Fragment() {

    private lateinit var binding: FragmentProfilePageBinding
    private lateinit var viewModel: ProfilePageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfilePageBinding.inflate(inflater, container, false)
        viewModel = ProfilePageViewModel()

        setupObservers()
        checkUser()

        binding.buttonPedidos.setOnClickListener {
            findNavController().navigate(R.id.action_profilePageFragment_to_purchaseFragment)
        }

        binding.buttonLogout.setOnClickListener {
            TokenManager.clearToken(requireContext())
            findNavController().navigate(R.id.action_profilePageFragment_to_loginFragment2)
        }

        return binding.root
    }

    private fun checkUser() {
        val token = TokenManager.getToken(requireContext())
        if (token != null) {
            viewModel.fetchUser(token)
        } else {
            Toast.makeText(requireContext(), "Sesión no iniciada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupObservers() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                binding.editTextUserName.setText(user.username)
                binding.editTextUserEmail.setText(user.email)
                binding.editTextUserAddress.setText(user.address)
            } else {
                binding.editTextUserName.setText("")
                binding.editTextUserEmail.setText("")
                binding.editTextUserAddress.setText("")
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            errorMsg?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                TokenManager.clearToken(requireContext())
                findNavController().navigate(R.id.action_profilePageFragment_to_loginFragment2)
            }
        }
    }
}


