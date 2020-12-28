package recipesforme.bl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipesforme.models.Phrase;
import recipesforme.models.Position;
import recipesforme.models.Word;
import recipesforme.repositories.PhraseRepository;
import recipesforme.repositories.PositionRepository;
import recipesforme.repositories.WordRepository;

import java.util.*;

@Service
public class PhraseService {

    @Autowired
    private PhraseRepository repository;

    public List<Phrase> findAll() {
        var phrases = (List<Phrase>) repository.findAll();
        return phrases;
    }

    public <S extends Phrase> Iterable<S> saveAll(Iterable<S> entities) {
        return repository.saveAll(entities);
    }

    public Optional<Phrase> findById(UUID phraseId){
        return repository.findById(phraseId);
    }

    public List<Phrase> findByText(String phraseText) { return repository.findByText(phraseText); }

    public <S extends Phrase> S save(S phrase) {
        return repository.save(phrase);
    }

}
