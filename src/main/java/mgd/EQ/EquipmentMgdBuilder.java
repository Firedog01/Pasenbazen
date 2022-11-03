package mgd.EQ;

import mgd.UniqueIdMgd;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

public class EquipmentMgdBuilder {

    private UniqueIdMgd entityId;

    private String name;

    private double bail;

    private double firstDayCost;

    private double nextDaysCost;

    private boolean archive;

    private String description;

    private boolean missing;


    // child parameters
    private String resolution;

    private String focalLength;

    private double weight;

    public EquipmentMgdBuilder() {}

    public EquipmentMgdBuilder setEntityId(UUID entityId) {
        this.entityId = new UniqueIdMgd(entityId);
        return this;
    }

    public EquipmentMgdBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public EquipmentMgdBuilder setBail(double bail) {
        this.bail = bail;
        return this;
    }

    public EquipmentMgdBuilder setFirstDayCost(double firstDayCost) {
        this.firstDayCost = firstDayCost;
        return this;
    }

    public EquipmentMgdBuilder setNextDaysCost(double nextDaysCost) {
        this.nextDaysCost = nextDaysCost;
        return this;
    }

    public EquipmentMgdBuilder setArchive(boolean archive) {
        this.archive = archive;
        return this;
    }

    public EquipmentMgdBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public EquipmentMgdBuilder setMissing(boolean missing) {
        this.missing = missing;
        return this;
    }

    // children setters
    public EquipmentMgdBuilder setResolution(String resolution) {
        this.resolution = resolution;
        return this;
    }

    public EquipmentMgdBuilder setFocalLength(String focalLength) {
        this.focalLength = focalLength;
        return this;
    }

    public EquipmentMgdBuilder setWeight(double weight) {
        this.weight = weight;
        return this;
    }


    public EquipmentMgd build(String type) {
        switch(type) {
            case "camera" -> {
                return new CameraMgd(entityId, name, bail,
                        firstDayCost, nextDaysCost, archive, description, missing, resolution);
            }
            case "trivet" -> {
                return new TrivetMgd(entityId, name, bail,
                        firstDayCost, nextDaysCost, archive, description, missing, weight);
            }
            case "lens" -> {
                return new LensMgd(entityId, name, bail,
                        firstDayCost, nextDaysCost, archive, description, missing, focalLength);
            }
        }
        return null;
    }
}
