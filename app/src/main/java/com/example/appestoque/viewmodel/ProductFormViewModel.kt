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
    val guest: LiveData<ProductModel> = productModel

    private val _saveGuest = MutableLiveData<SuccessFailure>()
    val saveGuest: LiveData<SuccessFailure> = _saveGuest

    fun get(id: Int){
        productModel.value = repository.get(id)
    }

    fun save(guest: ProductModel) {
        val successFailure = SuccessFailure(true, "")
        if (guest.id == 0) {
            successFailure.success = repository.insert(guest)
        }else{
            successFailure.success = repository.update(guest)
        }
    }

}