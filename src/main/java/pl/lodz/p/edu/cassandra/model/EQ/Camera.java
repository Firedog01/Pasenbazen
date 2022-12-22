package pl.lodz.p.edu.cassandra.model.EQ;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import pl.lodz.p.edu.cassandra.exception.EquipmentException;

import java.util.Objects;
import java.util.UUID;

@Entity(defaultKeyspace = "just_rent")
@CqlName("equipments")
public class Camera extends Equipment {

    @CqlName("resolution")
    private String resolution;

    public Camera(double firstDayCost,
                  double nextDaysCost,
                  double bail,
                  String name,
                  String resolution,
                  String description,
                  String discriminator
    ) throws EquipmentException {
        super(firstDayCost, nextDaysCost, bail, name,description, discriminator);
        this.resolution = resolution;
    }

    protected Camera() {}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Camera camera = (Camera) o;
        return Objects.equals(resolution, camera.resolution);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), resolution);
    }
}