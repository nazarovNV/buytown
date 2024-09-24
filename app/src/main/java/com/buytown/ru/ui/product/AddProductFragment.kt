package com.buytown.ru.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.buytown.ru.data.model.Product
import com.buytown.ru.databinding.FragmentAddProductBinding
import com.buytown.ru.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProductFragment : Fragment() {

    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!
    private val addProductViewModel: AddProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addButton.setOnClickListener {
            val name = binding.itemNameEditText.text.toString()
            val price = binding.itemPriceEditText.text.toString().toDoubleOrNull()
            val description = binding.itemDescriptionEditText.text.toString()
            val category = binding.itemCategoryEditText.text.toString()
            val imageUrl = binding.itemImageUrlEditText.text.toString()

            if (name.isBlank() || price == null || description.isBlank() || category.isBlank()) {
                Toast.makeText(context, "Заполните все обязательные поля", Toast.LENGTH_SHORT).show()
            } else {
                val product = Product(category, description,0.toString(), imageUrl, name, price)
                addProductViewModel.addProduct(product)
            }
        }

        addProductViewModel.addProductResult.observe(viewLifecycleOwner) { resource ->
            binding.progressBar.visibility = if (resource.status == Status.LOADING) View.VISIBLE else View.GONE
            when (resource.status) {
                Status.SUCCESS -> {
                    Toast.makeText(context, resource.data, Toast.LENGTH_SHORT).show()
                    clearForm()
                }
                Status.ERROR -> {
                    Toast.makeText(context, resource.message, Toast.LENGTH_SHORT).show()
                }
                else -> { /* DO NOTHING */ }
            }
        }

        addProductViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.addButton.isEnabled = !isLoading
        }
    }

    private fun clearForm() {
        binding.itemNameEditText.text?.clear()
        binding.itemPriceEditText.text?.clear()
        binding.itemDescriptionEditText.text?.clear()
        binding.itemCategoryEditText.text?.clear()
        binding.itemImageUrlEditText.text?.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
