package com.example.abha_create_verify

import android.annotation.SuppressLint
import com.example.abha_create_verify.utils.Patient
import com.example.abha_create_verify.data.model.VerifyAadhaarOTPResp
import com.example.abha_create_verify.data.model.VerifyAbhaPatient
import com.example.abha_create_verify.utils.AadhaarCardInfo
import com.example.abha_create_verify.utils.PatientDemographics
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PatientSubject {

    fun setAadhaarProfile(patient: VerifyAadhaarOTPResp) {
        patientSubject.name = patient.fullName
        separateFullName(patient.fullName)
        patientSubject.dateOfBirth = formatDateOfBirth(patient.birthdate)
        patientSubject.gender = convertGender(patient.gender)
        patientSubject.villageTownCity = patient.villageTownCity
        patientSubject.address = getAddress(patient)
        patientSubject.phoneNumber = patient.phone
        patientSubject.abhaNumber = patient.abhaNumber
        patientSubject.abhaAddress = patient.abhaAddress
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatDateOfBirth(dateOfBirth: String): String {
        val inputFormat = SimpleDateFormat("dd-MM-yyyy")
        val outputFormat = SimpleDateFormat("yyyy-MM-dd")
        if (dateOfBirth.length == 4) {
            return dateOfBirth
        }
        val date: Date = inputFormat.parse(dateOfBirth) as Date
        return outputFormat.format(date)
    }

    private fun getAddress(patient: VerifyAadhaarOTPResp): String {
        val addressString = StringBuilder()
        listOfNotNull(
            patient.house,
            patient.street,
            patient.villageTownCity,
            patient.district,
            patient.state,
            patient.pincode
        ).forEach { str ->
            if (addressString.isNotEmpty()) {
                addressString.append(", ")
            }
            addressString.append(str)
        }
        return addressString.toString()
    }

    fun setABHANumber(abhaNumber: String) {
        patientSubject.abhaNumber = abhaNumber
    }

    fun setMobile(mobile: String) {
        patientSubject.phoneNumber = mobile
    }

    fun setABHAAddress(abhaAddress: String) {
        patientSubject.abhaAddress = abhaAddress
    }

    private fun separateFullName(fullName: String) {
        val parts = fullName.split(" ")
        if (parts.size == 1) {
            patientSubject.firstName = parts[0]
            patientSubject.lastName = ""
        } else {
            patientSubject.firstName = parts.subList(0, parts.size - 1).joinToString(" ")
            patientSubject.lastName = parts.last()
        }
    }

    fun setPatient(patient: VerifyAbhaPatient) {
        patientSubject.abhaNumber = patient.abhaNumber
        patientSubject.abhaAddress = patient.abhaAddress
        patientSubject.name = patient.name
        patientSubject.firstName = patient.firstName + patient.middleName.let { " $it" }
        patientSubject.lastName = patient.lastName
        patientSubject.dateOfBirth =
            convertToDateFormat(patient.dayOfBirth, patient.monthOfBirth, patient.yearOfBirth)
        patientSubject.gender = convertGender(patient.gender)
        patientSubject.villageTownCity =
            patient.villageName ?: patient.townName ?: patient.subDistrictName
                    ?: patient.districtName
        patientSubject.address = patient.address
        patientSubject.phoneNumber = patient.mobile
    }

    fun setPatientDemographics() {
        demographics = PatientDemographics(
            patientSubject.abhaAddress!!,
            patientSubject.name!!,
            convertGender(patientSubject.gender!!),
            patientSubject.dateOfBirth!!,
            patientSubject.phoneNumber!!
        )
    }

    fun setPatient(aadhaarNumber: String, name: String, dateOfBirth: String, gender: String) {
        patientSubject.aadhaarNumber = aadhaarNumber
        separateFullName(name)
        patientSubject.dateOfBirth = formatDateOfBirth(dateOfBirth)
        patientSubject.gender = gender
    }

    fun setPatient(patient: AadhaarCardInfo?) {
        patient?.let {
            it.name?.let { name ->
                patientSubject.name = name
                separateFullName(name)
            }
            it.dateOfBirth?.let { dateOfBirth ->
                patientSubject.dateOfBirth = formatDateOfBirth(dateOfBirth)
            }
            it.gender?.let { gender ->
                patientSubject.gender =  if (gender.length == 1) convertGender(gender) else gender
            }

            val villageTownCity = it.villageTownCity ?: it.subDistrict ?: it.district
            patientSubject.villageTownCity =
                villageTownCity ?: "Default City" // Replace "Default City" with your default value

            val addressString = StringBuilder()
            listOfNotNull(
                it.villageTownCity,
                it.subDistrict,
                it.district,
                it.state,
                it.pinCode
            ).forEach { str ->
                if (str != null) {
                    if (addressString.isNotEmpty()) {
                        addressString.append(", ")
                    }
                    addressString.append(str)
                }
            }
            patientSubject.address = addressString.toString()
        }
    }

    fun setAadhaarNumber(aadhaarNumber: String) {
        patientSubject.aadhaarNumber = aadhaarNumber
    }

    private fun convertToDateFormat(day: String?, month: String?, year: String?): String {
        val d = day?.toIntOrNull() ?: 1
        val m = month?.toIntOrNull() ?: 1
        val y = year?.toIntOrNull()

        return String.format("%04d-%02d-%02d", y, m, d)
    }

    private fun convertGender(gender: String): String {
        return when (gender.uppercase(Locale.getDefault())) {
            "F" -> "Female"
            "M" -> "Male"
            "FEMALE" -> "F"
            "MALE" -> "M"
            "OTHER" -> "O"
            else -> "Other" // Return the original value if not "F" or "M"
        }
    }

    companion object {
        var patientSubject = Patient()
        var demographics = PatientDemographics()
    }
}