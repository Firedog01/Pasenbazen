package pl.lodz.p.edu.rest;

import pl.lodz.p.edu.rest.exception.ClientException;
import pl.lodz.p.edu.rest.exception.EquipmentException;
import pl.lodz.p.edu.rest.model.Address;
import pl.lodz.p.edu.rest.model.Client;
import pl.lodz.p.edu.rest.model.EQ.*;
import pl.lodz.p.edu.rest.model.Rent;
import pl.lodz.p.edu.rest.model.idType;
import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.LocalDateTime;

public class DataFaker {

    public static Address getAddress() {
        return new Address(randStr(7), randStr(10), randStr(4));
    }

    public static Client getClient(Address a) {
        try {
            return new Client(randStr(7), // idType.values()[(int)(Math.random() * 2) % 2],
                    randStr(10), randStr(10), a);
        } catch(ClientException e) {
            return null; // will never happen
        }
    }

    public static Client getClient() {
        try {
            Address a = getAddress();
            return new Client(randStr(7), // idType.values()[(int)(Math.random() * 2) % 2],
                    randStr(10), randStr(10), a);
        } catch(ClientException e) {
            return null; // will never happen
        }
    }

    public static Camera getCamera() {
        try {
            return new Camera(Math.random() * 100, Math.random() * 200,
                    Math.random() * 1000, randStr(10), randStr(8));
        } catch (EquipmentException e) {
            return null;
        }
    }

    public static Lens getLens() {
        try {
            return new Lens(Math.random() * 100, Math.random() * 200,
                    Math.random() * 1000, randStr(10), randStr(8));
        } catch (EquipmentException e) {
            return null;
        }
    }

    public static Lighting getLighting() {
        try {
            return new Lighting(Math.random() * 100, Math.random() * 200,
                    Math.random() * 1000, randStr(10), randStr(8));
        } catch (EquipmentException e) {
            return null;
        }
    }

    public static Microphone getMicrophone() {
        try {
            return new Microphone(Math.random() * 100, Math.random() * 200,
                Math.random() * 1000, randStr(10), randStr(8));
        } catch (EquipmentException e) {
            return null;
        }
    }

    public static Trivet getTrivet() {
        try {
            return new Trivet(Math.random() * 100, Math.random() * 200,
                Math.random() * 1000, randStr(10), Math.random() * 10);
        } catch (EquipmentException e) {
            return null;
        }
    }

    public static Rent getRent(Equipment e, Client c, Address a) {
        if(e == null) {
            e = getCamera();
        }
        if(c == null) {
            c = getClient();
        }
        if(a == null) {
            a = getAddress();
        }
        LocalDateTime nowLocal = LocalDateTime.now();
        DateTime now = nowLocal.toDateTime();
        long sinceEpoch = now.getMillis();
        long beginUnix = sinceEpoch + (long) (Math.random() * 10000000);
        long endUnix = beginUnix + (long) (Math.random() * 10000000);
        LocalDateTime begin = Instant.ofEpochMilli(beginUnix).toDateTime().toLocalDateTime();
        LocalDateTime end = Instant.ofEpochMilli(endUnix).toDateTime().toLocalDateTime();
        return new Rent(begin, end, e, c, a);
    }

    public static Rent getRent() {
        return getRent(null, null, null);
    }


    // source: https://www.geeksforgeeks.org/generate-random-string-of-given-size-in-java/
    static String randStr(int size)
    {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }
}
