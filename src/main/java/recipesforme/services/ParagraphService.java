package recipesforme.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipesforme.models.Paragraph;
import recipesforme.repositories.ParagraphRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ParagraphService {

    @Autowired
    private ParagraphRepository repository;

    public List<Paragraph> findAll() {
        var paragraphs = (List<Paragraph>) repository.findAll();
        return paragraphs;
    }

    public Optional<Paragraph> findById(Integer paragraphId) {
        var paragraph = repository.findById(paragraphId);
        return paragraph;
    }

    public List<Paragraph> findByTitle(String title) {
        List<Paragraph> paragraph = repository.findByTitle(title);
        return paragraph;
    }


}
