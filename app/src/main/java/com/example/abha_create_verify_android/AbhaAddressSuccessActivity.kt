package com.example.abha_create_verify_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.abha_create_verify_android.databinding.ActivityAbhaAddressSuceessBinding

class AbhaAddressSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAbhaAddressSuceessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abha_address_suceess)
        binding = ActivityAbhaAddressSuceessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarAbha)
        supportActionBar?.title = resources.getString(R.string.create_abha)

        binding.abhaNumber.text =  PatientSubject.patientSubject.observations["ABHA number"]
        binding.abhaAddress.text =  PatientSubject.patientSubject.observations["ABHA address"]

        binding.finishButton.setOnClickListener {
            finish()
        }

    }

}