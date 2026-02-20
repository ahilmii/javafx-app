package org.ahilmi.pro1_sm_2.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TeachesController {
    @FXML
    private TextField CourseIDTeaches;
    @FXML
    private TextField ProfessorIDTeaches;
    @FXML
    private TextField StartingDatepicker;
    @FXML
    private TextField EndingDatepicker;
    @FXML
    private Button TeachesGet;
    @FXML
    private Button TeachesClose;
    @FXML
    private Button TeachesClear;
    @FXML
    private Button TeachesSave;
    @FXML
    private Button TeachesDelete;
    @FXML
    private Button TeachesUpdate;
    public void checkId(String id, ActionEvent event) {
        if (id.isEmpty() || Integer.parseInt(id) <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Id is wrong!");
            alert.showAndWait();
            TeachesClear(event);
        }
    }
    public void TeachesClear(ActionEvent event) {
        CourseIDTeaches.setText("");
        ProfessorIDTeaches.setText("");
        StartingDatepicker.setText("");
        EndingDatepicker.setText("");
    }



}