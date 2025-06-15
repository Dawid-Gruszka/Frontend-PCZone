package com.david.pczone.UI.Register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.david.pczone.API.RetrofitInstance
import com.david.pczone.Models.UserCreate
import com.david.pczone.R
import com.david.pczone.databinding.FragmentRegisterBinding
import kotlinx.coroutines.launch
import androidx.lifecycle.Lifecycle

class RegisterFragment : Fragment() {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userApi = RetrofitInstance.api
        val factory = RegisterViewModelFactory(userApi)
        viewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]

        binding.buttonRegister.setOnClickListener {
            val username = binding.editTextUser2.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword2.text.toString().trim()
            val address = binding.editTextUserAddress.text.toString().trim()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                val newUser = UserCreate(username, email, password,address)
                viewModel.register(newUser)
            } else {
                Toast.makeText(requireContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerResult.collect { result ->
                    result?.let {
                        if (it == "Registro Completado") {
                            Toast.makeText(requireContext(), "Registro exitoso", Toast.LENGTH_LONG).show()
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment2)
                        } else {
                            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}
