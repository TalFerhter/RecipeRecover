package recipesforme.models;

import javax.persistence.*;
import java.sql.Date;
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
    @JoinColumn(name = "rating_id")
    private Rating ratings;

    private Time prep_time;
    private Time cook_time;
    private Time total_time;
    private int yields;
    private String path;
    private Date date;

    public Recipe(int recipe_id, String recipe_name, String site_name, Time prep_time, Time cook_time, Time total_time,
                  int yields, String path, Date date) {
        this.recipe_id = recipe_id;
        this.recipe_name = recipe_name;
        this.site_name = site_name;
        this.prep_time = prep_time;
        this.cook_time = cook_time;
        this.total_time = total_time;
        this.yields = yields;
        this.path = path;
        this.date = date;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public Author getAuthors() {
        return authors;
    }

    public void setAuthors(Author authors) {
        this.authors = authors;
    }

    public Rating getRatings() {
        return ratings;
    }

    public void setRatings(Rating ratings) {
        this.ratings = ratings;
    }

    public Time getPrep_time() {
        return prep_time;
    }

    public void setPrep_time(Time prep_time) {
        this.prep_time = prep_time;
    }

    public Time getCook_time() {
        return cook_time;
    }

    public void setCook_time(Time cook_time) {
        this.cook_time = cook_time;
    }

    public Time getTotal_time() {
        return total_time;
    }

    public void setTotal_time(Time total_time) {
        this.total_time = total_time;
    }

    public int getYields() {
        return yields;
    }

    public void setYields(int yields) {
        this.yields = yields;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

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
        if (this.yields != other.yields) {
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
        sb.append(", yields = ").append(yields);
        sb.append(", path = '").append(path);
        sb.append(", publish date = '").append(date).append("}");
        return sb.toString();
    }
}
