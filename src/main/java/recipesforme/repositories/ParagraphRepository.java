package recipesforme.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipesforme.models.Author;
import recipesforme.models.Paragraph;

import java.util.Optional;

@Repository
public interface ParagraphRepository extends CrudRepository<Paragraph, Integer> {

    Optional<Paragraph> findById(int paragraphId);

    Iterable<Paragraph> findAll();
}
