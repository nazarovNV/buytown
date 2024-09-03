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
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _products = MutableLiveData<Resource<List<Product>>>()
    val products: LiveData<Resource<List<Product>>> = _products



    fun fetchProducts() {
        viewModelScope.launch {
            _products.value = Resource.loading()
            try {
                val response = productRepository.getProducts()
                _products.value = Resource.success(response)
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error fetching products", e)
                _products.value = Resource.error("Failed to fetch products.")
            }
        }
    }

}
