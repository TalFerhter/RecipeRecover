package recipesforme.bl.services;

import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import recipesforme.models.Recipe;
import recipesforme.repositories.RecipeRepository;
import recipesforme.repositories.WordRepository;

import java.sql.*;
import java.sql.Date;
import java.util.*;

@Service
@RestController
public class RecipeService {

    @Autowired
    private RecipeRepository repository;

    @Autowired
    private WordRepository wordRepository;

    public List<Recipe> findAll() {
        var recipes = (List<Recipe>) repository.findAll();
        return recipes;
    }


    public <S extends Recipe> Iterable<S> saveAll(Iterable<S> entities) {
        return repository.saveAll(entities);
    }

    public Optional<Recipe> findById(UUID uuid) {
        return repository.findById(uuid);
    }

    public <S extends Recipe> S save(S entity) {
        return repository.save(entity);
    }

    public void delete(Recipe entity) {
        repository.delete(entity);
    }

    public Optional<Recipe> findByRecipeName(String name) {
        Optional<Recipe> recipe = repository.findByRecipeName(name);
        return recipe;
    }

    public List<Recipe> findByPartOfRecipeName(String name) {
        List<Recipe> recipe = repository.findByPartOfRecipeName(name);
        return recipe;
    }

    public List<Object> findByDetails(Optional<String> recipeName,
                                      Optional<String> siteName,
                                      Optional<String> authorName,
                                      Optional<String> path,
                                      Optional<Integer> yieldMin,
                                      Optional<Integer> yieldMax,
                                      Optional<String> category,
                                      Optional<String> level,
                                      Optional<List<String>> words) {
        List<Object> recipes = new ArrayList<>();
        if (words.isEmpty()){
            recipes.addAll(repository.findByDetailsWithoutWords(
                    (recipeName.isEmpty()?null:recipeName.get()),
                    (siteName.isEmpty()?null:siteName.get()),
                    (authorName.isEmpty()?null:authorName.get()),
                    (path.isEmpty()?null:path.get()),
                    (yieldMin.isEmpty()?null:yieldMin.get()),
                    (yieldMax.isEmpty()?null:yieldMax.get()),
                    (category.isEmpty()?null:category.get()),
                    (level.isEmpty()?null:level.get())));
        } else {
            recipes.addAll(repository.findByDetails(
                    (recipeName.isEmpty()?null:recipeName.get()),
                    (siteName.isEmpty()?null:siteName.get()),
                    (authorName.isEmpty()?null:authorName.get()),
                    (path.isEmpty()?null:path.get()),
                    (yieldMin.isEmpty()?null:yieldMin.get()),
                    (yieldMax.isEmpty()?null:yieldMax.get()),
                    (category.isEmpty()?null:category.get()),
                    (level.isEmpty()?null:level.get()),
                    (words.get())));
        }

        return recipes;
    }

    public String countWordsInGroupsOfRecipe(String recipeName) {
        String categoryUrl = "http://localhost:5001/recipe/category";
        int[] groupsCounter = new int[8];
        List<Object[]> l = this.wordRepository.countWordsInGroupsOfRecipe(recipeName);
        l.forEach(objects -> {
            System.out.println(Arrays.toString(objects));
            String groupName = String.valueOf(objects[0]);
            String groupValue = String.valueOf(objects[1]);
            switch (groupName) {
                case "Sweet":
                    groupsCounter[0] += Integer.parseInt(groupValue);
                    break;

                case "Meat":
                    groupsCounter[1] += Integer.parseInt(groupValue);
                    break;

                case "Vegetables":
                    groupsCounter[2] += Integer.parseInt(groupValue);
                    break;

                case "Spices":
                    groupsCounter[3] += Integer.parseInt(groupValue);
                    break;

                case "Baking":
                    groupsCounter[4] += Integer.parseInt(groupValue);
                    break;

                case "Grains":
                    groupsCounter[5] += Integer.parseInt(groupValue);
                    break;

                case "Dairy":
                    groupsCounter[6] += Integer.parseInt(groupValue);
                    break;

                case "Fruits":
                    groupsCounter[7] += Integer.parseInt(groupValue);
                    break;

            }
        });
        RestTemplate restTemplate = new RestTemplate();
        JSONArray groupsArray = new JSONArray();
        groupsArray.add(0, groupsCounter);
        HttpEntity<JSONArray> request = new HttpEntity<>(groupsArray);
        String category = restTemplate.postForObject(categoryUrl, request, String.class);
        String strCategory;
        switch (category) {
            case "1.0":
                strCategory = "First Courses";
                break;
            case "2.0":
                strCategory = "Soups";
                break;
            case "3.0":
                strCategory = "Salads";
                break;
            case "4.0":
                strCategory = "Main Courses";
                break;
            case "5.0":
                strCategory = "Side Dishes";
                break;
            case "6.0":
                strCategory = "Desserts";
                break;
            default:
                strCategory = "None";
        }
        return strCategory;
    }

    public Double countRecipes() {
        return this.repository.countRecipes();
    }
}
