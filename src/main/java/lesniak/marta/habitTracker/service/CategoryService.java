package lesniak.marta.habitTracker.service;

import lesniak.marta.habitTracker.dto.CategoryDto;
import lesniak.marta.habitTracker.entity.Category;
import lesniak.marta.habitTracker.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CustomUserDetailsService userService;

    public List<CategoryDto> getAllCategories(){

        return categoryRepository.findAllCategories();

    }

    public Category saveCategory(Category category){

       return categoryRepository.save(category);

    }

    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }

    public Category getCategoryById(Integer id) {

        Optional<Category> entity = categoryRepository.findById(id);

        return entity.orElse(null);
    }

    public List<CategoryDto> getCategoriesOfCurrentUser(){

        return categoryRepository.findAllCategoriesByUserId(userService.getCurrentUserId());

    }




}
