module org.ahilmi.pro1_sm_2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;


    opens org.ahilmi.pro1_sm_2 to javafx.fxml;
    opens org.ahilmi.pro1_sm_2.Controller to javafx.fxml;
    exports org.ahilmi.pro1_sm_2;
}