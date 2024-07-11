package com.example.gezginnruhsarr.adapterler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gezginnruhsarr.R
import com.example.gezginnruhsarr.CommentModel

class CommentListAdapter(private val commentList: ArrayList<CommentModel>) :
    RecyclerView.Adapter<CommentListAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_yorum_with_text, parent, false)
        return CommentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val currentComment = commentList[position]
        holder.emailTextView.text = currentComment.email
        holder.commentTextView.text = currentComment.comment
        holder.dateTextView.text = currentComment.date
    }

    override fun getItemCount() = commentList.size

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val emailTextView: TextView = itemView.findViewById(R.id.item_email)
        val commentTextView: TextView = itemView.findViewById(R.id.item_comment)
        val dateTextView: TextView = itemView.findViewById(R.id.item_date)
    }
}
