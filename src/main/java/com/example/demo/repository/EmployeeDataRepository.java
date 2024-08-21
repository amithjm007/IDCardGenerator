package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.EmployeeData;;

@Repository
public interface EmployeeDataRepository extends MongoRepository<EmployeeData, String>{

    @Query("{'empId': ?0}")
    EmployeeData findByEmpId(String empId);
}
