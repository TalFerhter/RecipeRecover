package recipesforme.bl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipesforme.models.Author;
import recipesforme.repositories.AuthorRepository;

import java.awt.desktop.OpenFilesEvent;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository repository;

    public List<Author> findAll() {
        var authors = (List<Author>) repository.findAll();
        return authors;
    }

    public <S extends Author> Iterable<S> saveAll(Iterable<S> entities) {
        return repository.saveAll(entities);
    }

    public <S extends Author> S save(S s) {
        return repository.save(s);
    }

    public Optional<Author> findByAuthorName(String authorName) {
        Optional<Author> authors = repository.findByAuthorName(authorName);
        return authors;
    }
}
