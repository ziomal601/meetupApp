package com.example.myapplication.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.SimpleModel


class RecyclerViewAdapter(private val list: List<SimpleModel>) :
    RecyclerView.Adapter<SimpleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SimpleViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        val movie: SimpleModel = list[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = list.size

}

class SimpleViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_view_kurwa, parent, false)) {
    private var firstTextView: TextView? = null
    private var secondTexView: TextView? = null


    init {
        firstTextView = itemView.findViewById(R.id.firstTextView)
        secondTexView = itemView.findViewById(R.id.secondTextView)
    }

    fun bind(movie: SimpleModel) {
        firstTextView?.text = movie.first
        secondTexView?.text = movie.second
    }

}
