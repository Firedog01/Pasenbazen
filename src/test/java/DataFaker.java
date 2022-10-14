import exception.ClientException;
import model.Address;
import model.Client;
import model.EQ.*;
import model.idType;

import javax.xml.crypto.Data;
import java.util.Random;
import java.util.random.RandomGenerator;

public class DataFaker {

    public static Address getAddress() {
        return new Address(randStr(7), randStr(10), randStr(4));
    }

    public static Client getClient(Address a) {
        int tries = 5;
        while(tries >= 0) {
            try {
                return new Client(randStr(7), idType.values()[(int)(Math.random() * 2) % 2],
                        randStr(10), randStr(10), a);
            } catch(ClientException e) {
                tries--;
            }
        }
        return null;
    }

    public static Client getClient() {
        int tries = 5;
        while(tries >= 0) {
            try {
                Address a = getAddress();
                return new Client(randStr(7), idType.values()[(int)(Math.random() * 2) % 2],
                        randStr(10), randStr(10), a);
            } catch(ClientException e) {
                tries--;
            }
        }
        return null;
    }

    public static Camera getCamera() {
        return new Camera(Math.random() * 100, Math.random() * 200,
                Math.random() * 1000, randStr(10), randStr(8));
    }

    public static Lens getLens() {
        return new Lens(Math.random() * 100, Math.random() * 200,
                Math.random() * 1000, randStr(10), randStr(8));
    }

    public static Lighting getLighting() {
        return new Lighting(Math.random() * 100, Math.random() * 200,
                Math.random() * 1000, randStr(10), randStr(8));
    }

    public static Microphone getMicrophone() {
        return new Microphone(Math.random() * 100, Math.random() * 200,
                Math.random() * 1000, randStr(10), randStr(8));
    }
    public static Trivet getTrivet() {
        return new Trivet(Math.random() * 100, Math.random() * 200,
                Math.random() * 1000, randStr(10), Math.random() * 10);
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
