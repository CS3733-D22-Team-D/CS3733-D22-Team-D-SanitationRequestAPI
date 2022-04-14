package edu.wpi.cs3733.D22.teamD.controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamD.backend.DAO;
import edu.wpi.cs3733.D22.teamD.backend.DAOPouch;
import edu.wpi.cs3733.D22.teamD.entities.Employee;
import edu.wpi.cs3733.D22.teamD.entities.Location;
import edu.wpi.cs3733.D22.teamD.request.Request;
import edu.wpi.cs3733.D22.teamD.request.SanitationRequest;
import edu.wpi.cs3733.D22.teamD.table.TableHelper;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class SanitationController extends AppController implements Initializable {

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
  @FXML private JFXComboBox<String> requestBox;
  @FXML private JFXComboBox<String> assignBox;

  /* Other JFX Objects */
  @FXML private VBox sceneBox;
  @FXML private Button submitButton;
  @FXML private StackPane windowContents;
  @FXML private Label errorLabel;

  DAO<SanitationRequest> sanitationRequestDAO;
  DAO<Location> locationDAO;
  DAO<Employee> employeeDAO;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      DAOPouch.init();
    } catch (Exception e) {
      e.printStackTrace();
    }
    onClearClicked();
    SanitationServiceInitializer init = new SanitationServiceInitializer();

    sanitationRequestDAO = DAOPouch.getSanitationRequestDAO();
    locationDAO = DAOPouch.getLocationDAO();
    employeeDAO = DAOPouch.getEmployeeDAO();

    try {
      // POPULATES TABLE
      List<SanitationRequest> requestList = sanitationRequestDAO.getAll();
      pendingRequests.getItems().addAll(requestList);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Something went wrong making Sanitation Req table");
    }

    try {
      init.initializeInputs();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    init.initializeTable();
  }

  @FXML
  void onClearClicked() {
    sanitationBox.setValue("");
    priorityBox.setValue("");
    locationBox.setValue("");
  }

  /**
   * gets all long names from a list of locations
   *
   * @param locations list of locations
   * @return a list of long names as strings
   */
  private List<String> getAllLongNames(List<Location> locations) {
    List<String> names = new ArrayList<>();
    for (Location loc : locations) {
      names.add(loc.getLongName());
    }
    return names;
  }

  private List<String> getAllNodeIDs(List<Employee> employees) {
    List<String> IDs = new ArrayList<>();
    for (Employee e : employees) {
      IDs.add(e.getNodeID());
    }
    return IDs;
  }

  @FXML
  void onSubmitClicked(MouseEvent event) {
    if (allFieldsFilled()) {
      Request.Priority priority = Request.Priority.valueOf(priorityBox.getValue());
      String roomID = locationBox.getValue();
      String requesterID = "null"; // SecurityController.getUser().getNodeID();
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
        errorLabel.setText("");
        onClearClicked();
        boolean hadClearance =
            addItem(
                new SanitationRequest(
                    priority, roomID, requesterID, assigneeID, sanitationType, status));

        if (!hadClearance) {
          // throw error saying that the user does not have permission to make the request.
          errorLabel.setText("Error: Permission Denied");
        }
      } else {
        // throw an error that the location does not exist
        errorLabel.setText("Error: Unknown Location");
      }
    } else {
      //  throw error message that all fields need to be filled
      errorLabel.setText("Error: One or more fields left empty");
    }
    // clear the fields
    // onClearClicked();
  }

  @FXML
  void quitProgram(ActionEvent event) {
    super.quitProgram();
  }

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
    pendingRequests.getItems().add(request);
    return true;

    /* remove security clearance
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
     */
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

    private void initializeInputs() throws SQLException {
      priorityBox.setItems(
          FXCollections.observableArrayList(TableHelper.convertEnum(Request.Priority.class)));
      sanitationBox.setItems(
          FXCollections.observableArrayList(TableHelper.convertEnum(SanitationTypes.class)));
      locationBox.setItems(
          (FXCollections.observableArrayList(getAllLongNames(locationDAO.getAll()))));
      requestBox.setItems((FXCollections.observableArrayList(getAllNodeIDs(employeeDAO.getAll()))));
      assignBox.setItems((FXCollections.observableArrayList(getAllNodeIDs(employeeDAO.getAll()))));
    }
  }
}
