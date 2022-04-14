package edu.wpi.cs3733.D22.teamD.controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamD.backend.DAO;
import edu.wpi.cs3733.D22.teamD.backend.DAOPouch;
import edu.wpi.cs3733.D22.teamD.backend.SecurityController;
import edu.wpi.cs3733.D22.teamD.entities.Location;
import edu.wpi.cs3733.D22.teamD.request.Request;
import edu.wpi.cs3733.D22.teamD.request.SanitationRequest;
import edu.wpi.cs3733.D22.teamD.table.TableHelper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SanitationController extends UIController {

  /* Table and table helper */
  @FXML private TableView<SanitationRequest> pendingRequests;
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


  DAO<SanitationRequest> sanitationRequestDAO = DAOPouch.getSanitationRequestDAO();
  DAO<Location> locationDAO = DAOPouch.getLocationDAO();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    super.initialize(location, resources);
    onClearClicked();

  }
  @FXML
  void onClearClicked() {
    sanitationBox.setValue("");
    priorityBox.setValue("");
    locationBox.setValue("");
  }

  @FXML
  void onSubmitClicked(MouseEvent event) {
    if (allFieldsFilled()) {
      Request.Priority priority = Request.Priority.valueOf(priorityBox.getValue());
      String roomID = locationBox.getValue();
      String requesterID = SecurityController.getUser().getNodeID();
      String assigneeID = "null";
      String sanitationType = sanitationBox.getValue().toString();
      Request.RequestStatus status = Request.RequestStatus.REQUESTED;

      /*Make sure the room exists*/
      boolean isALocation = false;
      Location location = new Location();
      ArrayList<Location> locations = new ArrayList<>();
      try {
        locations = (ArrayList<Location>) locationDAO.getAll();
      } catch (SQLException e) {
        e.printStackTrace();
      }

      location = locationDAO.filter(locations, 7, roomID).get(0);

      isALocation = location.getAttribute(7).equals(roomID);
      if (isALocation) {

        boolean hadClearance =
                addItem(
                        new SanitationRequest(
                                priority, roomID, requesterID, assigneeID, sanitationType, status));

        if (!hadClearance) {
          // throw error saying that the user does not have permission to make the request.
        }
      } else {
        // throw an error that the location does not exist
      }
    } else {
      //  throw error message that all fields need to be filled
    }
    // clear the fields
    onClearClicked();
  }

  @FXML
  void quitProgram(ActionEvent event) {}

  @FXML
  void saveToCSV(ActionEvent event) {
    super.saveToCSV(new SanitationRequest());
  }
  private boolean allFieldsFilled() {
    return !((sanitationBox.getValue().equals(""))
            || priorityBox.getValue().equals("")
            || locationBox.getValue().equals(""));
  }

  /** Adds new sanitationRequest to table of pending requests * */
  private boolean addItem(SanitationRequest request) {
    boolean hasClearance = false;
    try {
      hasClearance = sanitationRequestDAO.add(request);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    if (hasClearance) {
      pendingRequests.getItems().add(request);
    }
    return hasClearance;
  }

  public enum SanitationTypes {
    MoppingSweeping,
    Sterilize,
    Trash,
    BioHazard;
  }

  private class SanitationServiceInitializer {
    private void initializeTable() {
      helper = new TableHelper<>(pendingRequests, 0);
      helper.linkColumns(SanitationRequest.class);
    }

    private void initializeInputs() {
      priorityBox.setItems(
              FXCollections.observableArrayList(TableHelper.convertEnum(Request.Priority.class)));
      sanitationBox.setItems(
              FXCollections.observableArrayList(TableHelper.convertEnum(SanitationTypes.class)));

      locationBox.setItems((FXCollections.observableArrayList(getAllLongNames())));
    }
  }

}
