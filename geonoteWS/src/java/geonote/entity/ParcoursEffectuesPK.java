/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geonote.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author khadydieng
 */
@Embeddable
public class ParcoursEffectuesPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "parcoursID")
    private int parcoursID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "userID")
    private int userID;

    public ParcoursEffectuesPK() {
    }

    public ParcoursEffectuesPK(int parcoursID, int userID) {
        this.parcoursID = parcoursID;
        this.userID = userID;
    }

    public int getParcoursID() {
        return parcoursID;
    }

    public void setParcoursID(int parcoursID) {
        this.parcoursID = parcoursID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) parcoursID;
        hash += (int) userID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParcoursEffectuesPK)) {
            return false;
        }
        ParcoursEffectuesPK other = (ParcoursEffectuesPK) object;
        if (this.parcoursID != other.parcoursID) {
            return false;
        }
        if (this.userID != other.userID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "geonote.entity.ParcoursEffectuesPK[ parcoursID=" + parcoursID + ", userID=" + userID + " ]";
    }
    
}
