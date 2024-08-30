package com.buytown.ru.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.buytown.ru.MainActivity
import com.buytown.ru.R
import com.buytown.ru.databinding.FragmentLoginBinding
import com.buytown.ru.utils.Resource
import com.buytown.ru.utils.Status
import dagger.hilt.android.AndroidEntryPoint

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

        setupTextWatchers()
        updateButtonsState()

        loginViewModel.loginResult.observe(viewLifecycleOwner) { resource ->
            handleResult(resource)
        }
        loginViewModel.registerResult.observe(viewLifecycleOwner) { resource ->
            handleResult(resource)
        }
        loginViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }
    }

    private fun toggleRegistrationMode() {
        isRegistering = !isRegistering

        binding.usernameTextInputLayout.visibility = if (isRegistering) View.VISIBLE else View.GONE
        binding.loginButton.text = if (isRegistering) "Register" else "Login"
        binding.registerSwitchText.text = if (isRegistering) "Already have an account? Log in" else "Don't have an account? Register"
    }

    private fun loginUser() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        loginViewModel.login(email, password)
    }

    private fun registerUser() {
        val username = binding.usernameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        loginViewModel.register(username, email, password)
    }

    private fun handleResult(resource: Resource<*>) {
        binding.errorText.visibility = View.GONE
        when (resource.status) {
            Status.SUCCESS -> {
                when (resource.data) {
                    is String -> {
                        // Успешная авторизация, сохраняем токен и выполняем навигацию
                        (requireActivity() as MainActivity).saveToken(resource.data)
                        findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                    }
                    is Unit -> {
                        // Успешная регистрация, переключаем режим на вход
                        toggleRegistrationMode()
                    }
                }
            }
            Status.ERROR -> {
                binding.errorText.text = resource.message
                binding.errorText.visibility = View.VISIBLE
            }
            Status.LOADING -> {
                // Показывать иконку загрузки или сообщение. Обработка в showLoading()
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

        binding.loginButton.isEnabled = if (isRegistering) {
            isEmailNotEmpty && isPasswordNotEmpty && isUsernameNotEmpty
        } else {
            isEmailNotEmpty && isPasswordNotEmpty
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
        binding.registerSwitchText.isEnabled = enable
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
