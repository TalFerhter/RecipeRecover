package recipesforme.bl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipesforme.models.Paragraph;
import recipesforme.repositories.ParagraphRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParagraphService {

    @Autowired
    private ParagraphRepository repository;

    public List<Paragraph> findAll() {
        return (List<Paragraph>) repository.findAll();
    }

    public Optional<Paragraph> findById(UUID paragraphId) {
        return repository.findById(paragraphId);
    }

    public List<Paragraph> findByTitle(String title) {
        return repository.findByTitle(title);
    }

    public <S extends Paragraph> S save(S paragraph) {
        return repository.save(paragraph);
    }

}
