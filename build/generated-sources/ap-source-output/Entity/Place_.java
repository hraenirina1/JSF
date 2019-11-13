package Entity;

import Entity.Avion;
import Entity.PlaceId;
import Entity.Reservation;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-06T15:58:07")
@StaticMetamodel(Place.class)
public class Place_ { 

    public static volatile SingularAttribute<Place, Integer> occupation;
    public static volatile ListAttribute<Place, Reservation> reservations;
    public static volatile SingularAttribute<Place, PlaceId> id;
    public static volatile SingularAttribute<Place, Avion> avion;

}