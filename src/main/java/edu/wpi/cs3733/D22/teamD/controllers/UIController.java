package edu.wpi.cs3733.D22.teamD.controllers;

import edu.wpi.cs3733.D22.teamD.backend.DAO;
import edu.wpi.cs3733.D22.teamD.backend.DAOPouch;
import edu.wpi.cs3733.D22.teamD.entities.Location;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class UIController extends AppController implements Initializable {

  /* DAO Object to access all room numbers */
  private List<Location> locations;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    DAO<Location> dao = DAOPouch.getLocationDAO(); //TODO: this is null for some reason
    try {
      this.locations = dao.getAll();
    } catch (Exception e) {
      this.locations = new ArrayList<>();
    }
  }

  protected List<String> getAllLongNames() {
    List<String> names = new ArrayList<>();
    for (Location loc : this.locations) {
      names.add(loc.getLongName());
    }
    return names;
  }
}
