package org.example.dao.impl;

import org.example.config.config;
import org.example.dao.JobDao;
import org.example.models.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobDaoImpl implements JobDao {
    private static final Connection connection = config.getConnection();
    @Override
    public boolean createJobTable() {
        int execute = 0;
        String query = "create table if not exists jobs(" +
                "id serial primary key," +
                "position varchar," +
                "profession varchar," +
                "description varchar," +
                "experience varchar);";

        try (Statement statement = connection.createStatement()){
            execute = statement.executeUpdate(query);
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return execute == 0;
    }

    @Override
    public boolean addJob(Job job) {
String sql = """
        insert into jobs (position , profession , description , experience)
        values(?, ?, ?, ?);
        """;
int execute = 0;
try(PreparedStatement preparedStatement  = connection.prepareStatement(sql)){
preparedStatement.setString(1,job.getPosition());
preparedStatement.setString(2, job.getProfession());
preparedStatement.setString(3, job.getDescription());
preparedStatement.setLong(4,job.getExperience());
execute = preparedStatement.executeUpdate();
}catch (SQLException e){
    System.out.println(e.getMessage());
}
return execute != 0;
    }

    @Override
    public Job getJobById(Long jobId) {
      Job job = new Job();
      String sql = """
              select * from jobs  where id = ?
              """;
      try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
          preparedStatement.setLong(1,jobId);
          ResultSet resultSet  = preparedStatement.executeQuery();
while (resultSet.next()){
    job.setId(resultSet.getLong("id"));
    job.setPosition(resultSet.getString("position"));
    job.setProfession(resultSet.getString("profession"));
    job.setDescription(resultSet.getString("description"));
    job.setExperience(resultSet.getInt("experience"));
}
      } catch (SQLException e ){
          System.out.println(e.getMessage());
      }
        return job ;
    }

    @Override
    public List<Job> sortByExperience(String ascOrDesc) {
        String query = "SELECT * FROM jobs ORDER BY experience " + ascOrDesc;
        List<Job> jobs = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Job job = new Job();
                    job.setPosition(resultSet.getString("position"));
                    job.setProfession(resultSet.getString("profession"));
                    job.setDescription(resultSet.getString("description"));
                    job.setExperience(resultSet.getInt("experience"));
                    jobs.add(job);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jobs;
    }

    @Override
    public Job getJobByEmployeeId(Long employeeId) {
        String query  = "select s from employees where id =(select job_id from  employees where id=?) ";
        Job job = new Job();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.getResultSet();
            preparedStatement.setLong(1,employeeId);
            job.setId(resultSet.getLong("id"));
            job.setPosition(resultSet.getString("position"));
            job.setProfession(resultSet.getString("profession"));
            job.setDescription(resultSet.getString("description"));
            job.setExperience(resultSet.getInt("experience"));
            resultSet.close();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return job;
    }

    @Override
    public String deleteDescriptionColumn() {
        String sql = "ALTER TABLE jobs DROP COLUMN description";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            System.out.println("Success deleted");
            return "Success! Column 'description' deleted";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "Error occurred: " + e.getMessage();
        }
    }

}
