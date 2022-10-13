package model.EQ;


import jakarta.persistence.*;

@Entity
@Table(name = "LENS_EQ")
@DiscriminatorValue("LENS")
@PrimaryKeyJoinColumn(name = "LENS_EQ_ID")
public class Lens extends Equipment{
    @Column(name = "FLENGTH")
    private String focalLength;

    public Lens(double firstDayCost, double nextDaysCost, double bail, String name,
                        int id, String focalLength) {
        super(firstDayCost, nextDaysCost, bail, name, id);
        this.focalLength = focalLength;
    }

    public Lens() {

    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Lens{");
        sb.append("focalLength='").append(focalLength).append('\'');
        sb.append('}');
        return super.toString() + sb.toString();
    }

    public String getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(String focalLength) {
        this.focalLength = focalLength;
    }
}
