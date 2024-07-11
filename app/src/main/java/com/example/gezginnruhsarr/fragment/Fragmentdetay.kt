package com.example.gezginnruhsarr.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.gezginnruhsarr.R

class FragmentDetay : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fragmentdetay, container, false)

        // TextView'ları ve ImageView'ı bul
        val textViewAd = view.findViewById<TextView>(R.id.txtiladi)
        val imgviewFoto = view.findViewById<ImageView>(R.id.imgfoto)
        val textviewaciklama = view.findViewById<TextView>(R.id.txtaciklama)

        // Bundle'dan verileri al
        val ad = arguments?.getString("ad")
        val foto = arguments?.getString("foto")
        val aciklama = arguments?.getString("aciklama")
        val gezilecek = arguments?.getString("gezilcekyerler")
        val nesimeshur=arguments?.getString("nesimeshur")

        // Verileri TextView ve ImageView'a yerleştir
        textViewAd.text = ad
        if (foto != null) {
            Glide.with(this).load(foto).into(imgviewFoto)
        }
        textviewaciklama.text = aciklama

        // Butona tıklama olayı ekle
        val btngezilecek = view.findViewById<Button>(R.id.button1)
        btngezilecek.setOnClickListener {

            // Yeni fragment oluştur
            val gezilecekFragment = GezilecekFragment()

            val bundle = Bundle()
            bundle.putString("ad", ad)
            bundle.putString("foto", foto)
            bundle.putString("gezilcekyerler", gezilecek)

            // Bundle'ı fragment'a ekle
            gezilecekFragment.arguments = bundle

            // FragmentManager ile fragment değiştir
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, gezilecekFragment)
            transaction.addToBackStack(null)
            transaction.commit()

            Log.d("FragmentDetay", "Ad: $ad, Foto: $foto, Açıklama: $aciklama, Gezilecek Yerler: $gezilecek")

        }
        val btnnesimeshur = view.findViewById<Button>(R.id.button2)
        btnnesimeshur.setOnClickListener {

            // Yeni fragment oluştur
            val nesimeshurfragment = NesiMeshurFragment()

            // Verileri içeren bir Bundle oluştur
            val bundle = Bundle()
            bundle.putString("ad", ad)
            bundle.putString("foto", foto)
            bundle.putString("nesimeshur", nesimeshur)

            // Bundle'ı fragment'a ekle
            nesimeshurfragment.arguments = bundle

            // FragmentManager ile fragment değiştir
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,nesimeshurfragment)
            transaction.addToBackStack(null)
            transaction.commit()

            Log.d("FragmentDetay", "Ad: $ad, Foto: $foto, Açıklama: $aciklama, Nesi Meşhur: $nesimeshur")

        }
        return view
    }
}
