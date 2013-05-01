/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geonote.webservice;

import geonote.session.ParcoursFacade;
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
@WebService(serviceName = "Parcours")
@Stateless()
public class Parcours {
    @EJB
    private ParcoursFacade ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "create")
    @Oneway
    public void create(@WebParam(name = "entity") geonote.entity.Parcours entity) {
        ejbRef.create(entity);
    }

    @WebMethod(operationName = "edit")
    @Oneway
    public void edit(@WebParam(name = "entity") geonote.entity.Parcours entity) {
        ejbRef.edit(entity);
    }

    @WebMethod(operationName = "remove")
    @Oneway
    public void remove(@WebParam(name = "entity") geonote.entity.Parcours entity) {
        ejbRef.remove(entity);
    }

    @WebMethod(operationName = "find")
    public geonote.entity.Parcours find(@WebParam(name = "id") Object id) {
        return ejbRef.find(id);
    }

    @WebMethod(operationName = "findAll")
    public List<geonote.entity.Parcours> findAll() {
        return ejbRef.findAll();
    }

    @WebMethod(operationName = "findRange")
    public List<geonote.entity.Parcours> findRange(@WebParam(name = "range") int[] range) {
        return ejbRef.findRange(range);
    }

    @WebMethod(operationName = "count")
    public int count() {
        return ejbRef.count();
    }

    @WebMethod(operationName = "creerParcours")
    public int creerParcours(@WebParam(name = "nom") String nom, @WebParam(name = "theme") String theme, @WebParam(name = "cmt") String cmt) {
        return ejbRef.creerParcours(nom, theme, cmt);
    }

    @WebMethod(operationName = "returnIDParcours")
    public int returnIDParcours(@WebParam(name = "nom") String nom) {
        return ejbRef.returnIDParcours(nom);
    }

    @WebMethod(operationName = "afficherParcours")
    public List afficherParcours(@WebParam(name = "id") int id) {
        return ejbRef.afficherParcours(id);
    }

    @WebMethod(operationName = "supprimerParcours")
    @Oneway
    public void supprimerParcours(@WebParam(name = "id") int id) {
        ejbRef.supprimerParcours(id);
    }

    @WebMethod(operationName = "distance")
    public int distance(@WebParam(name = "dist") double dist, @WebParam(name = "nom") String nom) {
        return ejbRef.distance(dist, nom);
    }

    @WebMethod(operationName = "afficherParcoursTheme")
    public List afficherParcoursTheme(@WebParam(name = "theme") String theme) {
        return ejbRef.afficherParcoursTheme(theme);
    }
    
}
