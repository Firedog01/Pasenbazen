package model.EQ;

import jakarta.persistence.*;

@Entity
@Table(name = "trivet")
@DiscriminatorValue("TRIVET")
@PrimaryKeyJoinColumn(name = "equipment_id")
public class Trivet extends Equipment{

    @Column(name = "weight")
    private double weight;

    public Trivet(double firstDayCost, double nextDaysCost, double bail, String name,
                         int id, double weight) {

        super(firstDayCost, nextDaysCost, bail, name, id);
        this.weight = weight;
    }

    public Trivet() {

    }

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
