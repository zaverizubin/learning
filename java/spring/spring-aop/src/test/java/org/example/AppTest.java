package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

import static junit.framework.Assert.assertEquals;

@SpringBootTest(classes = App.class)
@Service
public class AppTest {

    @Autowired
    private ApplicationContext context;


    @Test
    public void testLogBeforeApp() {
        EmployeeManager employeeManager = this.context.getBean(EmployeeManager.class);
        EmployeeDTO employeeDTO = employeeManager.getEmployeeById(1);
        assertEquals(employeeDTO.getId(), 1);
    }

    @Test
    public void testLogAfterApp() {
        EmployeeManager employeeManager = this.context.getBean(EmployeeManager.class);
        EmployeeDTO employeeDTO = employeeManager.getEmployeeByName("Ashok");
        assertEquals(employeeDTO.getName(), "Ashok");
    }

    @Test
    public void testLogAfterReturningApp() {
        EmployeeManager employeeManager = this.context.getBean(EmployeeManager.class);
        EmployeeDTO employeeDTO = employeeManager.updateEmployeeAge(1, 34);
        assertEquals(employeeDTO.getAge(), 34);
    }

    @Test
    public void testLogAfterReturningWithExceptionApp() {
        EmployeeManager employeeManager = this.context.getBean(EmployeeManager.class);
        try{
            List<EmployeeDTO> employeeDTOs = employeeManager.getEmployeesByAge(0);
            assertEquals(employeeDTOs.size(), 2);
        }catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
        }
    }
}
