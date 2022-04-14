package edu.wpi.cs3733.D22.teamD.backend;

import edu.wpi.cs3733.D22.teamD.entities.Employee;
import edu.wpi.cs3733.D22.teamD.table.TableObject;

public class SecurityController {
  static Employee user;
  static SecurityController instance;

  private SecurityController() {}

  public static void setUser(Employee u) {
    user = u;
  }

  public static Employee getUser() {
    return user;
  }

  public static boolean permissionToAdd(TableObject type) {
    String tableName = type.getTableName();
    int clearance = user.getSecurityClearance();
    if (tableName.equals("")) {
      return false;
    } else if (tableName.equals("LABREQUESTS")) {
      return clearance >= 4;
    } else if (tableName.equals("MEALDELIVERYREQUESTS")) {
      return clearance >= 3;
    } else if (tableName.equals("MEDICALEQUIPMENTREQUESTS")) {
      return clearance >= 3;
    } else if (tableName.equals("MEDICINEREQUESTS")) {
      return clearance >= 3;
    } else if (tableName.equals("PATIENTTRANSPORTREQUESTS")) {
      return clearance >= 3;
    } else if (tableName.equals("SANITATIONREQUESTS")) {
      return clearance >= 3;
    } else if (tableName.equals("ACCOUNTS")) {
      return clearance >= 5;
    } else if (tableName.equals("EMPLOYEES")) {
      return clearance >= 5;
    } else if (tableName.equals("LOCATIONS")) {
      return clearance >= 5;
    } else if (tableName.equals("MEDICALEQUIPMENT")) {
      return clearance >= 3;
    } else if (tableName.equals("PATIENTS")) {
      return clearance >= 3;
    }
    return false;
  }

  public static boolean permissionToUpdate(TableObject type) {
    String tableName = type.getTableName();
    int clearance = user.getSecurityClearance();
    if (tableName.equals("")) {
      return false;
    } else if (tableName.equals("LABREQUESTS")) {
      return clearance >= 3;
    } else if (tableName.equals("MEALDELIVERYREQUESTS")) {
      return clearance >= 1;
    } else if (tableName.equals("MEDICALEQUIPMENTREQUESTS")) {
      return clearance >= 3;
    } else if (tableName.equals("MEDICINEREQUESTS")) {
      return clearance >= 3;
    } else if (tableName.equals("PATIENTTRANSPORTREQUESTS")) {
      return clearance >= 3;
    } else if (tableName.equals("SANITATIONREQUESTS")) {
      return clearance >= 2;
    } else if (tableName.equals("ACCOUNTS")) {
      return clearance >= 5;
    } else if (tableName.equals("EMPLOYEES")) {
      return clearance >= 5;
    } else if (tableName.equals("LOCATIONS")) {
      return clearance >= 5;
    } else if (tableName.equals("MEDICALEQUIPMENT")) {
      return clearance >= 3;
    } else if (tableName.equals("PATIENTS")) {
      return clearance >= 3;
    }
    return false;
  }

  public static boolean permissionToDelete(TableObject type) {
    String tableName = type.getTableName();
    int clearance = user.getSecurityClearance();
    if (tableName.equals("")) {
      return false;
    } else if (tableName.equals("LABREQUESTS")) {
      return clearance >= 3;
    } else if (tableName.equals("MEALDELIVERYREQUESTS")) {
      return clearance >= 1;
    } else if (tableName.equals("MEDICALEQUIPMENTREQUESTS")) {
      return clearance >= 3;
    } else if (tableName.equals("MEDICINEREQUESTS")) {
      return clearance >= 3;
    } else if (tableName.equals("PATIENTTRANSPORTREQUESTS")) {
      return clearance >= 3;
    } else if (tableName.equals("SANITATIONREQUESTS")) {
      return clearance >= 2;
    } else if (tableName.equals("ACCOUNTS")) {
      return clearance >= 5;
    } else if (tableName.equals("EMPLOYEES")) {
      return clearance >= 5;
    } else if (tableName.equals("LOCATIONS")) {
      return clearance >= 5;
    } else if (tableName.equals("MEDICALEQUIPMENT")) {
      return clearance >= 3;
    } else if (tableName.equals("PATIENTS")) {
      return clearance >= 3;
    }
    return false;
  }
}
