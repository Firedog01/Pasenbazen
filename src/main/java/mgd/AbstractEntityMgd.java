package mgd;

import org.bson.codecs.pojo.annotations.BsonProperty;

public abstract class AbstractEntityMgd implements AutoCloseable {

    public AbstractEntityMgd() {}
    public AbstractEntityMgd(UniqueIdMgd entityId) {
        this.entityId = entityId;
    }

    @BsonProperty("_id")
    private UniqueIdMgd entityId;

    public UniqueIdMgd getEntityId() {
        return entityId;
    }

    public void setEntityId(UniqueIdMgd entityId) {
        this.entityId = entityId;
    }
}
