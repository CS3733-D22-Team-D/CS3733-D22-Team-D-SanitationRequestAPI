package edu.wpi.cs3733.D22.teamD.request;

public interface IRequest {

  enum RequestStatus {
    REQUESTED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
  }

  enum Priority {
    LOW,
    MEDIUM,
    HIGH
  }

  /** @return name of the IRequest class */
  public String getRequestType();

  /** @return the priority of a given request */
  public Priority getPriority();

  /** @return whether or not this is a request that requires things to be moved */
  public boolean requiresTransport();

  /** @return roomID of a given request */
  public String getRoomID();
}
