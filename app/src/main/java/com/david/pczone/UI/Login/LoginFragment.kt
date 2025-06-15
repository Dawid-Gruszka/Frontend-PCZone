package com.david.pczone.UI.Login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.david.pczone.API.RetrofitInstance
import com.david.pczone.R
import com.david.pczone.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userApi = RetrofitInstance.api
        val factory = LoginViewModelFactory(userApi)
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        binding.button.setOnClickListener {
            val username = binding.editTextUser2.text.toString().trim()
            val password = binding.editTextPassword2.text.toString().trim()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(username, password, requireContext())
            } else {
                Toast.makeText(requireContext(), "Escriba usuario y contraseña", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.textViewRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment2_to_registerFragment)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginResult.collect { result ->
                    result?.let {
                        if (it == "SUCCESS") {
                            findNavController().navigate(R.id.action_loginFragment2_to_profilePageFragment)
                        } else {
                            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}
