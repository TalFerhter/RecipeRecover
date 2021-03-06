package recipesforme.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipesforme.models.Group;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends CrudRepository<Group, String> {

    Optional<Group> findById(String groupName);

    Iterable<Group> findAll();

    <S extends Group> S save(S entity);

    <S extends Group> Iterable<S> saveAll(Iterable<S> entities);
}
