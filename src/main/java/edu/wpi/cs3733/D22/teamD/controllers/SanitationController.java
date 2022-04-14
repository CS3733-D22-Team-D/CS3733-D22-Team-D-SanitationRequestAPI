package edu.wpi.cs3733.D22.teamD.controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class SanitationController {

    @FXML
    private TableColumn<?, ?> Assignee;

    @FXML
    private TableColumn<?, ?> Priority;

    @FXML
    private TableColumn<?, ?> ReqID;

    @FXML
    private TableColumn<?, ?> Requester;

    @FXML
    private TableColumn<?, ?> RoomID;

    @FXML
    private TableColumn<?, ?> Service;

    @FXML
    private TableColumn<?, ?> Status;

    @FXML
    private Button clearButton;

    @FXML
    private Button csvButton;

    @FXML
    private JFXComboBox<?> locationBox;

    @FXML
    private TableView<?> pendingRequests;

    @FXML
    private JFXComboBox<?> priorityBox;

    @FXML
    private JFXComboBox<?> sanitationBox;

    @FXML
    private VBox sceneBox;

    @FXML
    private Button submitButton;

    @FXML
    private StackPane windowContents;

    @FXML
    void onClearClicked(MouseEvent event) {

    }

    @FXML
    void onSubmitClicked(MouseEvent event) {

    }

    @FXML
    void saveToCSV(ActionEvent event) {

    }

}
