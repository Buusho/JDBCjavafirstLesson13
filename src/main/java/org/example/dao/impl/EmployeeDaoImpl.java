package org.example.dao.impl;

import org.example.config.config;
import org.example.dao.EmployeeDao;
import org.example.models.Employee;
import org.example.models.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDaoImpl implements EmployeeDao {
    private final Connection connection = config.getConnection();

    @Override
    public void createEmployee() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("""
                    create  table if not exists employees (
                    id serial  primary key ,
                    first_name varchar,
                    last_name varchar,
                    age int,
                    email varchar,
                    job_id int references jobs(id))""");
            statement.close();
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void addEmployee(Employee employee) {
        String sql = """
                insert into employees (first_name , last_name , age , email , job_id)
                values (?,?,?,?,?)
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2,employee.getLastName());
            preparedStatement.setInt(3,employee.getAge());
            preparedStatement.setString(4,employee.getEmail());
            preparedStatement.setLong(5,employee.getJob_Id());
            preparedStatement.executeUpdate();
            System.out.println("Success!");
        }catch (SQLException e ){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void dropTable() {
String sql= """
        drop table employees
        """;
try {
PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.executeUpdate();
    System.out.println("Success !");
}catch (SQLException e){
    System.out.println(e.getMessage());
}
    }

    @Override
    public void cleanTable() {
String sql = """
        truncate table employees
        """;
try(Statement statement = connection.createStatement()){
statement.executeUpdate(sql);
    System.out.println("Table successfully cleaned ! ");
}catch (SQLException e ){
    System.out.println(e.getMessage());
}

    }

    @Override
    public String updateEmployee(Long id, Employee employee) {
        String query = "update employees" +
                "set first_name = ?," +
                "last_name = ?," +
                "date_of_birth = ?," +
                "email = ?," +
                "job_id =?," +
                "where id = ?";
        int execute = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1,employee.getFirstName());
            preparedStatement.setString(2,employee.getLastName());
            preparedStatement.setString(3,employee.getEmail());
            preparedStatement.setLong(4,employee.getJob_Id());
            preparedStatement.setLong(5,id);
            execute= preparedStatement.executeUpdate();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return execute !=0 ? "Updated" : "Error";

    }

    @Override
    public List<Employee> getAllEmployees() {
List<Employee> employees = new ArrayList<>();
String sql = """
        select * from employees
        """;
try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
    ResultSet resultSet = preparedStatement.executeQuery();
    while (resultSet.next()){
Employee employee = new Employee();
employee.setId(resultSet.getLong("id"));
employee.setFirstName(resultSet.getString("first_name"));
employee.setLastName(resultSet.getString("last_name"));
employee.setAge(resultSet.getInt("age"));
employee.setEmail(resultSet.getString("email"));
employee.setJob_Id(resultSet.getInt("job_id"));
employees.add(employee);
    }
}catch (SQLException e){
    System.out.println(e.getMessage());
}
        return employees;
    }

    @Override
    public Employee findByEmail(String email) {
        Employee employees = new Employee();
        String query ="select * from  employees where email = ?;";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()){
                throw  new RuntimeException("Employee    with email "+email+"not found");
            }
            employees.setId(resultSet.getLong("id"));
            employees.setFirstName(resultSet.getString("first_name"));
            employees.setLastName(resultSet.getString("last_name"));
            employees.setEmail(resultSet.getString("email"));
            employees.setJob_Id(resultSet.getInt("job_id"));
            resultSet.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return employees;

    }

    @Override
    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        Map<Employee,Job> result = new HashMap<>();
        String query = """
                select e.* , j.* from employees e join jobs j on j.id = e.job_id where e.id = ?""";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setLong(1, employeeId );
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()){
                throw  new RuntimeException("User with id "+employeeId+"not found");
            }
            Employee employee = new Employee();
            employee.setId(resultSet.getLong("id"));
            employee.setFirstName(resultSet.getString("first_name"));
            employee.setLastName(resultSet.getString("last_name"));
            employee.setEmail(resultSet.getString("email"));
            Job job = new Job();
            job.setId(resultSet.getLong("id"));
            job.setPosition(resultSet.getString("position"));
            job.setProfession(resultSet.getString("profession"));
           // job.setDescription(resultSet.getString("description"));
            result.put(employee, job);
            resultSet.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        List<Employee> employees = new ArrayList<>();
        String query ="select * from  employees where position = ?;";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1,position);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()){
                throw  new RuntimeException("Employee    with email "+position+"not found");
            }
           Employee employee = new Employee();
            employee.setId(resultSet.getLong("id"));
            employee.setFirstName(resultSet.getString("first_name"));
            employee.setLastName(resultSet.getString("last_name"));
            employee.setEmail(resultSet.getString("email"));
            employee.setJob_Id(resultSet.getInt("job_id"));
            employees.add(employee);
            resultSet.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return employees;
    }
}
