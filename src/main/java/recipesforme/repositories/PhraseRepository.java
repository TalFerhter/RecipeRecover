package recipesforme.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipesforme.models.Author;
import recipesforme.models.Phrase;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PhraseRepository extends CrudRepository<Phrase, UUID> {

    Optional<Phrase> findById(UUID phraseId);

    Iterable<Phrase> findAll();

    List<Phrase> findByText(String phraseText);
}
