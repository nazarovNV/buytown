package com.buytown.ru.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.buytown.ru.R
import com.buytown.ru.databinding.FragmentLoginBinding
import com.buytown.ru.utils.Resource
import com.buytown.ru.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()

    private var isRegistering = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (isRegistering) {
                val username = binding.usernameEditText.text.toString()
                lifecycleScope.launch {
                    loginViewModel.register(username, email, password) { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                Toast.makeText(requireContext(), "Registration Successful", Toast.LENGTH_SHORT).show()
                                switchToLogin()
                            }
                            Status.ERROR -> {
                                Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                            }
                            Status.LOADING -> {
                                // отображение загрузки
                            }
                        }
                    }
                }
            } else {
                lifecycleScope.launch {
                    loginViewModel.login(email, password) { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                Toast.makeText(context, "SUCCESS: ${resource.data}", Toast.LENGTH_SHORT).show()
                                // Навигация на следующий фрагмент
                                // findNavController().navigate(R.id.action_loginFragment_to_productListFragment)
                            }
                            Status.ERROR -> {
                                Log.e("LoginFragment", "ERROR: ${resource.message}")
                                Toast.makeText(context, "ERROR: ${resource.message}", Toast.LENGTH_SHORT).show()
                            }
                            Status.LOADING -> {
                                // отображение загрузки
                            }
                        }
                    }
                }
            }
        }

        binding.registerButton.setOnClickListener {
            switchToRegister()
        }

        binding.registerButtonComplete.setOnClickListener {
            switchToLogin()
        }
    }

    private fun switchToRegister() {
        isRegistering = true
        binding.usernameEditText.visibility = View.VISIBLE
        binding.registerButton.visibility = View.GONE
        binding.registerButtonComplete.visibility = View.VISIBLE
        binding.loginButton.text = "Register"
    }

    private fun switchToLogin() {
        isRegistering = false
        binding.usernameEditText.visibility = View.GONE
        binding.registerButton.visibility = View.VISIBLE
        binding.registerButtonComplete.visibility = View.GONE
        binding.loginButton.text = "Login"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
