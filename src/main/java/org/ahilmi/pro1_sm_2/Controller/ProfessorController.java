package org.ahilmi.pro1_sm_2.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.ahilmi.pro1_sm_2.db.ProfessorCrudOperations;
import org.ahilmi.pro1_sm_2.dto.Professor;

public class ProfessorController {
    @FXML
    private TextField ProfessorName;

    @FXML
    private TextField ProfessorId;

    @FXML
    private TextField ProfessorDepartment;
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
            clearPrefessor(event);
        }
    }

    public void closePrefessor(ActionEvent event) {Platform.exit();}

    public void clearPrefessor(ActionEvent event) {
        ProfessorId.setText("");
        ProfessorName.setText("");
        ProfessorDepartment.setText("");}
    public void savePrefessor(ActionEvent event) {
        checkId((ProfessorId.getText()), event);
        Professor professor = new Professor();

    }
    public void deletePrefessor(ActionEvent event) {

    }
    public void getPrefessor(ActionEvent event) {

    }
    public void updatePrefessor(ActionEvent event) {

    }

}
