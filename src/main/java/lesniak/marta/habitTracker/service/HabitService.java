package lesniak.marta.habitTracker.service;

import lesniak.marta.habitTracker.dto.CategoryDto;
import lesniak.marta.habitTracker.dto.HabitDto;
import lesniak.marta.habitTracker.entity.Habit;
import lesniak.marta.habitTracker.repository.HabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabitService {

    @Autowired
    HabitRepository habitRepository;

    @Autowired
    CustomUserDetailsService userDetailsService;



    public List<Habit> getAllHabits() {

        return habitRepository.findAllWithCat(userDetailsService.getCurrentUserId(),null);

    }

    public List<Habit> getAllHabitsSorted(String sortField, String sortDir) {

        Sort sort = Sort.by(Sort.Direction.ASC, sortField != null ? sortField : "name");

        if ("desc".equalsIgnoreCase(sortDir)){
            sort = sort.descending();
        }
        return habitRepository.findAllWithCat(userDetailsService.getCurrentUserId(), sort);

    }

    public List<Habit> getByActiveWithCatSorted(Boolean active, String sortField, String sortDir){

        Sort sort = Sort.by(Sort.Direction.ASC, sortField != null ? sortField : "name");

        if ("desc".equalsIgnoreCase(sortDir)){
            sort = sort.descending();
        }
        return habitRepository.findAllByActiveWithCat(userDetailsService.getCurrentUserId(), active, sort);

    }

    public List<Habit> getAll() {

        return habitRepository.findAll();
    }

    public List<Habit> getAllSorted(String sortField, String sortDir) {

        Sort sort = Sort.by(Sort.Direction.ASC, sortField != null ? sortField : "name");

        if ("desc".equalsIgnoreCase(sortDir)){
            sort = sort.descending();
        }

        return habitRepository.findAll(sort);
    }

    public Habit saveHabit(Habit habit) {

        habit.setUser(userDetailsService.getCurrentUser());

        return habitRepository.save(habit);
    }

    public void deleteHabit(Integer id){
        habitRepository.deleteById(id);
    }

    public HabitDto getHabitDtoById(Integer id) {
        Optional<HabitDto> entity = habitRepository.getHabitDtoById(id, userDetailsService.getCurrentUserId());
        return entity.orElse(null);
    }

    public Habit getHabitById(Integer id) {
        Optional<Habit> entity = habitRepository.findById(id);
        return entity.orElse(null);
    }





}
