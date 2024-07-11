package com.example.gezginnruhsarr

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gezginnruhsarr.databinding.ActivityMainKayitolBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun isValidEmail(email: String): Boolean {
    val emailRegex = Regex("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")
    return emailRegex.matches(email)
}
class uye {
    var uno: Int = 0
    var isim: String = ""
    var soyisim: String = ""
    var yas: Int = 0
    var sifre: String = ""
    var mail: String = ""
    var telefon: String =" "

    constructor(isim: String, soyisim: String, yas: Int, sifre: String, mail: String, telefon:String) {
        this.isim = isim
        this.soyisim = soyisim
        this.yas = yas
        this.sifre = sifre
        this.mail = mail
        this.telefon=telefon
    }
}
    class MainKayitol : AppCompatActivity() {
        lateinit var auth: FirebaseAuth

        public override fun onStart() {
            super.onStart()
            val currentUser = auth.currentUser
            if (currentUser != null) {
                // User is signed in
            }
        }
        private lateinit var binding: ActivityMainKayitolBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            auth = Firebase.auth
            super.onCreate(savedInstanceState)
            binding = ActivityMainKayitolBinding.inflate(layoutInflater)
            val view = binding.root
            setContentView(view)
            uyeOl()
        }
        private fun updateUI(user: FirebaseUser?) {
            if (user != null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Kullanıcı oluşturulamadı.", Toast.LENGTH_SHORT).show()
            }
        }
        fun uyeOl() {
            val db = Firebase.firestore
            binding.btnKayitol2.setOnClickListener {

                val etad = binding.kayitAd.text.toString()
                val etsoyad = binding.kayitSoyad.text.toString()
                val etyas = binding.kayitYas.text.toString().toInt()
                val etsifre = binding.password.text.toString()
                val etmail = binding.email.text.toString()
                val ettelefon=binding.kayitTel.text.toString()

                val isValid = isValidEmail(etmail)

                if (isValid) {
                    auth.createUserWithEmailAndPassword(etmail, etsifre)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                val user = auth.currentUser
                                if (user != null) {
                                    val userDocument = hashMapOf(
                                        "uid" to user.uid,
                                        "isim" to etad,
                                        "soyisim" to etsoyad,
                                        "yas" to etyas,
                                        "sifre" to etsifre,
                                        "mail" to etmail,
                                        "telefon" to ettelefon
                                    )
                                    db.collection("isimler")
                                        .add(userDocument)  // add() metodu kullanılarak ekleme işlemi yapılır
                                        .addOnSuccessListener { documentReference ->
                                            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                                            Toast.makeText(this, "eklendi", Toast.LENGTH_SHORT).show()
                                            val intent = Intent(this, MainActivity::class.java)
                                            startActivity(intent)
                                            finish()
                                        }
                                        .addOnFailureListener { e ->
                                            Log.w(TAG, "Error adding document", e)
                                            Toast.makeText(this, "eklenemedii", Toast.LENGTH_SHORT).show()
                                        }
                                }
                            } else {
                                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Lütfen geçerli bir e-posta adresi girin.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
