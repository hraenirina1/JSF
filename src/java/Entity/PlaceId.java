package Entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PlaceId  implements java.io.Serializable {

    private String NAvion;
    private int NPlace;

    public PlaceId() {
    }
    public PlaceId(String NAvion, int NPlace) {
       this.NAvion = NAvion;
       this.NPlace = NPlace;
    }
   
    @Column(name="n_avion", nullable=false, length=10)
    public String getNAvion() {
        return this.NAvion;
    }
    public void setNAvion(String NAvion) {
        this.NAvion = NAvion;
    }

    @Column(name="n_place", nullable=false)
    public int getNPlace() {
        return this.NPlace;
    }
    public void setNPlace(int NPlace) {
        this.NPlace = NPlace;
    }

    @Override
    public String toString() {
        return "Avion " + NAvion + ", place " + NPlace;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.NAvion);
        hash = 73 * hash + this.NPlace;
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
        final PlaceId other = (PlaceId) obj;
        if (this.NPlace != other.NPlace) {
            return false;
        }
        if (!Objects.equals(this.NAvion, other.NAvion)) {
            return false;
        }
        return true;
    }
    
    
    

}


