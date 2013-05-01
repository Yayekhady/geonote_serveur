/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geonote.webservice;

import geonote.entity.Note;
import geonote.session.NoteFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author khadydieng
 */
@WebService(serviceName = "Notes")
@Stateless()
public class Notes {
    @EJB
    private NoteFacade ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "create")
    @Oneway
    public void create(@WebParam(name = "entity") Note entity) {
        ejbRef.create(entity);
    }

    @WebMethod(operationName = "edit")
    @Oneway
    public void edit(@WebParam(name = "entity") Note entity) {
        ejbRef.edit(entity);
    }

    @WebMethod(operationName = "remove")
    @Oneway
    public void remove(@WebParam(name = "entity") Note entity) {
        ejbRef.remove(entity);
    }

    @WebMethod(operationName = "find")
    public Note find(@WebParam(name = "id") Object id) {
        return ejbRef.find(id);
    }

    @WebMethod(operationName = "findAll")
    public List<Note> findAll() {
        return ejbRef.findAll();
    }

    @WebMethod(operationName = "findRange")
    public List<Note> findRange(@WebParam(name = "range") int[] range) {
        return ejbRef.findRange(range);
    }

    @WebMethod(operationName = "count")
    public int count() {
        return ejbRef.count();
    }

    @WebMethod(operationName = "creerNote")
    public int creerNote(@WebParam(name = "nom") String nom, @WebParam(name = "them") String them, @WebParam(name = "cmt") String cmt, @WebParam(name = "lal") double lal, @WebParam(name = "lgt") double lgt, @WebParam(name = "vil") String vil, @WebParam(name = "id") int id) {
        return ejbRef.creerNote(nom, them, cmt, lal, lgt, vil, id);
    }

    @WebMethod(operationName = "affecterParcours")
    public int affecterParcours(@WebParam(name = "p") int p, @WebParam(name = "note") String note, @WebParam(name = "id") int id) {
        return ejbRef.affecterParcours(p, note, id);
    }

    @WebMethod(operationName = "afficherNote")
    public List<Note> afficherNote(@WebParam(name = "id") int id) {
        return ejbRef.afficherNote(id);
    }

    @WebMethod(operationName = "idParcours")
    public List<Integer> idParcours(@WebParam(name = "id") int id) {
        return ejbRef.idParcours(id);
    }

    @WebMethod(operationName = "supprimerNote")
    @Oneway
    public void supprimerNote(@WebParam(name = "id") int id) {
        ejbRef.supprimerNote(id);
    }

    @WebMethod(operationName = "afficherNoteTheme")
    public List<Note> afficherNoteTheme(@WebParam(name = "id") int id, @WebParam(name = "theme") String theme) {
        return ejbRef.afficherNoteTheme(id, theme);
    }

    @WebMethod(operationName = "retourneNoteParcours")
    public List retourneNoteParcours(@WebParam(name = "id") int id) {
        return ejbRef.retourneNoteParcours(id);
    }
    
}
