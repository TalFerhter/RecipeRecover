package recipesforme.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.sql.Time;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @Type(type="pg-uuid")
    private UUID recipeId;

    private String recipeName;
    private String siteName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "authorId")
    @JsonIgnore
    private Author author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "levelId")
    @JsonIgnore
    private Level levels;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Position> positions;

    private Time prepTime;
    private Time cookTime;
    private Time totalTime;
    private int yieldMin;
    private int yieldMax;
    private String path;
    private String category;

    @Temporal(TemporalType.DATE)
    private Date date;

    public Recipe() {
        this.recipeId = UUID.randomUUID();
    }

    public Recipe(String recipeName, String siteName, Time prep_time,
                  Time cook_time, Time total_time, int min_yield, int max_yield,
                  String path, Date date, String category, Set<Position> positions) {
        this.recipeId = UUID.randomUUID();
        this.recipeName = recipeName;
        this.siteName = siteName;
        this.prepTime = prep_time;
        this.cookTime = cook_time;
        this.totalTime = total_time;
        this.yieldMin = min_yield;
        this.yieldMax = max_yield;
        this.path = path;
        this.date = date;
        this.category = category;
        this.positions = positions;
    }

    public UUID getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(UUID recipe_id) {
        this.recipeId = recipe_id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipe_name) {
        this.recipeName = recipe_name;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String site_name) {
        this.siteName = site_name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
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

    public int getYieldMin() {
        return yieldMin;
    }

    public void setYieldMin(int yieldMin) {
        this.yieldMin = yieldMin;
    }

    public int getYieldMax() {
        return yieldMax;
    }

    public void setYieldMax(int yieldMax) {
        this.yieldMax = yieldMax;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<Position> getPositions() {
        return positions;
    }

    public void setPositions(Set<Position> positions) {
        this.positions = positions;
    }

    public void addPosition(Position position) {
        this.positions.add(position);
    }

        @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Recipe{");
        sb.append("name = '").append(recipeName).append("'");
        sb.append(", site = '").append(siteName).append("'");
        sb.append(", preparation time = ").append(prepTime);
        sb.append(", cooking time = ").append(cookTime);
        sb.append(", total time = ").append(totalTime);
        sb.append(", yields = ").append(yieldMin).append(" to ").append(yieldMax);
        sb.append(", level = ").append(levels);
        sb.append(", category = ").append(category);
        sb.append(", path = '").append(path);
        sb.append(", publish date = '").append(date).append("}");
        return sb.toString();
    }
}
