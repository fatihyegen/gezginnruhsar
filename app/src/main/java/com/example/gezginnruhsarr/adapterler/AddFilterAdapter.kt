package com.example.gezginnruhsarr.adapterler

import com.example.gezginnruhsarr.CustomFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.gezginnruhsarr.ItemListViewHolder
import com.example.gezginnruhsarr.ItemModel
import com.example.gezginnruhsarr.R

class NewsListAdapter(
    private var newsList: List<ItemModel>, // Başlangıçta newsList'i List<ItemModel> olarak tanımlıyoruz
    private val onItemClick: (view: View, newsDTO: ItemModel) -> Unit
) : RecyclerView.Adapter<ItemListViewHolder>(), Filterable {

    private var filteredNewsList: List<ItemModel> = newsList // Filtrelenmiş haber listesi, başlangıçta tam liste olarak ayarlanır
    private var filter: CustomFilter? = null // Filtre nesnesi, başlangıçta null olarak ayarlanır

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        // Yeni bir view oluşturup ViewHolder'a dönüştürüyoruz
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image_with_text, parent, false)
        return ItemListViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return filteredNewsList.size // Filtrelenmiş haber listesinin eleman sayısını döndürüyoruz
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        holder.bindItems(newsList[position])
        holder.itemView.setOnClickListener { view ->
            onItemClick(view, newsList[position])
        }
    }


    override fun getFilter(): Filter {

        return filter as CustomFilter // Filtre nesnesini döndürüyoruz
    }

    // NewsListAdapter'e ait yardımcı metodlar:

    // Haber listesini güncelleyen metod
    fun updateNewsList(newList: List<ItemModel>) {
        newsList = newList
        filteredNewsList = newList // Filtrelenmiş liste değişikliklere göre güncellenir
        notifyDataSetChanged() // Adapter'a değişiklik olduğunu bildiriyoruz
    }

    // Filtrelenmiş haber listesini güncelleyen metod
    fun setFilteredNewsList(filteredList: List<ItemModel>) {
        filteredNewsList = filteredList // Filtrelenmiş liste değiştirilir
        notifyDataSetChanged() // Adapter'a değişiklik olduğunu bildiriyoruz
    }
}
