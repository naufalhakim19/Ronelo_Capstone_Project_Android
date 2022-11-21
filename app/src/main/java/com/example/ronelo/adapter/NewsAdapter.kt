package com.example.ronelo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.ronelo.databinding.ListNewsBinding
import com.example.ronelo.model.ModelNews


class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private var datalist = ArrayList<ModelNews>()

    fun news(data: List<ModelNews>) {
        this.datalist.clear()
        this.datalist.addAll(data)
    }

    inner class ViewHolder(private val binding: ListNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(modelNews: ModelNews) {
            Glide.with(itemView.context)
                .load(modelNews.img_news)
                .into(binding.ivNews)
            binding.titleNews.text = modelNews.title
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val news = ListNewsBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(news)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = datalist[position]
        holder.bind(news)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

}