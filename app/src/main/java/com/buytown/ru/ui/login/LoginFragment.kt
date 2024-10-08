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

        binding.authSwitchText.setOnClickListener {
            toggleRegistrationMode()
        }

        binding.registerButton.setOnClickListener {
            registerUser()
        }

        // Добавим TextWatchers для отслеживания изменений текста
        setupTextWatchers()
        updateButtonsState()

        // Соберите результаты входа и регистрации
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
        val navController = findNavController()

        loginViewModel.login(email, password, navController)
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
                        // Успешная авторизация
                        // Навигация будет выполнена из ViewModel
                    }
                    is Unit -> {
                        // Успешная регистрация
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
