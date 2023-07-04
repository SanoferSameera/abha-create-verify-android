package com.example.abha_sample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.abha_sample.databinding.ActivitySampleAbhaBinding
import org.json.JSONObject


class SampleAbhaActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySampleAbhaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val b = intent.extras
        val sessionToken = b!!.getString("sessionToken")
        Log.d("Module", "sessionToken: $sessionToken")
        binding = ActivitySampleAbhaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object {
        var SESSION_TOKEN: String = "abcdef"
        var PATIENT_JSON: JSONObject = JSONObject(
            "{\n" +
                    "  \"Subject type\": \"Patient\",\n" +
                    "  \"External ID\": null,\n" +
                    "  \"Voided\": false,\n" +
                    "  \"Registration date\": \"2023-06-22\",\n" +
                    "  \"location\": {\n" +
                    "    \"State\": \"Hosakote\",\n" +
                    "    \"State External ID\": null\n" +
                    "  },\n" +
                    "  \"First name\": \"Priya\",\n" +
                    "  \"Last name\": \"Rao\",\n" +
                    "  \"Date of birth\": \"2013-03-22\",\n" +
                    "  \"Gender\": \"Female\",\n" +
                    "  \"observations\": {\n" +
                    "  },\n" +
                    "  \"Address\": \"Hosakote\"\n" +
                    "}\n"
        )
    }


}