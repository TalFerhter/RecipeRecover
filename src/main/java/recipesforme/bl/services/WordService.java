package recipesforme.bl.services;

import net.minidev.json.JSONArray;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import recipesforme.models.Recipe;
import recipesforme.models.Word;
import recipesforme.repositories.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class WordService {

    @Autowired
    private WordRepository repository;

    public List<Word> findAll() {
        var words = (List<Word>) repository.findAll();
        return words;
    }

    public List<Word> findAll(List<String> searchWords) {
        var words = (List<Word>) repository.findAll(searchWords);
        return words;
    }

    public List<Word> findAllWithoutGroup() {
        var words = (List<Word>) repository.findAllWithoutGroup();
        return words;
    }

    public List<Word> findAllWithGroup(String groupName) {
        var words = (List<Word>) repository.findAllWithGroup(groupName);
        return words;
    }

    public <S extends Word> Iterable<S> saveAll(Iterable<S> entities) {
        return repository.saveAll(entities);
    }

    public Optional<Word> findById(String word) {
        return repository.findById(word);
    }

    public void deleteAll(Iterable<? extends Word> entities) {
        this.repository.deleteAll(entities);
    }

    public List<Recipe> findAllWithRecipe(List<String> words, String recipeName) {
        return this.repository.findAllWithRecipe(words, recipeName);
    }

    public List<Recipe> findAllRecipes(List<String> words) {
        return this.repository.findAllRecipes(words);
    }

    public Double averageWordLength(){
        return this.repository.averageWordLength();
    }

    public Double maxWordLength(){
        return this.repository.maxWordLength();
    }

    public Double countWords(){
        return this.repository.countWords();
    }
}
