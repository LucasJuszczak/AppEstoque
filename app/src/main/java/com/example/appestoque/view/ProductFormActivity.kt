package com.example.appestoque.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.appestoque.R
import com.example.appestoque.constants.DataBaseConstants
import com.example.appestoque.databinding.ActivityProductFormBinding
import com.example.appestoque.model.ProductModel
import com.example.appestoque.viewmodel.ProductFormViewModel

class ProductFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityProductFormBinding
    private lateinit var viewModel : ProductFormViewModel

    private var productId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[ProductFormViewModel::class.java]

        binding.buttonEnviar.setOnClickListener(this)
        binding.radioPresent.isChecked = true

        observe()
        loadData()

    }

    override fun onClick(view: View) {
        if(view.id == R.id.button_enviar){
            val name = binding.editTextName.text.toString()
            val presence = binding.radioPresent.isChecked
            val quantity = binding.editTextQuantity.text.toString()
            val value = binding.editTextValue.text.toString()

            val model = ProductModel(productId, name, presence, quantity, value)
            viewModel.save(model)
            finish()
        }
    }

    private fun observe(){
        viewModel.product.observe(this, Observer {
            binding.editTextName.setText(it.name)
            if(it.presence){
                binding.radioPresent.isChecked = true
            }else{
                binding.radioAbsent.isChecked = true
            }
            binding.editTextQuantity.setText(it.quantity)
            binding.editTextValue.setText(it.value)
        })

        viewModel.saveProduct.observe(this, Observer {
            if(it.success){
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

    private fun loadData() {
        val bundle = intent.extras
        if(bundle != null){
            productId = bundle.getInt(DataBaseConstants.PRODUCT.ID)
            viewModel.get(productId)
        }
    }
}