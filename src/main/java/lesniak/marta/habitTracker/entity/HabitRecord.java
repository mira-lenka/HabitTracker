package lesniak.marta.habitTracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class HabitRecord {

    @Id
    @GeneratedValue
    private Integer id;

    private Boolean completed;

    private Double measure;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "habit_id")
    private Habit habit;

    public HabitRecord(LocalDate date, Habit habit) {
        this.habit = habit;
        this.date = date;
        this.completed = Boolean.FALSE;

    }

}
