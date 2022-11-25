package mgd;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.codecs.pojo.annotations.BsonProperty;

public abstract class AbstractEntityMgd implements AutoCloseable {

    public AbstractEntityMgd() {}
    @JsonCreator
    public AbstractEntityMgd(@BsonProperty("_id") @JsonProperty("entityId") UniqueIdMgd entityId) {
        this.entityId = entityId;
    }

    @BsonProperty("_id")
    @JsonProperty("entityId")
    private UniqueIdMgd entityId;
    public UniqueIdMgd getEntityId() {
        return entityId;
    }

    public void setEntityId(UniqueIdMgd entityId) {
        this.entityId = entityId;
    }
}
