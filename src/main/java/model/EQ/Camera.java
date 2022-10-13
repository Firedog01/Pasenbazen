package model.EQ;

import jakarta.persistence.*;

@Entity
@Table(name = "camera")
@DiscriminatorValue("CAMERA")
@PrimaryKeyJoinColumn(name = "equipment_id")
public class Camera extends Equipment {

    @Column(name = "resolution")
    private String resolution;

    public Camera(double firstDayCost, double nextDaysCost, double bail, String name, int id, String resolution) {
        super(firstDayCost, nextDaysCost, bail, name, id);
        this.resolution = resolution;
    }

    public Camera() {

    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Camera{");
        sb.append("resolution='").append(resolution).append('\'');
        sb.append('}');
        return super.toString() + sb.toString();
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
}
