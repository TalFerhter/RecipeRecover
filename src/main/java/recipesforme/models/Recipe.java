package recipesforme.models;

import javax.persistence.*;
import java.util.Date;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int recipe_id;

    private String recipe_name;
    private String site_name;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author authors;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level levels;

    private Time prep_time;
    private Time cook_time;
    private Time total_time;
    private int min_yield;
    private int max_yield;
    private String path;
    private Date date;

    public Recipe() {
    }

    public Recipe(String recipe_name, String site_name, Time prep_time,
                  Time cook_time, Time total_time, int min_yield, int max_yield,
                  String path, Date date) {
        this.recipe_name = recipe_name;
        this.site_name = site_name;
        this.prep_time = prep_time;
        this.cook_time = cook_time;
        this.total_time = total_time;
        this.min_yield = min_yield;
        this.max_yield = max_yield;
        this.path = path;
        this.date = date;
    }

    public int getRecipeId() {
        return recipe_id;
    }

    public void setRecipeId(int recipe_id) {
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
        return prep_time;
    }

    public void setPrepTime(Time prep_time) {
        this.prep_time = prep_time;
    }

    public Time getCookTime() {
        return cook_time;
    }

    public void setCookTime(Time cook_time) {
        this.cook_time = cook_time;
    }

    public Time getTotalTime() {
        return total_time;
    }

    public void setTotalTime(Time total_time) {
        this.total_time = total_time;
    }

    public int getMinYield() {
        return min_yield;
    }

    public void setMinYield(int minYield) {
        this.min_yield = minYield;
    }

    public int getMaxYield() {
        return max_yield;
    }

    public void setMaxYield(int maxYield) {
        this.max_yield = maxYield;
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
        if (this.prep_time != other.prep_time) {
            return false;
        }
        if (this.cook_time != other.cook_time) {
            return false;
        }
        if (this.total_time != other.total_time) {
            return false;
        }
        if (this.min_yield != other.min_yield) {
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
        sb.append(", preparation time = ").append(prep_time);
        sb.append(", cooking time = ").append(cook_time);
        sb.append(", total time = ").append(total_time);
        sb.append(", yields = ").append(min_yield).append(" to ").append(max_yield);
        sb.append(", level = ").append(levels);
        sb.append(", path = '").append(path);
        sb.append(", publish date = '").append(date).append("}");
        return sb.toString();
    }
}
