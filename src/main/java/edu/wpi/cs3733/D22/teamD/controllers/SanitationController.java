package edu.wpi.cs3733.D22.teamD.controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamD.request.SanitationRequest;
import edu.wpi.cs3733.D22.teamD.table.TableHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SanitationController extends UIController {

  /* Table and table helper */
  @FXML private TableView<String> pendingRequests;
  private TableHelper<SanitationRequest> helper;

  /* Table Cols */
  @FXML private TableColumn<SanitationRequest, String> Assignee;
  @FXML private TableColumn<SanitationRequest, String> Priority;
  @FXML private TableColumn<SanitationRequest, String> ReqID;
  @FXML private TableColumn<SanitationRequest, String> Requester;
  @FXML private TableColumn<SanitationRequest, String> RoomID;
  @FXML private TableColumn<SanitationRequest, String> Service;
  @FXML private TableColumn<SanitationRequest, String> Status;

  /* Buttons */
  @FXML private Button clearButton;
  @FXML private Button csvButton;
  @FXML private Button quitButton;

  /* JFX Combo Box */
  @FXML private JFXComboBox<String> locationBox;
  @FXML private JFXComboBox<String> priorityBox;
  @FXML private JFXComboBox<String> sanitationBox;


  /* Other JFX Objects */
  @FXML private VBox sceneBox;
  @FXML private Button submitButton;
  @FXML private StackPane windowContents;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }
  @FXML
  void onClearClicked(MouseEvent event) {}

  @FXML
  void onSubmitClicked(MouseEvent event) {}

  @FXML
  void quitProgram(ActionEvent event) {}

  @FXML
  void saveToCSV(ActionEvent event) {}

}
