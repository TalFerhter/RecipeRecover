package recipesforme.models;

import javax.persistence.*;
import java.util.Date;
import java.sql.Time;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    private UUID recipe_id;

    private String recipe_name;
    private String site_name;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author authors;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level levels;

    private Time prepTime;
    private Time cookTime;
    private Time totalTime;
    private int yieldMin;
    private int yieldMax;
    private String path;

    @Temporal(TemporalType.DATE)
    private Date date;

    public Recipe() {
        this.recipe_id = UUID.randomUUID();
    }

    public Recipe(String recipe_name, String site_name, Time prep_time,
                  Time cook_time, Time total_time, int min_yield, int max_yield,
                  String path, Date date) {
        this.recipe_id = UUID.randomUUID();
        this.recipe_name = recipe_name;
        this.site_name = site_name;
        this.prepTime = prep_time;
        this.cookTime = cook_time;
        this.totalTime = total_time;
        this.yieldMin = min_yield;
        this.yieldMax = max_yield;
        this.path = path;
        this.date = date;
    }

    public UUID getRecipeId() {
        return recipe_id;
    }

    public void setRecipeId(UUID recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getRecipeName() {
        return recipe_name;
    }

    public void setRecipeName(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public String getSiteName() {
        return site_name;
    }

    public void setSiteName(String site_name) {
        this.site_name = site_name;
    }

    public Author getAuthors() {
        return authors;
    }

    public void setAuthors(Author authors) {
        this.authors = authors;
    }

    public Level getLevels() {
        return levels;
    }

    public void setLevels(Level levels) {
        this.levels = levels;
    }

    public Time getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(Time prep_time) {
        this.prepTime = prep_time;
    }

    public Time getCookTime() {
        return cookTime;
    }

    public void setCookTime(Time cook_time) {
        this.cookTime = cook_time;
    }

    public Time getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Time total_time) {
        this.totalTime = total_time;
    }

    public int getMinYield() {
        return yieldMin;
    }

    public void setMinYield(int minYield) {
        this.yieldMin = minYield;
    }

    public int getMaxYield() {
        return yieldMax;
    }

    public void setMaxYield(int maxYield) {
        this.yieldMax = maxYield;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
        final Recipe other = (Recipe) obj;
        if (!Objects.equals(this.recipe_name, other.recipe_name)) {
            return false;
        }
        if (!Objects.equals(this.site_name, other.site_name)) {
            return false;
        }
        if (this.prepTime != other.prepTime) {
            return false;
        }
        if (this.cookTime != other.cookTime) {
            return false;
        }
        if (this.totalTime != other.totalTime) {
            return false;
        }
        if (this.yieldMin != other.yieldMin) {
            return false;
        }
        if (this.yieldMax != other.yieldMax) {
            return false;
        }
        if (!Objects.equals(this.path, other.path)) {
            return false;
        }
        return Objects.equals(this.recipe_id, other.recipe_id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Recipe{");
        sb.append("name = '").append(recipe_name).append("'");
        sb.append(", site = '").append(site_name).append("'");
        sb.append(", preparation time = ").append(prepTime);
        sb.append(", cooking time = ").append(cookTime);
        sb.append(", total time = ").append(totalTime);
        sb.append(", yields = ").append(yieldMin).append(" to ").append(yieldMax);
        sb.append(", level = ").append(levels);
        sb.append(", path = '").append(path);
        sb.append(", publish date = '").append(date).append("}");
        return sb.toString();
    }
}
