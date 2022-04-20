package edu.wpi.cs3733.D22.teamD.backend;

import edu.wpi.cs3733.D22.teamD.table.TableObject;
import java.sql.*;

public class KeyChecker {
  private KeyChecker() {}

  public static boolean validID(TableObject type, String pk) throws SQLException {
    String tableName = type.getTableName();

    String query = "SELECT * FROM " + tableName;
    Statement stmt = ConnectionHelper.getConnection().createStatement();
    ResultSet resultSet = stmt.executeQuery(query);
    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
    String columnName = resultSetMetaData.getColumnName(1);
    stmt.close();

    query = "SELECT count(*) AS num FROM " + tableName + " WHERE " + columnName + " = ?";
    PreparedStatement prepStmt = ConnectionHelper.getConnection().prepareStatement(query);
    prepStmt.setString(1, pk);
    ResultSet rset = prepStmt.executeQuery();
    rset.next();
    boolean isValid = 1 == rset.getInt("num");
    prepStmt.close();
    return isValid;
  }
}
