package com.buytown.ru.ui.product

import android.util.Log
import androidx.lifecycle.*
import com.buytown.ru.data.model.Product
import com.buytown.ru.data.repository.ProductRepository
import com.buytown.ru.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _addProductResult = MutableLiveData<Resource<String>>()
    val addProductResult: LiveData<Resource<String>> = _addProductResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun addProduct(product: Product) {
        viewModelScope.launch {
            _isLoading.value = true
            _addProductResult.value = Resource.loading()
            try {
                val response = productRepository.addProduct(product)
                _addProductResult.value = Resource.success(response.message)
            } catch (e: Exception) {
                Log.e("AddProductViewModel", "Error adding product", e)
                _addProductResult.value = Resource.error(e.message ?: "Failed to add product.")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
