package org.ahilmi.pro1_sm_2.db;


import org.ahilmi.pro1_sm_2.dto.Teach;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class TeachCrudOperations {

    static final String DB_URL = "jdbc:postgresql://localhost:5432/pro1_sm";
    static final String USER = "postgres";
    static final String PASS = "ilikesql";

    // Get teaching details by id
    public Optional<Teach> getTeachById(int professor_id, int course_id, LocalDate start_date) {
        Teach teach = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM teaches WHERE professor_id = " + professor_id + " AND course_id = " + course_id + " AND start_date = '" + start_date + "';"; // SQL doğru mu
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                teach = new Teach();

                teach.setProfessorId(resultSet.getInt("professor_id"));
                teach.setCourseId(resultSet.getInt("course_id"));
                teach.setStudentCount(resultSet.getInt("student_count"));
                teach.setStartDate(LocalDate.parse(resultSet.getString("start_date")));
                String endDateStr = resultSet.getString("ending_date");
                if (endDateStr != null) { // to avoid nullreference error
                    teach.setEndingDate(LocalDate.parse(endDateStr));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (teach != null)
            return Optional.of(teach);
        else
            return Optional.empty();
    }


    // Insert a teaching by id
    public int insertTeachById(Teach teach) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Statement statement = connection.createStatement();

            String startDateFormatted = "'" + teach.getStartDate() + "'";
            String endDateFormatted = (teach.getEndingDate() == null) ? "NULL" : "'" + teach.getEndingDate() + "'";


            // Params dizisini düzeltiyoruz
            String params = teach.getProfessorId() + ", " +
                            teach.getCourseId() + ", " +
                            teach.getStudentCount() + ", " +
                            startDateFormatted + ", " +
                            endDateFormatted;

            // Check if there exists a record on that id
            if(getTeachById(teach.getProfessorId(), teach.getCourseId(), teach.getStartDate()).isPresent()) {
                result = -1;
            } else {
                String query = "INSERT INTO teaches (professor_id, course_id, student_count, start_date, ending_date) VALUES (" + params + ");";
                result = statement.executeUpdate(query);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result; // bu result u controller içerisinde saveCourse ile işleyeceğiz.
    }


    // Delete a teaching by id
    public int deleteTeachById(int professor_id, int course_id, LocalDate start_date) {
        int result = 0;

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Statement statement = connection.createStatement();
            String query = "DELETE FROM teaches WHERE professor_id = " + professor_id + " AND course_id = " + course_id + " AND start_date = '" + start_date + "';";
            result = statement.executeUpdate(query);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        return result;
    }

    // Update a teaching by id
    public int updateTeachById(Teach teach) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Statement statement = connection.createStatement();

            // Check if there exist a record on that id
            if(getTeachById(teach.getProfessorId(), teach.getCourseId(), teach.getStartDate()).isPresent()) {

                String endDateFormatted = (teach.getEndingDate() == null) ? "NULL" : "'" + teach.getEndingDate() + "'";

                // update teaching if professor_id and course_id matches with the record in db
                String query = "UPDATE teaches SET " +
                        "student_count = " + teach.getStudentCount() + ", " +
                        "ending_date = " + endDateFormatted + " " +
                        "WHERE professor_id = " + teach.getProfessorId() +
                        " AND course_id = " + teach.getCourseId() +
                        " AND start_date = '" + teach.getStartDate() + "';";
                result = statement.executeUpdate(query);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }











}
