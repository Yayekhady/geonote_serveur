/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geonote.session;

import geonote.entity.Users;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author khadydieng
 */
@Stateless
public class UsersFacade extends AbstractFacade<Users> {
    @PersistenceContext(unitName = "geonoteWSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }
    
     /**
     * Cette methode permet de creer un nouveau compte utilisateur en prenant comme parametre
     * @param log le login choisi
     * @param mdp le mot de passe choisi
     * @return un entier 0 si utilisateur existe deja, 1 dans le cas contraire
     */
        public int creerCompte (String log, String mdp){
        // pour ne pas remplir le champ id, mais cela s'incrémente normalement dans la base
        int i = 1;
        Users user = new Users ();
        //on recupere la liste des users pour verifier s'il n'exista pas deja
        List <Users> listUser = findAll();       
        for(Users u : listUser){
                if(u.getLogin().equals(log)){
                   return 0;                     
                }
               
        }
        //s'il n'existe pas, on le cree
        user.setLogin(log);
        user.setMdp(mdp);
        create(user);
        return 1;
    }

    
     /**
     * Cette methode permet de connecter un utilisateur en prenant comme parametre
     * @param log le login choisi
     * @param mdp le mot de passe choisi
     * @return un entier 0 si la connexion s'est bien deroulée, 1 dans le cas contraire
     */
    
    public int seConnecter(String log, String mdp) {
      List <Users> tUsers=this.findAll();
      int i=1;
      
      
      for(Users u : tUsers){
             if((u.getLogin().equals(log)) && (u.getMdp().equals(mdp))){
                   i=0; 
                }
               
        }
      return i;
    }
    
    /**
     * Cette methode permet d'avoir l'etat de connexion de l'utilisateur
     * @param log
     * @param mdp
     * @return 1 l'utilisateur est connecte  0 sinon
     */
        public int getetatAuth (String log, String mdp){
    
        Boolean b=false;
        List <Users> tUsers=this.findAll();

        int etat=0;
             
         for(Users u : tUsers){
             if((u.getLogin().equals(log)) && (u.getMdp().equals(mdp))){
                   b = u.getEtatAuth(); 
                    
                }
               
        }
       
        if(b==true){
            etat=1;
        }
        
       
    
    return etat;
}
   

    /**
     * Cette methode permet d'avoir l'id de l'utilisateur
     * @param log le login choisi
     * @param mdp le mot de passe choisi
     * @return l'id du user s'il est trouve, -1 s'il trouve pas
     */
   
  public int getUserID(String log, String mdp){
       
        int i=-1;
        List <Users> tUsers=this.findAll();
        for (int j = 0; j < this.count(); j++) {
          if ((tUsers.get(j).getLogin().equals(log)) && (tUsers.get(j).getMdp().equals(mdp)) ){   
              i = tUsers.get(j).getIdUser();
            }
        }
        
    return i;
  }
    
    
}
