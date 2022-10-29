package mgd.EQ;

import mgd.UniqueIdMgd;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator(key = "_clazz", value = "lighting")
public class LightingMgd extends EquipmentMgd {

    @BsonProperty("brightness")
    private String brightness;

    @BsonCreator
    public LightingMgd(@BsonProperty("_id") UniqueIdMgd entityId,
                   @BsonProperty("name") String name,
                   @BsonProperty("bail") double bail,
                   @BsonProperty("first_day_cost") double firstDayCost,
                   @BsonProperty("next_day_cost") double nextDaysCost,
                   @BsonProperty("archive") boolean archive,
                   @BsonProperty("description") String description,
                   @BsonProperty("missing") boolean missing,
                   @BsonProperty("brightness") String brightness
    ) {
        super(entityId, name, bail, firstDayCost, nextDaysCost,
                archive, description, missing);
        this.brightness = brightness;
    }

    @Override
    public void close() throws Exception {

    }

    public String getBrightness() {
        return brightness;
    }

    public void setBrightness(String brightness) {
        this.brightness = brightness;
    }
}
