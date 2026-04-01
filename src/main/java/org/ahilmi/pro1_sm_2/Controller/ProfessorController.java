package org.ahilmi.pro1_sm_2.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.ahilmi.pro1_sm_2.db.ProfessorCrudOperations;
import org.ahilmi.pro1_sm_2.dto.Professor;

import java.util.Optional;

public class ProfessorController {
    @FXML
    private TextField professorName;

    @FXML
    private TextField professorId;

    @FXML
    private TextField professorDepartment;

    @FXML
    private Button updateProfessor;

    @FXML
    private Button clearProfessor;

    @FXML
    private Button closeProfessor;

    @FXML
    private Button deleteProfessor;

    @FXML
    private Button getProfessor;

    @FXML
    private Button saveProfessor;

    public void checkId(String id, ActionEvent event) {
        if (id.isEmpty() || Integer.parseInt(id) <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Id is wrong!");
            alert.showAndWait();
            clearProfessor(event);
        }
    }

    public boolean isValid(String id, String name, String department) {
        if (id.isEmpty() || name.isEmpty() || department.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("ID, Name, and Department fields cannot be empty!");
            alert.showAndWait();
            return false;
        }
        try {
            Integer.parseInt(id);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("ID must be a number!");
            alert.showAndWait();
            return false;
        }
        return true;
    }




    public void closeProfessor(ActionEvent event) {Platform.exit();}

    public void clearProfessor(ActionEvent event) {
        professorId.setText("");
        professorName.setText("");
        professorDepartment.setText("");}

    public void saveProfessor(ActionEvent event) {
        if (!isValid(professorId.getText(), professorName.getText(), professorDepartment.getText())) { // if any of these inputs is empty, we will not allow the program to save it.
            return;                                                                       // to create a course, all areas must be filled in.
        }

        Professor professor = new Professor();

        professor.setId(Integer.parseInt(professorId.getText()));
        professor.setName(professorName.getText());
        professor.setDepartment(professorDepartment.getText());

        ProfessorCrudOperations crudOperations = new ProfessorCrudOperations();
        int res = crudOperations.insertProfessorById(professor);

        if(res > 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Professor with id " + professorId.getText() + " saved");
            alert.showAndWait();
            clearProfessor(event);
        } else if(res == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("There is a professor with the same id: " + professorId.getText());
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error on saving professor!");
            alert.showAndWait();
        }

    }

    public void deleteProfessor(ActionEvent event) {
        checkId(professorId.getText(), event);
        ProfessorCrudOperations crudOperations = new ProfessorCrudOperations();

        int id = Integer.parseInt(professorId.getText());
        int result = crudOperations.deleteProfessorById(id);

        if(result > 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Professor with id " + professorId.getText() + " deleted");
            alert.showAndWait();
            clearProfessor(event);
        } else if(result == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("There isn't any professor with the id: " + professorId.getText());
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error on deleting professor!");
            alert.showAndWait();
        }

    }


    public void getProfessor(ActionEvent event) {
        checkId(professorId.getText(), event);
        ProfessorCrudOperations crudOperations = new ProfessorCrudOperations();

        int id = Integer.parseInt(professorId.getText());
        Optional<Professor> professor = crudOperations.getProfessorById(id);

        if(professor.isPresent()){
            professorId.setText(Integer.toString(professor.get().getId()));
            professorName.setText(professor.get().getName());
            professorDepartment.setText(professor.get().getDepartment());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Professor with the id " + id + " not found");
            alert.showAndWait();
        }
    }



    public void updateProfessor(ActionEvent event) {
        checkId(professorId.getText(), event);
        Professor professor = new Professor();

        professor.setId(Integer.parseInt(professorId.getText()));
        professor.setName(professorName.getText());
        professor.setDepartment(professorDepartment.getText());

        ProfessorCrudOperations crudOperations = new ProfessorCrudOperations();
        int res = crudOperations.updateProfessorById(professor);

        if(res > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Professor with id " + professorId.getText() + " updated");
            alert.showAndWait();
            clearProfessor(event);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error on updating professor!");
            alert.showAndWait();
        }

    }

}
