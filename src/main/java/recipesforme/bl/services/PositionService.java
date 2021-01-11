package recipesforme.bl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipesforme.models.Position;
import recipesforme.models.Word;
import recipesforme.repositories.PositionRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public void deleteAll(Iterable<? extends Position> entities) {
        this.repository.deleteAll(entities);
    }

    public Optional<Position> findById(UUID posId){
        return repository.findById(posId);
    }

    public Iterable<Position> findByPositionDetails(int row, int col, Optional<UUID> recipe_id,
                                                          Optional<UUID> paragraph_id){
        if (recipe_id.isPresent()){
            if (paragraph_id.isPresent()){
                return repository.findWordPositionsByRecipe(recipe_id.get(), row, col, paragraph_id.get());
            }
            return repository.findWordPositionsByRecipe(recipe_id.get(), row, col);

        }
        if (paragraph_id.isPresent()) {
            return repository.findWordPositions(row, col, paragraph_id.get());
        }
        return repository.findWordPositions(row, col);
    }
}
