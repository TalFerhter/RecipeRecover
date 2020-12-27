package recipesforme.models;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    private UUID author_id;

    private String author_name;

    @OneToMany(mappedBy = "authors")
    private Set<Recipe> recipes = new HashSet<>();

    protected Author() {}

    public Author(String author_name) {
        this.author_id = UUID.randomUUID();
        this.author_name = author_name;
    }

    public UUID getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(UUID author_id) {
        this.author_id = author_id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
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
        if (!Objects.equals(this.author_name, other.author_name)) {
            return false;
        }
        return Objects.equals(this.author_id, other.author_id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Author{");
        sb.append("author_id = ").append(author_id);
        sb.append(", name = '").append(author_name).append("'}");
        return sb.toString();
    }
}
