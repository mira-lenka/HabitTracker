package lesniak.marta.habitTracker;



import lesniak.marta.habitTracker.entity.Habit;
import lesniak.marta.habitTracker.repository.CategoryRepository;
import lesniak.marta.habitTracker.repository.HabitRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import lesniak.marta.habitTracker.entity.Category;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SpringBootApplication
@EnableScheduling
public class HabitTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HabitTrackerApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(
//			CategoryRepository categoryRepository,
//			HabitRepository habitRepository
//	) {
//		return args -> {
//
//
//			Calendar calendar = Calendar.getInstance();
//
//			var category1 = Category.builder()
//					.id(1)
//					.name("sport")
//                    .color("#25f844")
//					.build();
//
//			var category2 = Category.builder()
//					.id(2)
//					.name("Spanish")
//                    .color("#e35a44")
//					.build();
//
//            var category3 = Category.builder()
//					.id(3)
//                    .name("IT")
//					.color("#31dbab")
//					.build();
//
//			calendar.set(2024, Calendar.AUGUST, 1, 0, 0, 0);
//			calendar.set(Calendar.MILLISECOND, 0);
//
//			var habit1 = Habit.builder()
//					.id(1)
//					.name("gym")
//					.startingDate(new Date(calendar.getTimeInMillis()))
//					.category(category1)
//					.build();
//
//
//			categoryRepository.save(category1);
//			categoryRepository.save(category2);
//			categoryRepository.save(category3);
//			habitRepository.save(habit1);
//		};
//
//	}

}
