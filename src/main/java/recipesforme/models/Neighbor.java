//package recipesforme.models;
//
//import javax.persistence.*;
//import java.util.UUID;
//
//@Entity
//@Table(name = "neighbor_position")
//public class Neighbor {
//
//    @Id
//    @Column(name = "pos_id")
//    private UUID currPos;
//
//    private UUID nextPos;
//
//    @OneToOne
//    @MapsId
//    @JoinColumn(name = "pos_id")
//    private Position position;
//
//    public Neighbor(){}
//
//    public Neighbor(UUID currPos) {
//        this.currPos = currPos;
//    }
//
//    public Neighbor(Position position) {
//        this.position = position;
//        this.currPos = position.getPos_id();
//    }
//
//    public UUID getCurrPos() {
//        return currPos;
//    }
//
//    public void setCurrPos(UUID currPos) {
//        this.currPos = currPos;
//    }
//
//    public UUID getNextPos() {
//        return nextPos;
//    }
//
//    public void setNextPos(UUID nextPos) {
//        this.nextPos = nextPos;
//    }
//
//    public Position getPosition() {
//        return position;
//    }
//
//    public void setPosition(Position position) {
//        this.position = position;
//        this.currPos = position.getPos_id();
//    }
//}
