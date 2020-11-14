package com.luv2code.springboot.thymeleafdemo.dao;


import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

  // that's it ... no need to write any code LOL!

  // add a method to sort by last name
  public List<Employee> findAllByOrderByLastNameAsc();

}
