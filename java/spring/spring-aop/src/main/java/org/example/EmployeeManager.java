package org.example;

import org.aspectj.weaver.reflect.ArgNameFinder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeManager {

    public EmployeeDTO getEmployeeById(Integer employeeId) {
        return new EmployeeDTO(employeeId);
    }

    public EmployeeDTO getEmployeeByName(String employeeName) {
        EmployeeDTO employeeDTO =  new EmployeeDTO();
        employeeDTO.setName(employeeName);
        return employeeDTO;
    }

    public EmployeeDTO updateEmployeeAge(Integer employeeId, int age) {
        EmployeeDTO employeeDTO = getEmployeeById(employeeId);
        employeeDTO.setAge(age);
        return employeeDTO;
    }

    public List<EmployeeDTO> getEmployeesByAge(Integer age) throws IllegalArgumentException {
        if(age <= 0 ) throw new IllegalArgumentException("Age must be greater than 0");

        List<EmployeeDTO> employees = new ArrayList<>();
        employees.add(new EmployeeDTO());
        employees.add(new EmployeeDTO());
        employees.forEach(employee -> employee.setAge(age));
        return employees;
    }


}
