package recipesforme.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipesforme.models.Position;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PositionRepository extends CrudRepository<Position, UUID> {

    Optional<Position> findById(UUID posId);

    Iterable<Position> findAll();

    <S extends Position> Iterable<S> saveAll(Iterable<S> entities);

    @Query(value = "select p from Position p where (p.pos_id = ?1) and (p.recipe.recipeId = ?2) and (p.row = ?3)")
    Iterable<Position> findRow(UUID posId, UUID recipeId, int row);

    @Query(value = "select p from Position p where p.row = ?1 and p.col = ?2")
    Iterable<Position> findWordPositions(int row, int col);

    @Query(value = "select p from Position p where p.row = ?1 and p.col = ?2 and p.paragraph.paragraphId = ?3")
    Iterable<Position> findWordPositions(int row, int col, UUID paragraph_id);

    @Query(value = "select p from Position p where p.recipe.recipeId = ?1 and p.row = ?2 and p.col = ?3")
    Iterable<Position> findWordPositionsByRecipe(UUID recipe_id, int row, int col);

    @Query(value = "select p from Position p where p.recipe.recipeId = ?1 and p.row = ?2 and p.col = ?3 and p.paragraph.paragraphId = ?4")
    Iterable<Position> findWordPositionsByRecipe(UUID recipe_id, int row, int col, UUID paragraph_id);

}
