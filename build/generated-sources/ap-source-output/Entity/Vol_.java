package Entity;

import Entity.Avion;
import Entity.Reservation;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-06T15:58:07")
@StaticMetamodel(Vol.class)
public class Vol_ { 

    public static volatile SingularAttribute<Vol, Integer> NVol;
    public static volatile ListAttribute<Vol, Reservation> reservations;
    public static volatile SingularAttribute<Vol, String> vileArrive;
    public static volatile SingularAttribute<Vol, Date> heureArrive;
    public static volatile SingularAttribute<Vol, String> villeDepart;
    public static volatile SingularAttribute<Vol, Avion> avion;
    public static volatile SingularAttribute<Vol, Date> heureDepart;

}