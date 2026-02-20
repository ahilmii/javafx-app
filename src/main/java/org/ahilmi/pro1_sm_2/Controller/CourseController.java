package org.ahilmi.pro1_sm_2.Controller;
import org.ahilmi.pro1_sm_2.dto.Course;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.ahilmi.pro1_sm_2.db.CourseCrudOperations;

import java.util.Optional;

public class CourseController {
    @FXML
    private TextField courseName;

    @FXML
    private TextField courseId;

    @FXML
    private TextField courseCredit;

    @FXML
    private Button getCourse;

    @FXML
    private Button updateCourse;

    @FXML
    private Button saveCourse;

    @FXML
    private Button deleteCourse;

    @FXML
    private Button closeCourse;

    @FXML
    private Button clearCourse;

    public void checkId(String id, ActionEvent event) {
        if (id.isEmpty() || Integer.parseInt(id) <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Id is wrong!");
            alert.showAndWait();
            clearCourse(event);
        }
    }

    @FXML
    public void closeCourse(ActionEvent event) {Platform.exit();}

    // ekranı temizle
    @FXML
    void clearCourse(ActionEvent event) {
        courseId.setText("");
        courseName.setText("");
        courseCredit.setText("");
    }


    // gui'dan aldığım veri ile course nesnesini dolduruyorum, bu nesnesyi db'ye gönderip işleyeceğim, dönen sonuca göre işlem yapacağım
    @FXML
    void saveCourse(ActionEvent event) {
        checkId(courseId.getText(), event);
        Course course = new Course();

        course.setId(Integer.parseInt(courseId.getText()));
        course.setName(courseName.getText());
        course.setCredit(Integer.parseInt(courseCredit.getText()));

        CourseCrudOperations crudOperations = new CourseCrudOperations();
        int res = crudOperations.insertCourseById(course);

        if(res > 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Course with id " + courseId.getText() + " saved");
            alert.showAndWait();
            clearCourse(event);
        } else if(res == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("There is a course with the same id: " + courseId.getText());
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error on saving course!");
            alert.showAndWait();
        }
    }


    @FXML
    void deleteCourse(ActionEvent event) {
        checkId(courseId.getText(), event);
        CourseCrudOperations crudOperations = new CourseCrudOperations();

        int id = Integer.parseInt(courseId.getText());
        int result = crudOperations.deleteCourseById(id);


        if(result > 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Course with id " + courseId.getText() + " deleted");
            alert.showAndWait();
            clearCourse(event);
        } else if(result == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("There isn't any course with the id: " + courseId.getText());
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error on deleting course!");
            alert.showAndWait();
        }

    }


    @FXML
    void getCourse(ActionEvent event) {
        checkId(courseId.getText(), event);
        CourseCrudOperations crudOperations = new CourseCrudOperations();

        int id = Integer.parseInt(courseId.getText());
        Optional<Course> course = crudOperations.getCourseById(id);

        if(course.isPresent()){
            courseId.setText(Integer.toString(course.get().getId()));
            courseName.setText(course.get().getName());
            courseCredit.setText(Integer.toString(course.get().getCredit()));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Course with id " + id + " not found");
            alert.showAndWait();
        }
    }


    @FXML
    void updateCourse(ActionEvent event) {
        checkId(courseId.getText(), event);
        Course course = new Course();

        course.setId(Integer.parseInt(courseId.getText()));
        course.setName(courseName.getText());
        course.setCredit(Integer.parseInt(courseCredit.getText()));

        CourseCrudOperations crudOperations = new CourseCrudOperations();
        int res = crudOperations.updateCourseById(course);
        if(res > 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Course with id " + courseId.getText() + " updated");
            alert.showAndWait();
            clearCourse(event);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error on updating course!");
            alert.showAndWait();
        }
    }

}