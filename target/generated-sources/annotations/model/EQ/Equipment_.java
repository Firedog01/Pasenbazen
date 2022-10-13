package model.EQ;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Equipment.class)
public abstract class Equipment_ extends model.AbstractEntity_ {

	public static volatile SingularAttribute<Equipment, String> name;
	public static volatile SingularAttribute<Equipment, Boolean> missing;
	public static volatile SingularAttribute<Equipment, String> description;
	public static volatile SingularAttribute<Equipment, Boolean> archive;
	public static volatile SingularAttribute<Equipment, Long> id;
	public static volatile SingularAttribute<Equipment, Double> bail;
	public static volatile SingularAttribute<Equipment, Double> nextDaysCost;
	public static volatile SingularAttribute<Equipment, Double> firstDayCost;

	public static final String NAME = "name";
	public static final String MISSING = "missing";
	public static final String DESCRIPTION = "description";
	public static final String ARCHIVE = "archive";
	public static final String ID = "id";
	public static final String BAIL = "bail";
	public static final String NEXT_DAYS_COST = "nextDaysCost";
	public static final String FIRST_DAY_COST = "firstDayCost";

}

