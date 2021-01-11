package recipesforme.bl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import recipesforme.models.Group;
import recipesforme.models.Paragraph;
import recipesforme.repositories.GroupRepository;
import recipesforme.repositories.ParagraphRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RestController
public class GroupService {

    @Autowired
    private GroupRepository repository;

    @GetMapping("/groups")
    public List<Group> findAll() {
        var groups = (List<Group>) repository.findAll();
        return groups;
    }

    public Optional<Group> findById(String groupName) {
        var groups = repository.findById(groupName);
        return groups;
    }

    public <S extends Group> S save(S group) {
        return repository.save(group);
    }

}
