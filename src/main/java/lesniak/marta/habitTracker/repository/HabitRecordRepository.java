package lesniak.marta.habitTracker.repository;

import lesniak.marta.habitTracker.entity.Habit;
import lesniak.marta.habitTracker.entity.HabitRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface HabitRecordRepository extends JpaRepository<HabitRecord,Integer> {

    @Query("SELECT r FROM HabitRecord r JOIN FETCH r.habit WHERE r.habit = :habit ORDER BY r.date DESC")
    List<HabitRecord> getByHabit(Habit habit);

    HabitRecord getByHabitAndDate(Habit habit, LocalDate date);

    @Query("SELECT r FROM HabitRecord r JOIN FETCH r.habit JOIN FETCH r.habit.category WHERE r.date = :date AND r.habit.active = TRUE")
    List<HabitRecord> getByDate(LocalDate date);

    @Query("SELECT r FROM HabitRecord r WHERE r.habit = :habit ORDER BY r.date DESC")
    List<HabitRecord> findLastRecordByHabit(@Param("habit") Habit habit, Pageable pageable);

    @Query("SELECT r FROM HabitRecord r JOIN FETCH r.habit JOIN FETCH r.habit.category JOIN r.habit.user " +
            "WHERE r.date = :date AND r.habit.active = TRUE AND r.habit.user.id = :id")
    List<HabitRecord> getByDateAndUserId(LocalDate date, Integer id);



}
