package lesniak.marta.habitTracker.controller;

import lesniak.marta.habitTracker.entity.Habit;
import lesniak.marta.habitTracker.entity.HabitRecord;
import lesniak.marta.habitTracker.service.HabitRecordService;
import lesniak.marta.habitTracker.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/records")
public class HabitRecordController {

    @Autowired
    HabitRecordService habitRecordService;

    @Autowired
    HabitService habitService;

    @PostMapping("/new/{id}")
    public String addRecord(@PathVariable Integer id){


        HabitRecord record = habitRecordService.getById(id);
        record.setCompleted(!record.getCompleted());

        habitRecordService.save(record);

        return "redirect:/records/dailyrecords";
    }

    @GetMapping("/{id}")
    public String recordByHabit(@PathVariable Integer id, Model model) {

        List<HabitRecord> records = habitRecordService.recordByHabitId(id);

        model.addAttribute("records",records);
        model.addAttribute("habit",habitService.getHabitById(id));

        return "habit/records";

    }

    @PostMapping("/change/{id}")
    public String editRecord(@PathVariable Integer id, @RequestParam("habit_id") Integer habit_id) {

        HabitRecord record = habitRecordService.getById(id);

        record.setCompleted(!record.getCompleted());

        habitRecordService.save(record);

        return "redirect:/records/" + habit_id;

    }

    @GetMapping("/dailyrecords")
    public String checklistHabits(Model model) {

        List<HabitRecord> habitRecordList = habitRecordService.getByDateAndCurrentUser(LocalDate.now());

        model.addAttribute("recordList", habitRecordList);


        return "dailyrecords";

    }

}
