package lesniak.marta.habitTracker.controller;

import lesniak.marta.habitTracker.dto.CategoryDto;
import lesniak.marta.habitTracker.dto.HabitDto;
import lesniak.marta.habitTracker.entity.Habit;
import lesniak.marta.habitTracker.entity.HabitRecord;
import lesniak.marta.habitTracker.service.CategoryService;
import lesniak.marta.habitTracker.service.HabitRecordService;
import lesniak.marta.habitTracker.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/habits")
public class HabitController {

    @Autowired
    HabitService habitService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    HabitRecordService habitRecordService;

    @GetMapping
    public String listHabits(@RequestParam(value = "sortField", required = false) String sortField,
                             @RequestParam(value = "sortDir", required = false) String sortDir, @RequestParam(value = "active", required = false) Optional<Boolean> active,
                             Model model) {

        List<Habit> habitList;

        if (active.isPresent()) {

         habitList = habitService.getByActiveWithCatSorted(active.get(), sortField, sortDir);

        }
        else {
         habitList = habitService.getAllHabitsSorted(sortField, sortDir);

        }
        model.addAttribute("active", active);
        model.addAttribute("habitList", habitList);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);

        model.addAttribute("reverseSortDir", sortDir == null || sortDir.equals("asc") ? "desc" : "asc");

        return "habit/habits";

    }

    @GetMapping("/details/{id}")
    public String displayHabit(@PathVariable Integer id, Model model){

        Habit habit = habitService.getHabitById(id);

        List<HabitRecord> records = habitRecordService.recordByHabit(habit);

        model.addAttribute("habit", habit);

        model.addAttribute("records", records);

        return "habit/details";

    }


    @GetMapping("/new")
    public String addHabit(Model model) {
        List<CategoryDto> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("habit", new Habit());
        return "habit/chabit";
    }

    @PostMapping
    public String saveHabit(@ModelAttribute("habit") Habit habit) {
        Calendar calendar = Calendar.getInstance();
        LocalDate date = LocalDate.now();
        habit.setStartingDate(date);
        habit.setActive(Boolean.TRUE);
        habitService.saveHabit(habit);
        return "redirect:habits";

    }

    @PostMapping("/delete/{id}")
    public String deleteHabit(@PathVariable Integer id) {
        habitService.deleteHabit(id);
        return "redirect:/habits";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {

        HabitDto habitOpt = habitService.getHabitDtoById(id);
        if (habitOpt != null) {
           // model.addAttribute("habitOpt", habitOpt);
            model.addAttribute("habit", habitService.getHabitById(id));
            List<CategoryDto> categories = categoryService.getAllCategories();
            model.addAttribute("categories",categories);


            return "habit/uhabit";

        }
        return "redirect:/habits";
    }

    @PostMapping("/update/{id}")
    public String updateHabit(@PathVariable Integer id, @ModelAttribute("habit") Habit habit) {
        Optional<Habit> existingHabitOpt = Optional.ofNullable(habitService.getHabitById(id));
        if (existingHabitOpt.isPresent()){
            Habit existingHabit = existingHabitOpt.get();
            existingHabit.setName(habit.getName());
            existingHabit.setDescription(habit.getDescription());
            existingHabit.setCategory(habit.getCategory());

            if (existingHabit.getStartingDate()!=habit.getStartingDate()){

//                LocalDate date1 = LocalDate.ofInstant(habit.getStartingDate().toInstant(), ZoneId.of("UTC"));
//                LocalDate date2 = LocalDate.ofInstant(existingHabit.getStartingDate().toInstant(), ZoneId.of("UTC"));

                LocalDate date1 = habit.getStartingDate();
                LocalDate date2 = LocalDate.now();

                addMissingHabitRecords(habit,date1,date2);

                existingHabit.setStartingDate(habit.getStartingDate());

            }
            //existingHabit.setEndingDate(habit.getEndingDate());
            existingHabit.setActive(habit.getActive());
            habitService.saveHabit(existingHabit);
        }

        return "redirect:/habits";
    }


    @Bean
    public ApplicationRunner checkAndFillMissingDates() {
        return args -> {
            // Fetch all habits
            List<Habit> habits = habitService.getAll();

            for (Habit habit : habits) {
                // Get the last record for this habit, if it exists
                HabitRecord record = habitRecordService.getLastRecordByHabit(habit);

                // Fallback to habit start date or a sensible default if no record exists
                LocalDate lastRecordDate;

                if (record != null && record.getDate() != null) {
                    // Use the date from the last record
                    lastRecordDate = record.getDate();
                } else if (habit.getStartingDate() != null) {
                    // If no record, fallback to habit start date
                    lastRecordDate = habit.getStartingDate();
                } else {
                    // If both record and starting date are null, fallback to yesterday
                    lastRecordDate = LocalDate.now().minusDays(1);
                }

                // If last recorded date is before today, fill in the missing dates
                if (lastRecordDate.isBefore(LocalDate.now())) {
                    addMissingHabitRecords(habit, lastRecordDate, LocalDate.now());
                }
            }
        };
    }

    public void addMissingHabitRecords(Habit habit, LocalDate startDate, LocalDate endDate) {

        long dateDiff = ChronoUnit.DAYS.between(startDate, endDate);

        // Start from the next day of the last recorded date to avoid duplicates
        for (int i = 1; i <= dateDiff; i++) {
            LocalDate date = startDate.plusDays(i);
            HabitRecord record = new HabitRecord(date, habit);
            habitRecordService.save(record);
        }
    }



}
