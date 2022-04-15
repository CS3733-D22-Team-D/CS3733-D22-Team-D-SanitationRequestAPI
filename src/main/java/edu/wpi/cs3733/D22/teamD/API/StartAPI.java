package edu.wpi.cs3733.D22.teamD.API;

import edu.wpi.cs3733.D22.teamD.App;
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
   * @param originLocationID location where the service request starts from (IF NEEDED)
   * @throws ServiceException if something unexpected occurs
   */
  public static void run(
      int xCoord,
      int yCoord,
      int windowWidth,
      int windowLength,
      String cssPath,
      String destLocationID,
      String originLocationID)
      throws ServiceException {
    Stage primaryStage = new Stage();
    Parent root;
    try {
      root =
          FXMLLoader.load(Objects.requireNonNull(App.class.getResource("views/Sanitation.fxml")));
    } catch (IOException e) {
      throw new ServiceException();
    }
    Scene scene = new Scene(root);
  }
}
