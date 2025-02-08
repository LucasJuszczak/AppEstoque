package com.example.appestoque.view.viewholder

import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.appestoque.databinding.RowGuestBinding
import com.example.appestoque.model.GuestModel
import com.example.appestoque.view.listener.OnGuestListener

class GuestViewHolder(private val bind: RowGuestBinding, private val listener: OnGuestListener) : RecyclerView.ViewHolder(bind.root) {
    fun bind(guest: GuestModel) {
        //Utilizando a classe R do Java
//        itemView.findViewById<TextView>(R.id.text_name).text = guest.name
        //Utilizando viewBinding
        bind.textName.text = guest.name

        bind.textName.setOnClickListener{
            listener.onClick(guest.id)
        }

        bind.textName.setOnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle("Remoção do convidado")
                .setMessage("Deseja remover?")
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