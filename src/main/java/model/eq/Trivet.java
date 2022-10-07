package model.eq;

public class Trivet extends Equipment{
    private double weigh;

    public Trivet(double firstDayCost, double nextDaysCost, double bait, String name, boolean archive,
                        String description, int id, boolean missing, double weigh) {

        super(firstDayCost, nextDaysCost, bait, name, archive, description, id, missing);
        this.weigh = weigh;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Trivet{");
        sb.append("weigh=").append(weigh);
        sb.append('}');
        return super.toString() + sb.toString();
    }
}
