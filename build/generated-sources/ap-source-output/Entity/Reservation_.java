package Entity;

import Entity.Place;
import Entity.Vol;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-06T15:58:07")
@StaticMetamodel(Reservation.class)
public class Reservation_ { 

    public static volatile SingularAttribute<Reservation, Integer> NReservation;
    public static volatile SingularAttribute<Reservation, String> nomVoyageur;
    public static volatile SingularAttribute<Reservation, Vol> vol;
    public static volatile SingularAttribute<Reservation, Place> place;
    public static volatile SingularAttribute<Reservation, Date> dateReservation;

}