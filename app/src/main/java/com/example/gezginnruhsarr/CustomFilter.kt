package com.example.gezginnruhsarr

import android.widget.Filter
import com.example.gezginnruhsarr.adapterler.NewsListAdapter
import java.util.*
import kotlin.collections.ArrayList

class CustomFilter(
    private var filterList: ArrayList<ItemModel>,
    private var adapter: NewsListAdapter
) : Filter() {
    override fun performFiltering(charSequence: CharSequence?): FilterResults {
        val charString = charSequence?.toString()?.lowercase(Locale.ROOT) ?: ""
        val results = FilterResults()

        results.values = if (charString.isEmpty()) {
            filterList
        } else {
            filterList.filter {
                it.ad.lowercase(Locale.ROOT).contains(charString) ||
                        it.foto.lowercase(Locale.ROOT).contains(charString)
            } as ArrayList<ItemModel>
        }

        return results
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults) {
        adapter.setFilteredNewsList(results.values as ArrayList<ItemModel>)
    }
}