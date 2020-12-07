package recipesforme.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipesforme.models.Author;
import recipesforme.models.Phrase;

import java.util.Optional;

@Repository
public interface PhraseRepository extends CrudRepository<Phrase, Integer> {

    Optional<Phrase> findById(int phraseId);

    Iterable<Phrase> findAll();
}
