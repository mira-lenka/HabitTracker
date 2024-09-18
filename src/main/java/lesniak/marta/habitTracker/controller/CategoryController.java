package lesniak.marta.habitTracker.controller;

import lesniak.marta.habitTracker.dto.CategoryDto;
import lesniak.marta.habitTracker.dto.HabitDto;
import lesniak.marta.habitTracker.entity.Category;
import lesniak.marta.habitTracker.entity.Habit;
import lesniak.marta.habitTracker.service.CategoryService;
import lesniak.marta.habitTracker.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategoryController {

        @Autowired
        CategoryService categoryService;

        @Autowired
        CustomUserDetailsService userService;


        @GetMapping
        public String listCategories(Model model) {

                List<CategoryDto> categoryDtoList = categoryService.getCategoriesOfCurrentUser();

                model.addAttribute("categoryList", categoryDtoList);

                return "category/categories";

        }

        @GetMapping("/new")
        public String addCategory(Model model) {

        model.addAttribute("category", new Category());

        return "category/ccategory";
        }

        @PostMapping
        public String saveHabit(@ModelAttribute("category") Category category) {

                category.setUser(userService.getCurrentUser());
                categoryService.saveCategory(category);


                return "redirect:/categories";

        }

        @PostMapping("/delete/{id}")
        public String deleteHabit(@PathVariable Integer id, RedirectAttributes redirectAttributes) {

                try {
                        categoryService.deleteCategory(id);
                } catch (Exception e) {

                        redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete category");

                }

                return "redirect:/categories";
        }


        @GetMapping("/edit/{id}")
        public String showUpdateForm(@PathVariable Integer id, Model model) {

                Category category = categoryService.getCategoryById(id);

                if(category != null) {
                  model.addAttribute("category", category);
                  return "category/ucategory";
                }

                return "redirect:/categories";
        }

        @PostMapping("/update/{id}")
        public String updateCategory(@PathVariable Integer id, @ModelAttribute("category") Category category) {
                Optional<Category> existingCategoryOpt = Optional.ofNullable(categoryService.getCategoryById(id));
                if (existingCategoryOpt.isPresent()){
                        Category existingCategory = existingCategoryOpt.get();
                        existingCategory.setName(category.getName());
                        existingCategory.setColor(category.getColor());
                        categoryService.saveCategory(existingCategory);
                }

                return "redirect:/categories";
        }

}
