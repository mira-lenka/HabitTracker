package lesniak.marta.habitTracker.repository;

import lesniak.marta.habitTracker.dto.CategoryDto;
import lesniak.marta.habitTracker.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @Query("SELECT c.id AS id, c.name AS name, c.color AS color FROM Category c")
    List<CategoryDto> findAllCategories();

    @Query("SELECT c.id AS id, c.name AS name, c.color AS color FROM Category c JOIN c.user u WHERE u.id = :id")
    List<CategoryDto> findAllCategoriesByUserId(Integer id);

}
