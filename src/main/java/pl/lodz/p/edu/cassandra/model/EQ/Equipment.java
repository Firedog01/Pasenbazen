package pl.lodz.p.edu.cassandra.model.EQ;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import pl.lodz.p.edu.cassandra.exception.EquipmentException;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity(defaultKeyspace = "just_rent")
@CqlName("equipments")
public class Equipment implements Serializable {

    @CqlName("equipmentUuid")
    @PartitionKey
    private UUID uuid;

    @Size(min = 1)
    @CqlName("name")
    private String name;

    @Positive
    @CqlName("bail")
    private double bail;

    @Positive
    @CqlName("firstDayCost")
    private double firstDayCost;

    @Positive
    @CqlName("nextDayCost")
    private double nextDaysCost;

    @CqlName("isArchived")
    private boolean archive;

    @CqlName("description")
    private String description;

    @CqlName("discriminator")
    private String discriminator;


    @CqlName("isMissing")
    private boolean missing;

//    private List<Rent> equipmentRents = new ArrayList<>();

    public Equipment(double firstDayCost, double nextDaysCost, double bail, String name, String description,  String discriminator
    ) throws EquipmentException {
        if (firstDayCost <= 0.0 || nextDaysCost <= 0.0 || bail <= 0.0) {
            throw new EquipmentException("Prosze podac prawidlowy koszt wypozyczenia");
        }
        if (name.length() == 0) {
            throw new EquipmentException("Prosze podac prawidlowa nazwe");
        }
        this.uuid = UUID.randomUUID();
        this.firstDayCost = firstDayCost;
        this.nextDaysCost = nextDaysCost;
        this.bail = bail;
        this.name = name;
        this.discriminator = discriminator;
        this.archive = false;
        this.description = description;
        this.missing = false;
    }


    protected Equipment() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("-------------------- EquipmentException{");
        sb.append("uuid=").append(uuid);
        sb.append(", firstDayCost=").append(firstDayCost);
        sb.append(", nextDaysCost=").append(nextDaysCost);
        sb.append(", bail=").append(bail);
        sb.append(", name='").append(name).append('\'');
        sb.append(", archive=").append(archive);
        sb.append(", description='").append(description).append('\'');
        sb.append(", missing=").append(missing);
        sb.append('}');
        return sb.toString();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBail() {
        return bail;
    }

    public void setBail(double bail) {
        this.bail = bail;
    }

    public double getFirstDayCost() {
        return firstDayCost;
    }

    public void setFirstDayCost(double firstDayCost) {
        this.firstDayCost = firstDayCost;
    }

    public double getNextDaysCost() {
        return nextDaysCost;
    }

    public void setNextDaysCost(double nextDaysCost) {
        this.nextDaysCost = nextDaysCost;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    public boolean isMissing() {
        return missing;
    }

    public void setMissing(boolean missing) {
        this.missing = missing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipment equipment = (Equipment) o;
        return Double.compare(equipment.bail, bail) == 0 && Double.compare(equipment.firstDayCost, firstDayCost) == 0 && Double.compare(equipment.nextDaysCost, nextDaysCost) == 0 && archive == equipment.archive && missing == equipment.missing && Objects.equals(uuid, equipment.uuid) && Objects.equals(name, equipment.name) && Objects.equals(description, equipment.description) && Objects.equals(discriminator, equipment.discriminator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, bail, firstDayCost, nextDaysCost, archive, description, discriminator, missing);
    }
}
//    public List<Rent> getEquipmentRents() {
//        return equipmentRents;
//    }
//
//    public void setEquipmentRents(List<Rent> equipmentRents) {
//        this.equipmentRents = equipmentRents;
//    }
//}