//package recipesforme.repositories;
//
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;
//import recipesforme.models.Neighbor;
//
//import java.util.Optional;
//import java.util.UUID;
//
//@Repository
//public interface NeighborRepository extends CrudRepository<Neighbor, UUID> {
//
//    Optional<Neighbor> findById(UUID authorId);
//
//    Iterable<Neighbor> findAll();
//
//    <S extends Neighbor> S save(S s);
//
//    <S extends Neighbor> Iterable<S> saveAll(Iterable<S> entities);
//
//    @Query(value = "select n from Neighbor n where n.nextPos = ?1")
//    Optional<Neighbor> findByNextPos(UUID nextPos);
//}
