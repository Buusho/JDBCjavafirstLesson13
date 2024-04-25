package org.example;

import org.example.models.Employee;
import org.example.models.Job;
import org.example.service.EmployeeService;
import org.example.service.JobService;
import org.example.service.impl.EmployeeServiceImpl;
import org.example.service.impl.JobServiceImpl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {


        JobService jobService =new JobServiceImpl();
        //jobService.createJobTable();
       // System.out.println(jobService.addJob(new Job("Mentor","Java","Beckend Developer",1)));
       // System.out.println(jobService.addJob(new Job("Managment","JS","Fronted",2)));
        //System.out.println(jobService.getJobById(1L));
        //System.out.println(jobService.sortByExperience("desc"));
//        System.out.println(jobService.deleteDescriptionColumn());

        EmployeeService employeeService = new EmployeeServiceImpl ();
        //employeeService.createEmployee();
        //System.out.println(employeeService.addEmployee(new Employee(1L,"Masha","Chyngyzov",18,"adyl@gmail.com",1)));
        //System.out.println(employeeService.addEmployee(new Employee(2L,"Sasha","Bakytov",18,"adyl@gmail.com",2)));
        //System.out.println(employeeService.getEmployeeById(4L));
        //System.out.println(employeeService.getAllEmployees());
        //System.out.println(employeeService.findByEmail("adyl@gmail.com"));
        //System.out.println(employeeService.getEmployeeByPosition("Mentor"));
        System.out.println( "Hello World!" );
    }
}
