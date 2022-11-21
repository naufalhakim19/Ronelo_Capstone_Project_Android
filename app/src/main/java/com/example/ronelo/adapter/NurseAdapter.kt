package com.example.ronelo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.ronelo.databinding.ListNurseBinding
import com.example.ronelo.model.ModelNurse

class NurseAdapter : RecyclerView.Adapter<NurseAdapter.ViewHolder>() {

    private var dataList = ArrayList<ModelNurse>()

    fun nurse(data: List<ModelNurse>) {
        this.dataList.clear()
        this.dataList.addAll(data)
    }

    inner class ViewHolder(private val binding: ListNurseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(modelNurse: ModelNurse) {
            Glide.with(itemView.context)
                .load(modelNurse.img_nurse)
                .into(binding.nurseImg)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val nurse =
            ListNurseBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(nurse)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nurse = dataList[position]
        holder.bind(nurse)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}