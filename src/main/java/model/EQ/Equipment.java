package model.EQ;

import exception.EquipmentException;
import model.Rent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public abstract class Equipment {


    private UUID eqUUID;

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
        if(firstDayCost <= 0.0 || nextDaysCost <= 0.0 || bail <= 0.0) {
            throw new EquipmentException("Prosze podac prawidlowy koszt wypozyczenia");
        }
        if(name.length() == 0) {
            throw new EquipmentException("Prosze podac prawidlowa nazwe");
        }
        this.eqUUID = UUID.randomUUID();
        this.firstDayCost = firstDayCost;
        this.nextDaysCost = nextDaysCost;
        this.bail = bail;
        this.name = name;
        this.archive = false;
        this.description = null;
        this.missing = false;
    }

    protected Equipment() {}

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("-------------------- Equipment{");
        sb.append("id=").append(eqUUID);
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

    public UUID getEqUUID() {
        return eqUUID;
    }

    public double getFirstDayCost() {
        return firstDayCost;
    }

    public double getNextDaysCost() {
        return nextDaysCost;
    }

    public double getBail() {
        return bail;
    }

    public String getName() {
        return name;
    }

    public boolean isArchive() {
        return archive;
    }

    public String getDescription() {
        return description;
    }

    public boolean isMissing() {
        return missing;
    }

    public List<Rent> getEquipmentRents() {
        return equipmentRents;
    }


    public void setFirstDayCost(double firstDayCost) {
        this.firstDayCost = firstDayCost;
    }

    public void setNextDaysCost(double nextDaysCost) {
        this.nextDaysCost = nextDaysCost;
    }

    public void setBail(double bail) {
        this.bail = bail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMissing(boolean missing) {
        this.missing = missing;
    }

    public void setEquipmentRents(List<Rent> equipmentRents) {
        this.equipmentRents = equipmentRents;
    }
}
