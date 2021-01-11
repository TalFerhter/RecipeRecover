package recipesforme.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "positions")
public class Position {

    @Id
    @Type(type="pg-uuid")
    private UUID pos_id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "recipeId")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "paragraph_id")
    @JsonIgnore
    private Paragraph paragraph;

    private int row;
    private int col;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "words_positions",
            joinColumns = @JoinColumn(name = "pos_id", referencedColumnName = "pos_id"),
            inverseJoinColumns = @JoinColumn(name = "word", referencedColumnName = "word"))
    @JsonIgnore
    private Set<Word> words = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "phrases_positions",
            joinColumns = @JoinColumn(name = "pos_id", referencedColumnName = "pos_id"),
            inverseJoinColumns = @JoinColumn(name = "phrase_id", referencedColumnName = "phrase_id"))
    private Set<Phrase> phrases = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonIgnore
    @JoinColumn(name = "currPos")
    private Neighbor neighbor;

    public Position() {}

    public Position(int row, int col, Recipe recipe, Paragraph currParagraph) {
        this.pos_id = UUID.randomUUID();
        this.row = row;
        this.col = col;
        this.recipe = recipe;
        this.paragraph = currParagraph;
    }

    public UUID getPos_id() {
        return pos_id;
    }

    public void setPos_id(UUID pos_id) {
        this.pos_id = pos_id;
    }

    public Paragraph getParagraph() {
        return paragraph;
    }

    public void setParagraph(Paragraph paragraph) {
        this.paragraph = paragraph;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Set<Word> getWords() {
        return words;
    }

    public void setWord(Word word) {
        this.words.add(word);
    }

    public Set<Phrase> getPhrases() {
        return phrases;
    }

    public void setPhrase(Phrase phrase) {
        this.phrases.add(phrase);
    }

    public void setWords(Set<Word> words) {
        this.words = words;
    }

    public void setPhrases(Set<Phrase> phrases) {
        this.phrases = phrases;
    }

    public Neighbor getNeighbor() {
        return neighbor;
    }

    public void setNeighbor(Neighbor neighbor) {
        this.neighbor = neighbor;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Position{");
        sb.append("pos_id = ").append(pos_id);
        sb.append(", paragraph_id = '").append(paragraph).append("'");
        sb.append(", row = ").append(row);
        sb.append(", col = ").append(col).append("}");
        return sb.toString();
    }
}
