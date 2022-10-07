package model.EQ;

public abstract class Equipment {
    private double firstDayCost;
    private double nextDaysCost;
    private double bait;

    private String name;

    private boolean archive;

    private String description; //Maybe something other?

    private int id; //int? Integer?

    private boolean missing;

    public Equipment(double firstDayCost, double nextDaysCost, double bail, String name, boolean archive,
                     String description, int id, boolean missing) {

        this.firstDayCost = firstDayCost;
        this.nextDaysCost = nextDaysCost;
        this.bait = bail;
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

    public double getFirstDayCost() {
        return firstDayCost;
    }

    public double getNextDaysCost() {
        return nextDaysCost;
    }

    public double getBail() {
        return bait;
    }

    public String getName() {
        return name;
    }

    public boolean isArchive() {
        return archive;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public boolean isMissing() {
        return missing;
    }
}
