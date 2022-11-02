package mgd;

import mgd.EQ.*;
import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.LocalDateTime;


public class DataFakerMgd {

    public static AddressMgd getAddressMgd() {
        return new AddressMgd(new UniqueIdMgd(), randStr(7), randStr(10), randStr(4));
    }

    public static ClientMgd getClientMgd(AddressMgd a) {
        return new ClientMgd(new UniqueIdMgd(), randStr(7),
                randStr(10), randStr(10), a, false);
    }

    public static ClientMgd getClientMgd() {
        AddressMgd a = getAddressMgd();
        return getClientMgd(a);
    }

    public static CameraMgd getCameraMgd() {
        return new CameraMgd(new UniqueIdMgd(),
                randStr(10), Math.random() * 1000,
                Math.random() * 100, Math.random() * 200,
                false, randStr(60),
                false, randNum(4));
    }

    public static LensMgd getLensMgd() {
        return new LensMgd(new UniqueIdMgd(),
                randStr(10), Math.random() * 1000,
                Math.random() * 100, Math.random() * 200,
                false, randStr(60),
                false, randNum(4));
    }

    public static TrivetMgd getTrivetMgd() {
        return new TrivetMgd(new UniqueIdMgd(),
                randStr(10), Math.random() * 1000,
                Math.random() * 100, Math.random() * 200,
                false, randStr(60),
                false, Math.random() * 20);
    }

    public static RentMgd getRentMgd(EquipmentMgd e, ClientMgd c, AddressMgd a) {
        if(e == null) {
            e = getCameraMgd();
        }
        if(c == null) {
            c = getClientMgd();
        }
        if(a == null) {
            a = getAddressMgd();
        }
        LocalDateTime nowLocal = LocalDateTime.now();
        DateTime now = nowLocal.toDateTime();
        long sinceEpoch = now.getMillis();
        long beginUnix = sinceEpoch + (long) (Math.random() * 10000000);
        long endUnix = beginUnix + (long) (Math.random() * 10000000);
        LocalDateTime begin = Instant.ofEpochMilli(beginUnix).toDateTime().toLocalDateTime();
        LocalDateTime end = Instant.ofEpochMilli(endUnix).toDateTime().toLocalDateTime();
        return new RentMgd(new UniqueIdMgd(), begin, end, e, c, a);
    }

    public static RentMgd getRent() {
        return getRentMgd(null, null, null);
    }


    // source: https://www.geeksforgeeks.org/generate-random-string-of-given-size-in-java/
    static String randStr(int size) {
        String dict = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        return randStr(size, dict);
    }

    static String randNum(int size) {
        String dict = "0123456789";
        return randStr(size, dict);
    }

    static private String randStr(int size, String dict) {
        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            int index = (int)(dict.length() * Math.random());
            sb.append(dict.charAt(index));
        }
        return sb.toString();
    }
}
