package com.example.myapplication.trending.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.AdapterTrendingBinding
import com.example.myapplication.trending.viewmodel.repositories.source.local.models.Trending

class TrendingAdapter(private val list: MutableList<Trending>) :
    RecyclerView.Adapter<TrendingAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        return VH(
            AdapterTrendingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun update(updates: MutableList<Trending>) {
        if (list.isNotEmpty())
            list.clear()
        list.addAll(updates)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class VH(private val mBinding: AdapterTrendingBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        fun bind(trending: Trending) = mBinding.run {
            tvName.text = trending.username
            tvLang.text = trending.language
            tvLibName.text = trending.libraryName
            tvStars.text = trending.stars.toString()
            tvDesc.text = trending.description

        }
    }
}