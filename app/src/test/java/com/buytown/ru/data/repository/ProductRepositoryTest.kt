package com.buytown.ru.data.repository


import com.buytown.ru.data.model.Product
import com.buytown.ru.data.network.AddProductResponse
import com.buytown.ru.data.network.ApiService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class ProductRepositoryTest {

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var productRepository: ProductRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        productRepository = ProductRepository(apiService)
    }

    @Test
    fun `get products success`() = runBlocking {
        val products = listOf(
            Product("category", "description", "1", "url", "name", 100.0)
        )
        `when`(apiService.getProducts()).thenReturn(products)

        val result = productRepository.getProducts()

        assertEquals(products, result)
    }

    @Test(expected = Exception::class)
    fun `get products failure`(): Unit = runBlocking {
        `when`(apiService.getProducts()).thenThrow(RuntimeException("Failed to fetch products"))

        productRepository.getProducts()
    }

    @Test
    fun `add product success`() = runBlocking {
        val product = Product("category", "description", "1", "url", "name", 100.0)
        val addProductResponse = AddProductResponse("Product added successfully")
        `when`(apiService.addProduct(product)).thenReturn(addProductResponse)

        val result = productRepository.addProduct(product)

        assertEquals(addProductResponse, result)
    }

    @Test(expected = Exception::class)
    fun `add product failure`(): Unit = runBlocking {
        val product = Product("category", "description", "1", "url", "name", 100.0)
        `when`(apiService.addProduct(product)).thenThrow(RuntimeException("Failed to add product"))

        productRepository.addProduct(product)
    }
}