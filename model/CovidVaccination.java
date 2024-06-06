package com.lab.api.covidvaccination.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CovidVaccination {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int verificationId;
    private String beneficiaryName;
    private int age;
    private String gender;
    private String vaccineName;

    public CovidVaccination() {
    }

    public CovidVaccination(int verificationId, String beneficiaryName, int age, String gender, String vaccineName) {
        this.verificationId = verificationId;
        this.beneficiaryName = beneficiaryName;
        this.age = age;
        this.gender = gender;
        this.vaccineName = vaccineName;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getVerificationId() {
        return verificationId;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public void setVerificationId(int verificationId) {
        this.verificationId = verificationId;
    }


}

