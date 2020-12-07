package recipesforme.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipesforme.models.Author;
import recipesforme.models.Word;

import java.util.Optional;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Integer> {

    Optional<Author> findById(int authorId);

    Iterable<Author> findAll();
}
