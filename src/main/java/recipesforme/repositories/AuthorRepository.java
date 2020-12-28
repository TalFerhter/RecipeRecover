package recipesforme.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipesforme.models.Author;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorRepository extends CrudRepository<Author, UUID> {

    Optional<Author> findById(UUID authorId);

    Iterable<Author> findAll();

    <S extends Author> S save(S s);

    <S extends Author> Iterable<S> saveAll(Iterable<S> entities);

    @Query(value = "select l from Author l where l.authorName = ?1")
    Optional<Author> findByAuthorName(String authorName);
}
