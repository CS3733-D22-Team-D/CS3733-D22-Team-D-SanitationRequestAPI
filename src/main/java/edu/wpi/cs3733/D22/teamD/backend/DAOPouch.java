package edu.wpi.cs3733.D22.teamD.backend;

import edu.wpi.cs3733.D22.teamD.entities.EmployeeObj;
import edu.wpi.cs3733.D22.teamD.entities.LocationObj;
import edu.wpi.cs3733.D22.teamD.request.SanitationIRequest;
import java.io.IOException;
import java.sql.SQLException;

public class DAOPouch {
  private static Dao<SanitationIRequest> sanitationRequestDao;
  private static Dao<LocationObj> locationDao;
  private static Dao<EmployeeObj> employeeDao;

  private DAOPouch() {}

  public static void init() throws SQLException, IOException {
    sanitationRequestDao = new Dao<>(new SanitationIRequest());
    locationDao = new Dao<>(new LocationObj());
    employeeDao = new Dao<>(new EmployeeObj());
  }

  public static Dao<SanitationIRequest> getSanitationRequestDAO() {
    return sanitationRequestDao;
  }

  public static Dao<LocationObj> getLocationDAO() {
    return locationDao;
  }

  public static Dao<EmployeeObj> getEmployeeDAO() {
    return employeeDao;
  }
}
