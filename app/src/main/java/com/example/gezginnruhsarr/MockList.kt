package com.example.gezginnruhsarr

import android.content.Context
import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

object MockList {

    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    @SuppressLint("RestrictedApi")
    fun getMockedItemList(callback: (List<ItemModel>) -> Unit) {
        firestore.collection("sehirler")
            .get()
            .addOnSuccessListener { documents ->
                val itemList = ArrayList<ItemModel>()
                val pendingTasks = documents.size() // Number of pending tasks
                if (pendingTasks == 0) {
                    callback(emptyList())
                    return@addOnSuccessListener
                }

                for (document in documents) {
                    val ad = document.getString("ad") ?: "Ad bulunamadı"
                    val aciklama = document.getString("sehirdetay")?: "Açıklama bulunamadı"
                    val nesimeshur = document.getString("nesimeshur")?:"Nesi Meşhur bulunamadı"
                    val gezilecekyerler = document.getString("gezilcekyerler")?:"Gezilecek yer bulunamadı"
                    val fotoField = document.get("foto")
                    val fotoUrl = when (fotoField) {
                        is String -> fotoField
                        is Map<*, *> -> fotoField["url"] as? String ?: "Foto bulunamadı"
                        else -> "Foto bulunamadı"
                    }
                    itemList.add(ItemModel(ad, fotoUrl, nesimeshur, aciklama, gezilecekyerler))
                    if (itemList.size == pendingTasks) {
                        callback(itemList)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("MockList", "Error getting documents: ", exception)
                callback(emptyList())
            }
    }

    fun getMockedStaticItemList(context: Context): List<ItemModel> {
        val itemList: ArrayList<ItemModel> = ArrayList()
        val cityNames = listOf("City1", "City2", "City3") // Replace with actual city names
        for (city in cityNames) {
            val resourceId = getResourceId(context, city.toLowerCase())
        }
        return itemList
    }

    private fun getResourceId(context: Context, resourceName: String): Int {
        return context.resources.getIdentifier(resourceName, "drawable", context.packageName)
    }
}

// Assuming ItemModel is defined as follows:
