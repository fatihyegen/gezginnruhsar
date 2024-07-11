package com.example.gezginnruhsarr.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.gezginnruhsarr.adapterler.CommentListAdapter
import com.example.gezginnruhsarr.CommentModel
import com.example.gezginnruhsarr.R
import com.google.firebase.firestore.FirebaseFirestore


class NesiMeshurFragment : Fragment() {

    private lateinit var commentAdapter: CommentListAdapter
    private val commentList: ArrayList<CommentModel> = arrayListOf()
    private lateinit var firestore: FirebaseFirestore
    private var currentIlAdi: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firestore = FirebaseFirestore.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_nesi_meshur, container, false)

        val textViewAd = view.findViewById<TextView>(R.id.txtiladi)
        val imgviewFoto = view.findViewById<ImageView>(R.id.imgfoto)
        val textViewNesiMeshur = view.findViewById<TextView>(R.id.txtnesimeshur)
        val recyclerViewComments2 = view.findViewById<RecyclerView>(R.id.recyclerViewComments2)

        val ad = arguments?.getString("ad")
        val foto = arguments?.getString("foto")
        val nesimeshur = arguments?.getString("nesimeshur")
        val buttonYorumEkle = view.findViewById<Button>(R.id.buttonYorumEkle)


        textViewAd.text = ad
        if (foto != null) {
            Glide.with(this).load(foto).into(imgviewFoto)
        }

        // Gezilecek yerleri virgülle ayır ve alt alta yaz
        if (!nesimeshur.isNullOrEmpty()) {
            val gezilecekYerlerArray = nesimeshur.split(",")
            val formattedGezilecekYerler = gezilecekYerlerArray.joinToString(separator = "\n") { it.trim() }
            textViewNesiMeshur.text = formattedGezilecekYerler
        } else {
            textViewNesiMeshur.text = "Nesi meşhur bilgisi yok"
        }
        commentAdapter = CommentListAdapter(commentList)
        recyclerViewComments2.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewComments2.adapter = commentAdapter

        // Firestore'dan yorumları çek
        fetchComments(ad ?: "")

        buttonYorumEkle.setOnClickListener {
            val yeniYorumEkleFragment = NesiMeshurYorumEkleFragment()

            // İl adını (gezilecek yer adını) argument olarak gönder
            val bundle = Bundle()
            bundle.putString("ad", ad)
            yeniYorumEkleFragment.arguments = bundle

            yeniYorumEkleFragment.show(childFragmentManager, "YeniYorumEkleFragment")
        }

        return view
    }
    private fun fetchComments(ilAdi: String) {
        // Sadece il adı değiştiğinde ve ilk defa yorumlar çekilecekse
        if (currentIlAdi != ilAdi) {
            currentIlAdi = ilAdi // İl adını güncelle

            commentList.clear() // Önceki yorumları temizle

            firestore.collection("yorum2")
                .whereEqualTo("ilAdi", ilAdi)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val email = document.getString("email") ?: ""
                        val comment = document.getString("yorum") ?: ""
                        val date = document.getString("tarih") ?: ""

                        val commentModel = CommentModel(email, comment, date)
                        commentList.add(commentModel)
                    }
                    commentAdapter.notifyDataSetChanged() // Adapter'a değişiklikleri bildir
                }
                .addOnFailureListener { exception ->
                    Log.e("GezilecekFragment", "Veri çekerken hata oluştu", exception)
                }
        }
    }
}