//package recipesforme.bl.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import recipesforme.models.Level;
////import recipesforme.models.Neighbor;
//import recipesforme.repositories.LevelRepository;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//public class NeighborService {
//
//    @Autowired
//    private NeighborService repository;
//
////    public List<Neighbor> findAll() {
////        var neighbors = (List<Neighbor>) repository.findAll();
////        return neighbors;
////    }
//
//    public <S extends Neighbor> Iterable<S> saveAll(Iterable<S> entities) {
//        return repository.saveAll(entities);
//    }
//
//    public <S extends Neighbor> S save(S s) { return repository.save(s);}
//
//
//    public Optional<Neighbor> findByNextPos(UUID nextPos) {
//        Optional<Neighbor> neighbor = repository.findByNextPos(nextPos);
//        return neighbor;
//    }
//}
