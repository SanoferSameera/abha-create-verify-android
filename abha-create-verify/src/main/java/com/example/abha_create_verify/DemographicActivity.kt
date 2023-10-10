package com.example.abha_create_verify

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Switch
import com.example.abha_create_verify.databinding.ActivityDemographicBinding
import com.example.abha_create_verify.utils.DialogUtils
import com.facebook.react.ReactActivity
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DemographicActivity : ReactActivity() {

    private lateinit var binding: ActivityDemographicBinding
    private lateinit var dobCalendarInput: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemographicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarLayout.includeToolbar.toolbarAbha)
        supportActionBar?.title = resources.getString(R.string.create_abha)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dobCalendarInput = findViewById(R.id.dobCalendarInput)

        var isToogleChecked = false

        var yearOfBirthSwitch = findViewById<Switch>(R.id.yearOfBirthSwitch)
        var dobEditTextLayout = findViewById<View>(R.id.dobTextInputLayout)
        var dobCalendarInputLayout = findViewById<View>(R.id.dobCalendarInputLayout)

        yearOfBirthSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            isToogleChecked = isChecked// Show/hide the appropriate TextInputEditText based on the switch state
            if (isChecked) {
                dobCalendarInputLayout.setVisibility(View.GONE)
                dobEditTextLayout.visibility = View.VISIBLE
            } else {
                dobCalendarInputLayout.setVisibility(View.VISIBLE)
                dobEditTextLayout.visibility = View.GONE
            }
        }

        dobCalendarInput.setOnClickListener {
            showDatePickerDialog()
        }

        val genderOptions = resources.getStringArray(R.array.gender_menu_items)
        val genderSpinner = findViewById<Spinner>(R.id.genderSpinner)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = adapter

        binding.appBarLayout.includeToolbar.customCloseButton.setOnClickListener {
            DialogUtils.showConfirmationDialog(this) {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        binding.proceedButton.setOnClickListener {
            binding.errorMsg.text = ""
            var dob = ""
            if(isToogleChecked) {
                dob = binding.dobEditText.text.toString()
            } else {
                dob = binding.dobCalendarInput.text.toString()
            }

            if(binding.NameEditText.text.toString().isNotEmpty() && dob != "" && binding.genderSpinner.selectedItem.toString() != genderOptions[0]) {
                PatientSubject().setPatient(
                    intent.getStringExtra("aadhaarNumber").toString(),
                    binding.NameEditText.text.toString(),
                    dob,
                    binding.genderSpinner.selectedItem.toString()
                )

                exitApplication()
            }
            else {
                binding.errorMsg.text = "Please fill all the fields"
            }
        }
    }


    @SuppressLint("VisibleForTests")
    private fun exitApplication() {
        val map: WritableMap = Arguments.createMap()
        val gson = Gson()
        val jsonString = gson.toJson(PatientSubject.patientSubject)
        map.putString("patientInfo", jsonString)

        try {
            reactInstanceManager.currentReactContext
                ?.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                ?.emit("abha_response", map)
        } catch (e: Exception) {
            Log.e("ReactNative", "Caught Exception: ${e.message}")
        }

        onBackPressedDispatcher.onBackPressed()
    }


    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)

                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                dobCalendarInput.setText(dateFormat.format(selectedDate.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

         val maxDate = Calendar.getInstance()
         datePickerDialog.datePicker.maxDate = maxDate.timeInMillis

        datePickerDialog.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val intent = Intent(this, AuthModeActivity::class.java)
        startActivity(intent)
        finish()
    }
}