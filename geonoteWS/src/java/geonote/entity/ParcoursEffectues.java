/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geonote.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author khadydieng
 */
@Entity
@Table(name = "parcours_effectues")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParcoursEffectues.findAll", query = "SELECT p FROM ParcoursEffectues p"),
    @NamedQuery(name = "ParcoursEffectues.findByParcoursID", query = "SELECT p FROM ParcoursEffectues p WHERE p.parcoursEffectuesPK.parcoursID = :parcoursID"),
    @NamedQuery(name = "ParcoursEffectues.findByUserID", query = "SELECT p FROM ParcoursEffectues p WHERE p.parcoursEffectuesPK.userID = :userID"),
    @NamedQuery(name = "ParcoursEffectues.findByNotation", query = "SELECT p FROM ParcoursEffectues p WHERE p.notation = :notation"),
    @NamedQuery(name = "ParcoursEffectues.findByNb", query = "SELECT p FROM ParcoursEffectues p WHERE p.nb = :nb")})
public class ParcoursEffectues implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ParcoursEffectuesPK parcoursEffectuesPK;
    @Column(name = "notation")
    private Integer notation;
    @Column(name = "nb")
    private Integer nb;
    @JoinColumn(name = "userID", referencedColumnName = "id_user", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;
    @JoinColumn(name = "parcoursID", referencedColumnName = "id_parcours", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Parcours parcours;

    public ParcoursEffectues() {
    }

    public ParcoursEffectues(ParcoursEffectuesPK parcoursEffectuesPK) {
        this.parcoursEffectuesPK = parcoursEffectuesPK;
    }

    public ParcoursEffectues(int parcoursID, int userID) {
        this.parcoursEffectuesPK = new ParcoursEffectuesPK(parcoursID, userID);
    }

    public ParcoursEffectuesPK getParcoursEffectuesPK() {
        return parcoursEffectuesPK;
    }

    public void setParcoursEffectuesPK(ParcoursEffectuesPK parcoursEffectuesPK) {
        this.parcoursEffectuesPK = parcoursEffectuesPK;
    }

    public Integer getNotation() {
        return notation;
    }

    public void setNotation(Integer notation) {
        this.notation = notation;
    }

    public Integer getNb() {
        return nb;
    }

    public void setNb(Integer nb) {
        this.nb = nb;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Parcours getParcours() {
        return parcours;
    }

    public void setParcours(Parcours parcours) {
        this.parcours = parcours;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (parcoursEffectuesPK != null ? parcoursEffectuesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParcoursEffectues)) {
            return false;
        }
        ParcoursEffectues other = (ParcoursEffectues) object;
        if ((this.parcoursEffectuesPK == null && other.parcoursEffectuesPK != null) || (this.parcoursEffectuesPK != null && !this.parcoursEffectuesPK.equals(other.parcoursEffectuesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "geonote.entity.ParcoursEffectues[ parcoursEffectuesPK=" + parcoursEffectuesPK + " ]";
    }
    
}
