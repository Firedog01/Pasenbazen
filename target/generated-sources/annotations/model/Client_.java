package model;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Client.class)
public abstract class Client_ extends model.AbstractEntity_ {

	public static volatile SingularAttribute<Client, String> firstName;
	public static volatile SingularAttribute<Client, String> lastName;
	public static volatile SingularAttribute<Client, String> clientId;
	public static volatile SingularAttribute<Client, idType> idType;
	public static volatile SingularAttribute<Client, Address> address;
	public static volatile SingularAttribute<Client, Boolean> archive;
	public static volatile SingularAttribute<Client, Long> id;

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String CLIENT_ID = "clientId";
	public static final String ID_TYPE = "idType";
	public static final String ADDRESS = "address";
	public static final String ARCHIVE = "archive";
	public static final String ID = "id";

}

