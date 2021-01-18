package recipesforme.bl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import recipesforme.models.Level;
import recipesforme.repositories.LevelRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LevelService {

    @Autowired
    private LevelRepository repository;

    public List<Level> findAll() {
        var levels = (List<Level>) repository.findAll();
        return levels;
    }

    public <S extends Level> Iterable<S> saveAll(Iterable<S> entities) {
        return repository.saveAll(entities);
    }

    public <S extends Level> S save(S s) { return repository.save(s);}


    public Optional<Level> findByLevelName(String name) {
        Optional<Level> level = repository.findByLevelName(name);
        return level;
    }
}
