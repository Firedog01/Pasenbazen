package model.EQ;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("lightning")
public class Lighting extends Equipment {
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
