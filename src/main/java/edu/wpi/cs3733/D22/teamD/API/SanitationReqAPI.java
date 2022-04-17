package edu.wpi.cs3733.D22.teamD.API;

import edu.wpi.cs3733.D22.teamD.backend.DAO;
import edu.wpi.cs3733.D22.teamD.backend.DAOPouch;
import edu.wpi.cs3733.D22.teamD.request.SanitationRequest;

import java.sql.SQLException;
import java.util.List;

/**
 * Allows for API users to access sanitation requests from the database
 */
public class SanitationReqAPI {

    private DAO<SanitationRequest> sanitationDAO;

    public SanitationReqAPI() {
        try {
            DAOPouch.init();
        } catch (Exception e) {
            System.err.println("Unable to Init DAO");
            e.printStackTrace();
        }
        this.sanitationDAO = DAOPouch.getSanitationRequestDAO();
    }

    /**
     * get a list of all Sanitation Requests in the Database
     * @return a list of all sanitation requests
     */
    public List<SanitationRequest> getAllRequests() {
        try {
            return this.sanitationDAO.getAll();
        } catch (SQLException e) {
            System.err.println("Database Error");
            throw new RuntimeException(e);
        }
    }
    public void deleteRequest(SanitationRequest request) {
        try {
            this.sanitationDAO.delete(request);
        } catch (SQLException e) {
            System.err.println("Database Error");
            throw new RuntimeException(e);
        }
    }

}
