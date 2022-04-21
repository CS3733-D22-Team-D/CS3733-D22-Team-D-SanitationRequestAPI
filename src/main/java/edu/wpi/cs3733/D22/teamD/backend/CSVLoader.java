package edu.wpi.cs3733.D22.teamD.backend;

import com.opencsv.CSVReader;
import edu.wpi.cs3733.D22.teamD.entities.EmployeeObj;
import edu.wpi.cs3733.D22.teamD.entities.LocationObj;
import edu.wpi.cs3733.D22.teamD.request.SanitationIRequest;
import edu.wpi.cs3733.D22.teamD.table.TableObj;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CSVLoader {

  static HashMap<String, TableObj> filenames = new HashMap<>();

  static {
    filenames.put("SanitationIRequest", new SanitationIRequest());
    filenames.put("TowerLocations", new LocationObj());
    filenames.put("EmployeeObj", new EmployeeObj());
  }

  private CSVLoader() {}

  public static void loadAll() throws SQLException {
    Statement stmt = ConnectionHelper.getConnection().createStatement();
    filenames.forEach(
        (k, v) -> {
          //          System.out.println("Currently on " + v.getTableName());
          try {
            try {
              stmt.execute(v.getTableInit());
            } catch (SQLException e) {
              //              System.out.printf("%s table already created\n", v.getTableName());
            }
            load(v, k + ".csv");
          } catch (IOException e) {
            e.printStackTrace();
          } catch (SQLException e) {
            e.printStackTrace();
          }
        });
    stmt.close();
  }

  public static void load(TableObj type, String filename) throws IOException, SQLException {
    InputStreamReader f =
        new InputStreamReader(
            Objects.requireNonNull(CSVLoader.class.getClassLoader().getResourceAsStream(filename)));
    CSVReader read = new CSVReader(f);
    List<String[]> entries = read.readAll();
    if (entries.size() < 1) return;
    entries.remove(0);
    String tableName = type.getTableName();
    String query = "SELECT * FROM " + tableName;

    Statement stmt = ConnectionHelper.getConnection().createStatement();
    ResultSet resultSet = stmt.executeQuery(query);
    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
    int numAttributes = resultSetMetaData.getColumnCount();

    String updateStatement = "INSERT INTO " + tableName + " VALUES(";
    String drop = "DELETE FROM " + tableName + " WHERE ";
    drop += resultSetMetaData.getColumnLabel(1);
    drop += " = ?";
    for (int i = 1; i < numAttributes; i++) {
      updateStatement += "?,";
    }
    updateStatement += "?)";
    PreparedStatement prepStmt = ConnectionHelper.getConnection().prepareStatement(updateStatement);
    PreparedStatement dropStmt = ConnectionHelper.getConnection().prepareStatement(drop);
    for (String[] line : entries) {
      if (KeyCheck.validID(type, line[0])) {
        dropStmt.setString(1, line[0]);
        dropStmt.executeUpdate();
      }
      for (int i = 1; i <= numAttributes; i++) {
        prepStmt.setString(i, line[i - 1]);
      }
      prepStmt.executeUpdate();
    }

    prepStmt.close();
  }
}
