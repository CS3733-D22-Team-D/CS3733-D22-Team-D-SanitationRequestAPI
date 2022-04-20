package edu.wpi.cs3733.D22.teamD.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
  static Connection connection;

  static connectionType type;

  public enum connectionType {
    EMBEDDED,
    CLIENTSERVER
  }

  static {
    System.out.println("I am running hello!");
    init();
    switchToEmbedded();
  }

  private ConnectionHelper() {}

  public static void init() {}

  public static connectionType getType() {
    return type;
  }

  public static Connection getConnection() {
    return connection;
  }

  public static boolean switchToClientServer() {
    try {
      csvSaver.saveAll();
      Class.forName("org.apache.derby.jdbc.ClientDriver");
      System.out.println("Connecting to client");
      connection =
          DriverManager.getConnection("jdbc:derby://localhost:1527/BaW_Database;create=true");
      System.out.println("Connected to the client server");
      csvLoader.loadAll();
      type = connectionType.CLIENTSERVER;
    } catch (SQLException e) {
      System.out.println("Could not connect to the client server, reverting back to embedded");
      type = connectionType.EMBEDDED;
      return false;
    } catch (ClassNotFoundException e) {
      System.out.println("Driver error, try making sure you don't have any other instances open!");
      type = connectionType.EMBEDDED;
      return false;
    }
    try {
      DAOPouch.init();
    } catch (Exception e) {
      System.out.println("DAOPouch could not initialize");
    }
    return true;
  }

  public static boolean switchToEmbedded() {
    try {
      if (connection != null) {
        csvSaver.saveAll();
      }
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      System.out.println("Connecting to embedded");
      connection = DriverManager.getConnection("jdbc:derby:BaW_database;create = true");
      csvLoader.loadAll();
      System.out.println("Connected to the embedded server");
      type = connectionType.EMBEDDED;
    } catch (SQLException e) {
      System.out.println(
          "Could not connect to the embedded server, reverting back to client server");
      type = connectionType.CLIENTSERVER;
      return false;
    } catch (ClassNotFoundException e) {
      //      System.out.println("Driver error, try making sure you don't have any other instances
      // open!");
      type = connectionType.CLIENTSERVER;
      return false;
    }
    try {
      DAOPouch.init();
    } catch (Exception e) {
      System.out.println("DAOPouch could not initialize");
    }
    return true;
  }
}
