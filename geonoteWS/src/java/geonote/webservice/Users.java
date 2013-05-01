/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geonote.webservice;

import geonote.session.UsersFacade;
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
@WebService(serviceName = "Users")
@Stateless()
public class Users {
    @EJB
    private UsersFacade ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "create")
    @Oneway
    public void create(@WebParam(name = "entity") geonote.entity.Users entity) {
        ejbRef.create(entity);
    }

    @WebMethod(operationName = "edit")
    @Oneway
    public void edit(@WebParam(name = "entity") geonote.entity.Users entity) {
        ejbRef.edit(entity);
    }

    @WebMethod(operationName = "remove")
    @Oneway
    public void remove(@WebParam(name = "entity") geonote.entity.Users entity) {
        ejbRef.remove(entity);
    }

    @WebMethod(operationName = "find")
    public geonote.entity.Users find(@WebParam(name = "id") Object id) {
        return ejbRef.find(id);
    }

    @WebMethod(operationName = "findAll")
    public List<geonote.entity.Users> findAll() {
        return ejbRef.findAll();
    }

    @WebMethod(operationName = "findRange")
    public List<geonote.entity.Users> findRange(@WebParam(name = "range") int[] range) {
        return ejbRef.findRange(range);
    }

    @WebMethod(operationName = "count")
    public int count() {
        return ejbRef.count();
    }

    @WebMethod(operationName = "creerCompte")
    public int creerCompte(@WebParam(name = "log") String log, @WebParam(name = "mdp") String mdp) {
        return ejbRef.creerCompte(log, mdp);
    }

    @WebMethod(operationName = "seConnecter")
    public int seConnecter(@WebParam(name = "log") String log, @WebParam(name = "mdp") String mdp) {
        return ejbRef.seConnecter(log, mdp);
    }

    @WebMethod(operationName = "getetatAuth")
    public int getetatAuth(@WebParam(name = "log") String log, @WebParam(name = "mdp") String mdp) {
        return ejbRef.getetatAuth(log, mdp);
    }

    @WebMethod(operationName = "getUserID")
    public int getUserID(@WebParam(name = "log") String log, @WebParam(name = "mdp") String mdp) {
        return ejbRef.getUserID(log, mdp);
    }
    
}
