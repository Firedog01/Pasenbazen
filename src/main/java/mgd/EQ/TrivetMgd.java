package mgd.EQ;

import mgd.UniqueIdMgd;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator(key = "_clazz", value = "trivet")
public class TrivetMgd extends EquipmentMgd {

    @BsonProperty("weight")
    private double weight;

    @BsonCreator
    public TrivetMgd(@BsonProperty("_id") UniqueIdMgd entityId,
                         @BsonProperty("name") String name,
                         @BsonProperty("bail") double bail,
                         @BsonProperty("first_day_cost") double firstDayCost,
                         @BsonProperty("next_day_cost") double nextDaysCost,
                         @BsonProperty("archive") boolean archive,
                         @BsonProperty("description") String description,
                         @BsonProperty("missing") boolean missing,
                         @BsonProperty("weight") double weight
    ) {
        super(entityId, name, bail, firstDayCost, nextDaysCost,
                archive, description, missing);
        this.weight = weight;
    }

    @Override
    public void close() throws Exception {

    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrivetMgd)) return false;
        if (!super.equals(o)) return false;

        TrivetMgd trivetMgd = (TrivetMgd) o;

        return Double.compare(trivetMgd.weight, weight) == 0;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
