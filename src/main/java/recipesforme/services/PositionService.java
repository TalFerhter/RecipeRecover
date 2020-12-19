package recipesforme.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipesforme.models.Position;
import recipesforme.models.Word;
import recipesforme.repositories.PositionRepository;
import recipesforme.repositories.WordRepository;

import java.util.List;

@Service
public class PositionService {

    @Autowired
    private PositionRepository repository;

    public List<Position> findAll() {
        var positions = (List<Position>) repository.findAll();
        return positions;
    }

    public <S extends Position> Iterable<S> saveAll(Iterable<S> entities) {
        return repository.saveAll(entities);
    }
}
