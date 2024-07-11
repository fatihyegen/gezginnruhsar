package com.example.gezginnruhsarr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gezginnruhsarr.databinding.ActivityMainanaBinding
import com.example.gezginnruhsarr.fragment.SehirListesiFragment
import com.example.gezginnruhsarr.fragment.kullanicifragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Mainana : AppCompatActivity() {
    private lateinit var binding: ActivityMainanaBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainanaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        // Firebase dilini ayarlayın
        auth.setLanguageCode("tr") // Türkçe dil kodu

        bottomNav = findViewById(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.itsehirlistesi -> {
                    loadFragment(SehirListesiFragment())
                    true
                }
                R.id.itkullanici -> {
                    loadFragment(kullanicifragment())
                    true
                }
                else -> false
            }
        }

        // Default olarak ana sayfa fragment'ını yükleyin
        if (savedInstanceState == null) {
            bottomNav.selectedItemId = R.id.itsehirlistesi
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}
