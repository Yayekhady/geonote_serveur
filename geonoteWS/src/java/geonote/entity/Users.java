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
@Table(name = "users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByIdUser", query = "SELECT u FROM Users u WHERE u.idUser = :idUser"),
    @NamedQuery(name = "Users.findByLogin", query = "SELECT u FROM Users u WHERE u.login = :login"),
    @NamedQuery(name = "Users.findByMdp", query = "SELECT u FROM Users u WHERE u.mdp = :mdp"),
    @NamedQuery(name = "Users.findByEtatAuth", query = "SELECT u FROM Users u WHERE u.etatAuth = :etatAuth")})
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_user")
    private Integer idUser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "login")
    private String login;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "mdp")
    private String mdp;
    @Column(name = "etat_auth")
    private Boolean etatAuth;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private List<ParcoursEffectues> parcoursEffectuesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usersID")
    private List<Note> noteList;

    public Users() {
    }

    public Users(Integer idUser) {
        this.idUser = idUser;
    }

    public Users(Integer idUser, String login, String mdp) {
        this.idUser = idUser;
        this.login = login;
        this.mdp = mdp;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Boolean getEtatAuth() {
        return etatAuth;
    }

    public void setEtatAuth(Boolean etatAuth) {
        this.etatAuth = etatAuth;
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
        hash += (idUser != null ? idUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.idUser == null && other.idUser != null) || (this.idUser != null && !this.idUser.equals(other.idUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "geonote.entity.Users[ idUser=" + idUser + " ]";
    }
    
}
