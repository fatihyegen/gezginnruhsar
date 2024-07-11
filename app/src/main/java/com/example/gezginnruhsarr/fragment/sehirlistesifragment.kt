package com.example.gezginnruhsarr.fragment

import com.example.gezginnruhsarr.adapterler.ItemListAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gezginnruhsarr.ItemModel
import com.example.gezginnruhsarr.MockList
import com.example.gezginnruhsarr.R

class SehirListesiFragment : Fragment() {

    private lateinit var adapter: ItemListAdapter
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sehirlistesifragment, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview2)
        searchView = view.findViewById<SearchView>(R.id.searchView)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        MockList.getMockedItemList { items ->
            adapter = ItemListAdapter(items as ArrayList<ItemModel>)
            recyclerView.adapter = adapter

            adapter.setOnItemClickListener(object : ItemListAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val selectedItem = items[position]

                    // FragmentDetay'a geçiş yap ve tıklanan öğenin verilerini aktar
                    val fragmentDetay = FragmentDetay()
                    val bundle = Bundle()
                    bundle.putString("ad", selectedItem.ad)
                    bundle.putString("foto", selectedItem.foto)
                    bundle.putString("aciklama", selectedItem.aciklama)
                    bundle.putString("gezilcekyerler", selectedItem.gezilcekyerler)
                    bundle.putString("nesimeshur", selectedItem.nesimeshur)
                    fragmentDetay.arguments = bundle

                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragmentDetay)
                        .addToBackStack(null)
                        .commit()
                }
            })
        }

        return view
    }

    // Fetching data from the database
    private fun fetchDataFromDatabase(itemId: String) {
        // Use itemId to fetch data from the database
        // This should typically be done in an AsyncTask or Coroutine
    }
}
