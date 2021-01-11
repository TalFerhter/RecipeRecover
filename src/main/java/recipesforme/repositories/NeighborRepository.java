package recipesforme.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipesforme.models.Neighbor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NeighborRepository extends CrudRepository<Neighbor, UUID> {

    Optional<Neighbor> findById(UUID authorId);

    Iterable<Neighbor> findAll();

    <S extends Neighbor> S save(S s);

    <S extends Neighbor> Iterable<S> saveAll(Iterable<S> entities);

    void deleteAll(Iterable<? extends Neighbor> entities);

    @Query(value = "select n from Neighbor n where n.neighbor_pos = ?1")
    Optional<Neighbor> findByNeighborPos(UUID nextPos);

    @Query(value = "WITH RECURSIVE subordinates AS ( SELECT pos_id, neighbor_pos " +
            "FROM recipesforme.neighbor_position " +
            "WHERE pos_id = (?1) " +
            "UNION " +
            "SELECT e.pos_id, e.neighbor_pos " +
            "FROM recipesforme.neighbor_position AS e " +
            "INNER JOIN subordinates AS s ON s.neighbor_pos = e.pos_id ) " +
            "SELECT * FROM subordinates limit ?2", nativeQuery = true)
    List<Neighbor> findNNeighbors(UUID posId, int n);
}
