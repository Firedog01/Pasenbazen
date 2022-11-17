package pl.lodz.p.edu.rest.model.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import pl.lodz.p.edu.rest.model.Equipment;
import pl.lodz.p.edu.rest.model.users.Admin;

public class EquipmentDTO {

    private String name;

    private double bail;

    private double firstDayCost;

    private double nextDaysCost;

    private boolean archive;

    private String description;

    private boolean missing;

    public EquipmentDTO() {}

    public EquipmentDTO(Equipment e) {
        name = e.getName();
        bail = e.getBail();
        firstDayCost = e.getFirstDayCost();
        nextDaysCost = e.getNextDaysCost();
        archive = e.isArchive();
        missing = e.isMissing();
        description = e.getDescription();
    }

    @Override
    public String toString() {
        return "EquipmentDTO{" +
                "name='" + name + '\'' +
                ", bail=" + bail +
                ", firstDayCost=" + firstDayCost +
                ", nextDaysCost=" + nextDaysCost +
                ", archive=" + archive +
                ", description='" + description + '\'' +
                ", missing=" + missing +
                '}';
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

    public boolean isMissing() {
        return missing;
    }

    public void setMissing(boolean missing) {
        this.missing = missing;
    }
}
