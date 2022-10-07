package model.eq;

public abstract class Equipment {
    private double firstDayCost;
    private double nextDaysCost;
    private double bait;

    private String name;

    private boolean archive;

    private String description; //Maybe something other?

    private int id; //int? Integer?

    private boolean missing;

    public Equipment(double firstDayCost, double nextDaysCost, double bait, String name, boolean archive,
                     String description, int id, boolean missing) {

        this.firstDayCost = firstDayCost;
        this.nextDaysCost = nextDaysCost;
        this.bait = bait;
        this.name = name;
        this.archive = archive;
        this.description = description;
        this.id = id;
        this.missing = missing;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Equipment{");
        sb.append("firstDayCost=").append(firstDayCost);
        sb.append(", nextDaysCost=").append(nextDaysCost);
        sb.append(", bait=").append(bait);
        sb.append(", name='").append(name).append('\'');
        sb.append(", archive=").append(archive);
        sb.append(", description='").append(description).append('\'');
        sb.append(", id=").append(id);
        sb.append(", missing=").append(missing);
        sb.append('}');
        return sb.toString();
    }
}
