package edu.wpi.cs3733.D22.teamD.API;

import edu.wpi.cs3733.D22.teamD.backend.DAO;
import edu.wpi.cs3733.D22.teamD.backend.DAOPouch;
import edu.wpi.cs3733.D22.teamD.entities.Employee;
import java.sql.SQLException;

/**
 * Class that allows users of this API to modify the built-in employee database Users of this API
 * can: - Add Employees - Remove Employees - Edit Existing Employees
 */
public class EmployeeEditor {

  DAO<Employee> employeeDAO;

  public EmployeeEditor() {
    try {
      DAOPouch.init();
    } catch (Exception e) {
      System.err.println("Unable to Init DAO");
      e.printStackTrace();
    }
    this.employeeDAO = DAOPouch.getEmployeeDAO();
  }

  /**
   * Add a given employee to the database
   *
   * @param employee to be added
   */
  public void addEmployee(Employee employee) {
    try {
      this.employeeDAO.add(employee);
    } catch (SQLException e) {
      System.err.println("Unable to add Employee to Database");
    }
  }

  /**
   * Remove a given employee to the database
   *
   * @param employee to be removed
   */
  public void removeEmployee(Employee employee) {
    try {
      this.employeeDAO.delete(employee);
    } catch (SQLException e) {
      System.err.println("Unable to delete Employee to Database");
    }
  }

  /**
   * Allows for an employee to be modified
   *
   * @param employee employee to be edited
   * @param newEmployee new employee with updated attributes
   */
  public void modifyEmployee(Employee employee, Employee newEmployee) {
    try {
      this.employeeDAO.update(employee);
    } catch (SQLException e) {
      System.err.println("Unable to edit the Employee");
    }
  }
}
