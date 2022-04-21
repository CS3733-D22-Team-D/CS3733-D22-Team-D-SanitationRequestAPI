package edu.wpi.cs3733.D22.teamD.table;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(MultiTab.class)
public @interface TableHandle {
  int table();

  int col();
}
