package com.luv2code.springboot.thymeleafdemo.service;


import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import java.util.List;

public interface EmployeeService {

  public List<Employee> findAll();

  public Employee findById(int theId);

  public void save (Employee theEmployee);

  public void deleteById(int theId);

}
