package edu.wpi.cs3733.D22.teamD.API;

import edu.wpi.cs3733.D22.teamD.App;
import edu.wpi.cs3733.D22.teamD.backend.ConnectionHandler;
import edu.wpi.cs3733.D22.teamD.backend.DAO;
import edu.wpi.cs3733.D22.teamD.backend.DAOPouch;
import edu.wpi.cs3733.D22.teamD.controllers.SanitationController;
import edu.wpi.cs3733.D22.teamD.entities.Location;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** Class containing the method to start the API USE THE RUN METHOD TO START */
public class StartAPI {

  DAO<Location> locationDAO;

  public StartAPI() throws ServiceException {
    ConnectionHandler.init();
    ConnectionHandler.switchToEmbedded();
    try {
      DAOPouch.init();
    } catch (Exception e) {
      throw new ServiceException("There was an issue initializing the DAO object");
    }
    this.locationDAO = DAOPouch.getLocationDAO();
  }

  /**
   * Allows for the API to be RUN
   *
   * @param xCoord starting x coordinate of the program window
   * @param yCoord starting y coordinate of the program window
   * @param windowWidth width of the program window
   * @param windowLength height of the program window
   * @param cssPath path to CSS styling file
   * @param destLocationID location where the request is done
   * @throws ServiceException if something unexpected occurs
   */
  public void run(
      int xCoord,
      int yCoord,
      int windowWidth,
      int windowLength,
      String cssPath,
      String destLocationID)
      throws ServiceException {

    Stage primaryStage = new Stage();
    Parent root;
    try {
      root =
          FXMLLoader.load(Objects.requireNonNull(App.class.getResource("views/Sanitation.fxml")));
    } catch (IOException e) {
      throw new ServiceException("Unable to load FXML file");
    }

    /* Check if the location that was entered was valid */
    boolean validLocation;
    try {
      validLocation = checkForValidLocation(destLocationID);
    } catch (SQLException e) {
      throw new ServiceException("Error Connecting to Database");
    }
    if (validLocation) SanitationController.locationID = destLocationID;
    else throw new ServiceException("The location ID did not match any present in the database");

    /* Set Window Attributes and display */
    Scene scene = new Scene(root);
    primaryStage.setMinWidth(windowLength);
    primaryStage.setMinHeight(windowWidth);
    primaryStage.setScene(scene);
    primaryStage.setX(xCoord);
    primaryStage.setY(yCoord);

    /* Handle CSS path */
    System.out.println(scene.getStylesheets());
    try {
      primaryStage
          .getScene()
          .getStylesheets()
          .add(Objects.requireNonNull(getClass().getClassLoader().getResource(cssPath)).toString());
    } catch (Exception e) {
      throw new ServiceException("There was a problem with the CSS Path");
    }
    /* Show stage */
    primaryStage.show();
  }

  /**
   * Checks if a given location is present in the database
   *
   * @param locationID the location to be checked
   * @return true if the location is in the database
   * @throws SQLException Database error
   */
  public boolean checkForValidLocation(String locationID) throws SQLException {
    List<Location> locationList = this.locationDAO.getAll();
    for (Location l : locationList) {
      if (l.getNodeID().equals(locationID)) return true;
    }
    return false;
  }
}
