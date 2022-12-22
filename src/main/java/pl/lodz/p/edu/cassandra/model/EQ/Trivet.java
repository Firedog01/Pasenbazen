package pl.lodz.p.edu.cassandra.model.EQ;


import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import pl.lodz.p.edu.cassandra.exception.EquipmentException;

import java.util.Objects;
import java.util.UUID;

@Entity(defaultKeyspace = "just_rent")
@CqlName("equipments")
public class Trivet extends Equipment{

    @CqlName("weight")
    private double weight;

    public Trivet(double firstDayCost,
                  double nextDaysCost,
                  double bail,
                  String name,
                  double weight,
                  String discriminator
    ) throws EquipmentException {
        super(firstDayCost, nextDaysCost, bail, name, discriminator);
        this.weight = weight;
    }

    public Trivet(UUID uuid, String name, double bail, double firstDayCost, double nextDaysCost, boolean archive,
                String description, String discriminator, boolean missing, double weight) throws EquipmentException {
        super(uuid, name, bail, firstDayCost, nextDaysCost, archive, description, discriminator, missing);
        this.weight = weight;
    }

    protected Trivet() {}

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Trivet{");
        sb.append("weigh=").append(weight);
        sb.append('}');
        return super.toString() + sb.toString();
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weigh) {
        this.weight = weigh;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Trivet trivet = (Trivet) o;
        return Double.compare(trivet.weight, weight) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), weight);
    }
}