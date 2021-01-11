package recipesforme.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "phrases")
public class Phrase {

    @Id
    @Type(type="pg-uuid")
    private UUID phrase_id;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "phrases")
    @JsonIgnore
    private Set<Position> positions = new HashSet<>();

    private String text;

    public Phrase() {}

    public Phrase(String text) {
        this.phrase_id = UUID.randomUUID();
        this.text = text;
    }

    public UUID getPhrase_id() {
        return phrase_id;
    }

    public void setPhrase_id(UUID phrase_id) {
        this.phrase_id = phrase_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<Position> getPositions() {
        return positions;
    }

    public void setPositions(Set<Position> positions) {
        this.positions = positions;
    }

    public void addPosition(Position position){
        this.positions.add(position);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Phrase{");
        sb.append("phrase_id = ").append(phrase_id);
        sb.append(", text = '").append(text).append("'}");
        return sb.toString();
    }
}
