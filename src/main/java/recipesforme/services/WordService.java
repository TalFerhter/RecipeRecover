package recipesforme.services;

import recipesforme.models.Word;
import recipesforme.repositories.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordService implements IWordService {

    @Autowired
    private WordRepository repository;

    @Override
    public List<Word> findAll() {
        var words = (List<Word>) repository.findAll();
        return words;
    }
}
