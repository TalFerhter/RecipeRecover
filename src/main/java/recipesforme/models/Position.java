package recipesforme.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "positions")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int pos_id;

    @ManyToOne
    private Paragraph paragraph;

    private int row;
    private int col;

    @ManyToMany(mappedBy = "positions")
    private List<Word> words;

    @ManyToMany(mappedBy = "positions")
    private List<Phrase> phrases;

    protected Position() {}

    // Do I need to generate the pos_id or to set it?
    public Position(int pos_id, int row, int col) {
        this.pos_id = pos_id;
        this.row = row;
        this.col = col;
    }

    public Paragraph getParagraph() {
        return paragraph;
    }

    public void setParagraph(Paragraph paragraph) {
        this.paragraph = paragraph;
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