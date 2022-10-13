package model.EQ;

import jakarta.persistence.*;

@Entity
@Table(name = "light")
@DiscriminatorValue("LIGHT")
@PrimaryKeyJoinColumn(name = "equipment_id")
public class Lighting extends Equipment {
    @Column(name = "brightness")
    private String brightness;

    public Lighting(double firstDayCost,
                    double nextDaysCost,
                    double bail,
                    String name,
                    String brightness
    ) {
        super(firstDayCost, nextDaysCost, bail, name);
        this.brightness = brightness;
    }

    protected Lighting() {}

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
