package lesniak.marta.habitTracker.repository;

import lesniak.marta.habitTracker.dto.HabitDto;
import lesniak.marta.habitTracker.entity.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Optional;

@Repository
public interface HabitRepository extends JpaRepository<Habit,Integer> {


    @Query("SELECT h FROM Habit h JOIN FETCH h.category JOIN h.user u WHERE u.id = :user_id")
    List<Habit> findAllWithCat(Integer user_id, Sort sort);

    @Query("SELECT h FROM Habit h JOIN FETCH h.category JOIN h.user u WHERE u.id = :user_id AND h.active = :active")
    List<Habit> findAllByActiveWithCat(Integer user_id, Boolean active, Sort sort);

    @Query("SELECT h.id AS id, h.name AS name, h.startingDate as startingDate, c.name AS category FROM Habit h JOIN h.category c JOIN h.user u WHERE h.id = :id AND u.id = :user_id")
    Optional<HabitDto> getHabitDtoById(Integer id, Integer user_id);



}


