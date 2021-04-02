package com.example.myapplication.trending.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.trending.viewmodel.repositories.source.local.models.Trending

class TrendingAdapter(private val list: List<Trending>) :
    RecyclerView.Adapter<TrendingAdapter.VH>() {


    inner class VH(v: View) : RecyclerView.ViewHolder(v) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return list.size
    }
}