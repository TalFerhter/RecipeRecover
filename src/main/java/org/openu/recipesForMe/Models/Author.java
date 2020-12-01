package org.openu.recipesForMe.Models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int author_id;

    private String author_name;

    public Author(int author_id, String author_name) {
        this.author_id = author_id;
        this.author_name = author_name;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
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
        final Author other = (Author) obj;
        if (!Objects.equals(this.author_name, other.author_name)) {
            return false;
        }
        return Objects.equals(this.author_id, other.author_id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Author{");
        sb.append("author_id = ").append(author_id);
        sb.append(", name = '").append(author_name).append("'}");
        return sb.toString();
    }
}
