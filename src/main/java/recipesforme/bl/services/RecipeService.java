package recipesforme.bl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipesforme.models.Recipe;
import recipesforme.repositories.RecipeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository repository;

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
}

