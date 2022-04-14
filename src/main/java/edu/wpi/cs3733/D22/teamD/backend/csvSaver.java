package edu.wpi.cs3733.D22.teamD.backend;

import com.opencsv.CSVWriter;
import edu.wpi.cs3733.D22.teamD.table.TableObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class csvSaver {
  private csvSaver() {}

  public static void saveAll() {
    csvLoader.filenames.forEach(
        (k, v) -> {
          try {
            save(v, "src/main/resources/" + k + ".csv");
          } catch (IOException e) {
            e.printStackTrace();
          } catch (SQLException e) {
            e.printStackTrace();
          }
        });
  }

  public static void save(TableObject type, String filename) throws SQLException, IOException {
    File file = new File(filename);
    FileWriter outputFile = new FileWriter(file);
    CSVWriter writer =
        new CSVWriter(outputFile, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);

    String tableName = type.getTableName();
    String query = "SELECT * FROM " + tableName;

    Statement stmt = ConnectionHandler.getConnection().createStatement();
    ResultSet resultSet = stmt.executeQuery(query);
    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
    int numAttributes = resultSetMetaData.getColumnCount();
    String[] save_tableObject = new String[numAttributes];

    while (resultSet.next()) {
      for (int i = 1; i <= numAttributes; i++) save_tableObject[i - 1] = resultSet.getString(i);
      writer.writeNext(save_tableObject);
    }

    stmt.close();
    writer.close();
  }
}
