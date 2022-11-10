package pl.lodz.p.edu.rest.DTO;

import pl.lodz.p.edu.rest.model.Address;

import java.util.UUID;

public class RentDTO {
    private UUID equipmentUUID;
    private UUID clientUUID;
    private Address shippingAddress;
    private String beginTime;
    private String endTime;

    public RentDTO() {
    }

    public UUID getEquipmentUUID() {
        return equipmentUUID;
    }

    public void setEquipmentUUID(UUID equipmentUUID) {
        this.equipmentUUID = equipmentUUID;
    }

    public UUID getClientUUID() {
        return clientUUID;
    }

    public void setClientUUID(UUID clientUUID) {
        this.clientUUID = clientUUID;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
