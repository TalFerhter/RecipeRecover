package recipesforme.models;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    private UUID authorId;

    private String authorName;

    @OneToMany(mappedBy = "author")
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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Author other = (Author) obj;
        if (!Objects.equals(this.authorName, other.authorName)) {
            return false;
        }
        return Objects.equals(this.authorId, other.authorId);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Author{");
        sb.append("authorId = ").append(authorId);
        sb.append(", name = '").append(authorName).append("'}");
        return sb.toString();
    }
}
