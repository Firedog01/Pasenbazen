package repository.codec;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

import java.time.LocalDateTime;

public class LocalDateTimeCodec implements Codec<LocalDateTime> {
    private Codec<String> stringCodec;
    public LocalDateTimeCodec(CodecRegistry registry) {
        stringCodec = registry.get(String.class);
    }

    @Override
    public LocalDateTime decode(BsonReader bsonReader, DecoderContext decoderContext) {
        String dateTimeString = stringCodec.decode(bsonReader, decoderContext);
        return LocalDateTime.parse(dateTimeString);
    }

    @Override
    public void encode(BsonWriter bsonWriter, LocalDateTime localDateTime, EncoderContext encoderContext) {
        String dateTimeString = localDateTime.toString();
        stringCodec.encode(bsonWriter, dateTimeString, encoderContext);
    }

    @Override
    public Class<LocalDateTime> getEncoderClass() {
        return LocalDateTime.class;
    }
}
