package mgd.EQ;

import lombok.AllArgsConstructor;
import mgd.UniqueIdMgd;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator(key = "_clazz", value = "camera")
public class CameraMgd extends EquipmentMgd {

    @BsonProperty("resolution")
    private String resolution;

    @BsonCreator
    public CameraMgd(@BsonProperty("_id") UniqueIdMgd entityId,
                     @BsonProperty("name") String name,
                     @BsonProperty("bail") double bail,
                     @BsonProperty("first_day_cost") double firstDayCost,
                     @BsonProperty("next_day_cost") double nextDaysCost,
                     @BsonProperty("archive") boolean archive,
                     @BsonProperty("description") String description,
                     @BsonProperty("missing") boolean missing,
                     @BsonProperty("resolution") String resolution
    ) {
        super(entityId, name, bail, firstDayCost, nextDaysCost,
                archive, description, missing);
        this.resolution = resolution;
    }

    @Override
    public void close() throws Exception {

    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CameraMgd)) return false;
        if (!super.equals(o)) return false;

        CameraMgd cameraMgd = (CameraMgd) o;

        return resolution != null ? resolution.equals(cameraMgd.resolution) : cameraMgd.resolution == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (resolution != null ? resolution.hashCode() : 0);
        return result;
    }
}
