package pl.lodz.p.edu.rest.model.DTO;

import pl.lodz.p.edu.rest.model.Address;

import java.util.UUID;

public class RentDTO {
    private String equipmentUUID;
    private String clientUUID;
    private String beginTime;
    private String endTime;

    public RentDTO() {
    }

    public RentDTO(String clientUUID, String equipmentUUID,
                   String beginTime, String endTime) {
        this.clientUUID = clientUUID;
        this.equipmentUUID = equipmentUUID;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public String getEquipmentUUID() {
        return equipmentUUID;
    }

    public String getClientUUID() {
        return clientUUID;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public UUID getClientUUIDFromString() {
        return UUID.fromString(clientUUID);
    }

    public UUID getEquipmentUUIDFromString() {
        return UUID.fromString(equipmentUUID);
    }

    public void setEquipmentUUID(String equipmentUUID) {
        this.equipmentUUID = equipmentUUID;
    }

    public void setClientUUID(String clientUUID) {
        this.clientUUID = clientUUID;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "RentDTO{" +
                "equipmentUUID='" + equipmentUUID + '\'' +
                ", clientUUID='" + clientUUID + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
