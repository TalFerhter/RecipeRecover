package recipesforme.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    private String category;

    protected Category() {}

    public Category(String categoryName) {
        this.category = categoryName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
