package recipesforme.bl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipesforme.models.Group;
import recipesforme.models.Paragraph;
import recipesforme.repositories.GroupRepository;
import recipesforme.repositories.ParagraphRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GroupService {

    @Autowired
    private GroupRepository repository;

    public List<Group> findAll() {
        var groups = (List<Group>) repository.findAll();
        return groups;
    }

    public Optional<Group> findById(UUID paragraphId) {
        var groups = repository.findById(paragraphId);
        return groups;
    }

    public List<Group> findByTitle(String title) {
        List<Group> groups = repository.findByTitle(title);
        return groups;
    }

    public <S extends Group> S save(S group) {
        return repository.save(group);
    }

}
