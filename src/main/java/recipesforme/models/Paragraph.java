package recipesforme.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "paragraphs")
public class Paragraph {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int paragraph_id;

    private String title;

    protected Paragraph() {}

    public Paragraph(int paragraphId, String title) {
        this.paragraph_id = paragraphId;
        this.title = title;
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
