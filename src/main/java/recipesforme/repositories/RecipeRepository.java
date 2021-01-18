package recipesforme.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import recipesforme.models.Author;
import recipesforme.models.Recipe;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;
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

    @Query(value = "select * from recipesforme.recipes " +
            "where ((:name) is null or " +
            "recipes.recipe_name like '%' || (:name) || '%')", nativeQuery = true)
    List<Recipe> findByPartOfRecipeName(@Param("name") String name);

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

    @Query(value = "SELECT recipes.recipe_name, authors.author_name, " +
            "recipes.path, recipes.date, recipes.cook_time, recipes.prep_time, " +
            "recipes.total_time, recipes.yield_min, recipes.site_name, " +
            "recipes.yield_max, levels.level_name, recipes.category " +
            "FROM recipesforme.recipes " +
            "INNER JOIN recipesforme.authors ON recipes.author_id = authors.author_id " +
            "INNER JOIN recipesforme.levels ON recipes.level_id = levels.level_id " +
            "WHERE (cast(:recipeName as text) IS null OR recipes.recipe_name like '%' || cast(:recipeName as text) || '%') " +
            "AND recipes.recipe_id IN ( SELECT positions.recipe_id " +
                                        "FROM recipesforme.words_positions " +
                                        "INNER JOIN recipesforme.positions ON (words_positions.pos_id = positions.pos_id) " +
                                        "WHERE words_positions.word IN (:wordList)) " +
            "AND (cast(:siteName as text) IS null OR recipes.site_name = cast(:siteName as text)) " +
            "AND (cast(:authorName as text) IS null OR authors.author_name = cast(:authorName as text)) " +
            "AND (cast(:path as text) IS null OR recipes.path = cast(:path as text)) " +
            "AND ((:yieldMin) IS null OR recipes.yield_min = cast(cast(:yieldMin as text) as integer)) " +
            "AND ((:yieldMax) IS null OR recipes.yield_max = cast(cast(:yieldMax as text) as integer)) " +
            "AND (cast(:category as text) IS null OR recipes.category = cast(:category as text)) " +
            "AND (cast(:level as text) IS null OR levels.level_name = cast(:level as text)) ",
            nativeQuery = true)
    List<Object> findByDetails(@Param("recipeName") String recipeName,
                               @Param("siteName") String siteName,
                               @Param("authorName") String authorName,
                               @Param("path") String path,
                               @Param("yieldMin") Integer yieldMin,
                               @Param("yieldMax") Integer yieldMax,
                               @Param("category") String category,
                               @Param("level") String level,
                               @Param("wordList") List<String> words);

    @Query(value = "SELECT recipes.recipe_name, authors.author_name, " +
            "recipes.path, recipes.date, recipes.cook_time, recipes.prep_time, " +
            "recipes.total_time, recipes.yield_min, recipes.site_name, " +
            "recipes.yield_max, levels.level_name, recipes.category " +
            "FROM recipesforme.recipes " +
            "INNER JOIN recipesforme.authors ON recipes.author_id = authors.author_id " +
            "INNER JOIN recipesforme.levels ON recipes.level_id = levels.level_id " +
            "WHERE (cast(:recipeName as text) IS null OR recipes.recipe_name like '%' || cast(:recipeName as text) || '%') " +
            "AND (cast(:siteName as text) IS null OR recipes.site_name = cast(:siteName as text)) " +
            "AND (cast(:authorName as text) IS null OR authors.author_name = cast(:authorName as text)) " +
            "AND (cast(:path as text) IS null OR recipes.path = cast(:path as text)) " +
            "AND ((:yieldMin) IS null OR recipes.yield_min = cast(cast(:yieldMin as text) as integer)) " +
            "AND ((:yieldMax) IS null OR recipes.yield_max = cast(cast(:yieldMax as text) as integer)) " +
            "AND (cast(:category as text) IS null OR recipes.category = cast(:category as text)) " +
            "AND (cast(:level as text) IS null OR levels.level_name = cast(:level as text)) ",
            nativeQuery = true)
    List<Object> findByDetailsWithoutWords(@Param("recipeName") String recipeName,
                                           @Param("siteName") String siteName,
                                           @Param("authorName") String authorName,
                                           @Param("path") String path,
                                           @Param("yieldMin") Integer yieldMin,
                                           @Param("yieldMax") Integer yieldMax,
                                           @Param("category") String category,
                                           @Param("level") String level);

    @Query (value = "SELECT COUNT(recipe_id) FROM recipesforme.recipes;", nativeQuery = true)
    Double countRecipes();

}
