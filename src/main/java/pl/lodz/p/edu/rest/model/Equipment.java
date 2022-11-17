package pl.lodz.p.edu.rest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import pl.lodz.p.edu.rest.model.DTO.EquipmentDTO;

@Entity
@Table(name = "equipment")
@Access(AccessType.FIELD)
public class Equipment extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(initialValue = 0, name = "equipment_sequence_generator")
    @Column(name = "equipment_id", updatable = false)
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "bail")
    private double bail;

    @Column(name = "first_day_cost")
    private double firstDayCost;

    @Column(name = "next_day_cost")
    private double nextDaysCost;

    @Column(name = "archive")
    private boolean archive;

    @Column(name = "description")
    private String description;

    @Column(name = "missing")
    private boolean missing;


    public Equipment(double firstDayCost, double nextDaysCost, double bail, String name) {
        this.firstDayCost = firstDayCost;
        this.nextDaysCost = nextDaysCost;
        this.bail = bail;
        this.name = name;
        this.archive = false;
        this.description = null;
        this.missing = false;
    }

    public Equipment(EquipmentDTO equipmentDTO) {
        this.merge(equipmentDTO);
    }

    public boolean verify() {
        return !(firstDayCost <= 0.0 || nextDaysCost <= 0.0 || bail <= 0.0)
                && !name.isEmpty();
    }

    public void merge(EquipmentDTO equipmentDTO) {
        this.name = equipmentDTO.getName();
        this.bail = equipmentDTO.getBail();
        this.firstDayCost = equipmentDTO.getFirstDayCost();
        this.nextDaysCost = equipmentDTO.getNextDaysCost();
        this.archive = equipmentDTO.isArchive();
        this.description = equipmentDTO.getDescription();
        this.missing = equipmentDTO.isMissing();
    }

    protected Equipment() {}

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("-------------------- Equipment{");
        sb.append("id=").append(id);
        sb.append(", firstDayCost=").append(firstDayCost);
        sb.append(", nextDaysCost=").append(nextDaysCost);
        sb.append(", bail=").append(bail);
        sb.append(", name='").append(name).append('\'');
        sb.append(", archive=").append(archive);
        sb.append(", description='").append(description).append('\'');
        sb.append(", id=").append(id);
        sb.append(", missing=").append(missing);
        sb.append('}');
        return sb.toString();
    }



    public Long getId() {
        return id;
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

}
