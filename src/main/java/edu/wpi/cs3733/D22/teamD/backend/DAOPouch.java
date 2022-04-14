package edu.wpi.cs3733.D22.teamD.backend;

import edu.wpi.cs3733.D22.teamD.request.SanitationRequest;

import java.io.IOException;
import java.sql.SQLException;

public class DAOPouch {
    private static DAO<SanitationRequest> sanitationRequestDAO;

    private DAOPouch() {}

    public static void init() throws SQLException, IOException {
        sanitationRequestDAO = new DAO<>(new SanitationRequest());
    }

    public static DAO<SanitationRequest> getSanitationRequestDAO() {
        return sanitationRequestDAO;
    }
}