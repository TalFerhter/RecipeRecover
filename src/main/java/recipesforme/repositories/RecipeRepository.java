package recipesforme.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipesforme.models.Author;
import recipesforme.models.Recipe;

import java.util.Optional;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Integer> {

    Optional<Recipe> findById(int recipeId);

    Iterable<Recipe> findAll();
}
