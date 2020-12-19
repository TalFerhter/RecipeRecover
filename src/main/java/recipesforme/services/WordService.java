package recipesforme.services;

import recipesforme.models.Word;
import recipesforme.repositories.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordService {

    @Autowired
    private WordRepository repository;

    public List<Word> findAll() {
        var words = (List<Word>) repository.findAll();
        return words;
    }

    public <S extends Word> Iterable<S> saveAll(Iterable<S> entities) {
        return repository.saveAll(entities);
    }
}
