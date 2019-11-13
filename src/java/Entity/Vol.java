package Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="vol"
    ,catalog="projet_jsf"
)
public class Vol  implements java.io.Serializable {

    private int NVol;
    private Avion avion;    
    private Date heureDepart;
    private Date heureArrive;
    private String villeDepart;
    private String vileArrive;
    private List<Reservation> reservations = new ArrayList<>();

    public Vol() {
    }
    public Vol(int NVol, Avion avion, Date heureDepart, Date heureArrive, String villeDepart, String vileArrive) {
        this.NVol = NVol;
        this.avion = avion;
        this.heureDepart = heureDepart;
        this.heureArrive = heureArrive;
        this.villeDepart = villeDepart;
        this.vileArrive = vileArrive;
    }
    public Vol(int NVol, Avion avion, Date heureDepart, Date heureArrive, String villeDepart, String vileArrive, List<Reservation> reservations) {
       this.NVol = NVol;
       this.avion = avion;
       this.heureDepart = heureDepart;
       this.heureArrive = heureArrive;
       this.villeDepart = villeDepart;
       this.vileArrive = vileArrive;
       this.reservations = reservations;
    }
   
    @Id
    @Column(name="n_vol", unique=true, nullable=false)
    public int getNVol() {
        return this.NVol;
    }
    public void setNVol(int NVol) {
        this.NVol = NVol;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="n_avion", nullable=false)
    public Avion getAvion() {
        return this.avion;
    }
    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="heure_depart", nullable=false, length=19)
    public Date getHeureDepart() {
        return this.heureDepart;
    }    
    public void setHeureDepart(Date heureDepart) {
        this.heureDepart = heureDepart;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="heure_arrive", nullable=false, length=19)
    public Date getHeureArrive() {
        return this.heureArrive;
    }
    public void setHeureArrive(Date heureArrive) {
        this.heureArrive = heureArrive;
    }

    @Column(name="ville_depart", nullable=false, length=30)
    public String getVilleDepart() {
        return this.villeDepart;
    }
    public void setVilleDepart(String villeDepart) {
        this.villeDepart = villeDepart;
    }

    
    @Column(name="vile_arrive", nullable=false, length=30)
    public String getVileArrive() {
        return this.vileArrive;
    }
    public void setVileArrive(String vileArrive) {
        this.vileArrive = vileArrive;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="vol")
    public List<Reservation> getReservations() {
        return this.reservations;
    }
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "Vol " + NVol;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.NVol;
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
        final Vol other = (Vol) obj;
        if (this.NVol != other.NVol) {
            return false;
        }
        return true;
    }

}


