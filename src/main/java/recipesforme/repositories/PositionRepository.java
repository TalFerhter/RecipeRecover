package recipesforme.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import recipesforme.models.Position;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PositionRepository extends CrudRepository<Position, UUID> {

    Optional<Position> findById(UUID posId);

    Iterable<Position> findAll();

    <S extends Position> Iterable<S> saveAll(Iterable<S> entities);

    void deleteAll(Iterable<? extends Position> entities);

    @Query(value = "select p from Position p where p.row = ?1 and p.col = ?2")
    Iterable<Position> findWordPositions(int row, int col);

    @Query(value = "select p from Position p where p.row = ?1 and p.col = ?2 and p.paragraph.paragraphId = ?3")
    Iterable<Position> findWordPositions(int row, int col, UUID paragraph_id);

    @Query(value = "select p from Position p where p.recipe.recipeId = ?1 and p.row = ?2 and p.col = ?3")
    Iterable<Position> findWordPositionsByRecipe(UUID recipe_id, int row, int col);

    @Query(value = "select p from Position p where p.recipe.recipeId = ?1 and p.row = ?2 and p.col = ?3 and p.paragraph.paragraphId = ?4")
    Iterable<Position> findWordPositionsByRecipe(UUID recipe_id, int row, int col, UUID paragraph_id);

    @Query(value = "select wp.word " +
            "from recipesforme.words_positions as wp " +
            "inner join recipesforme.positions as p on p.pos_id = wp.pos_id " +
            "where p.recipe_id\\:\\:text like :recipeId " +
            "order by p.row, p.col", nativeQuery = true)
    Iterable<String> findRecipeText(@Param("recipeId") String recipe_id);

    @Query (value = "SELECT AVG(row) avg_length FROM recipesforme.positions;", nativeQuery = true)
    Double averageRowLength();

    @Query (value = "SELECT AVG(col) avg_length FROM recipesforme.positions;", nativeQuery = true)
    Double averageColLength();

    @Query (value = "SELECT MAX(row) avg_length FROM recipesforme.positions;", nativeQuery = true)
    Double maxRowLength();

    @Query (value = "SELECT MAX(col) avg_length FROM recipesforme.positions;", nativeQuery = true)
    Double maxColLength();

    @Query (value = "SELECT COUNT(pos_id) FROM recipesforme.positions;", nativeQuery = true)
    Double countPositions();

}
