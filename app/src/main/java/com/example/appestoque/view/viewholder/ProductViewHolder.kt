package com.example.appestoque.view.viewholder

import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.appestoque.databinding.RowGuestBinding
import com.example.appestoque.model.ProductModel
import com.example.appestoque.view.listener.OnProductListener

class ProductViewHolder(private val bind: RowGuestBinding, private val listener: OnProductListener) : RecyclerView.ViewHolder(bind.root) {
    fun bind(product: ProductModel) {
        bind.textName.text = product.name
        bind.textQuantity.text = product.quantity
        bind.textValue.text = product.value

        bind.textName.setOnClickListener{
            listener.onClick(product.id)
        }

        bind.textName.setOnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle("Remoção do Produto")
                .setMessage("Deseja remover o produto?")
                .setPositiveButton("Sim"
                ) { dialog, which ->
                    listener.onDelete(product.id)
                }
                .setNegativeButton("Não", null)
                .create()
                .show()
            true
        }
    }
}