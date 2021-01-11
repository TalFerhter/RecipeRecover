package recipesforme.bl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipesforme.models.Level;
import recipesforme.models.Neighbor;
import recipesforme.models.Word;
import recipesforme.repositories.LevelRepository;
import recipesforme.repositories.NeighborRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NeighborService {

    @Autowired
    private NeighborRepository repository;

    public List<Neighbor> findAll() {
        var neighbors = (List<Neighbor>) repository.findAll();
        return neighbors;
    }

    public <S extends Neighbor> Iterable<S> saveAll(Iterable<S> entities) {
        return repository.saveAll(entities);
    }

    public <S extends Neighbor> S save(S s) {
        return repository.save(s);
    }

    public void deleteAll(Iterable<? extends Neighbor> entities) {
        this.repository.deleteAll(entities);
    }

    public Optional<Neighbor> findByNeighborPos(UUID nextPos) {
        Optional<Neighbor> neighbor = repository.findByNeighborPos(nextPos);
        return neighbor;
    }

    public List<Neighbor> findNNeighbors(UUID posId, int n) {
        return repository.findNNeighbors(posId, n);
    }
}
