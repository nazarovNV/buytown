package com.buytown.ru.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.buytown.ru.MainActivity
import com.buytown.ru.R
import com.buytown.ru.databinding.FragmentProductListBinding
import com.buytown.ru.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private val productViewModel: ProductViewModel by viewModels()
    private val productListAdapter = ProductListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()

        val token = (requireActivity() as MainActivity).getToken() ?: return
        productViewModel.fetchProducts("Bearer $token")

        binding.addProductButton.setOnClickListener {
            findNavController().navigate(R.id.action_productListFragment_to_addProductFragment)
        }
    }

    private fun setupRecyclerView() {
        binding.productsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productListAdapter
        }
    }

    private fun setupObservers() {
        productViewModel.products.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    resource.data?.let {
                        if (it.isEmpty()) {
                            showNoProductsMessage(true)
                        } else {
                            showNoProductsMessage(false)
                            productListAdapter.setProducts(it)
                        }
                    }
                }
                Status.ERROR -> {
                    showNoProductsMessage(true)
                    binding.noProductsText.text = resource.message
                }
                Status.LOADING -> {
                    // здесь можно показать индикатор загрузки
                }
            }
        })
    }

    private fun showNoProductsMessage(show: Boolean) {
        binding.noProductsText.visibility = if (show) View.VISIBLE else View.GONE
        binding.addProductButton.visibility = if (show) View.VISIBLE else View.GONE
        binding.productsRecyclerView.visibility = if (show) View.GONE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
