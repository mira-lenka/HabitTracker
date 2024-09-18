package lesniak.marta.habitTracker.startup;

import lesniak.marta.habitTracker.entity.Habit;
import lesniak.marta.habitTracker.entity.HabitRecord;
import lesniak.marta.habitTracker.repository.HabitRecordRepository;
import lesniak.marta.habitTracker.service.HabitRecordService;
import lesniak.marta.habitTracker.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class HabitStartupChecker {

    @Autowired
    private HabitRecordRepository recordRepository;

    @Autowired
    private HabitService habitService;

    @Autowired
    private HabitRecordService recordService;





}
