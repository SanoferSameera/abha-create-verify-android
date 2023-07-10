package com.example.abha_create_verify

import com.example.abha_create_verify.data.model.VerifyAadhaarOTPResp
import com.google.gson.Gson
import com.google.gson.JsonObject

class PatientSubject {

        var name: String = ""
        var firstName: String = ""
        var middleName: String = ""
        var lastName: String = ""
        var dateOfBirth: String = ""
        var gender: String = ""
        var abhaNumber: String = ""
        var abhaAddress: String = ""
        var address: String = ""
        var phoneNumber: String = ""

    fun setDemographics(patient: VerifyAadhaarOTPResp){
        patientSubject.name = patient.fullName
        separateFullName(patient.fullName)
        patientSubject.dateOfBirth = patient.birthdate
        patientSubject.gender = patient.gender
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
        patientSubject.address = addressString.toString()
    }

    fun setABHANumber(abhaNumber: String){
        patientSubject.abhaNumber = abhaNumber
    }

    fun setMobile(mobile: String){
        patientSubject.phoneNumber = mobile
    }

    fun setABHAAddress(abhaAddress: String){
        patientSubject.abhaAddress = abhaAddress
    }
    private fun separateFullName(fullName: String) {
        val parts = fullName.split(" ")
        patientSubject.firstName = parts[0]
        patientSubject.middleName = if (parts.size > 2) parts.subList(1, parts.size - 1).joinToString(" ") else ""
        patientSubject.lastName = parts.last()
    }

    fun covertToJson(){
        val gson = Gson()
        patientSubjectJson = gson.toJsonTree(patientSubject).asJsonObject
    }


    companion object {
        var patientSubject = PatientSubject()
        var patientSubjectJson = JsonObject()
    }
}