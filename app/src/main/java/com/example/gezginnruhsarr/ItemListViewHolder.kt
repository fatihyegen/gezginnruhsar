package com.example.gezginnruhsarr

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ItemListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bindItems(itemModel: ItemModel) {
        val description = itemView.findViewById<TextView>(R.id.item_textName)
        val image = itemView.findViewById<ImageView>(R.id.image)
        description.text = itemModel.ad
        val aciklama=itemView.findViewById<TextView>(R.id.txtaciklama)

        // Eğer itemModel.foto bir drawable resource ID ise:

        // Eğer itemModel.foto bir URL ise:
        Glide.with(itemView.context)
            .load(itemModel.foto) // URL'yi yükle
            .into(image) // Resmi ImageView'e yerleştir
    }
}
