package recipesforme.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipesforme.models.Author;
import recipesforme.models.Paragraph;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParagraphRepository extends CrudRepository<Paragraph, UUID> {

    Optional<Paragraph> findById(UUID paragraphId);

    Iterable<Paragraph> findAll();

    <S extends Paragraph> Iterable<S> saveAll(Iterable<S> entities);

    <S extends Paragraph> S save(S s);

    List<Paragraph> findByTitle(String title);
}
