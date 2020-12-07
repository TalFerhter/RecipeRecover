package recipesforme.models;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "words")
public class Word {

    @Id
    private String word;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "words_positions",
        joinColumns = @JoinColumn(name = "word", referencedColumnName = "word"),
        inverseJoinColumns = @JoinColumn(name = "pos_id", referencedColumnName = "pos_id"))
    private List<Position> positions;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "groups_words",
            joinColumns = @JoinColumn(name = "word", referencedColumnName = "word"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "group_id"))
    private List<Group> groups;

    protected Word() {}

    public Word(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    // Do I need this?
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
        final Word other = (Word) obj;
        return Objects.equals(this.word, other.word);
    }
}
