package edu.wpi.cs3733.D22.teamD.request;

import edu.wpi.cs3733.D22.teamD.table.TableHandler;
import edu.wpi.cs3733.D22.teamD.table.TableObject;
import java.time.LocalDateTime;

public class SanitationRequest extends TableObject implements Request {

  // TABLEOBJECT METHODS
  @Override
  public String getTableInit() {
    return "CREATE TABLE SANITATIONREQUESTS(nodeid varchar(80) PRIMARY KEY,"
        + "priority varchar(20),"
        + "roomID varchar(60),"
        + "requesterID varchar(60),"
        + "assigneeID varchar(60),"
        + "sanitationType varchar(20),"
        + "cleanStatus varchar(20))";
  }

  @Override
  public String getTableName() {
    return "SANITATIONREQUESTS";
  }

  @Override
  public String getAttribute(int columnNumber) {

    switch (columnNumber) {
      case 1:
        return nodeID;
      case 2:
        return priority.toString();
      case 3:
        return roomID;
      case 4:
        return requesterID;
      case 5:
        return assigneeID;
      case 6:
        return sanitationType;
      case 7:
        return cleanStatus.toString();
      default:
        throw new IndexOutOfBoundsException();
    }
  }

  @Override
  public void setAttribute(int columnNumber, String newAttribute) {

    switch (columnNumber) {
      case 1:
        nodeID = newAttribute;
        break;
      case 2:
        priority = Priority.valueOf(newAttribute);
        break;
      case 3:
        roomID = newAttribute;
        break;
      case 4:
        requesterID = newAttribute;
        break;
      case 5:
        assigneeID = newAttribute;
        break;
      case 6:
        sanitationType = newAttribute;
        break;
      case 7:
        cleanStatus = RequestStatus.valueOf(newAttribute);
        break;
      default:
        throw new IndexOutOfBoundsException();
    }
  }

  @Override
  public Object get() {
    return new SanitationRequest();
  }

  @Override
  public String getRequestType() {
    return "Sanitation Request";
  }

  @Override
  public Priority getPriority() {
    return priority;
  }

  @Override
  public boolean requiresTransport() {
    return false;
  }

  // ATTRIBUTES
  @TableHandler(table = 0, col = 0)
  private String nodeID;

  @TableHandler(table = 0, col = 1)
  private Priority priority;

  @TableHandler(table = 0, col = 2)
  private String roomID;

  @TableHandler(table = 0, col = 3)
  private String requesterID;

  @TableHandler(table = 0, col = 4)
  private String assigneeID;

  @TableHandler(table = 0, col = 5)
  private String sanitationType;

  @TableHandler(table = 0, col = 6)
  private RequestStatus cleanStatus;

  // CONSTRUCTOR

  public SanitationRequest(
      Priority priority,
      String roomID,
      String requesterID,
      String assigneeID,
      String sanitationType,
      RequestStatus cleanStatus) {

    this.nodeID = priority.toString() + requesterID + LocalDateTime.now().toString();

    this.priority = priority;
    this.roomID = roomID;
    this.requesterID = requesterID;
    this.assigneeID = assigneeID;
    this.sanitationType = sanitationType;
    this.cleanStatus = cleanStatus;
  }

  public SanitationRequest() {}

  // SETTERS AND GETTERS

  public String getNodeID() {
    return nodeID;
  }

  public void setNodeID(String nodeID) {
    this.nodeID = nodeID;
  }

  public void setPriority(Priority priority) {
    this.priority = priority;
  }

  public String getRoomID() {
    return roomID;
  }

  public void setRoomID(String roomID) {
    this.roomID = roomID;
  }

  public String getRequesterID() {
    return requesterID;
  }

  public void setRequesterID(String requesterID) {
    this.requesterID = requesterID;
  }

  public String getAssigneeID() {
    return assigneeID;
  }

  public void setAssigneeID(String assigneeID) {
    this.assigneeID = assigneeID;
  }

  public String getSanitationType() {
    return sanitationType;
  }

  public void setSanitationType(String sanitationType) {
    this.sanitationType = sanitationType;
  }

  public RequestStatus getCleanStatus() {
    return cleanStatus;
  }

  public void setCleanStatus(RequestStatus cleanStatus) {
    this.cleanStatus = cleanStatus;
  }
}
