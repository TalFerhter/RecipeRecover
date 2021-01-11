package recipesforme.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipesforme.models.Author;
import recipesforme.models.Category;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends CrudRepository<Category, String> {

    Optional<Category> findById(String category);

    Iterable<Category> findAll();

    <S extends Category> S save(S s);

    <S extends Category> Iterable<S> saveAll(Iterable<S> entities);

}
