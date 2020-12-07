package recipesforme.models;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int rating_id;

    @OneToMany(mappedBy = "ratings")
    private List<Recipe> recipes;

    protected Rating() {}

    public Rating(int rating) {
        this.rating_id = rating;
    }

    public int getRating_id() {
        return rating_id;
    }

    public void setRating_id(int rating_id) {
        this.rating_id = rating_id;
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
        final Rating other = (Rating) obj;
        return Objects.equals(this.rating_id, other.rating_id);
    }
}
