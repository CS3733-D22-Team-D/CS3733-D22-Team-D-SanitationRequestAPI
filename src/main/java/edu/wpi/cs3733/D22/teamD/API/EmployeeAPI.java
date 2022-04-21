package edu.wpi.cs3733.D22.teamD.API;

import edu.wpi.cs3733.D22.teamD.backend.Dao;
import edu.wpi.cs3733.D22.teamD.backend.DAOPouch;
import edu.wpi.cs3733.D22.teamD.entities.EmployeeObj;

import java.sql.SQLException;
import java.util.List;

/**
 * Class that allows users of this API to modify the built-in employee database Users of this API
 * can: - Add Employees - Remove Employees - Edit Existing Employees
 */
public class EmployeeAPI {

  private Dao<EmployeeObj> employeeDao;

  public EmployeeAPI() {
    try {
      DAOPouch.init();
    } catch (Exception e) {
      System.err.println("Unable to Init Dao");
      e.printStackTrace();
    }
    this.employeeDao = DAOPouch.getEmployeeDAO();
  }

  /**
   * Add a given employeeObj to the database
   *
   * @param employeeObj to be added
   */
  public void addEmployee(EmployeeObj employeeObj) {
    try {
      this.employeeDao.add(employeeObj);
    } catch (SQLException e) {
      System.err.println("Unable to add EmployeeObj to Database");
      throw new RuntimeException(e);
    }
  }

  /**
   * Remove a given employeeObj to the database
   *
   * @param employeeObj to be removed
   */
  public void removeEmployee(EmployeeObj employeeObj) {
    try {
      this.employeeDao.delete(employeeObj);
    } catch (SQLException e) {
      System.err.println("Unable to delete EmployeeObj to Database");
    }
  }

  /**
   * Allows for an employeeObj to be modified
   *
   * @param employeeObj employeeObj to be edited
   * @param newEmployeeObj new employeeObj with updated attributes
   */
  public void modifyEmployee(EmployeeObj employeeObj, EmployeeObj newEmployeeObj) {
    try {
      this.employeeDao.update(employeeObj);
    } catch (SQLException e) {
      throw new RuntimeException();
    }
  }

  /**
   * get all employees in the database
   *
   * @return a list of employees
   */
  public List<EmployeeObj> getAllEmployees() {
    List<EmployeeObj> employeeObjs;
    try {
      employeeObjs = this.employeeDao.getAll();
    } catch (SQLException e) {
      throw new RuntimeException();
    }
    return employeeObjs;
  }
}
