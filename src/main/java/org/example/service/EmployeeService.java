package org.example.service;

import org.example.models.Employee;
import org.example.models.Job;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    void createEmployee();
    boolean addEmployee(Employee employee);
    void dropTable();
    void cleanTable();
    String updateEmployee(Long id,Employee employee);
    List<Employee> getAllEmployees();
    Employee findByEmail(String email);
    Map<Employee, Job> getEmployeeById(Long employeeId);
    List<Employee> getEmployeeByPosition(String position);
}
