package org.ahilmi.pro1_sm_2.db;


import org.ahilmi.pro1_sm_2.dto.Professor;

import java.sql.*;
import java.util.Optional;

public class ProfessorCrudOperations {
    static final String DB_URL = "jdbc:postgresql://localhost:5432/pro1_sm";
    static final String USER = "postgres";
    static final String PASS = "ilikesql";


    // Get a professor by id
    public Optional<Professor> getProfessorById(int id) {
        Professor professor = null;

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM professors WHERE id = " + id;
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                professor = new Professor();
                professor.setId(resultSet.getInt("id"));
                professor.setName(resultSet.getString("prof_name"));
                professor.setDepartment(resultSet.getString("department"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (professor != null)
            return Optional.of(professor);
        else
            return Optional.empty();
    }


    // Insert a professor by id
    public int insertProfessorById(Professor professor) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Statement statement = connection.createStatement();
            String params = professor.getId() + ", \'" + professor.getName() + "\',\'" + professor.getDepartment() + "\'";

            // Check if there exist a record on that id
            if(getProfessorById(professor.getId()).isPresent()) {
                result = -1;
            } else {
                String query = "INSERT INTO professors (id, prof_name, department) VALUES (" + params + ");";
                result = statement.executeUpdate(query);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result; // bu result u controller içerisinde saveProfessor ile işleyeceğiz.
    }


    // Delete a professor by id
    public int deleteProfessorById(int id) {
        int result = 0;

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Statement statement = connection.createStatement();
            String query = "DELETE FROM professors WHERE id = " + id;
            result = statement.executeUpdate(query);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        return result;
    }


    // Update a professor by id
    public int updateProfessorById(Professor professor) {
        int result = 0;

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Statement statement = connection.createStatement();

            // Check if there exists a record on that id
            if(getProfessorById(professor.getId()).isPresent()) {
                String query = "UPDATE professors SET " +
                        "prof_name =  '" + professor.getName() +"', " +
                        "department = '" + professor.getDepartment() + "' " + " WHERE id = " + professor.getId() + ";";

                result = statement.executeUpdate(query);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }


}
