package com.example.abha_create_verify

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.abha_create_verify.databinding.ActivityDemographicsManualOrQrscanBinding
import com.example.abha_create_verify.utils.AadhaarPlainTextQrParser
import com.example.abha_create_verify.utils.AadhaarSecureQrParser
import com.example.abha_create_verify.utils.AadhaarXmlQrParser
import com.example.abha_create_verify.utils.DialogUtils
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import org.xml.sax.InputSource
import javax.xml.parsers.DocumentBuilderFactory


class DemographicsManualOrQRScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDemographicsManualOrQrscanBinding
    private val successMessage = MutableLiveData<String>()
    private val failureMessage = MutableLiveData<String>()
    private var aadhaarNumber: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemographicsManualOrQrscanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarLayout.includeToolbar.toolbarAbha)
        supportActionBar?.title = resources.getString(R.string.create_abha)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        aadhaarNumber = intent.getStringExtra("aadhaarNumber")

        binding.scanQRButton.setOnClickListener {
            launchScanner()
        }

        binding.enterManuallyButton.setOnClickListener {
            moveToManualDemographic()
        }
        binding.appBarLayout.includeToolbar.customCloseButton.setOnClickListener {
            DialogUtils.showConfirmationDialog(this) {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        successMessage.observe(this) { message ->
            showMessage(message)
        }

        failureMessage.observe(this) { message ->
            showMessage(message)
        }
    }

    private fun launchScanner() {
        PatientSubject().setAadhaarNumber(aadhaarNumber.toString())
        val intent = Intent(this, AadhaarQRScanActivity::class.java)
        intent.putExtra("isCreateAbhaScan", true)
        startActivity(intent)
        finish()
    }

    private fun moveToManualDemographic() {
        val intent = Intent(this, DemographicActivity::class.java)
        intent.putExtra("aadhaarNumber", aadhaarNumber)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        val intent = Intent(this, AuthModeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}