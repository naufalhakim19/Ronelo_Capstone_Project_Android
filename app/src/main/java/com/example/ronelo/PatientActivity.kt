package com.example.ronelo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import com.example.ronelo.adapter.NurseAdapter
import com.example.ronelo.adapter.PatientAdapter
import com.example.ronelo.databinding.ActivityPatientBinding
import com.example.ronelo.model.MainModel

class PatientActivity : AppCompatActivity() {
    companion object {
        const val PATIENT_ID = "patient_id"
    }

    lateinit var patientAdapter: PatientAdapter
    private lateinit var binding: ActivityPatientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Patient"

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        configDataPatient()
        toSchedule()
    }

    private fun configDataPatient() {
        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainModel::class.java)
        val modelPat = viewModel.returnPatient()
        patientAdapter = PatientAdapter()
        patientAdapter.patient(modelPat)
    }

    private fun toSchedule() {
        binding.btnShowSchedule.setOnClickListener {
            val moveToschedule = Intent(this, ScheduleActivity::class.java)
            startActivity(moveToschedule)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}