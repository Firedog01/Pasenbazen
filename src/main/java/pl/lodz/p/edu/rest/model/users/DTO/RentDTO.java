package pl.lodz.p.edu.rest.model.users.DTO;

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

    public UUID getClientUUID() {
        return clientUUID;
    }


    public Address getShippingAddress() {
        return shippingAddress;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
