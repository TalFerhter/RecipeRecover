package recipesforme.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipesforme.models.Author;
import recipesforme.models.Rating;

import java.util.Optional;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Integer> {

    Optional<Rating> findById(int ratingId);

    Iterable<Rating> findAll();
}
