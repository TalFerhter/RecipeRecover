package recipesforme.models;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "positions")
public class Position {

    @Id
    private UUID pos_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "paragraph_id")
    private Paragraph paragraph;

    private int row;
    private int col;

    @ManyToMany(mappedBy = "positions", fetch = FetchType.LAZY)
    private Set<Word> words = new HashSet<>();

    @ManyToMany(mappedBy = "positions")
    private Set<Phrase> phrases = new HashSet<>();

    @OneToOne(mappedBy = "position", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
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
        final Position other = (Position) obj;
        if (!Objects.equals(this.paragraph, other.paragraph)) {
            return false;
        }
        if (this.row != other.row) {
            return false;
        }
        if (this.col != other.col) {
            return false;
        }
        return Objects.equals(this.pos_id, other.pos_id);
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
