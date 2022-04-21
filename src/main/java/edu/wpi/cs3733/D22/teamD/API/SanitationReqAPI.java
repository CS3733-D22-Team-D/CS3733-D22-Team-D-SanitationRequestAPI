package edu.wpi.cs3733.D22.teamD.API;

import edu.wpi.cs3733.D22.teamD.backend.Dao;
import edu.wpi.cs3733.D22.teamD.backend.DAOPouch;
import edu.wpi.cs3733.D22.teamD.request.SanitationIRequest;

import java.sql.SQLException;
import java.util.List;

/** Allows for API users to access sanitation requests from the database */
public class SanitationReqAPI {

  private Dao<SanitationIRequest> sanitationDao;

  public SanitationReqAPI() {
    try {
      DAOPouch.init();
    } catch (Exception e) {
      System.err.println("Unable to Init Dao");
      e.printStackTrace();
    }
    this.sanitationDao = DAOPouch.getSanitationRequestDAO();
  }

  /**
   * get a list of all Sanitation Requests in the Database
   *
   * @return a list of all sanitation requests
   */
  public List<SanitationIRequest> getAllRequests() {
    try {
      return this.sanitationDao.getAll();
    } catch (SQLException e) {
      System.err.println("Database Error");
      throw new RuntimeException(e);
    }
  }

  public void deleteRequest(SanitationIRequest request) {
    try {
      this.sanitationDao.delete(request);
    } catch (SQLException e) {
      System.err.println("Database Error");
      throw new RuntimeException(e);
    }
  }
}
