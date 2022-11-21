package com.example.ronelo.alarm

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import androidx.appcompat.app.ActionBar
import com.example.ronelo.databinding.ActivityAlarmBinding

class AlarmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlarmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Reminder"
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        binding.setAlarm.setOnClickListener {
            val intent = Intent(AlarmClock.ACTION_SET_ALARM)
//
//            intent.putExtra(AlarmClock.EXTRA_MESSAGE, "My New Alarm")
//            intent.putExtra(AlarmClock.EXTRA_HOUR, 10)
//            intent.putExtra(AlarmClock.EXTRA_MINUTES, 32)
//            intent.putExtra(AlarmClock.EXTRA_DAYS, arrayListOf(2, 3, 4))

            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}