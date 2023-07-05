package com.example.abha_creation

import com.example.abha_creation.data.model.VerifyAadhaarOTPResp
import com.google.gson.JsonObject

class PatientSubject {

        var subjectType: String = "Patient"
        var externalId: String? = null
        var voided: Boolean = false
        var registrationDate: String = ""
        var locationState: String = ""
        var locationStateExternalId: String? = null
        var firstName: String = ""
        var lastName: String = ""
        var dateOfBirth: String = ""
        var gender: String = ""
        var observations: Map<String, String> = emptyMap()
        var address: String = ""


    fun setDemographics(patient: VerifyAadhaarOTPResp){
        patientSubject.firstName = patient.fullName
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
        patientSubject.observations = patientSubject.observations.toMutableMap().apply {
            put("ABHA number", abhaNumber)
        }
    }

    fun setMobile(mobile: String){
        patientSubject.observations = patientSubject.observations.toMutableMap().apply {
            put("Phone number", mobile)
        }
    }

    fun setABHAAddress(abhaAddress: String){
        patientSubject.observations = patientSubject.observations.toMutableMap().apply {
            put("ABHA address", abhaAddress)
        }
    }


    companion object {
        var patientSubject = PatientSubject()
        var PatientSubjectJson = JsonObject()
    }
}