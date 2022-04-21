package edu.wpi.cs3733.D22.teamD;

import java.io.IOException;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    Parent root =
        FXMLLoader.load(Objects.requireNonNull(App.class.getResource("views/Sanitation.fxml")));
    primaryStage.setMinWidth(780);
    primaryStage.setMinHeight(548);
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.getScene().setRoot(root);
    primaryStage.setTitle("Team-D Sanitation Services API");
    primaryStage.show();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
