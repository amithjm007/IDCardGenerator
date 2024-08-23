package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.demo.helpers.EmployeeDataIdCardHelper.buildIdCard;

import java.awt.image.BufferedImage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.EmployeeData;
import com.example.demo.repository.EmployeeDataRepository;

@Service
@Transactional
public class EmployeeDataService {

    @Autowired
    private EmployeeDataRepository employeeRepository;
    private AtomicInteger counter = new AtomicInteger();

    private final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

    public List<EmployeeData> getAll(){
        return employeeRepository.findAll();
    }

    public EmployeeData getById(String empId){
        return employeeRepository.findByEmpId(empId);
    }

    public void addEmployee(Map<String, String> empDetails, MultipartFile empImage){
        String empId = getNewEmpId();
        String empPhotoId = empId + empDetails.get("empName");
        EmployeeData employee = new EmployeeData(empId, empDetails.get("empName"), empDetails.get("empRole"), empDetails.get("firmName"), empPhotoId);
        
        Boolean empPhotoSaved = saveEmpPhoto(empImage, empPhotoId);

        if(empPhotoSaved){
            employeeRepository.save(employee);
        }
    }

    public String getNewEmpId(){
        return Integer.toString(counter.incrementAndGet());
    }

    public Boolean saveEmpPhoto(MultipartFile empPhoto, String empId){
        try{
            if (!Files.exists(Paths.get(UPLOAD_DIRECTORY))){
                Files.createDirectories(Paths.get(UPLOAD_DIRECTORY));
            }
    
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, empId + ".jpg");
            Files.write(fileNameAndPath, empPhoto.getBytes());
            return true;
        } catch (IOException e){
            return false;
        }
    }

    public BufferedImage getIdCard(String empId) throws IOException {
        EmployeeData employee = getById(empId);
        return buildIdCard(employee);
    }
}
