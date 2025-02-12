package com.example.appestoque.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appestoque.model.ProductModel
import com.example.appestoque.repository.ProductRepository

class ProductsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ProductRepository.getInstance(application.applicationContext)

    private val listAllProducts = MutableLiveData<List<ProductModel>>()
    val products: LiveData<List<ProductModel>> = listAllProducts

    fun getAll() {
        listAllProducts.value = repository.getAll()
    }

    fun getPresent() {
        listAllProducts.value = repository.getPresence()
    }

    fun getAbsent() {
        listAllProducts.value = repository.getAbsent()
    }

    fun delete(id: Int) {
        repository.delete(id)
    }
}