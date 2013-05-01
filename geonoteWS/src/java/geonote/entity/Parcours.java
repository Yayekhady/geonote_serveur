/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geonote.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author khadydieng
 */
@Entity
@Table(name = "parcours")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parcours.findAll", query = "SELECT p FROM Parcours p"),
    @NamedQuery(name = "Parcours.findByIdParcours", query = "SELECT p FROM Parcours p WHERE p.idParcours = :idParcours"),
    @NamedQuery(name = "Parcours.findByNomParcours", query = "SELECT p FROM Parcours p WHERE p.nomParcours = :nomParcours"),
    @NamedQuery(name = "Parcours.findByTheme", query = "SELECT p FROM Parcours p WHERE p.theme = :theme"),
    @NamedQuery(name = "Parcours.findByDistance", query = "SELECT p FROM Parcours p WHERE p.distance = :distance")})
public class Parcours implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_parcours")
    private Integer idParcours;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nom_parcours")
    private String nomParcours;
    @Lob
    @Size(max = 16777215)
    @Column(name = "comment_parcours")
    private String commentParcours;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "theme")
    private String theme;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "distance")
    private Double distance;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parcours")
    private List<ParcoursEffectues> parcoursEffectuesList;
    @OneToMany(mappedBy = "parcoursID")
    private List<Note> noteList;

    public Parcours() {
    }

    public Parcours(Integer idParcours) {
        this.idParcours = idParcours;
    }

    public Parcours(Integer idParcours, String nomParcours, String theme) {
        this.idParcours = idParcours;
        this.nomParcours = nomParcours;
        this.theme = theme;
    }

    public Integer getIdParcours() {
        return idParcours;
    }

    public void setIdParcours(Integer idParcours) {
        this.idParcours = idParcours;
    }

    public String getNomParcours() {
        return nomParcours;
    }

    public void setNomParcours(String nomParcours) {
        this.nomParcours = nomParcours;
    }

    public String getCommentParcours() {
        return commentParcours;
    }

    public void setCommentParcours(String commentParcours) {
        this.commentParcours = commentParcours;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @XmlTransient
    public List<ParcoursEffectues> getParcoursEffectuesList() {
        return parcoursEffectuesList;
    }

    public void setParcoursEffectuesList(List<ParcoursEffectues> parcoursEffectuesList) {
        this.parcoursEffectuesList = parcoursEffectuesList;
    }

    @XmlTransient
    public List<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParcours != null ? idParcours.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parcours)) {
            return false;
        }
        Parcours other = (Parcours) object;
        if ((this.idParcours == null && other.idParcours != null) || (this.idParcours != null && !this.idParcours.equals(other.idParcours))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "geonote.entity.Parcours[ idParcours=" + idParcours + " ]";
    }
    
}
