package com.example.gezginnruhsarr.adapterler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gezginnruhsarr.ItemListViewHolder
import com.example.gezginnruhsarr.ItemModel
import com.example.gezginnruhsarr.R

class ItemListAdapter(val itemList: ArrayList<ItemModel>) :
    RecyclerView.Adapter<ItemListViewHolder>() {

    // Listener interface
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    // Listener instance
    private var listener: OnItemClickListener? = null

    // Listener setter
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        return ItemListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_image_with_text,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        holder.bindItems(itemList[position])

        // ViewHolder'a tıklama olayını dinlemesi için bir listener ekleyin
        holder.itemView.setOnClickListener {
            // Tıklanan öğenin pozisyonunu dinleyiciye iletin
            listener?.onItemClick(position)
        }
    }
}
