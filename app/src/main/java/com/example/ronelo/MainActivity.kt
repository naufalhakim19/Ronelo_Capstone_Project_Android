package com.example.ronelo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ronelo.adapter.NewsAdapter
import com.example.ronelo.adapter.NurseAdapter
import com.example.ronelo.consultMenu.display.ChatbotActivity
import com.example.ronelo.databinding.ActivityMainBinding
import com.example.ronelo.model.MainModel

class MainActivity : AppCompatActivity() {
    lateinit var nurseAdapter: NurseAdapter
    lateinit var newsAdapter: NewsAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        supportActionBar?.title = "Ronelo"

        configDataNurse()
        configRecyclerNurse()

        configDataNews()
        configRecyclerNews()

        toRoneloPatient()
        toRoneloMedicine()
        toRoneloConsultation()
    }

    private fun toRoneloConsultation() {
        binding.buttonConsult.setOnClickListener{
            val intent = Intent(this,ChatbotActivity::class.java)
            startActivity(intent)
        }
    }

    private fun toRoneloMedicine() {
        binding.buttonCamera.setOnClickListener {
            val intent= Intent(this,MedicineActivity::class.java)
            startActivity(intent)
        }
    }


    private fun toRoneloPatient() {
        binding.buttonTakeCare.setOnClickListener {
            val moveToPatient = Intent(this, PatientActivity::class.java)
            startActivity(moveToPatient)
        }
    }

    private fun configRecyclerNurse() {
        binding.rvNurse.adapter = nurseAdapter
        binding.rvNurse.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvNurse.setHasFixedSize(true)
    }

    private fun configDataNurse() {
        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainModel::class.java)
        val modelNur = viewModel.returnNurse()
        nurseAdapter = NurseAdapter()
        nurseAdapter.nurse(modelNur)
    }

    private fun configRecyclerNews() {
        binding.rvNews.adapter = newsAdapter
        binding.rvNews.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvNews.setHasFixedSize(true)
    }

    private fun configDataNews() {
        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainModel::class.java)
        val modNews = viewModel.returnNews()
        newsAdapter = NewsAdapter()
        newsAdapter.news(modNews)
    }
}