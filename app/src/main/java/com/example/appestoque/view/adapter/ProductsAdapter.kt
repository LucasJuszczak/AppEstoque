package com.example.appestoque.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appestoque.databinding.RowGuestBinding
import com.example.appestoque.model.ProductModel
import com.example.appestoque.view.listener.OnProductListener
import com.example.appestoque.view.viewholder.ProductViewHolder

class ProductsAdapter: RecyclerView.Adapter<ProductViewHolder>() {
    private var productList: List<ProductModel> = listOf()
    private lateinit var listener : OnProductListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val item = RowGuestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(item, listener)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updatedProducts(list: List<ProductModel>){
        productList = list
        //Atualizar
        notifyDataSetChanged()
    }

    fun attachListener(productListener: OnProductListener){
        listener = productListener
    }
}