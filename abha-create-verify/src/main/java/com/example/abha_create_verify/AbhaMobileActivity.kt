package com.example.abha_create_verify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.abha_create_verify.data.api.ApiHelper
import com.example.abha_create_verify.data.api.RetrofitBuilder
import com.example.abha_create_verify.data.model.GenerateMobileOTPReq
import com.example.abha_create_verify.databinding.ActivityAbhaMobileBinding
import com.example.abha_create_verify.utils.Status

class AbhaMobileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAbhaMobileBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAbhaMobileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

        setSupportActionBar(binding.toolbarAbha)
        supportActionBar?.title = resources.getString(R.string.create_abha)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.proceedButton.setOnClickListener {
            val mobileNumber = binding.mobileEditText.text.toString()
            viewModel.checkAndGenerateMobileOtp(GenerateMobileOTPReq(mobileNumber)).observe(this) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            resource.data?.let { data ->
                                if (data.mobileLinked == "true") {
                                    val intent = Intent(this, PatientBioActivity::class.java)
                                    PatientSubject().setMobile(mobileNumber)
                                    startActivity(intent)
                                } else {
                                    val intent = Intent(this, AbhaOTPActivity::class.java)
                                    intent.putExtra("mobileNumber", mobileNumber)
                                    startActivity(intent)
                                }
                            }
                        }

                        Status.ERROR -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        }

                        Status.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        )[MainViewModel::class.java]
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            // Handle back button click here
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}