package pl.lodz.p.edu.rest.model.EQ;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import pl.lodz.p.edu.rest.exception.EquipmentException;

@Entity
@Table(name = "trivet")
@DiscriminatorValue("TRIVET")
@PrimaryKeyJoinColumn(name = "equipment_id")
public class Trivet extends Equipment{

    @NotNull
    @Column(name = "weight")
    private double weight;

    public Trivet(double firstDayCost,
                  double nextDaysCost,
                  double bail,
                  String name,
                  double weight
    ) throws EquipmentException {
        super(firstDayCost, nextDaysCost, bail, name);
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
}