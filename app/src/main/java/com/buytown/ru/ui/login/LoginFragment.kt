package com.buytown.ru.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.buytown.ru.databinding.FragmentLoginBinding
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
            if (isRegistering) {
                registerUser()
            } else {
                loginUser()
            }
        }

        binding.registerSwitchText.setOnClickListener {
            toggleRegistrationMode()
        }

        binding.authSwitchText.setOnClickListener {
            toggleRegistrationMode()
        }

        binding.registerButton.setOnClickListener {
            registerUser()
        }

        // Добавим TextWatchers для отслеживания изменений текста
        setupTextWatchers()
        updateButtonsState()
    }

    private fun toggleRegistrationMode() {
        isRegistering = !isRegistering

        binding.usernameTextInputLayout.visibility = if (isRegistering) View.VISIBLE else View.GONE
        binding.loginButton.visibility = if (isRegistering) View.GONE else View.VISIBLE
        binding.registerButton.visibility = if (isRegistering) View.VISIBLE else View.GONE

        binding.registerSwitchText.visibility = if (isRegistering) View.GONE else View.VISIBLE
        binding.authSwitchText.visibility = if (isRegistering) View.VISIBLE else View.GONE

        binding.instructionText.text = if (isRegistering) {
            "Добро пожаловать в BuyTown!\nДля регистрации введите ваш логин, почту и пароль"
        } else {
            "Введите ваш логин и пароль"
        }

        updateButtonsState()
    }

    private fun loginUser() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        lifecycleScope.launch {
            showLoading(true)
            loginViewModel.login(email, password) { resource ->
                showLoading(false)
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

                    Status.LOADING -> TODO()
                }
            }
        }
    }

    private fun registerUser() {
        val username = binding.usernameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        lifecycleScope.launch {
            showLoading(true)
            loginViewModel.register(username, email, password) { resource ->
                showLoading(false)
                when (resource.status) {
                    Status.SUCCESS -> {
                        Toast.makeText(requireContext(), "Registration Successful", Toast.LENGTH_SHORT).show()
                        toggleRegistrationMode()
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                    }

                    Status.LOADING -> TODO()
                }
            }
        }
    }

    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateButtonsState()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        binding.usernameEditText.addTextChangedListener(textWatcher)
        binding.emailEditText.addTextChangedListener(textWatcher)
        binding.passwordEditText.addTextChangedListener(textWatcher)
    }

    private fun updateButtonsState() {
        val isEmailNotEmpty = binding.emailEditText.text.toString().isNotEmpty()
        val isPasswordNotEmpty = binding.passwordEditText.text.toString().isNotEmpty()
        val isUsernameNotEmpty = binding.usernameEditText.text.toString().isNotEmpty()

        if (isRegistering) {
            binding.registerButton.isEnabled = isUsernameNotEmpty && isEmailNotEmpty && isPasswordNotEmpty
        } else {
            binding.loginButton.isEnabled = isEmailNotEmpty && isPasswordNotEmpty
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        enableInputs(!isLoading)
    }

    private fun enableInputs(enable: Boolean) {
        binding.emailEditText.isEnabled = enable
        binding.passwordEditText.isEnabled = enable
        binding.usernameEditText.isEnabled = enable
        binding.loginButton.isEnabled = enable
        binding.registerButton.isEnabled = enable
        binding.registerSwitchText.isEnabled = enable
        binding.authSwitchText.isEnabled = enable
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


