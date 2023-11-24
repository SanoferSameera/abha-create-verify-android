package com.example.abha_create_verify

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.abha_create_verify.PatientSubject.Companion.patientSubject
import com.example.abha_create_verify.utils.AadhaarPlainTextQrParser
import com.example.abha_create_verify.utils.AadhaarSecureQrParser
import com.example.abha_create_verify.utils.AadhaarXmlQrParser
import com.example.abha_create_verify.utils.Patient
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import org.xml.sax.InputSource
import javax.xml.parsers.DocumentBuilderFactory


class AadhaarQRScanActivity : AppCompatActivity() {

    var isCreateAbhaScan = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        barcodeLauncher.launch(ScanOptions().setOrientationLocked(false).setPrompt("Scan Aadhaar QR Code"))

        isCreateAbhaScan = intent.getBooleanExtra("isCreateAbhaScan", false)
    }

    private val barcodeLauncher = registerForActivityResult<ScanOptions, ScanIntentResult>(
        ScanContract()
    ) { result: ScanIntentResult ->
        try {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
                handleBack()
            } else {
                Toast.makeText(this, "Scanned, Processing... ", Toast.LENGTH_SHORT)
                    .show()
                result.contents?.let {
                    Toast.makeText(this, "Processing... $it", Toast.LENGTH_SHORT)
                        .show()
                    processQRData(it)
                    displayAadhaarInfo()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error processing QR code", Toast.LENGTH_SHORT).show()
        }
    }


    private fun handleBack() {
        if(isCreateAbhaScan) {
            val intent = Intent(this, DemographicsManualOrQRScanActivity::class.java)
            startActivity(intent)
            finish()
        }
        else {
            finish()
        }
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

    private fun displayAadhaarInfo() {
        val intent = Intent(this, ScannedAadhaarInfoActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun processQRData(scannedData: String) {
        Toast.makeText(this, "isCreateAbhaScan $isCreateAbhaScan", Toast.LENGTH_SHORT).show()
//        if(!isCreateAbhaScan) patientSubject = Patient()
        try {
            val aadhaarCardInfo = when {
                isSecureQR(scannedData) -> AadhaarSecureQrParser(scannedData).getScannedAadhaarInfo()
                isXmlBasedQR(scannedData) -> AadhaarXmlQrParser(scannedData).getAadhaarCardInfo()
                else -> AadhaarPlainTextQrParser(scannedData).getAadhaarCardInfo()
            }
            PatientSubject().setPatient(aadhaarCardInfo)

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error Processing QR", Toast.LENGTH_SHORT).show()
        }
    }

}