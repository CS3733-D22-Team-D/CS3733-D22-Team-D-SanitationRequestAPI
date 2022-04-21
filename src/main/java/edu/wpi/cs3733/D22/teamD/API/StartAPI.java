package edu.wpi.cs3733.D22.teamD.API;

import edu.wpi.cs3733.D22.teamD.App;
import edu.wpi.cs3733.D22.teamD.controllers.SanitationControl;
import java.io.IOException;
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
    try {
      appLaunch(xCoord, yCoord, windowWidth, windowLength, cssPath, destLocationID);
    } catch (IOException e) {
      throw new ServiceException("API HAS FAILED");
    }
  }

  public static void appLaunch(
      int xCoord,
      int yCoord,
      int windowWidth,
      int windowLength,
      String cssPath,
      String destLocationID)
      throws IOException {
    try {
      App.launch(App.class);
    } catch (Exception e) {
      SanitationControl.xCoord = xCoord;
      SanitationControl.yCoord = yCoord;
      SanitationControl.windowWidth = windowWidth;
      SanitationControl.windowLength = windowLength;
      SanitationControl.cssPath = cssPath;
      SanitationControl.locationID = destLocationID;
      SanitationControl.start(new Stage());
    }
  }
}
