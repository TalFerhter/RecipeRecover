package recipesforme.models;


import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "words")
public class Word {

    @Id
    private String word;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "words_positions",
        joinColumns = @JoinColumn(name = "word", referencedColumnName = "word"),
        inverseJoinColumns = @JoinColumn(name = "pos_id", referencedColumnName = "pos_id"))
    private Set<Position> positions =  new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "groups_words",
            joinColumns = @JoinColumn(name = "word", referencedColumnName = "word"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "group_id"))
    private Set<Group> groups;

    public Word() {}

    public Word(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Set<Position> getPositions() {
        return positions;
    }

    public void setPositions(Set<Position> positions) {
        this.positions = positions;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public void addPosition(Position position) {
        this.positions.add(position);
    }

    public void addGroup(Group group){
        this.groups.add(group);
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
