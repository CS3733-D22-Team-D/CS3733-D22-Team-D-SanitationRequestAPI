package edu.wpi.cs3733.D22.teamD.backend;

import java.util.function.Supplier;

public abstract class TableObject implements Supplier {
    public TableObject() {}

    public abstract String getTableInit();

    public abstract String getTableName();

    public abstract String getAttribute(int columnNumber);

    public abstract void setAttribute(int columnNumber, String newAttribute);

    public abstract Object get();
}
