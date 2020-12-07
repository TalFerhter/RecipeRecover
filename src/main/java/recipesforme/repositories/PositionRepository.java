package recipesforme.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipesforme.models.Position;
import recipesforme.models.Word;

import java.util.Optional;

@Repository
public interface PositionRepository extends CrudRepository<Position, Integer> {

    Optional<Position> findById(int posId);

    Iterable<Position> findAll();
}
