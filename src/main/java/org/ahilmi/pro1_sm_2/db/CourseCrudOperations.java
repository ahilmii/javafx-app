package org.ahilmi.pro1_sm_2.db;

import org.ahilmi.pro1_sm_2.dto.Course;

import java.sql.*;
import java.util.Optional;

public class CourseCrudOperations {
    static final String DB_URL = "jdbc:postgresql://localhost:5432/pro1_sm";
    static final String USER = "postgres";
    static final String PASS = "ilikesql";

    // Get a course by id
    public Optional<Course> getCourseById(int id) {
        Course course = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM courses WHERE id = " + id;
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                course = new Course();
                course.setId(resultSet.getInt("id"));
                course.setName(resultSet.getString("course_name"));
                course.setCredit(resultSet.getInt("credit"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (course != null)
            return Optional.of(course);
        else
            return Optional.empty();
    }



    // Insert a course by id
    public int insertCourseById(Course course) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Statement statement = connection.createStatement();
            String params = course.getId() + ", \'" + course.getName() + "\',\'" + course.getCredit() + "\'";

            // Check if there exist a record on that id
            if(getCourseById(course.getId()).isPresent()) {
                result = -1;
            } else {
                String query = "INSERT INTO courses (id, course_name, credit) VALUES (" + params + ");"; // TABLO İSMİNİ  VE SÜTUNLARI KONTROL ETMEYİ UNUNTMA
                result = statement.executeUpdate(query);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result; // bu result u controller içerisinde saveCourse ile işleyeceğiz.
    }


    // Delete a car by id
    public int deleteCourseById(int id) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Statement statement = connection.createStatement();
            String query = "DELETE FROM courses WHERE id = " + id; // TABLO İSMİ
            result = statement.executeUpdate(query);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        return result;
    }


    // Update a car by id
    public int updateCourseById(Course course) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Statement statement = connection.createStatement();

            // Check if there exist a record on that id
            if(getCourseById(course.getId()).isPresent()) {
                String query = "UPDATE courses SET " +
                        "course_name =  '" + course.getName() +"', " +
                        "credit = '" + course.getCredit() + "' " + " WHERE id = " + course.getId() + ";";

                result = statement.executeUpdate(query);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}