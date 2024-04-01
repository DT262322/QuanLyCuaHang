package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Customers;

public class WarrantyController {

    @FXML
    private Button btnConfirm;

    @FXML
    private ComboBox<Customers> txtCusID;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtID;

    @FXML
    private ComboBox<?> txtStaff;

    @FXML
    private TextField txtStatus;

    @FXML
    void btnConfirm_click(ActionEvent event) {

    }

}
