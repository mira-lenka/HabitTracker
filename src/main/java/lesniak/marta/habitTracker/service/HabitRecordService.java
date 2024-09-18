package lesniak.marta.habitTracker.service;

import lesniak.marta.habitTracker.entity.Habit;
import lesniak.marta.habitTracker.entity.HabitRecord;
import lesniak.marta.habitTracker.repository.HabitRecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class HabitRecordService {

    @Autowired
    HabitRecordRepository habitRecordRepository;

    @Autowired
    HabitService habitService;

    @Autowired
    CustomUserDetailsService userService;

    @Scheduled(cron = "0 0 0 * * ?") //cron expression for midnight
    public void createDailyRecord() {

        List<Habit> habits = habitService.getAll();

        Calendar calendar = Calendar.getInstance();
        LocalDate date = LocalDate.now();

        for (Habit habit : habits) {
            HabitRecord record = new HabitRecord(date,habit);
            habitRecordRepository.save(record);
        }


    }

    public HabitRecord recordByHabitAndDate(Habit habit, LocalDate date) {

        return habitRecordRepository.getByHabitAndDate(habit,date);

    }

    public List<HabitRecord> recordByDate(LocalDate date) {

        List<HabitRecord> records = new ArrayList<>();

        List<Habit> habits = habitService.getAllHabits();

        for (Habit habit : habits) {

            HabitRecord record = habitRecordRepository.getByHabitAndDate(habit,date);

            records.add(record);

        }

        return records;

    }

    public HabitRecord saveRecord(HabitRecord record) {

       return habitRecordRepository.save(record);

    }

    public List<HabitRecord> recordByHabitId(Integer id){

        List<HabitRecord> records = habitRecordRepository.getByHabit(habitService.getHabitById(id));

        return records;

    }

    public List<HabitRecord> recordByHabit(Habit habit){

        return habitRecordRepository.getByHabit(habit);

    }

    public HabitRecord getLastRecordByHabit(Habit habit) {

        Pageable pageable = PageRequest.of(0, 1);  // Get only the first result
        List<HabitRecord> records = habitRecordRepository.findLastRecordByHabit(habit, pageable);

        return records.isEmpty() ? null : records.get(0);

    }

    public HabitRecord save(HabitRecord record) {

        return habitRecordRepository.save(record);
    }

    public HabitRecord getById(Integer id) {

       return habitRecordRepository.findById(id).orElse(null);
    }

    public List<HabitRecord> getByDate(LocalDate date) {

        return habitRecordRepository.getByDate(date);

    }

    public List<HabitRecord> getByDateAndCurrentUser(LocalDate date) {

        return habitRecordRepository.getByDateAndUserId(date, userService.getCurrentUserId());

    }

}
