package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.EmployeeData;
import com.example.demo.service.EmployeeDataService;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class EmployeeDataController {
    @Autowired
    EmployeeDataService employeeService;


    @GetMapping("/employees")
    public List<EmployeeData> getAllEmployees(){
        return employeeService.getAll();
    }
    
    @GetMapping("/employees/{empId}")
    public EmployeeData getEmployee(@PathVariable("empId") String empId) {
        return employeeService.getById(empId);
    }

    @GetMapping(value = "/employees/idCard/{empId}", produces = MediaType.IMAGE_PNG_VALUE)
    public BufferedImage getIdCard(@PathVariable("empId") String empId) throws IOException {
        return employeeService.getIdCard(empId);
    }
    
    @PostMapping("/employees")
    public void addEmployee(@RequestParam Map<String, String> params, @RequestParam("empImage") MultipartFile empImage) {
        employeeService.addEmployee(params, empImage);
    }
}