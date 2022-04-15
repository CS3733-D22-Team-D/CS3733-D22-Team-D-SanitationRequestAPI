package edu.wpi.cs3733.D22.teamD.API;

import edu.wpi.cs3733.D22.teamD.App;
import edu.wpi.cs3733.D22.teamD.controllers.SanitationController;
import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartAPI {

  public StartAPI() {}

  /**
   * Allows for the API to be run when in use by other teams
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
      System.err.println("The FXML page was unable to be loaded");
      throw new ServiceException();
    }

    /* Handle destination location ID */
    SanitationController.locationID = destLocationID;

    /* Set Window Attributes and display */
    Scene scene = new Scene(root);
    primaryStage.setMinWidth(windowLength);
    primaryStage.setMinHeight(windowWidth); // This hurts my brain
    primaryStage.setScene(scene);
    primaryStage.setX(xCoord);
    primaryStage.setY(yCoord);
    System.out.println(scene.getStylesheets());
    primaryStage
        .getScene()
        .getStylesheets()
        .add(Objects.requireNonNull(getClass().getClassLoader().getResource(cssPath)).toString());
    primaryStage.show();
  }
}
