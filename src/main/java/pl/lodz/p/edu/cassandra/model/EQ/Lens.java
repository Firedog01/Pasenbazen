package pl.lodz.p.edu.cassandra.model.EQ;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import pl.lodz.p.edu.cassandra.exception.EquipmentException;

import java.util.Objects;
import java.util.UUID;

@Entity(defaultKeyspace = "just_rent")
@CqlName("equipments")
public class Lens extends Equipment {

    @CqlName("focalLength")
    private String focalLength;

    public Lens(double firstDayCost,
                double nextDaysCost,
                double bail,
                String name,
                String focalLength,
                String description,
                String discriminator
    ) throws EquipmentException {
        super(firstDayCost, nextDaysCost, bail, name, description, discriminator);
        this.focalLength = focalLength;
    }

    protected Lens() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Lens lens = (Lens) o;
        return Objects.equals(focalLength, lens.focalLength);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), focalLength);
    }
}
