package edu.wpi.cs3733.D22.teamD.API;

import edu.wpi.cs3733.D22.teamD.backend.DAOPouch;
import edu.wpi.cs3733.D22.teamD.backend.Dao;
import edu.wpi.cs3733.D22.teamD.entities.LocationObj;
import java.sql.SQLException;
import java.util.List;

/** Class that allows users to edit the location database */
public class LocationAPI {

  private Dao<LocationObj> locationDao;

  public LocationAPI() {
    try {
      DAOPouch.init();
    } catch (Exception e) {
      System.err.println("There was an error connecting to the database");
      throw new RuntimeException();
    }
    this.locationDao = DAOPouch.getLocationDAO();
  }

  /**
   * Add a locationObj to the database
   *
   * @param locationObj locationObj to add
   */
  public void addLocation(LocationObj locationObj) {
    try {
      this.locationDao.add(locationObj);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * remove a locationObj from the database
   *
   * @param locationObj locationObj to remove
   */
  public void removeLocation(LocationObj locationObj) {
    try {
      this.locationDao.delete(locationObj);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Get all locations in the database
   *
   * @return a list of all locations
   */
  public List<LocationObj> getAllLocations() {
    List<LocationObj> locationObjs;
    try {
      locationObjs = this.locationDao.getAll();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return locationObjs;
  }
}
