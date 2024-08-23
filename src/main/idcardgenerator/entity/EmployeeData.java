package com.example.demo.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Employees")
public class EmployeeData {
    private String empId;
    private String empName;
    private String empRole;
    private String firmName;
    private String empPhotoId;
    
    public EmployeeData(String empId, String empName, String empRole, String firmName, String empPhotoId){
        this.empId = empId;
        this.empName = empName;
        this.empRole = empRole;
        this.firmName = firmName;
        this.empPhotoId = empPhotoId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }
    
    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpRole() {
        return empRole;
    }

    public void setEmpRole(String empRole) {
        this.empRole = empRole;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public String getEmpPhotoId() {
        return empPhotoId;
    }

    public void setEmpPhotoId(String empPhotoId) {
        this.empPhotoId = empPhotoId;
    }
}
