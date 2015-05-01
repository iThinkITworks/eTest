/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etest.model;

/**
 *
 * @author jetdario
 */
public class Faculty {
    
    private int facultyId;
    private String firstname;
    private String middlename;
    private String lastname;
    private String position;
    private String facultyStatus;

    public int getFacultyId() {
        return facultyId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPosition() {
        return position;
    }

    public String getFacultyStatus(){
        return facultyStatus;
    }
    
    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    
    public void setFacultyStatus(String facultyStatus){
        this.facultyStatus = facultyStatus;
    }
    
}
