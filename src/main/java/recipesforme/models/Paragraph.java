package recipesforme.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "paragraphs")
public class Paragraph {

    @Id
    @Column(name = "paragraph_id")
    @Type(type="pg-uuid")
    private UUID paragraphId;

    private String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paragraph")
    @JsonIgnore
    private Set<Position> positions = new HashSet<>();

    public Paragraph() {
        this.paragraphId = UUID.randomUUID();
        this.title = "General";
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

    public Set<Position> getPositions() {
        return positions;
    }

    public void setPositions(Set<Position> positions) {
        this.positions = positions;
    }

    public void addPositions(Position position) {
        this.positions.add(position);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Paragraph{");
        sb.append("paragraphId = ").append(paragraphId);
        sb.append(", title = '").append(title).append("'}");
        return sb.toString();
    }
}
