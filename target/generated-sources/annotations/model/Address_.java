package model;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Address.class)
public abstract class Address_ extends model.AbstractEntity_ {

	public static volatile SingularAttribute<Address, String> city;
	public static volatile SingularAttribute<Address, String> street;
	public static volatile SingularAttribute<Address, String> streetNr;
	public static volatile SingularAttribute<Address, Long> id;

	public static final String CITY = "city";
	public static final String STREET = "street";
	public static final String STREET_NR = "streetNr";
	public static final String ID = "id";

}

