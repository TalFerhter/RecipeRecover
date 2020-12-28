package recipesforme.models;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "levels")
public class Level {

    @Id
    private UUID levelId;

    private String levelName;

    @OneToMany(mappedBy = "levels")
    private Set<Recipe> recipes = new HashSet<>();

    public Level() {}

    public Level(String levelName) {
        this.levelId = UUID.randomUUID();
        this.levelName = levelName;
    }

    public UUID getLevelId() {
        return levelId;
    }

    public void setLevelId(UUID levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
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
        final Level other = (Level) obj;
        if (!Objects.equals(this.levelName, other.levelName)) {
            return false;
        }
        return Objects.equals(this.levelId, other.levelId);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Author{");
        sb.append("levelId = ").append(levelId);
        sb.append(", name = '").append(levelName).append("'}");
        return sb.toString();
    }
}
