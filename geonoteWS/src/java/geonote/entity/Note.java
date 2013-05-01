/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geonote.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author khadydieng
 */
@Entity
@Table(name = "note")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Note.findAll", query = "SELECT n FROM Note n"),
    @NamedQuery(name = "Note.findByIdNote", query = "SELECT n FROM Note n WHERE n.idNote = :idNote"),
    @NamedQuery(name = "Note.findByNomNote", query = "SELECT n FROM Note n WHERE n.nomNote = :nomNote"),
    @NamedQuery(name = "Note.findByTheme", query = "SELECT n FROM Note n WHERE n.theme = :theme"),
    @NamedQuery(name = "Note.findByLatitude", query = "SELECT n FROM Note n WHERE n.latitude = :latitude"),
    @NamedQuery(name = "Note.findByLongitude", query = "SELECT n FROM Note n WHERE n.longitude = :longitude"),
    @NamedQuery(name = "Note.findByVille", query = "SELECT n FROM Note n WHERE n.ville = :ville")})
public class Note implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_note")
    private Integer idNote;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nom_note")
    private String nomNote;
    @Lob
    @Size(max = 16777215)
    @Column(name = "comment_note")
    private String commentNote;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "theme")
    private String theme;
    @Basic(optional = false)
    @NotNull
    @Column(name = "latitude")
    private double latitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "longitude")
    private double longitude;
    @Size(max = 45)
    @Column(name = "ville")
    private String ville;
    @JoinColumn(name = "usersID", referencedColumnName = "id_user")
    @ManyToOne(optional = false)
    private Users usersID;
    @JoinColumn(name = "parcoursID", referencedColumnName = "id_parcours")
    @ManyToOne
    private Parcours parcoursID;

    public Note() {
    }

    public Note(Integer idNote) {
        this.idNote = idNote;
    }

    public Note(Integer idNote, String nomNote, String theme, double latitude, double longitude) {
        this.idNote = idNote;
        this.nomNote = nomNote;
        this.theme = theme;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getIdNote() {
        return idNote;
    }

    public void setIdNote(Integer idNote) {
        this.idNote = idNote;
    }

    public String getNomNote() {
        return nomNote;
    }

    public void setNomNote(String nomNote) {
        this.nomNote = nomNote;
    }

    public String getCommentNote() {
        return commentNote;
    }

    public void setCommentNote(String commentNote) {
        this.commentNote = commentNote;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Users getUsersID() {
        return usersID;
    }

    public void setUsersID(Users usersID) {
        this.usersID = usersID;
    }

    public Parcours getParcoursID() {
        return parcoursID;
    }

    public void setParcoursID(Parcours parcoursID) {
        this.parcoursID = parcoursID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNote != null ? idNote.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Note)) {
            return false;
        }
        Note other = (Note) object;
        if ((this.idNote == null && other.idNote != null) || (this.idNote != null && !this.idNote.equals(other.idNote))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "geonote.entity.Note[ idNote=" + idNote + " ]";
    }
    
}
