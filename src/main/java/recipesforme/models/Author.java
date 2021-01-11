package recipesforme.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @Type(type="pg-uuid")
    private UUID authorId;

    private String authorName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "author")
    private Set<Recipe> recipes = new HashSet<>();

    protected Author() {}

    public Author(String authorName) {
        this.authorId = UUID.randomUUID();
        this.authorName = authorName;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Collection<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Author{");
        sb.append("authorId = ").append(authorId);
        sb.append(", name = '").append(authorName).append("'}");
        return sb.toString();
    }
}
