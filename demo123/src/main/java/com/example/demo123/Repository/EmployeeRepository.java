package com.example.demo123.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo123.Model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
