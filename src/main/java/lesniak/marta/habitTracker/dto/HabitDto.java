package lesniak.marta.habitTracker.dto;

import java.time.LocalDate;


public interface HabitDto {

    Integer getId();

    String getName();

    String getDescription();

    String getMeasure();

    String getActive();

//    String getPeriod();

    String getCategory();

    LocalDate getStartingDate();

    LocalDate getEndingDate();



}
