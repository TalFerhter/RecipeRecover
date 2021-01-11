package recipesforme.repositories;

import org.hibernate.annotations.Type;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import recipesforme.models.Recipe;
import recipesforme.models.Word;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordRepository extends CrudRepository<Word, String> {

    Optional<Word> findById(String word);

    Iterable<Word> findAll();

    <S extends Word> Iterable<S> saveAll(Iterable<S> entities);

    void deleteAll(Iterable<? extends Word> entities);

    @Query(value = "select w from Word w where size(w.groups) = 0 ")
    Iterable<Word> findAllWithoutGroup();

    @Query(value = "SELECT word " +
            "FROM recipesforme.groups_words " +
            "WHERE group_name = (?1)", nativeQuery = true)
    Iterable<Word> findAllWithGroup(String groupName);

    @Query(value = "select w from Word w where w.word in (?1) ")
    Iterable<Word> findAll(List<String> searchWords);

    @Query(value = "SELECT Cast(recipe_id as varchar) recipe_id, recipe_name, author_name, path, " +
            "date, cook_time, prep_time, total_time, yield_min, yield_max, level_name, category " +
            "FROM recipesforme.recipes " +
            "INNER JOIN recipesforme.authors ON (authors.author_id = recipes.author_id) " +
            "INNER JOIN recipesforme.levels ON (levels.level_id = recipes.level_id) " +
            "WHERE recipes.recipe_name like '%' || (:name) || '%' " +
            "AND recipes.recipe_id IN (" +
                "SELECT positions.recipe_id " +
                "FROM recipesforme.words_positions " +
                "INNER JOIN recipesforme.positions ON (words_positions.pos_id = positions.pos_id)" +
                "WHERE words_positions.word IN (:wordList) " +
                ")"
            , nativeQuery = true)
    List<Recipe> findAllWithRecipe(@Param("wordList") List<String> words,@Param("name") String recipeName);

    @Query(value = "SELECT Cast(recipe_id as varchar) recipe_id, recipe_name, author_name, path, " +
            "date, cook_time, prep_time, total_time, yield_min, yield_max, level_name, category " +
            "FROM recipesforme.recipes " +
            "INNER JOIN recipesforme.authors ON (authors.author_id = recipes.author_id) " +
            "INNER JOIN recipesforme.levels ON (levels.level_id = recipes.level_id) " +
            "WHERE recipes.recipe_id IN (" +
                "SELECT positions.recipe_id " +
                "FROM recipesforme.words_positions " +
                "INNER JOIN recipesforme.positions ON (words_positions.pos_id = positions.pos_id)" +
                "WHERE words_positions.word IN (:wordList) " +
                ")"
            , nativeQuery = true)
    List<Recipe> findAllRecipes(@Param("wordList")List<String> words);
}
