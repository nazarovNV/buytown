package com.buytown.ru.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.buytown.ru.R
import com.buytown.ru.data.model.Product
import com.bumptech.glide.Glide
import com.buytown.ru.databinding.ItemProductBinding

class ProductListAdapter : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    private val products = mutableListOf<Product>()

    fun setProducts(newProducts: List<Product>) {
        products.clear()
        products.addAll(newProducts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            with(binding) {
                productName.text = product.name
                productDescription.text = product.description
                productPrice.text = product.price.toString()

                Glide.with(root)
                    .load(product.imageUrl)
                    .placeholder(R.drawable.placeholder_image) // добавьте заглушку
                    .into(productImage)
            }
        }
    }
}
