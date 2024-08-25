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

            lifecycleScope.launch {
                loginViewModel.login(email, password) { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            // Навигация на экран списка товаров (замените на ваш идентификатор)
                            // TODO("Сделать переход на сл фрагмент")
                            //findNavController().navigate(R.id.action_loginFragment_to_productListFragment)
                            Toast.makeText(context, "SUCCESS: ${resource.data}", Toast.LENGTH_SHORT).show()
                        }
                        Status.ERROR -> {
                            Log.e("LoginFragment", "ERROR: ${resource.data}")
                            Toast.makeText(context, "ERROR: ${resource.message}", Toast.LENGTH_SHORT).show()
                        }
                        Status.LOADING -> {
                            // отображение загрузки
                        }
                    }
                }
            }
        }

        binding.registerButton.setOnClickListener {
            binding.usernameEditText.visibility = View.VISIBLE
            binding.registerButton.visibility = View.GONE
        }

        binding.registerButtonComplete.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            lifecycleScope.launch {
                loginViewModel.register(username, email, password) { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Toast.makeText(requireContext(), "Registration Successful", Toast.LENGTH_SHORT).show()
                            binding.usernameEditText.visibility = View.GONE
                            binding.registerButton.visibility = View.VISIBLE
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
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
