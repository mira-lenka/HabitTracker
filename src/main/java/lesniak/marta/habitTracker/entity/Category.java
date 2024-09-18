package lesniak.marta.habitTracker.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@ToString(exclude = "habits")
public class Category {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String color;

    @OneToMany(mappedBy = "category")
    private List<Habit> habits;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Category(String name, String color) {
        this.name = name;
        this.color = color;
    }
}
