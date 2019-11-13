package Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="avion"
    ,catalog="projet_jsf"
)
public class Avion  implements java.io.Serializable {

    private String NAvion;
    private String type;
    private int nbPlace;
    private List<Vol> vols = new ArrayList<>();
    private List<Place> places = new ArrayList<>();

    public Avion() {
    }
    public Avion(String NAvion, String type, int nbPlace) {
        this.NAvion = NAvion;
        this.type = type;
        this.nbPlace = nbPlace;
    }
    public Avion(String NAvion, String type, int nbPlace, List<Vol> vols, List<Place> places) {
       this.NAvion = NAvion;
       this.type = type;
       this.nbPlace = nbPlace;
       this.vols = vols;
       this.places = places;
    }
   
    @Id    
    @Column(name="n_avion", unique=true, nullable=false, length=10)
    public String getNAvion() {
        return this.NAvion;
    }
    public void setNAvion(String NAvion) {
        this.NAvion = NAvion;
    }

    @Column(name="type", nullable=false, length=10)
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Column(name="nb_place", nullable=false)
    public int getNbPlace() {
        return this.nbPlace;
    }
    public void setNbPlace(int nbPlace) {
        this.nbPlace = nbPlace;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="avion")
    public List<Vol> getVols() {
        return this.vols;
    }
    public void setVols(List<Vol> vols) {
        this.vols = vols;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="avion")
    public List<Place> getPlaces() {
        return this.places;
    }
    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    @Override
    public String toString() {
        return "Avion " + type + " numero" + NAvion;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.NAvion);
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
        final Avion other = (Avion) obj;
        if (!Objects.equals(this.NAvion, other.NAvion)) {
            return false;
        }
        return true;
    }
    
    
}


