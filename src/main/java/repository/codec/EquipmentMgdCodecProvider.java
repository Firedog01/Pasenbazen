package repository.codec;

import mgd.EQ.EquipmentMgd;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class EquipmentMgdCodecProvider implements CodecProvider {

    public EquipmentMgdCodecProvider() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Codec<T> get(Class<T> aClass, CodecRegistry codecRegistry) {
        if(aClass == EquipmentMgd.class) {
            return (Codec<T>) new EquipmentMgdCodec(codecRegistry);
        }
        return null;
    }

}
