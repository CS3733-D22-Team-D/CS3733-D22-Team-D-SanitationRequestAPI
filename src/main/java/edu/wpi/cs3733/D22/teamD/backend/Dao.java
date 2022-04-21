package edu.wpi.cs3733.D22.teamD.backend;

import edu.wpi.cs3733.D22.teamD.table.TableObj;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Dao<T extends TableObj> {
  Orm<T> orm;
  TableObj type;

  /**
   * Creates a basic Dao which connects to the database
   *
   * @param type : The table / TableObj you wish to connect to and access
   * @throws SQLException
   * @throws IOException
   */
  public Dao(T type) throws SQLException, IOException {
    orm = new Orm<T>(type);
    this.type = type;
  }

  /**
   * Creates a basic Dao and populates / updates it using the filename given
   *
   * @param type : Table / TableObj you wish to use and update
   * @param filename : Filename you wish to grab your data from
   * @throws SQLException
   * @throws IOException
   */
  public Dao(T type, String filename) throws SQLException, IOException {
    orm = new Orm<T>(type, filename);
    this.type = type;
  }

  /**
   * Returns a list of everything in the table
   *
   * @return : a List of TableObjects for the type you set up the Dao with
   * @throws SQLException
   */
  public List<T> getAll() throws SQLException {
    return orm.getAll();
  }

  /**
   * Gets a singular instance of the TableObj using its primaryKey
   *
   * @param primaryKey : the nodeID / ID String you wanted to find
   * @return
   * @throws SQLException
   */
  public T get(String primaryKey) throws SQLException {
    return orm.get(primaryKey);
  }

  /**
   * Updates the current TableObj you send in into the database
   *
   * @param type : the object you wish to update
   * @throws SQLException
   */
  public void update(T type) throws SQLException {
    orm.update(type);
  }

  /**
   * Deletes the occurrence for the TableObj you send in
   *
   * @param type : the TableObj you wish to delete
   * @throws SQLException
   */
  public void delete(T type) throws SQLException {
    orm.delete(type.getAttribute(1));
  }

  /**
   * Adds the TableObj type into the database
   *
   * @param type
   * @throws SQLException
   */
  public void add(T type) throws SQLException {
    orm.add(type);
  }

  /**
   * Saves the current TableObj's table into the CSV file you input
   *
   * @param filename : save file name
   */
  public void save(String filename) {
    try {
      CSVSaver.save(type, filename);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Loads the information from the inputted file csv into the database
   *
   * @param filename : filename you wish to import
   */
  public void load(String filename) {
    try {
      CSVLoader.load(type, filename);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Sorts the input list of objects you give, will add all occurences of the attribute occuring in
   * the given column to the return List
   *
   * @param list : inputted dataset to sort from
   * @param column : column you wish to search
   * @param attribute : the attribute / key / ID you wish to select
   * @return : A List of all TableObjects with the occurring attribute for the column given
   */
  public List<T> filter(List<T> list, int column, String attribute) {
    List<T> ret = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getAttribute(column).equals(attribute)) {
        ret.add(list.get(i));
      }
    }
    return ret;
  }

  /**
   * Sorts the entire database and returns a List of TableObjects in which the attribute occurs in
   * the specific column you are looking in Heavy emphasis on ENTIRE DATABASE
   *
   * @param column : The column you wish to sort
   * @param attribute : the key / attribute you are searching for
   * @return : a List of all TableObjects in which the attribute occurs
   * @throws SQLException
   */
  public List<T> filter(int column, String attribute) throws SQLException {
    return filter(orm.getAll(), column, attribute);
  }

  public List<T> search(List<T> list, int column, String attribute) {
    List<T> ret = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getAttribute(column).contains(attribute)) {
        ret.add(list.get(i));
      }
    }
    return ret;
  }

  /**
   * Sorts the entire database and returns a List of TableObjects in which the attribute occurs in
   * the specific column you are looking in Heavy emphasis on ENTIRE DATABASE
   *
   * @param column : The column you wish to sort
   * @param attribute : the key / attribute you are searching for
   * @return : a List of all TableObjects in which the attribute occurs
   * @throws SQLException
   */
  public List<T> search(int column, String attribute) throws SQLException {
    return search(orm.getAll(), column, attribute);
  }
}
