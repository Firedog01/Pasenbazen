package model;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AbstractEntity.class)
public abstract class AbstractEntity_ {

	public static volatile SingularAttribute<AbstractEntity, UniqueId> entityId;
	public static volatile SingularAttribute<AbstractEntity, Long> version;

	public static final String ENTITY_ID = "entityId";
	public static final String VERSION = "version";

}
