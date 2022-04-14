package edu.wpi.cs3733.D22.teamD.backend;

import edu.wpi.cs3733.D22.teamD.entities.Employee;
import edu.wpi.cs3733.D22.teamD.entities.Location;
import edu.wpi.cs3733.D22.teamD.request.SanitationRequest;
import java.io.IOException;
import java.sql.SQLException;

public class DAOPouch {
  private static DAO<SanitationRequest> sanitationRequestDAO;
  private static DAO<Location> locationDAO;
  private static DAO<Employee> employeeDAO;

  private DAOPouch() {}

  public static void init() throws SQLException, IOException {
    sanitationRequestDAO = new DAO<>(new SanitationRequest());
    locationDAO = new DAO<>(new Location());
    employeeDAO = new DAO<>(new Employee());
  }

  public static DAO<SanitationRequest> getSanitationRequestDAO() {
    return sanitationRequestDAO;
  }

  public static DAO<Location> getLocationDAO() {
    return locationDAO;
  }

  public static DAO<Employee> getEmployeeDAO() {
    return employeeDAO;
  }
}
