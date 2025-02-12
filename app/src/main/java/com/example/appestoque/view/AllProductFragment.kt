package com.example.appestoque.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appestoque.constants.DataBaseConstants
import com.example.appestoque.databinding.FragmentAllGuestsBinding
import com.example.appestoque.view.adapter.ProductsAdapter
import com.example.appestoque.view.listener.OnProductListener
import com.example.appestoque.viewmodel.ProductsViewModel

class AllProductFragment : Fragment() {

    private var _binding: FragmentAllGuestsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProductsViewModel
    private val adapter = ProductsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(ProductsViewModel::class.java)
        _binding = FragmentAllGuestsBinding.inflate(inflater, container, false)

        binding.recyclerGuests.layoutManager = LinearLayoutManager(context)

        binding.recyclerGuests.adapter = adapter

        val listener = object : OnProductListener {
            override fun onClick(id: Int) {
                val intent = Intent(context, ProductFormActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(DataBaseConstants.PRODUCT.ID, id)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                viewModel.delete(id)
                viewModel.getAll()
            }
        }

        adapter.attachListener(listener)

        viewModel.getAll()

        observe()

        return binding.root
    }

    override fun onResume(){
        super.onResume()
        viewModel.getAll()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        viewModel.products.observe(viewLifecycleOwner) {
            adapter.updateProducts(it)
        }
    }
}