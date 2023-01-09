package repository.codec;

import mgd.EQ.EquipmentMgd;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

import java.time.LocalDateTime;

public class LocalDateTimeCodecProvider implements CodecProvider {
    @SuppressWarnings("unchecked")
    @Override
    public <T> Codec<T> get(Class<T> aClass, CodecRegistry codecRegistry) {
        if(aClass == LocalDateTime.class) {
            return (Codec<T>) new LocalDateTimeCodec(codecRegistry);
        }
        return null;
    }
}