package org.openu.recipesForMe.Models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "words_positions")
public class Word_Position {

    private String word;
    private int pos_id;

    public Word_Position(String word, int pos_id) {
        this.word = word;
        this.pos_id = pos_id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
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
        final Word_Position other = (Word_Position) obj;
        if (!Objects.equals(this.word, other.word)) {
            return false;
        }
        return Objects.equals(this.pos_id, other.pos_id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Word Position{");
        sb.append("word = '").append(word).append("'");
        sb.append(", pos_id = ").append(pos_id).append("}");
        return sb.toString();
    }
}
