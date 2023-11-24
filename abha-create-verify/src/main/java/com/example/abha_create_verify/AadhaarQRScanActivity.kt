package com.example.abha_create_verify

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.abha_create_verify.utils.AadhaarPlainTextQrParser
import com.example.abha_create_verify.utils.AadhaarSecureQrParser
import com.example.abha_create_verify.utils.AadhaarXmlQrParser
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import org.xml.sax.InputSource
import javax.xml.parsers.DocumentBuilderFactory


class AadhaarQRScanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        barcodeLauncher.launch(ScanOptions().setOrientationLocked(false).setPrompt("Scan Aadhaar QR Code"))
    }

    private val barcodeLauncher = registerForActivityResult<ScanOptions, ScanIntentResult>(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Scanned, Processing... ", Toast.LENGTH_SHORT)
                .show()
            result.contents?.let {
                processQRData(it)
                displayAadhaarInfo()
            }
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
        val aadhaarCardInfo = when {
            isSecureQR(scannedData) -> AadhaarSecureQrParser(scannedData).getScannedAadhaarInfo()
            isXmlBasedQR(scannedData) -> AadhaarXmlQrParser(scannedData).getAadhaarCardInfo()
            else -> AadhaarPlainTextQrParser(scannedData).getAadhaarCardInfo()
        }
        PatientSubject().setPatient(aadhaarCardInfo)
    }

}