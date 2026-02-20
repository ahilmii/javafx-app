package org.ahilmi.pro1_sm_2.db;

import org.ahilmi.pro1_sm_2.dto.Course;
import org.ahilmi.pro1_sm_2.dto.Professor;

import java.sql.*;
import java.util.Optional;

public class ProfessorCrudOperations {
    static final String DB_URL = "jdbc:postgresql://localhost:5432/pro1_sm";
    static final String USER = "postgres";
    static final String PASS = "ilikesql";


    // Get a course by id
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






}
