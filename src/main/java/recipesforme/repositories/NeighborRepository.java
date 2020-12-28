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

    @Query(value = "select n from Neighbor n where n.nextPos = ?1")
    Optional<Neighbor> findByNextPos(UUID nextPos);

    @Query(value = "WITH RECURSIVE subordinates AS (\n" +
            "\tSELECT\n" +
            "\t\tpos_id,\n" +
            "\t\tnext_pos\n" +
            "\tFROM\n" +
            "\t\trecipesforme.neighbor_position\n" +
            "\tWHERE\n" +
            "\t\tpos_id = ?1\n" +
            "\tUNION\n" +
            "\t\tSELECT\n" +
            "\t\t\te.pos_id,\n" +
            "\t\t\te.next_pos\n" +
            "\t\tFROM\n" +
            "\t\t\trecipesforme.neighbor_position e\n" +
            "\t\tINNER JOIN subordinates s ON s.next_pos = e.pos_id\n" +
            "\t\n" +
            ") SELECT\n" +
            "\t*\n" +
            "FROM\n" +
            "\tsubordinates limit ?2;", nativeQuery = true)
    List<Neighbor> findNNeighbors(UUID posId, int n);
}
