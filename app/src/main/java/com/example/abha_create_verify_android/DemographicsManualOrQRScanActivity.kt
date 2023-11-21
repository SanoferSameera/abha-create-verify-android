package com.example.abha_create_verify_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.abha_create_verify_android.databinding.ActivityDemographicsManualOrQrscanBinding
import com.example.abha_create_verify_android.utils.AadhaarPlainTextQrParser
import com.example.abha_create_verify_android.utils.AadhaarSecureQrParser
import com.example.abha_create_verify_android.utils.AadhaarXmlQrParser
import com.example.abha_create_verify_android.utils.DialogUtils
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
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

    private fun launchScanner(): String? {
        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
                Barcode.FORMAT_AZTEC
            )
            .build()
        var data: String? = null
        val scanner = GmsBarcodeScanning.getClient(this, options)

        val moduleInstallRequest =
            ModuleInstallRequest.newBuilder()
                .addApi(scanner)
                .build()
        val moduleInstallClient = ModuleInstall.getClient(this)

        moduleInstallClient
            .installModules(moduleInstallRequest)
            .addOnSuccessListener {
                Log.d("QR", "Modules successfully installed")
            }
            .addOnFailureListener {
                Log.e("MainActivity", "Error installing modules", it)
            }

        scanner.startScan()
            .addOnSuccessListener { barcode ->
                data = barcode.rawValue
                Log.d("QR", "Successful: $data")
                successMessage.postValue("Scanning done, processing your data")
                data?.let {
                    processQRData(it)
                    displayAadhaarInfo()
                }
            }
            .addOnFailureListener { e ->
                val errorMessage = "Some unexpected error occurred: $e"
                Log.e("QR", "failed:  $e")
                failureMessage.postValue(errorMessage)
            }
        return data
    }

    private fun displayAadhaarInfo() {
        val intent = Intent(this, ScannedAadhaarInfoActivity::class.java)
        intent.putExtra("aadhaarNumber", aadhaarNumber)
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

    private fun processQRData(scannedData: String) {
        val aadhaarCardInfo = when {
            isSecureQR(scannedData) -> AadhaarSecureQrParser(scannedData).getScannedAadhaarInfo()
            isXmlBasedQR(scannedData) -> AadhaarXmlQrParser(scannedData).getAadhaarCardInfo()
            else -> AadhaarPlainTextQrParser(scannedData).getAadhaarCardInfo()
        }
        PatientSubject().setPatient(aadhaarCardInfo)
    }


    private fun isSecureQR(sample: String): Boolean {
        return  sample.toBigIntegerOrNull() != null
    }

    private fun isXmlBasedQR(testString: String?): Boolean {
        return try {
            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val inputSource = InputSource(testString?.reader())
            builder.parse(inputSource)
            true
        } catch (e: Exception) {
            false
        }
    }

}