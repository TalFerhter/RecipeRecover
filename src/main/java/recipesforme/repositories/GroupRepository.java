package recipesforme.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipesforme.models.Group;
import recipesforme.models.Word;

import java.util.Optional;

@Repository
public interface GroupRepository extends CrudRepository<Group, Integer> {

    Optional<Group> findById(int groupId);

    Iterable<Group> findAll();
}
