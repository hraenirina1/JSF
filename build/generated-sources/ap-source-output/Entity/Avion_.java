package Entity;

import Entity.Place;
import Entity.Vol;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-06T15:58:07")
@StaticMetamodel(Avion.class)
public class Avion_ { 

    public static volatile SingularAttribute<Avion, String> NAvion;
    public static volatile SingularAttribute<Avion, Integer> nbPlace;
    public static volatile ListAttribute<Avion, Place> places;
    public static volatile ListAttribute<Avion, Vol> vols;
    public static volatile SingularAttribute<Avion, String> type;

}