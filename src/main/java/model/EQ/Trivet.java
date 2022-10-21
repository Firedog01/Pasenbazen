package model.EQ;

import exception.EquipmentException;

public class Trivet extends Equipment{

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
