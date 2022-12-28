package guru.learningjournal.examples.kafka.ktableaggregate.services;

import guru.learningjournal.examples.kafka.model.DepartmentAggregate;
import guru.learningjournal.examples.kafka.model.Employee;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class RecordBuilder {
    public DepartmentAggregate init(){
        DepartmentAggregate departmentAggregate = new DepartmentAggregate();
        departmentAggregate.setEmployeeCount(0);
        departmentAggregate.setTotalSalary(0);
        departmentAggregate.setAvgSalary(0D);
        return departmentAggregate;
    }

    // In fact, I should have named the method as an adder()
    // When the employer is joining a department, we will call the adder method
    public DepartmentAggregate aggregate(Employee emp, DepartmentAggregate aggValue){
        DepartmentAggregate departmentAggregate = new DepartmentAggregate();
        departmentAggregate.setEmployeeCount(aggValue.getEmployeeCount() + 1);
        departmentAggregate.setTotalSalary(aggValue.getTotalSalary() + emp.getSalary());
        departmentAggregate.setAvgSalary((aggValue.getTotalSalary() + emp.getSalary()) / (aggValue.getEmployeeCount() + 1D));
        return departmentAggregate;
    }

    // In fact, I should have named the method as a subtractor()
    // When an employee is leaving a department, we will call the subtractor method.
    public DepartmentAggregate subtract(Employee emp, DepartmentAggregate aggValue){
        DepartmentAggregate departmentAggregate = new DepartmentAggregate();
        departmentAggregate.setEmployeeCount(aggValue.getEmployeeCount() - 1);
        departmentAggregate.setTotalSalary(aggValue.getTotalSalary() - emp.getSalary());
        departmentAggregate.setAvgSalary((aggValue.getTotalSalary() - emp.getSalary()) / (aggValue.getEmployeeCount() - 1D));
        return departmentAggregate;
    }
}
