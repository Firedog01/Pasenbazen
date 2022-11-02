package mapper;

import model.EQ.Equipment;
import org.bson.Document;

import static model.EQ.Equipment_.*;

public class EqMapper {
    public static <T extends Equipment> Document toMongoEq(T t) {
        Document eqDocument = new Document(
                ID, t.getEntityId().getUniqueID())
                .append(NAME, t.getName())
                .append(BAIL, t.getBail())
                .append(FIRST_DAY_COST, t.getFirstDayCost())
                .append(NEXT_DAYS_COST, t.getNextDaysCost())
                .append(ARCHIVE, t.isArchive())
                .append(MISSING, t.isMissing())
                .append(DESCRIPTION, t.getDescription()
        );
        return eqDocument;
    }

    public static <T extends Equipment> T fromMongoEq(Document eqDocument) {


        return null;
    }

}
