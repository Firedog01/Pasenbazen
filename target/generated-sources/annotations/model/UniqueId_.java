package model;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;
import javax.annotation.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UniqueId.class)
public abstract class UniqueId_ {

	public static volatile SingularAttribute<UniqueId, UUID> uniqueID;

	public static final String UNIQUE_ID = "uniqueID";

}

