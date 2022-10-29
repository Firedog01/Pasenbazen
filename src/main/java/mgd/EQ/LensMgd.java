package mgd.EQ;

import mgd.UniqueIdMgd;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class LensMgd extends EquipmentMgd {

    @BsonProperty("focal_length")
    private String focalLength;

    @BsonCreator
    public LensMgd(@BsonProperty("_id") UniqueIdMgd entityId,
                     @BsonProperty("name") String name,
                     @BsonProperty("bail") double bail,
                     @BsonProperty("first_day_cost") double firstDayCost,
                     @BsonProperty("next_day_cost") double nextDaysCost,
                     @BsonProperty("archive") boolean archive,
                     @BsonProperty("description") String description,
                     @BsonProperty("missing") boolean missing,
                     @BsonProperty("focal_length") String focalLength
    ) {
        super(entityId, name, bail, firstDayCost, nextDaysCost,
                archive, description, missing);
        this.focalLength = focalLength;
    }

    @Override
    public void close() throws Exception {

    }

    public String getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(String focalLength) {
        this.focalLength = focalLength;
    }
}
