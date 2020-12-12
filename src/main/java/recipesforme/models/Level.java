package recipesforme.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "levels")
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int level_id;

    private String level_name;

    @OneToMany(mappedBy = "levels")
    private Collection<Recipe> recipes;

    protected Level() {}

    public Level(String level_name) {
        this.level_name = level_name;
    }

    public int getAuthor_id() {
        return level_id;
    }

    public void setAuthor_id(int level_id) {
        this.level_id = level_id;
    }

    public String getAuthor_name() {
        return level_name;
    }

    public void setAuthor_name(String level_name) {
        this.level_name = level_name;
    }

    public Collection<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Collection<Recipe> recipes) {
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
        final Level other = (Level) obj;
        if (!Objects.equals(this.level_name, other.level_name)) {
            return false;
        }
        return Objects.equals(this.level_id, other.level_id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Author{");
        sb.append("level_id = ").append(level_id);
        sb.append(", name = '").append(level_name).append("'}");
        return sb.toString();
    }
}
