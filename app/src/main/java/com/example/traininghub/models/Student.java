package com.example.traininghub.models;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Student {

    private int id;
    private String nameAr;
    private String nameEn;
    private String email;
    private String phoneNumber;
    private String phoneNumberSec;
    private String state;
    private String city;
    private String address;
    private String faculty;
    private String degree;
    private String idNumber;
    private String passportNumber;
    private String skillCardNumber;
    private String image;
    private String idImage;

    public Student() {
    }

    public Student(String nameAr, String nameEn, String email,
                   String phoneNumber, String phoneNumberSec,
                   String state, String city, String address,
                   String faculty, String degree, String passportNumber,
                   String skillCardNumber, String idNumber) {
        this.nameAr = nameAr;
        this.nameEn = nameEn;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.phoneNumberSec = phoneNumberSec;
        this.state = state;
        this.city = city;
        this.address = address;
        this.faculty = faculty;
        this.degree = degree;
        this.passportNumber = passportNumber;
        this.skillCardNumber = skillCardNumber;
        this.idNumber=idNumber;
    }

    public Student(String email,String profileImage) {
        this.email = email;
        this.image=profileImage;
    }

    public int getId() {
        return id;
    }

    public String getNameAr() {
        return nameAr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPhoneNumberSec() {
        return phoneNumberSec;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getDegree() {
        return degree;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public String getSkillCardNumber() {
        return skillCardNumber;
    }

    public String getImage() {
        return image;
    }

    public String getIdImage() {
        return idImage;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPhoneNumberSec(String phoneNumberSec) {
        this.phoneNumberSec = phoneNumberSec;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public void setSkillCardNumber(String skillCardNumber) {
        this.skillCardNumber = skillCardNumber;
    }


}
