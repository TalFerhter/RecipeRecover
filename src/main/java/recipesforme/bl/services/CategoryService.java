package recipesforme.bl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import recipesforme.models.Author;
import recipesforme.models.Category;
import recipesforme.repositories.AuthorRepository;
import recipesforme.repositories.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@RestController
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @GetMapping("/categories")
    public List<Category> findAll() {
        var categories = (List<Category>) repository.findAll();
        return categories;
    }

    public Optional<Category> findById(String category){
        return this.repository.findById(category);
    }

    public <S extends Category> Iterable<S> saveAll(Iterable<S> entities) {
        return repository.saveAll(entities);
    }

    public <S extends Category> S save(S s) {
        return repository.save(s);
    }

}
