package recipesforme.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "neighbor_position")
public class Neighbor {

    @Id
    @Type(type="pg-uuid")
    private UUID pos_id;

    @Type(type="pg-uuid")
    private UUID neighbor_pos;

    @OneToOne(mappedBy = "neighbor", fetch = FetchType.LAZY)
    @JsonIgnore
    private Position position;

    public Neighbor(){}

    public Neighbor(UUID pos_id) {
        this.pos_id = pos_id;
    }

    public Neighbor(Position position) {
        this.position = position;
        this.pos_id = position.getPos_id();
    }

    public UUID getCurrPos() {
        return pos_id;
    }

    public void setCurrPos(UUID pos_id) {
        this.pos_id = pos_id;
    }

    public UUID getNeighborPos() {
        return neighbor_pos;
    }

    public void setNeighborPos(UUID neighborPos) {
        this.neighbor_pos = neighborPos;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
