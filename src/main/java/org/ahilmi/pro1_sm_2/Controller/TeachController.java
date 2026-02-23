package org.ahilmi.pro1_sm_2.Controller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import org.ahilmi.pro1_sm_2.db.TeachCrudOperations;
import org.ahilmi.pro1_sm_2.dto.Teach;

import java.time.LocalDate;
import java.util.Optional;

public class TeachController {
    @FXML
    private TextField courseId;
    @FXML
    private TextField professorId;
    @FXML
    private TextField startingDate;
    @FXML
    private TextField endingDate;
    @FXML
    private TextField studentCount;

    @FXML
    private Button getTeach;
    @FXML
    private Button closeTeach;
    @FXML
    private Button clearTeach;
    @FXML
    private Button saveTeach;
    @FXML
    private Button deleteTeach;
    @FXML
    private Button updateTeach;


    public boolean isValid(String profId, String courseId, String startDateStr, ActionEvent event) {

        if (profId.isEmpty() || courseId.isEmpty() || startDateStr.isEmpty()) { // if any of these areas is empty then it will generate an error return false
            Alert alert = new Alert(Alert.AlertType.ERROR); // this is how, we force user to fill in all required area to make an action
            alert.setTitle("Error");
            alert.setHeaderText("Professor ID, Course ID and Starting Date cannot be empty!");
            alert.showAndWait();
            return false;
        }

        try { // number checking
            if (Integer.parseInt(profId) <= 0 || Integer.parseInt(courseId) <= 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("IDs must be positive numbers!");
                alert.showAndWait();
                return false;
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("IDs must be numbers!");
            alert.showAndWait();
            return false;
        }

        return true;
    }

    public void closeTeach(ActionEvent event) {
        Platform.exit();}


    public void clearTeach(ActionEvent event) {
        courseId.setText("");
        professorId.setText("");
        startingDate.setText("");
        endingDate.setText("");
        studentCount.setText("");
    }


    @FXML
    void saveTeach(ActionEvent event) {
        if (!isValid(professorId.getText(), courseId.getText(), startingDate.getText(), event)) {
            return;
        }
        Teach teach = new Teach();

        teach.setProfessorId(Integer.parseInt(professorId.getText()));
        teach.setCourseId(Integer.parseInt(courseId.getText()));
        teach.setStartDate(LocalDate.parse(startingDate.getText()));
        if (endingDate.getText() != null && !endingDate.getText().isEmpty()) { // user may not fill in the ending date text field, so we must handle that case.
            teach.setEndingDate(LocalDate.parse(endingDate.getText()));
        } else {
            teach.setEndingDate(null);
        }
        teach.setStudentCount(Integer.parseInt(studentCount.getText()));


        TeachCrudOperations crudOperations = new TeachCrudOperations();
        int res = crudOperations.insertTeachById(teach);

        if(res > 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Teach with professor id " + professorId.getText() + " and course id " + courseId.getText() + " and start date " + startingDate.getText() + " saved");
            alert.showAndWait();
            clearTeach(event);
        } else if(res == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("There is a teach with the same data: " + professorId.getText() +  "\r\n" + courseId.getText() + "\r\n" + startingDate.getText());
            alert.showAndWait();
        } else if (res == -2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Foreign Key Error");
            alert.setHeaderText("Course ID " + teach.getCourseId() + " does not exist in the database!");
            alert.showAndWait();
        } else if (res == -3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Foreign Key Error");
            alert.setHeaderText("Professor ID " + teach.getProfessorId() + " does not exist in the database!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error on saving the teaching!");
            alert.showAndWait();
        }
    }

    @FXML
    void deleteTeach(ActionEvent event) {
        if (!isValid(professorId.getText(), courseId.getText(), startingDate.getText(), event)) {
            return;
        }
        TeachCrudOperations crudOperations = new TeachCrudOperations();

        int prof_id = Integer.parseInt(professorId.getText());
        int course_id = Integer.parseInt(courseId.getText());
        LocalDate start_date = LocalDate.parse(startingDate.getText());
        int result = crudOperations.deleteTeachById(prof_id, course_id, start_date);


        if(result > 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Teach with professor id " + professorId.getText() + " and course id" + courseId.getText() + " and start date" + startingDate.getText() + " deleted");
            alert.showAndWait();
            clearTeach(event);
        } else if(result == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("There isn't any teaching with the id: " + professorId.getText() + " or " + courseId.getText());
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error on deleting course!");
            alert.showAndWait();
        }

    }


    @FXML
    void getTeach(ActionEvent event) {
        if (!isValid(professorId.getText(), courseId.getText(), startingDate.getText(), event)) {
            return;
        }
        TeachCrudOperations crudOperations = new TeachCrudOperations();

        int prof_id = Integer.parseInt(professorId.getText());
        int course_id = Integer.parseInt(courseId.getText());
        LocalDate start_date = LocalDate.parse(startingDate.getText());
        Optional<Teach> teach = crudOperations.getTeachById(prof_id, course_id, start_date);

        if(teach.isPresent()){
            professorId.setText(Integer.toString(teach.get().getProfessorId()));
            courseId.setText(Integer.toString(teach.get().getCourseId()));
            startingDate.setText(teach.get().getStartDate().toString());

            if (teach.get().getEndingDate() != null) {
                endingDate.setText(teach.get().getEndingDate().toString()); // when we try to run this code without checking null possibility, program still works but seems no problem but method doesn't work
                                                                            // if endingdate value is null in a record, toString() tries to convert null into string and it throws error.
            } else {
                endingDate.setText("");
            }

            studentCount.setText(Integer.toString(teach.get().getStudentCount()));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Teach data with professor id " + professorId.getText() + " and " + courseId.getText() + " and " + startingDate.getText() + " not found");
            alert.showAndWait();
        }
    }


    @FXML
    void updateTeach(ActionEvent event) {
        if (!isValid(professorId.getText(), courseId.getText(), startingDate.getText(), event)) {
            return;
        }
        Teach teach = new Teach();

        teach.setProfessorId(Integer.parseInt(professorId.getText()));
        teach.setCourseId(Integer.parseInt(courseId.getText()));
        teach.setStartDate(LocalDate.parse(startingDate.getText()));
        if (endingDate.getText() != null && !endingDate.getText().isEmpty()) { // user may not fill in the ending date text field, so we must handle that case.
            teach.setEndingDate(LocalDate.parse(endingDate.getText()));
        } else {
            teach.setEndingDate(null);
        }
        teach.setStudentCount(Integer.parseInt(studentCount.getText()));


        TeachCrudOperations crudOperations = new TeachCrudOperations();
        int res = crudOperations.updateTeachById(teach);

        if(res > 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Teaching data with id " + professorId.getText() + " and " + courseId.getText() + " and " + startingDate.getText() + " updated");
            alert.showAndWait();
            clearTeach(event);
        } else if (res == -2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Foreign Key Error");
            alert.setHeaderText("Course ID " + teach.getCourseId() + " does not exist in the database!");
            alert.showAndWait();
        } else if (res == -3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Foreign Key Error");
            alert.setHeaderText("Professor ID " + teach.getProfessorId() + " does not exist in the database!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error on updating teaching!");
            alert.showAndWait();
        }
    }



}