package mgd.EQ;

import mgd.UniqueIdMgd;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator(key = "_clazz", value = "trivet")
public class TrivetMgd extends EquipmentMgd {

    @BsonProperty("sensitivity")
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
}
