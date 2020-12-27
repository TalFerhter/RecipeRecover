package recipesforme.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "paragraphs")
public class Paragraph {

    @Id
    private UUID paragraph_id;

    private String title;

    public Paragraph() {
        this.paragraph_id = UUID.randomUUID();
        this.title = "Intro";
    }

    public Paragraph(String title) {
        this.paragraph_id = UUID.randomUUID();
        this.title = title;
    }

    public UUID getParagraph_id() {
        return paragraph_id;
    }

    public void setParagraph_id(UUID paragraph_id) {
        this.paragraph_id = paragraph_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        final Paragraph other = (Paragraph) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        return Objects.equals(this.paragraph_id, other.paragraph_id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Paragraph{");
        sb.append("paragraph_id = ").append(paragraph_id);
        sb.append(", title = '").append(title).append("'}");
        return sb.toString();
    }
}
