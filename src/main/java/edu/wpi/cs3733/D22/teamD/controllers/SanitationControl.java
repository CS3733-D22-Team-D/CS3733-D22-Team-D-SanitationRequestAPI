package edu.wpi.cs3733.D22.teamD.controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamD.App;
import edu.wpi.cs3733.D22.teamD.backend.CSVSaver;
import edu.wpi.cs3733.D22.teamD.backend.DAOPouch;
import edu.wpi.cs3733.D22.teamD.backend.Dao;
import edu.wpi.cs3733.D22.teamD.entities.EmployeeObj;
import edu.wpi.cs3733.D22.teamD.entities.LocationObj;
import edu.wpi.cs3733.D22.teamD.request.IRequest;
import edu.wpi.cs3733.D22.teamD.request.SanitationIRequest;
import edu.wpi.cs3733.D22.teamD.table.TableHelp;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SanitationControl implements Initializable {

  /* Table and table helper */
  @FXML private TableView<SanitationIRequest> pendingRequests;
  private TableHelp<SanitationIRequest> helper;

  /* Table Cols */
  @FXML private TableColumn<SanitationIRequest, String> Assignee;
  @FXML private TableColumn<SanitationIRequest, String> Priority;
  @FXML private TableColumn<SanitationIRequest, String> ReqID;
  @FXML private TableColumn<SanitationIRequest, String> Requester;
  @FXML private TableColumn<SanitationIRequest, String> RoomID;
  @FXML private TableColumn<SanitationIRequest, String> Service;
  @FXML private TableColumn<SanitationIRequest, String> Status;

  /* Buttons */
  @FXML private Button clearButton;
  @FXML private Button csvButton;
  @FXML private Button quitButton;

  /* JFX Combo Box */
  @FXML private JFXComboBox<String> priorityBox;
  @FXML private JFXComboBox<String> sanitationBox;
  @FXML private JFXComboBox<String> requestBox;
  @FXML private JFXComboBox<String> assignBox;

  /* Other JFX Objects */
  @FXML private VBox sceneBox;
  @FXML private Button submitButton;
  @FXML private StackPane windowContents;
  @FXML private Label errorLabel;

  public static int xCoord;
  public static int yCoord;
  public static int windowWidth;
  public static int windowLength;
  public static String cssPath;
  public static String locationID;

  Dao<SanitationIRequest> sanitationRequestDao;
  Dao<LocationObj> locationDao;
  Dao<EmployeeObj> employeeDao;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      DAOPouch.init();
    } catch (Exception e) {
      e.printStackTrace();
    }
    onClearClicked();
    SanitationServiceInitializer init = new SanitationServiceInitializer();

    sanitationRequestDao = DAOPouch.getSanitationRequestDAO();
    locationDao = DAOPouch.getLocationDAO();
    employeeDao = DAOPouch.getEmployeeDAO();

    try {
      // POPULATES TABLE
      List<SanitationIRequest> requestList = sanitationRequestDao.getAll();
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

  public static void start(Stage primaryStage) throws IOException {
    Parent root =
        FXMLLoader.load(Objects.requireNonNull(App.class.getResource("views/Sanitation.fxml")));
    primaryStage.setMinWidth(windowLength);
    primaryStage.setMinHeight(windowWidth);
    primaryStage.setX(xCoord);
    primaryStage.setY(yCoord);
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.getScene().setRoot(root);
    primaryStage.setTitle("Team-D Sanitation Services API");
    try {
      primaryStage
          .getScene()
          .getStylesheets()
          .add(
              Objects.requireNonNull(SanitationControl.class.getClassLoader().getResource(cssPath))
                  .toString());
    } catch (Exception e) {
      System.err.println("Could not find CSS path");
    }
    primaryStage.show();
  }

  @FXML
  void onClearClicked() {
    sanitationBox.setValue("");
    priorityBox.setValue("");
    requestBox.setValue("");
    assignBox.setValue("");
    errorLabel.setText("");
  }

  /**
   * gets all long names from a list of locationObjs
   *
   * @param locationObjs list of locationObjs
   * @return a list of long names as strings
   */
  private List<String> getAllNodeIDs(List<LocationObj> locationObjs) {
    List<String> names = new ArrayList<>();
    for (LocationObj loc : locationObjs) {
      names.add(loc.getNodeID());
    }
    return names;
  }

  private List<String> getAllEmpNames(List<EmployeeObj> employeeObjs) {
    List<String> IDs = new ArrayList<>();
    for (EmployeeObj e : employeeObjs) {
      IDs.add(e.getNodeID() + " " + e.getFirstName() + " " + e.getLastName());
    }
    return IDs;
  }

  @FXML
  void onSubmitClicked(MouseEvent event) {
    if (allFieldsFilled()) {
      IRequest.Priority priority = IRequest.Priority.valueOf(priorityBox.getValue());

      /*
      The locationObj ID needs to be specified in the API run method, if running the jar directly the
      locationObj will display as "null"
      */
      String roomID;
      if (locationID == null) roomID = "null";
      else roomID = locationID;

      String requesterID = requestBox.getValue(); // SecurityController.getUser().getNodeID();
      String assigneeID = assignBox.getValue();
      String sanitationType = sanitationBox.getValue().toString();
      IRequest.RequestStatus status = IRequest.RequestStatus.REQUESTED;

      /*Make sure the room exists*/
      LocationObj locationObj = new LocationObj();
      ArrayList<LocationObj> locationObjs;
      try {
        locationObjs = (ArrayList<LocationObj>) locationDao.getAll();
      } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Unable to access locationObj database");
        return;
      }
      List<String> ids = getAllNodeIDs(locationObjs);
      ids.add("null"); // allows for an undetermined room to be displayed

      if (ids.contains(roomID)) {
        errorLabel.setText("");
        onClearClicked();
        addItem(
            new SanitationIRequest(
                priority, roomID, requesterID, assigneeID, sanitationType, status));

      } else {
        errorLabel.setText("Error: Unknown LocationObj");
      }
    } else {
      //  throw error message that all fields need to be filled
      errorLabel.setText("Error: One or more fields left empty");
    }
  }

  @FXML
  void quitProgram(ActionEvent event) {
    CSVSaver.saveAll();
    if (sceneBox != null && sceneBox.getScene() != null) {
      Stage window = (Stage) sceneBox.getScene().getWindow();
      if (window != null) window.close();
    }
    Platform.exit();
  }

  @FXML
  void saveToCSV() {
    FileChooser fileSys = new FileChooser();
    Stage window = (Stage) sceneBox.getScene().getWindow();
    fileSys.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
    File csv = fileSys.showSaveDialog(window);
    try {
      CSVSaver.save(new SanitationIRequest(), csv.getAbsolutePath());
    } catch (Exception e) {
      System.err.println("Unable to save to CSV");
    }
  }

  private boolean allFieldsFilled() {
    return !((sanitationBox.getValue().equals(""))
        || priorityBox.getValue().equals("")
        || assignBox.getValue().equals("")
        || requestBox.getValue().equals(""));
  }

  /** Adds new sanitationRequest to table of pending requests * */
  private void addItem(SanitationIRequest request) {
    try {
      sanitationRequestDao.add(request);
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
      helper = new TableHelp<>(pendingRequests, 0);
      helper.linkColumns(SanitationIRequest.class);
    }

    private void initializeInputs() throws SQLException {
      priorityBox.setItems(
          FXCollections.observableArrayList(TableHelp.convertEnum(IRequest.Priority.class)));
      sanitationBox.setItems(
          FXCollections.observableArrayList(TableHelp.convertEnum(SanitationTypes.class)));
      requestBox.setItems(
          (FXCollections.observableArrayList(getAllEmpNames(employeeDao.getAll()))));
      assignBox.setItems((FXCollections.observableArrayList(getAllEmpNames(employeeDao.getAll()))));
    }
  }
}
