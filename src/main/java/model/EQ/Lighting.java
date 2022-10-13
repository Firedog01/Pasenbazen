package model.EQ;

import jakarta.persistence.*;

@Entity
@Table(name = "LIGHT_EQ")
@DiscriminatorValue("LIGHT")
@PrimaryKeyJoinColumn(name = "LIGHT_EQID")
public class Lighting extends Equipment {
    @Column(name = "BRIGHTNESS")
    private String brightness;

    public Lighting(double firstDayCost, double nextDaysCost, double bail, String name,
                    int id, String brightness) {
        super(firstDayCost, nextDaysCost, bail, name, id);
        this.brightness = brightness;
    }

    public Lighting() {

    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Lighting{");
        sb.append("brightness='").append(brightness).append('\'');
        sb.append('}');
        return super.toString() + sb.toString();
    }

    public String getBrightness() {
        return brightness;
    }

    public void setBrightness(String brightness) {
        this.brightness = brightness;
    }
}
