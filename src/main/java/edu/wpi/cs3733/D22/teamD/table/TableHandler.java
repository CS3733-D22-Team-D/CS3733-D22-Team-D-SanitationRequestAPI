package edu.wpi.cs3733.D22.teamD.table;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(MultiTable.class)
public @interface TableHandler {
    int table();

    int col();
}
