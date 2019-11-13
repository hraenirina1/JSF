package Entity;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="reservation"
    ,catalog="projet_jsf"
)
public class Reservation  implements java.io.Serializable {

    private Integer NReservation;
    private Place place;
    private Vol vol;
    private Date dateReservation;
    private String nomVoyageur;

    public Reservation() {
    }
    public Reservation(Place place, Vol vol, Date dateReservation, String nomVoyageur) {
       this.place = place;
       this.vol = vol;
       this.dateReservation = dateReservation;
       this.nomVoyageur = nomVoyageur;
    }
   
    @Id 
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="n_reservation", unique=true, nullable=false)
    public Integer getNReservation() {
        return this.NReservation;
    }
    public void setNReservation(Integer NReservation) {
        this.NReservation = NReservation;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumns( { 
        @JoinColumn(name="n_place", referencedColumnName="n_place", nullable=false), 
        @JoinColumn(name="n_avion", referencedColumnName="n_avion", nullable=false) } )
    public Place getPlace() {
        return this.place;
    }
    public void setPlace(Place place) {
        this.place = place;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="n_vol", nullable=false)
    public Vol getVol() {
        return this.vol;
    }
    public void setVol(Vol vol) {
        this.vol = vol;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="date_reservation", nullable=false, length=10)
    public Date getDateReservation() {
        return this.dateReservation;
    }
    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    @Column(name="nom_voyageur", nullable=false, length=100)
    public String getNomVoyageur() {
        return this.nomVoyageur;
    }
    public void setNomVoyageur(String nomVoyageur) {
        this.nomVoyageur = nomVoyageur;
    }

    @Override
    public String toString() {
        return "Reservation " + NReservation;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.NReservation);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reservation other = (Reservation) obj;
        if (!Objects.equals(this.NReservation, other.NReservation)) {
            return false;
        }
        return true;
    }
    
    
}


