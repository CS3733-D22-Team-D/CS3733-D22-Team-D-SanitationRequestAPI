package edu.wpi.cs3733.D22.teamD.controllers;

import edu.wpi.cs3733.D22.teamD.backend.csvSaver;
import edu.wpi.cs3733.D22.teamD.table.TableObject;
import java.io.File;
import javafx.application.Platform;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AppController {

  @FXML protected Node mainNode;
  @FXML private VBox error;
  @FXML private StackPane windowContents;
  @FXML private VBox sceneBox;

  @FXML
  protected void quitProgram() {
    csvSaver.saveAll();
    if (sceneBox != null && sceneBox.getScene() != null) {
      Stage window = (Stage) sceneBox.getScene().getWindow();
      if (window != null) window.close();
    }
    Platform.exit();
    System.exit(0);
  }

  protected void saveToCSV(TableObject type) {
    FileChooser fileSys = new FileChooser();
    Stage window = (Stage) sceneBox.getScene().getWindow();
    fileSys.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
    File csv = fileSys.showSaveDialog(window);
    try {
      csvSaver.save(type, csv.getAbsolutePath());
    } catch (Exception e) {
      System.err.println("Unable to Save CSV of type: " + type);
    }
  }
}
