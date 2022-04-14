package edu.wpi.cs3733.D22.teamD.backend;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(MultiTable.class)
public @interface TableHandler {
    int table();

    int col();
}
