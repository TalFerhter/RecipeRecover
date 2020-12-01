package org.openu.recipesForMe.Models;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "phrases_positions")
public class Phrase_Position {

    private int phrase_id;
    private int pos_id;

    public Phrase_Position(int phrase_id, int pos_id) {
        this.phrase_id = phrase_id;
        this.pos_id = pos_id;
    }

    public int getPhrase_id() {
        return phrase_id;
    }

    public void setPhrase_id(int phrase_id) {
        this.phrase_id = phrase_id;
    }

    public int getPos_id() {
        return pos_id;
    }

    public void setPos_id(int pos_id) {
        this.pos_id = pos_id;
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
        final Phrase_Position other = (Phrase_Position) obj;
        if (!Objects.equals(this.pos_id, other.pos_id)) {
            return false;
        }
        return Objects.equals(this.phrase_id, other.phrase_id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Phrase Position{");
        sb.append("phrase_id = ").append(phrase_id);
        sb.append(", pos_id = ").append(pos_id).append("}");
        return sb.toString();
    }
}
