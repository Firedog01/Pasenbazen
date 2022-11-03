package repository.codec;

import exception.EquipmentException;
import mgd.EQ.*;
import mgd.UniqueIdMgd;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.StringCodec;
import org.bson.codecs.configuration.CodecRegistry;


import java.util.UUID;

public class EquipmentMgdCodec implements Codec<EquipmentMgd> {

    private Codec<String> stringCodec;
    private Codec<Double> doubleCodec;
    private Codec<Boolean> booleanCodec;
    private Codec<UUID> uuidCodec;

    public EquipmentMgdCodec(CodecRegistry registry) {
        stringCodec = registry.get(String.class);
        doubleCodec = registry.get(Double.class);
        booleanCodec = registry.get(Boolean.class);
        uuidCodec = registry.get(UUID.class);
    }

    @Override
    public EquipmentMgd decode(BsonReader reader, DecoderContext decoderContext) {
        String type = null;

        EquipmentMgdBuilder builder = new EquipmentMgdBuilder();

        reader.readStartDocument();
        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String fieldName = reader.readName();
            switch(fieldName) {
                case "_id" -> builder.setEntityId(uuidCodec.decode(reader, decoderContext));
                case "_clazz" -> type = reader.readString();
                case "name" -> builder.setName(reader.readString());
                case "bail" -> builder.setBail(reader.readDouble());
                case "first_day_cost" -> builder.setFirstDayCost(reader.readDouble());
                case "next_day_cost" -> builder.setNextDaysCost(reader.readDouble());
                case "archive" -> builder.setArchive(reader.readBoolean());
                case "description" -> builder.setDescription(reader.readString());
                case "missing" -> builder.setMissing(reader.readBoolean());
                // children fields
                case "focal_length" -> builder.setFocalLength(reader.readString());
                case "resolution" -> builder.setResolution(reader.readString());
                case "weight" -> builder.setWeight(reader.readDouble());
            }
        }
        reader.readEndDocument();

        return builder.build(type);
    }


    @Override
    public void encode(BsonWriter writer, EquipmentMgd equipmentMgd, EncoderContext encoderContext)  {
        writer.writeStartDocument();
        writer.writeName("_id");
        uuidCodec.encode(writer, equipmentMgd.getEntityId().getUuid(), encoderContext);
        writer.writeName("_clazz");
        switch (equipmentMgd) {
            case CameraMgd camera -> {
                stringCodec.encode(writer, "camera", encoderContext);
                writer.writeName("resolution");
                stringCodec.encode(writer, camera.getResolution(), encoderContext);
            }
            case TrivetMgd trivetMgd -> {
                stringCodec.encode(writer, "trivet", encoderContext);
                writer.writeName("weight");
                doubleCodec.encode(writer, trivetMgd.getWeight(), encoderContext);
            }
            case LensMgd lens -> {
                stringCodec.encode(writer, "lens", encoderContext);
                writer.writeName("focal_length");
                stringCodec.encode(writer, lens.getFocalLength(), encoderContext);
            }
            default -> {
                throw new RuntimeException("Unhandled type of EquipmentMgd, update codec file");
            }
        }
        writer.writeName("name");
        stringCodec.encode(writer, equipmentMgd.getName(), encoderContext);
        writer.writeName("bail");
        doubleCodec.encode(writer, equipmentMgd.getBail(), encoderContext);
        writer.writeName("first_day_cost");
        doubleCodec.encode(writer, equipmentMgd.getFirstDayCost(), encoderContext);
        writer.writeName("next_day_cost");
        doubleCodec.encode(writer, equipmentMgd.getNextDaysCost(), encoderContext);
        writer.writeName("archive");
        booleanCodec.encode(writer, equipmentMgd.isArchive(), encoderContext);
        writer.writeName("description");
        stringCodec.encode(writer, equipmentMgd.getDescription(), encoderContext);
        writer.writeName("missing");
        booleanCodec.encode(writer, equipmentMgd.isMissing(), encoderContext);
        writer.writeEndDocument();
    }

    @Override
    public Class<EquipmentMgd> getEncoderClass() {
        return EquipmentMgd.class;
    }
}
