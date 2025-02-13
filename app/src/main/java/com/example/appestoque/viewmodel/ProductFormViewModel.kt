package com.example.appestoque.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appestoque.model.ProductModel
import com.example.appestoque.model.SuccessFailure
import com.example.appestoque.repository.ProductRepository

class ProductFormViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ProductRepository.getInstance(application)

    private val productModel = MutableLiveData<ProductModel>()
    val product: LiveData<ProductModel> = productModel

    private val _saveProduct = MutableLiveData<SuccessFailure>()
    val saveProduct: LiveData<SuccessFailure> = _saveProduct

    fun get(id: Int){
        productModel.value = repository.get(id)
    }

    fun save(product: ProductModel) {
        val successFailure = SuccessFailure(true, "")
        if (product.id == 0) {
            successFailure.success = repository.insert(product)
        }else{
            successFailure.success = repository.update(product)
        }
    }

}