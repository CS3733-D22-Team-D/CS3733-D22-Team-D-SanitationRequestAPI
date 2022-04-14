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

  @FXML private TableColumn<?, String> Assignee;

  @FXML private TableColumn<?, String> Priority;

  @FXML private TableColumn<?, String> ReqID;

  @FXML private TableColumn<?, String> Requester;

  @FXML private TableColumn<?, String> RoomID;

  @FXML private TableColumn<?, String> Service;

  @FXML private TableColumn<?, String> Status;

  @FXML private Button clearButton;

  @FXML private Button csvButton;

  @FXML private Button quitButton;

  @FXML private JFXComboBox<String> locationBox;

  @FXML private TableView<String> pendingRequests;

  @FXML private JFXComboBox<String> priorityBox;

  @FXML private JFXComboBox<String> sanitationBox;

  @FXML private VBox sceneBox;

  @FXML private Button submitButton;

  @FXML private StackPane windowContents;

  @FXML
  void onClearClicked(MouseEvent event) {}

  @FXML
  void onSubmitClicked(MouseEvent event) {}

  @FXML
  void quitProgram(ActionEvent event) {}

  @FXML
  void saveToCSV(ActionEvent event) {}
}
