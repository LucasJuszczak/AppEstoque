package com.example.appestoque.view.viewholder

import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.appestoque.databinding.RowGuestBinding
import com.example.appestoque.model.ProductModel
import com.example.appestoque.view.listener.OnProductListener

class ProductViewHolder(private val bind: RowGuestBinding, private val listener: OnProductListener) : RecyclerView.ViewHolder(bind.root) {
    fun bind(guest: ProductModel) {
        bind.textName.text = guest.name
        bind.textQuantity.text = guest.quantity
        bind.textValue.text = guest.value

        bind.textName.setOnClickListener{
            listener.onClick(guest.id)
        }

        bind.textName.setOnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle("Remoção do Produto")
                .setMessage("Deseja remover o produto?")
                .setPositiveButton("Sim"
                ) { dialog, which ->
                    listener.onDelete(guest.id)
                }
                .setNegativeButton("Não", null)
                .create()
                .show()
            true
        }
    }
}