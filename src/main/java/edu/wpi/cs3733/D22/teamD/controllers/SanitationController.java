package edu.wpi.cs3733.D22.teamD.controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamD.API.ServiceException;
import edu.wpi.cs3733.D22.teamD.API.StartAPI;
import edu.wpi.cs3733.D22.teamD.backend.DAO;
import edu.wpi.cs3733.D22.teamD.backend.DAOPouch;
import edu.wpi.cs3733.D22.teamD.backend.csvSaver;
import edu.wpi.cs3733.D22.teamD.entities.Employee;
import edu.wpi.cs3733.D22.teamD.entities.Location;
import edu.wpi.cs3733.D22.teamD.request.Request;
import edu.wpi.cs3733.D22.teamD.request.SanitationRequest;
import edu.wpi.cs3733.D22.teamD.table.TableHelper;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SanitationController implements Initializable {

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

  public static String locationID;

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
    locationBox.setValue(locationID);
  }

  @FXML
  void onClearClicked() {
    sanitationBox.setValue("");
    priorityBox.setValue("");
    locationBox.setValue("");
    requestBox.setValue("");
    assignBox.setValue("");
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

  private List<String> getAllEmpNames(List<Employee> employees) {
    List<String> IDs = new ArrayList<>();
    for (Employee e : employees) {
      IDs.add(e.getNodeID() + " " + e.getFirstName() + " " + e.getLastName());
    }
    return IDs;
  }

  @FXML
  void onSubmitClicked(MouseEvent event) {
    if (allFieldsFilled()) {
      Request.Priority priority = Request.Priority.valueOf(priorityBox.getValue());
      String roomID = locationBox.getValue();
      String requesterID = requestBox.getValue(); // SecurityController.getUser().getNodeID();
      String assigneeID = assignBox.getValue();
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
        addItem(
            new SanitationRequest(
                priority, roomID, requesterID, assigneeID, sanitationType, status));

      } else {
        errorLabel.setText("Error: Unknown Location");
      }
    } else {
      //  throw error message that all fields need to be filled
      errorLabel.setText("Error: One or more fields left empty");
    }
    // TODO: REMOVE AFTER DEBUG
    StartAPI startAPI = new StartAPI();
    try {
      startAPI.run(0, 0, 500, 800, "edu/wpi/cs3733/D22/teamD/assets/style.css", "FHALL01401");
    } catch (ServiceException e) {
      e.printStackTrace();
    }
  }

  @FXML
  void quitProgram(ActionEvent event) {
    csvSaver.saveAll();
    if (sceneBox != null && sceneBox.getScene() != null) {
      Stage window = (Stage) sceneBox.getScene().getWindow();
      if (window != null) window.close();
    }
    Platform.exit();
    System.exit(0);
  }

  @FXML
  void saveToCSV() {
    FileChooser fileSys = new FileChooser();
    Stage window = (Stage) sceneBox.getScene().getWindow();
    fileSys.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
    File csv = fileSys.showSaveDialog(window);
    try {
      csvSaver.save(new SanitationRequest(), csv.getAbsolutePath());
    } catch (Exception e) {
      System.err.println("Unable to save to CSV");
    }
  }

  private boolean allFieldsFilled() {
    return !((sanitationBox.getValue().equals(""))
        || priorityBox.getValue().equals("")
        || locationBox.getValue().equals(""));
  }

  /** Adds new sanitationRequest to table of pending requests * */
  private void addItem(SanitationRequest request) {
    try {
      sanitationRequestDAO.add(request);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    pendingRequests.getItems().add(request);
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
      requestBox.setItems(
          (FXCollections.observableArrayList(getAllEmpNames(employeeDAO.getAll()))));
      assignBox.setItems((FXCollections.observableArrayList(getAllEmpNames(employeeDAO.getAll()))));
    }
  }
}
