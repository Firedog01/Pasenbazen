package mgd.EQ;

import mgd.UniqueIdMgd;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class MicrophoneMgd extends EquipmentMgd {

    @BsonProperty("sensitivity")
    private String sensitivity;

    @BsonCreator
    public MicrophoneMgd(@BsonProperty("_id") UniqueIdMgd entityId,
                       @BsonProperty("name") String name,
                       @BsonProperty("bail") double bail,
                       @BsonProperty("first_day_cost") double firstDayCost,
                       @BsonProperty("next_day_cost") double nextDaysCost,
                       @BsonProperty("archive") boolean archive,
                       @BsonProperty("description") String description,
                       @BsonProperty("missing") boolean missing,
                       @BsonProperty("sensitivity") String sensitivity
    ) {
        super(entityId, name, bail, firstDayCost, nextDaysCost,
                archive, description, missing);
        this.sensitivity = sensitivity;
    }

    @Override
    public void close() throws Exception {

    }

    public String getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(String sensitivity) {
        this.sensitivity = sensitivity;
    }
}
