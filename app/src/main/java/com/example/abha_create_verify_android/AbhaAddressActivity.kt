package com.example.abha_create_verify_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.abha_create_verify_android.data.api.ApiHelper
import com.example.abha_create_verify_android.data.api.RetrofitBuilder
import com.example.abha_create_verify_android.databinding.ActivityAbhaAddressBinding
import com.example.abha_create_verify_android.utils.Status

class AbhaAddressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAbhaAddressBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abha_address)
        binding = ActivityAbhaAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

        setSupportActionBar(binding.toolbarAbha)
        supportActionBar?.title = resources.getString(R.string.create_abha)

        val abhaNumberVal = intent.getStringExtra("healthIdNumber")
        binding.abhaNumberCreated.text = String.format(binding.abhaNumberCreated.text.toString(),abhaNumberVal)


        binding.createCustom.setOnClickListener {
            val intent = Intent(this, CustomAbhaAddressActivity::class.java)
            startActivity(intent)
        }

        binding.createDefault.setOnClickListener {
            viewModel.createDefaultAbhaAddress().observe(this) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            resource.data?.let { data ->
                                PatientSubject().setABHAAddress(data.abhaAddress)
                                val intent = Intent(this, AbhaAddressSuccessActivity::class.java)
                                startActivity(intent)
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

}