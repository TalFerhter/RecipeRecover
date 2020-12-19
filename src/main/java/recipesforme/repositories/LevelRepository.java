package recipesforme.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipesforme.models.Level;

import java.util.Optional;

@Repository
public interface LevelRepository extends CrudRepository<Level, Integer> {

    Optional<Level> findById(int levelId);

    Iterable<Level> findAll();

    @Override
    <S extends Level> S save(S entity);
}
