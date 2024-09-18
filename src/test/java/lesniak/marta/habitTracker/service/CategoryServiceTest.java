package lesniak.marta.habitTracker.service;

import lesniak.marta.habitTracker.dto.CategoryDto;
import lesniak.marta.habitTracker.entity.Category;
import lesniak.marta.habitTracker.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    //which service we want to test
    @InjectMocks
    private CategoryService service;

    //declare the dependencies;
    @Mock
    private CategoryRepository repository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSaveCategory() {

        Category cat = new Category("IT", "#056473");

        //mock the calls
        when(repository.save(cat)).thenReturn(cat);

        Category category = service.saveCategory(cat);

        assertEquals(cat.getName(),category.getName());
        assertEquals(cat.getColor(),category.getColor());

        verify(repository,times(1)).save(cat);
    }

//    @Test
//    public void shouldReturnAllCategories() {
//        //Given
//        List<Category> list = new ArrayList<>();
//
//        list.add(new Category("IT", "#056473"));
//
//        when(repository.findAllCategories()).thenReturn(list);
//
//        List<CategoryDto> categories = service.getAllCategories();
//
//        assertEquals(list.size(),categories.size());
//    }

    @Test
    public void shouldReturnCategoryById() {

        Integer id = 1;
        Category cat = new Category("IT", "#056473");

        when(repository.findById(id)).thenReturn(Optional.of(cat));

        Category category = service.getCategoryById(id);

        assertEquals(cat.getName(),category.getName());

    }
}