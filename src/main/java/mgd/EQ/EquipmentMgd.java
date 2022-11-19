package mgd.EQ;

import mgd.AbstractEntityMgd;
import mgd.UniqueIdMgd;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator(key = "_clazz")
public abstract class EquipmentMgd extends AbstractEntityMgd {

    @BsonProperty("name")
    private String name;

    @BsonProperty("bail")
    private double bail;

    @BsonProperty("first_day_cost")
    private double firstDayCost;

    @BsonProperty("next_day_cost")
    private double nextDaysCost;

    @BsonProperty("archive")
    private boolean archive;

    @BsonProperty("description")
    private String description;

    @BsonProperty("missing")
    private boolean missing;

    public EquipmentMgd(UniqueIdMgd entityId,
                        String name,
                        double bail,
                        double firstDayCost,
                        double nextDaysCost,
                        boolean archive,
                        String description,
                        boolean missing) {
        super(entityId);
        this.firstDayCost = firstDayCost;
        this.nextDaysCost = nextDaysCost;
        this.bail = bail;
        this.name = name;
        this.archive = archive;
        this.description = description;
        this.missing = missing;
    }

    public double getFirstDayCost() {
        return firstDayCost;
    }

    public double getNextDaysCost() {
        return nextDaysCost;
    }

    public double getBail() {
        return bail;
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

    public boolean isMissing() {
        return missing;
    }

    public void setFirstDayCost(double firstDayCost) {
        this.firstDayCost = firstDayCost;
    }

    public void setNextDaysCost(double nextDaysCost) {
        this.nextDaysCost = nextDaysCost;
    }

    public void setBail(double bail) {
        this.bail = bail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMissing(boolean missing) {
        this.missing = missing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EquipmentMgd)) return false;

        EquipmentMgd that = (EquipmentMgd) o;

        if (Double.compare(that.bail, bail) != 0) return false;
        if (Double.compare(that.firstDayCost, firstDayCost) != 0) return false;
        if (Double.compare(that.nextDaysCost, nextDaysCost) != 0) return false;
        if (archive != that.archive) return false;
        if (missing != that.missing) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        temp = Double.doubleToLongBits(bail);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(firstDayCost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(nextDaysCost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (archive ? 1 : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (missing ? 1 : 0);
        return result;
    }
}
