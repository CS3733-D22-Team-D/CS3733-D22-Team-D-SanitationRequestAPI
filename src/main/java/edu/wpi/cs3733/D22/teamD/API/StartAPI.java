package edu.wpi.cs3733.D22.teamD.API;

import edu.wpi.cs3733.D22.teamD.App;
import edu.wpi.cs3733.D22.teamD.controllers.SanitationControl;
import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** Class containing the method to start the API USE THE RUN METHOD TO START */
public class StartAPI {
  public StartAPI() {}

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

    App.launch(App.class);

    Stage primaryStage = new Stage();
    Parent root;
    try {
      root =
          FXMLLoader.load(Objects.requireNonNull(App.class.getResource("views/Sanitation.fxml")));
    } catch (IOException e) {
      throw new ServiceException("Unable to load FXML file");
    }

    SanitationControl.locationID = destLocationID;

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

  public static void appLaunch() throws IOException {
    try {
      App.launch(App.class);
    } catch (Exception e) {
      SanitationControl.start(new Stage());
    }
  }
}
