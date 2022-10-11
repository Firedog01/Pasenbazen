package model;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.Generated;
import model.Client.idType;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Client.class)
public abstract class Client_ extends model.AbstractEntity_ {

	public static volatile SingularAttribute<Client, String> firstName;
	public static volatile SingularAttribute<Client, String> lastName;
	public static volatile SingularAttribute<Client, idType> idType;
	public static volatile SingularAttribute<Client, Long> id_client;
	public static volatile SingularAttribute<Client, Boolean> archive;
	public static volatile SingularAttribute<Client, String> ID;
	public static volatile ListAttribute<Client, Rent> currentRents;

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String ID_TYPE = "idType";
	public static final String ID_CLIENT = "id_client";
	public static final String ARCHIVE = "archive";
	public static final String I_D = "ID";
	public static final String CURRENT_RENTS = "currentRents";

}

