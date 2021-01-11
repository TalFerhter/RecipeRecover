package recipesforme.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "levels")
public class Level {

    @Id
    @Type(type="pg-uuid")
    private UUID levelId;

    private String levelName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "levels")
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
    public String toString() {
        final StringBuilder sb = new StringBuilder("Level{");
        sb.append("levelId = ").append(levelId);
        sb.append(", name = '").append(levelName).append("'}");
        return sb.toString();
    }
}
