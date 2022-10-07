import model.eq.Equipment;
import org.joda.time.Days;
import org.joda.time.Hours;

import java.time.LocalDateTime;

public class Rent {

    private int id;

    private org.joda.time.LocalDateTime beginTime;
    private org.joda.time.LocalDateTime endTime;

    private boolean shipped;

    private boolean eqReturned;

    private Equipment equipment;

    private Client client;

    private Address shippingAddress;

    public Rent(int id, org.joda.time.LocalDateTime beginTime, org.joda.time.LocalDateTime endTime, boolean shipped,
                boolean eqReturned, Equipment equipment, Client client, Address shippingAddress) {
        this.id = id; // W jaki sposób to id ma być generowane?
        this.beginTime = beginTime;
//        this.beginTime = LocalDateTime.now();  FIXME ?
        this.endTime = endTime;
        this.shipped = shipped;
        this.eqReturned = eqReturned;
        this.equipment = equipment;
        this.client = client;
        this.shippingAddress = shippingAddress;
    }

    double getRentCost() {
        if (!eqReturned) {
            return 0.0;
        } else if (equipment.isMissing()) {
            return equipment.getBail();
        } else {
            long diffDays= Math.abs(Days.daysBetween(beginTime, endTime).getDays());
            // Nie jestem pewien co do tego
            if (diffDays > 1) {
                return equipment.getFirstDayCost() + equipment.getNextDaysCost() * (diffDays - 1);
            } else {
                return equipment.getFirstDayCost();
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Rent{");
        sb.append("id=").append(id);
        sb.append(", beginTime=").append(beginTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", shipped=").append(shipped);
        sb.append(", eqReturned=").append(eqReturned);
        sb.append('}');
        return sb.toString();
    }
}
