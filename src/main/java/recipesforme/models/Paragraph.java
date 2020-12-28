package recipesforme.models;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "paragraphs")
public class Paragraph {

    @Id
    private UUID paragraphId;

    private String title;

    public Paragraph() {
        this.paragraphId = UUID.randomUUID();
        this.title = "Intro";
    }

    public Paragraph(String title) {
        this.paragraphId = UUID.randomUUID();
        this.title = title;
    }

    public UUID getParagraphId() {
        return paragraphId;
    }

    public void setParagraphId(UUID paragraphId) {
        this.paragraphId = paragraphId;
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
        return Objects.equals(this.paragraphId, other.paragraphId);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Paragraph{");
        sb.append("paragraphId = ").append(paragraphId);
        sb.append(", title = '").append(title).append("'}");
        return sb.toString();
    }
}
