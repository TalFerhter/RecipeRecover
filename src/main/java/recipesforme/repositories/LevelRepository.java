package recipesforme.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import recipesforme.models.Level;
import recipesforme.models.Paragraph;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LevelRepository extends CrudRepository<Level, UUID> {

    Optional<Level> findById(UUID levelId);

    @Query(value = "select l from Level l where l.levelName = ?1")
    Optional<Level> findByLevelName(String levelName);

    Iterable<Level> findAll();

    <S extends Level> S save(S entity);

    <S extends Level> Iterable<S> saveAll(Iterable<S> entities);

}
