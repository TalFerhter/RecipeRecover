package recipesforme.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import recipesforme.models.Author;
import recipesforme.models.Recipe;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, UUID> {

    Optional<Recipe> findById(UUID recipeId);

    Iterable<Recipe> findAll();

    @Override
    Iterable<Recipe> findAllById(Iterable<UUID> UUIDs);

    @Override
    <S extends Recipe> S save(S entity);

    @Override
    void delete(Recipe entity);

    @Query(value = "select r from Recipe r where r.recipeName = ?1")
    Optional<Recipe> findByRecipeName(String name);

    @Query(value = "select r from Recipe r where r.author.authorId = ?1")
    Iterable<Recipe> findByAuthor(String author);

    @Query(value = "select r from Recipe r where r.siteName = ?1")
    Iterable<Recipe> findBySite(String site);

    @Query("select r from Recipe r where r.cookTime <= :time")
    Iterable<Recipe> findAllWithLessCookTime(@Param("time") Time time);

    @Query("select r from Recipe r where r.cookTime = :time")
    Iterable<Recipe> findAllWithCookTime(@Param("time") Time time);

    @Query("select r from Recipe r where r.cookTime >= :time")
    Iterable<Recipe> findAllWithMoreCookTime(@Param("time") Time time);

    @Query("select r from Recipe r where r.prepTime <= :time")
    Iterable<Recipe> findAllWithLessPrepTime(@Param("time") Time time);

    @Query("select r from Recipe r where r.prepTime = :time")
    Iterable<Recipe> findAllWithPrepTime(@Param("time") Time time);

    @Query("select r from Recipe r where r.prepTime >= :time")
    Iterable<Recipe> findAllWithMorePrepTime(@Param("time") Time time);

    @Query("select r from Recipe r where r.totalTime <= :time")
    Iterable<Recipe> findAllWithLessTotalTime(@Param("time") Time time);

    @Query("select r from Recipe r where r.totalTime = :time")
    Iterable<Recipe> findAllWithTotalTime(@Param("time") Time time);

    @Query("select r from Recipe r where r.totalTime >= :time")
    Iterable<Recipe> findAllWithMoreTotalTime(@Param("time") Time time);

    @Query("select r from Recipe r where r.yieldMin <= :yield")
    Iterable<Recipe> findAllWithLessYield(@Param("yield") int yield);

    @Query("select r from Recipe r where r.yieldMin = :yield")
    Iterable<Recipe> findAllWithYield(@Param("yield") int yield);

    @Query("select r from Recipe r where r.yieldMax >= :yield or r.yieldMin >= :yield")
    Iterable<Recipe> findAllWithMoreYield(@Param("yield") int yield);

    @Query("select r from Recipe r where r.yieldMax >= :min_yield and r.yieldMin <= :max_yield")
    Iterable<Recipe> findAllInRangeYield(@Param("min_yield") int min_yield, @Param("max_yield") int max_yield);

    @Query("select r from Recipe r where r.date <= :date")
    Iterable<Recipe> findAllWithCreationDateBefore(@Param("date") Date date);

    @Query("select r from Recipe r where r.date = :date")
    Iterable<Recipe> findAllWithCreationAtDate(@Param("date") Date date);

    @Query("select r from Recipe r where r.date >= :date")
    Iterable<Recipe> findAllWithCreationDateAfter(@Param("date") Date date);
}
