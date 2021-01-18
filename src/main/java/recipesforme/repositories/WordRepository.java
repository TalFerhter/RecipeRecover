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

    @Query(value = "WITH recipe_details AS " +
                        "(SELECT r.recipe_id, r.category, r.recipe_name " +
                            "FROM recipesforme.recipes AS r " +
                            "WHERE r.recipe_name=(:recipe)), " +
                        "recipe_positions AS " +
                        "(SELECT rp.pos_id, rd.category, rd.recipe_name " +
                        "FROM recipesforme.positions AS rp " +
                        "INNER JOIN recipe_details AS rd ON rp.recipe_id = rd.recipe_id), " +
                        "recipe_words AS " +
                        "(SELECT DISTINCT wp.word, rp.category, rp.recipe_name " +
                        "FROM recipesforme.words_positions AS wp " +
                        "INNER JOIN recipe_positions AS rp ON wp.pos_id = rp.pos_id) " +
                    "SELECT group_name, count(gw.word), rw.category, rw.recipe_name " +
                    "FROM recipesforme.groups_words AS gw " +
                    "INNER JOIN recipe_words as rw ON gw.word = rw.word " +
                    "GROUP BY group_name, rw.category, rw.recipe_name ",
            nativeQuery = true)
    List<Object[]> countWordsInGroupsOfRecipe(@Param("recipe")String recipe);

    @Query (value = "SELECT AVG(CHAR_LENGTH(word)) avg_length from recipesforme.words;", nativeQuery = true)
    Double averageWordLength();

    @Query (value = "SELECT MAX(CHAR_LENGTH(word)) max_length from recipesforme.words;", nativeQuery = true)
    Double maxWordLength();

    @Query (value = "SELECT COUNT(word) countWords from recipesforme.words;", nativeQuery = true)
    Double countWords();
}
