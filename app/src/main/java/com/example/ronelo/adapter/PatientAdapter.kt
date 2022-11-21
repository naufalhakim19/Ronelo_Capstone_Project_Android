package com.example.ronelo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ronelo.R
import com.example.ronelo.databinding.ActivityPatientBinding
import com.example.ronelo.model.ModelPatient

class PatientAdapter : RecyclerView.Adapter<PatientAdapter.ViewHolder>() {

    private var datalist = ArrayList<ModelPatient>()

    fun patient(data: List<ModelPatient>) {
        this.datalist.clear()
        this.datalist.addAll(data)
    }

    inner class ViewHolder(private val binding: ActivityPatientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("StringFormatInvalid")
        fun bind(modelPatient: ModelPatient) {
            Glide.with(itemView.context)
                .load(modelPatient.img)
                .into(binding.ivImgPatient)
            binding.tvName.text = itemView.context.getString(R.string.name_s, modelPatient.name)
            binding.tvAge.text = itemView.context.getString(R.string.age_s, modelPatient.age)
            binding.tvGender.text =
                itemView.context.getString(R.string.gender_s, modelPatient.gender)
            binding.tvDisease.text =
                itemView.context.getString(R.string.disease_s, modelPatient.disease)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val patient =
            ActivityPatientBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(patient)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val patient = datalist[position]
        holder.bind(patient)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}