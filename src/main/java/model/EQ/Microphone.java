package model.EQ;

import jakarta.persistence.*;

@Entity
@Table(name = "microphone")
@DiscriminatorValue("MICROPHONE")
@PrimaryKeyJoinColumn(name = "equipment_id")
public class Microphone extends Equipment{

    @Column(name = "sensitivity")
    private String sensitivity;

    public Microphone(double firstDayCost, double nextDaysCost, double bail, String name,
                            int id, String sensitivity) {
        super(firstDayCost, nextDaysCost, bail, name, id);
        this.sensitivity = sensitivity;
    }

    public Microphone() {

    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Microphone{");
        sb.append("brightness='").append(sensitivity).append('\'');
        sb.append('}');
        return super.toString() + sb.toString();
    }

    public String getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(String brightness) {
        this.sensitivity = brightness;
    }
}
