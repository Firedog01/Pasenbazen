package pl.lodz.p.edu.cassandra.model.EQ;

import pl.lodz.p.edu.cassandra.exception.EquipmentException;
import pl.lodz.p.edu.cassandra.model.Rent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public abstract class Equipment implements Serializable {
    
    private UUID uuid;

    private String name;

    private double bail;

    private double firstDayCost;

    private double nextDaysCost;

    private boolean archive;

    private String description;

    private boolean missing;

    private List<Rent> equipmentRents = new ArrayList<>();

    public Equipment(double firstDayCost, double nextDaysCost, double bail, String name
    ) throws EquipmentException {
        if (firstDayCost <= 0.0 || nextDaysCost <= 0.0 || bail <= 0.0) {
            throw new EquipmentException("Prosze podac prawidlowy koszt wypozyczenia");
        }
        if (name.length() == 0) {
            throw new EquipmentException("Prosze podac prawidlowa nazwe");
        }
        this.firstDayCost = firstDayCost;
        this.nextDaysCost = nextDaysCost;
        this.bail = bail;
        this.name = name;
        this.archive = false;
        this.description = null;
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

    public double getBail() {
        return bail;
    }

    public void setBail(double bail) {
        this.bail = bail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isMissing() {
        return missing;
    }

    public void setMissing(boolean missing) {
        this.missing = missing;
    }

    public List<Rent> getEquipmentRents() {
        return equipmentRents;
    }

    public void setEquipmentRents(List<Rent> equipmentRents) {
        this.equipmentRents = equipmentRents;
    }
}