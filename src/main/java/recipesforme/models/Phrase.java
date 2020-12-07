package recipesforme.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "phrases")
public class Phrase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int phrase_id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "phrases_positions",
            joinColumns = @JoinColumn(name = "phrase_id", referencedColumnName = "phrase_id"),
            inverseJoinColumns = @JoinColumn(name = "pos_id", referencedColumnName = "pos_id"))
    private List<Position> positions;

    private String text;

    protected Phrase() {}

    public Phrase(int phrase_id, String text) {
        this.phrase_id = phrase_id;
        this.text = text;
    }

    public int getPhrase_id() {
        return phrase_id;
    }

    public void setPhrase_id(int phrase_id) {
        this.phrase_id = phrase_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        final Phrase other = (Phrase) obj;
        if (!Objects.equals(this.text, other.text)) {
            return false;
        }
        return Objects.equals(this.phrase_id, other.phrase_id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Phrase{");
        sb.append("phrase_id = ").append(phrase_id);
        sb.append(", text = '").append(text).append("'}");
        return sb.toString();
    }
}