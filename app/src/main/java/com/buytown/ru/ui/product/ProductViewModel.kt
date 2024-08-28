package com.buytown.ru.ui.product

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

    fun fetchProducts(token: String) {
        _products.value = Resource.loading()
        viewModelScope.launch {
            try {
                val products = productRepository.getProducts(token)
                _products.value = Resource.success(products)
            } catch (e: Exception) {
                _products.value = Resource.error("Не удалось загрузить товары.")
            }
        }
    }
}
