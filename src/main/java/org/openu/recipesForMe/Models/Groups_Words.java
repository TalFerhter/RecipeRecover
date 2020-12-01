package org.openu.recipesForMe.Models;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "groups_words")
public class Groups_Words {

    private String word;
    private int group_id;

    public Groups_Words(String word, int group_id) {
        this.word = word;
        this.group_id = group_id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
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
        final Groups_Words other = (Groups_Words) obj;
        if (!Objects.equals(this.group_id, other.group_id)) {
            return false;
        }
        return Objects.equals(this.word, other.word);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Word Group{");
        sb.append("word = '").append(word).append("'");
        sb.append(", group_id = ").append(group_id).append("'}");
        return sb.toString();
    }
}
