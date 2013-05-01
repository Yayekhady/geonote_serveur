/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geonote.session;

import geonote.entity.ParcoursEffectues;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author khadydieng
 */
@Stateless
public class ParcoursEffectuesFacade extends AbstractFacade<ParcoursEffectues> {
    @PersistenceContext(unitName = "geonoteWSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParcoursEffectuesFacade() {
        super(ParcoursEffectues.class);
    }
    
    
      /**
     *Cette methode permet d instancier un parcours qu un utilisateur a effectue
     * si l utilisateur a deja effectue ce parcours, il met a jour la note
     * et incremente le compteur de parcours effectues:nb
     * @param pa parcours id
     * @param u user id
     * @param notation notation donnee
     * @return 1 si le parcours est enregistré 0 sinon
     */
         public int creerParcoursEffectues(int pa, int u, int notation){
    
        int b=0;
        ParcoursEffectues pef=new ParcoursEffectues(pa,u);
        List <ParcoursEffectues> listpef = findAll();
           
        
        for(ParcoursEffectues p : listpef){
            if(p.equals(pef)){
                //si le parcours existe deja on met a jour la note
                if (notation!=-1){
                    p.setNotation(notation);
                    p.setNb(p.getNb()+1);
                    b=1;
                }  
                else{
                    b=0;
                }
            }        
        }
        //le parcours n'existe pas dans la table
        if (notation!=-1){
            pef.setNotation(notation);
            pef.setNb(1);
            create(pef);
            b=1;
        }
        else{
           pef.setNb(1);
           create(pef);
           b=1;
        }
        return b;  
    }
    

    /**
     * Permet de retrouver les parcours effectues par un user
     * @param id l id du user
     * @return une liste contenant les parcours
     */
        public List afficherParcoursEf(int id){
        
        List <ParcoursEffectues> pef=null;
        List <ParcoursEffectues> listpef=this.findAll();
        for(ParcoursEffectues p : listpef){
            if(p.getUsers().getIdUser()==id){
                pef.add(p);           
            }        
        }
        
        return pef;  
    
    }
    
}
