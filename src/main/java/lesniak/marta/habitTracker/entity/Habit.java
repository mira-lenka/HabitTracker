package lesniak.marta.habitTracker.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@ToString(exclude = "category")
public class Habit {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private String name;

    private String description;

//    private Measure measure;

    private Boolean active;

    @DateTimeFormat(pattern = "yyyy-MM-dd") // Specify your date format
    private LocalDate startingDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd") // Specify your date format
    private LocalDate endingDate;


    @ManyToOne
    @JoinColumn(name="category_id")
    Category category;

}
