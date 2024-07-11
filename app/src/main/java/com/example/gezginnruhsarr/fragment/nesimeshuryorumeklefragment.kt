package com.example.gezginnruhsarr.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.gezginnruhsarr.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class NesiMeshurYorumEkleFragment : DialogFragment() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_yeni_yorum_ekle, container, false)

        // Firebase Firestore ve Auth instance'larını al
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        // Layout'taki bileşenleri tanımla
        val editTextYorum = view.findViewById<EditText>(R.id.editTextYorum)
        val buttonKaydet = view.findViewById<Button>(R.id.buttonKaydet)

        // Kaydet butonuna tıklama işlemi ekle
        buttonKaydet.setOnClickListener {
            kaydetYorum(editTextYorum.text.toString())
        }

        return view
    }

    private fun kaydetYorum(yorum: String) {
        // Kullanıcının email adresini al
        val userEmail = auth.currentUser?.email ?: ""

        val ilAdi = arguments?.getString("ad") ?: ""
        val tarih = Calendar.getInstance().time.toString()

        // Yorumu Firestore'a ekle
        val yorumMap = hashMapOf(
            "email" to userEmail,
            "ilAdi" to ilAdi,
            "yorum" to yorum,
            "tarih" to tarih
        )

        firestore.collection("yorum2")
            .add(yorumMap)
            .addOnSuccessListener {
                dismiss() // Diyalog fragmentı kapat
            }
            .addOnFailureListener { e ->
                // Yorum eklerken hata oluşursa log tut
                e.printStackTrace()
            }
    }
}
