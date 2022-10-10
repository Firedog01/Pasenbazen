package model;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.Generated;
import model.Client.Address;
import org.joda.time.LocalDateTime;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Rent.class)
public abstract class Rent_ {

	public static volatile SingularAttribute<Rent, Boolean> shipped;
	public static volatile SingularAttribute<Rent, Boolean> eqReturned;
	public static volatile SingularAttribute<Rent, Client> client;
	public static volatile SingularAttribute<Rent, Address> shippingAddress;
	public static volatile SingularAttribute<Rent, Integer> id;
	public static volatile SingularAttribute<Rent, LocalDateTime> beginTime;
	public static volatile SingularAttribute<Rent, LocalDateTime> endTime;

	public static final String SHIPPED = "shipped";
	public static final String EQ_RETURNED = "eqReturned";
	public static final String CLIENT = "client";
	public static final String SHIPPING_ADDRESS = "shippingAddress";
	public static final String ID = "id";
	public static final String BEGIN_TIME = "beginTime";
	public static final String END_TIME = "endTime";

}

