package edu.wpi.cs3733.D22.teamD.API;

import edu.wpi.cs3733.D22.teamD.backend.DAO;
import edu.wpi.cs3733.D22.teamD.backend.DAOPouch;
import edu.wpi.cs3733.D22.teamD.entities.Location;
import java.sql.SQLException;
import java.util.List;

/** Class that allows users to edit the location database */
public class LocationAPI {

  private DAO<Location> locationDAO;

  public LocationAPI() {
    try {
      DAOPouch.init();
    } catch (Exception e) {
      System.err.println("There was an error connecting to the database");
      throw new RuntimeException();
    }
    this.locationDAO = DAOPouch.getLocationDAO();
  }

  /**
   * Add a location to the database
   *
   * @param location location to add
   */
  public void addLocation(Location location) {
    try {
      this.locationDAO.add(location);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * remove a location from the database
   *
   * @param location location to remove
   */
  public void removeLocation(Location location) {
    try {
      this.locationDAO.delete(location);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Get all locations in the database
   *
   * @return a list of all locations
   */
  public List<Location> getAllLocations() {
    List<Location> locations;
    try {
      locations = this.locationDAO.getAll();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return locations;
  }
}
