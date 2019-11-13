package Entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="place"
    ,catalog="projet_jsf"
)
public class Place  implements java.io.Serializable {

    private PlaceId id;
    private Avion avion;
    private int occupation;
    private List<Reservation> reservations = new ArrayList<>();

    public Place() {
    }
    public Place(PlaceId id, Avion avion, int occupation) {
        this.id = id;
        this.avion = avion;
        this.occupation = occupation;
    }
    public Place(PlaceId id, Avion avion, int occupation, List<Reservation> reservations) {
       this.id = id;
       this.avion = avion;
       this.occupation = occupation;
       this.reservations = reservations;
    }
   
    @EmbeddedId    
    @AttributeOverrides( {
        @AttributeOverride(name="NAvion", column=@Column(name="n_avion", nullable=false, length=10) ), 
        @AttributeOverride(name="NPlace", column=@Column(name="n_place", nullable=false) ) } )
    public PlaceId getId() {
        return this.id;
    }    
    public void setId(PlaceId id) {
        this.id = id;
    }
    
    public int getPlace() {
        return this.id.getNPlace();
    }
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="n_avion", nullable=false, insertable=false, updatable=false)
    public Avion getAvion() {
        return this.avion;
    }
    public void setAvion(Avion avion) {
        this.avion = avion;
        id.setNAvion(avion.getNAvion());
    }

    @Column(name="occupation", nullable=false)
    public int getOccupation() {
        return this.occupation;
    }    
    public void setOccupation(int occupation) {
        this.occupation = occupation;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="place")
    public List<Reservation> getReservations() {
        return this.reservations;
    }    
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return id.toString() ;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final Place other = (Place) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
}


