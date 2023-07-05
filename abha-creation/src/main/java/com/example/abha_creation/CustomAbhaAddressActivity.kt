package com.example.abha_creation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.abha_creation.data.api.ApiHelper
import com.example.abha_creation.data.api.RetrofitBuilder
import com.example.abha_creation.data.model.CreateAbhaAddressReq
import com.example.abha_creation.databinding.ActivityCustomAbhaAddressBinding
import com.example.abha_creation.utils.Status

class CustomAbhaAddressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomAbhaAddressBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_abha_address)
        binding = ActivityCustomAbhaAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

        setSupportActionBar(binding.toolbarAbha)
        supportActionBar?.title = resources.getString(R.string.create_abha)

        binding.proceedButton.setOnClickListener {
            viewModel.createAbhaAddress(CreateAbhaAddressReq(binding.editTextAbhaAddress.text.toString(),binding.checkbox.isChecked.toString())).observe(this
            ) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            PatientSubject().setABHAAddress(binding.editTextAbhaAddress.text.toString() + getString(R.string.abha_suffix))
                            val intent = Intent(this, AbhaAddressSuccessActivity::class.java)
                            startActivity(intent)
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