package com.example.gezginnruhsarr.fragment

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.gezginnruhsarr.MainActivity
import com.example.gezginnruhsarr.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class kullanicifragment : Fragment() {
    private lateinit var textviewad: TextView
    private lateinit var textviewsoyad: TextView
    private lateinit var textviewmail: TextView
    private lateinit var textviewtelefon: TextView
    private lateinit var vt: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vt = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Bu fragment için layout'u şişir
        val view = inflater.inflate(R.layout.fragment_fragmentkullanici, container, false)

        textviewad = view.findViewById(R.id.txtad)
        textviewmail = view.findViewById(R.id.txtmail)
        textviewtelefon = view.findViewById(R.id.txttelefon)
        textviewsoyad = view.findViewById(R.id.txtsoyad)
        val buttonSignOut: Button = view.findViewById(R.id.button_sign_out)

        val currentUser = auth.currentUser

        if (currentUser != null) {
            val uid = currentUser.uid
            Log.d(TAG, "Mevcut kullanıcı UID: $uid")

            vt.collection("isimler")
                .whereEqualTo("uid", uid)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        for (document in documents) {
                            Log.d(TAG, "${document.id} => ${document.data}")
                            textviewad.text = document.getString("isim") ?: "Ad bulunamadı"
                            textviewsoyad.text = document.getString("soyisim") ?: "Soyad bulunamadı"
                            textviewtelefon.text = document.getString("telefon") ?: "Telefon bulunamadı"
                            textviewmail.text = document.getString("mail") ?: "Mail bulunamadı"
                        }
                    } else {
                        Log.d(TAG, "Böyle bir belge yok")
                        textviewad.text = "Ad bulunamadı"
                        textviewtelefon.text = "Telefon bulunamadı"
                        textviewmail.text = "Mail bulunamadı"
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Belge alınırken hata oluştu.", exception)
                    textviewad.text = "Ad bulunamadı"
                    textviewtelefon.text = "Telefon bulunamadı"
                    textviewmail.text = "Mail bulunamadı"
                }
        } else {
            Log.d(TAG, "Hiçbir kullanıcı giriş yapmamış")
            textviewad.text = "Kullanıcı bulunamadı"
            textviewmail.text = "Kullanıcı bulunamadı"
            textviewtelefon.text = "Kullanıcı bulunamadı"
        }

        buttonSignOut.setOnClickListener {
            auth.signOut()
            // MainActivity'e yönlendirme
            val intent = Intent(activity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            Log.d(TAG, "Kullanıcı çıkış yaptı")
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            kullanicifragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}