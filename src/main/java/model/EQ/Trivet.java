package model.EQ;

public class Trivet extends Equipment{
    private double weigh;

    public Trivet(double firstDayCost, double nextDaysCost, double bail, String name,
                         int id, double weigh) {

        super(firstDayCost, nextDaysCost, bail, name, id);
        this.weigh = weigh;
    }

    public Trivet() {

    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Trivet{");
        sb.append("weigh=").append(weigh);
        sb.append('}');
        return super.toString() + sb.toString();
    }

    public double getWeigh() {
        return weigh;
    }

    public void setWeigh(double weigh) {
        this.weigh = weigh;
    }
}
