package mgd.EQ;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import mgd.UniqueIdMgd;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator(key = "_clazz", value = "camera")
public class CameraMgd extends EquipmentMgd {

    @BsonProperty("resolution")
    @JsonProperty("resolution")
    private String resolution;

    @BsonCreator
    @JsonCreator
    public CameraMgd(@BsonProperty("_id") @JsonProperty("_id") UniqueIdMgd entityId,
                     @BsonProperty("name") @JsonProperty("name") String name,
                     @BsonProperty("bail") @JsonProperty("bail") double bail,
                     @BsonProperty("first_day_cost") @JsonProperty("first_day_cost") double firstDayCost,
                     @BsonProperty("next_day_cost") @JsonProperty("next_day_cost") double nextDaysCost,
                     @BsonProperty("archive") @JsonProperty("archive") boolean archive,
                     @BsonProperty("description") @JsonProperty("description") String description,
                     @BsonProperty("missing") @JsonProperty("missing") boolean missing,
                     @BsonProperty("resolution") @JsonProperty("resolution") String resolution
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
