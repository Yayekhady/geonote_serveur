/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geonote.session;

import geonote.entity.Parcours;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author khadydieng
 */
@Stateless
public class ParcoursFacade extends AbstractFacade<Parcours> {
    @PersistenceContext(unitName = "geonoteWSPU")
    private EntityManager em;
    private List <Parcours>tParcours;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParcoursFacade() {
        super(Parcours.class);
    }
     /**
 * Permet la creation d un nouveau parcours prend en paremetre:
 * @param nom nom du parcours
 * @param theme le theme du parcours au choix parmi 4
 * @param commentaire un commentaire facultatif
 * @param distance la distance qui se calcule en faisant la somme de la distance
 * entre les notes
 * 
 * @return 1 si le parcours est enregistré 0 sinon
 */
    
        public int creerParcours(String nom, String theme, String cmt){
        int b=0;
        Parcours p=new Parcours();
        
        if("".equals(cmt)){
            p.setNomParcours(nom);
            p.setTheme(theme);
            b=1; 
	create( p );     
        }
        else{
            p.setNomParcours(nom);
            p.setTheme(theme);
            p.setCommentParcours(cmt);
            b=1;  
	create (p );       
        }
        
        return b;
    }
    
    
    /**
     * Cette methode permet de retourne l id d un parcours connaissant son nom
     * elle servira a tagger les notes a un parcours
     * @param nom nom du parcours
     * @return id du parcours
     */
        public int returnIDParcours(String nom){
        
        int id = -1;
        
        List <Parcours> tParcours=this.findAll();
        
        //on recherche l id du parcours
        for (Parcours p: tParcours){
            if(p.getNomParcours().equals(nom)){
                id=p.getIdParcours();       
            }
        }
        return id;     
    }
    
    
    
    /**
     * retourne les parcours crees
     * @param id id de l'utilisateur
     * @return une liste des parcours
     */
        public List afficherParcours(int id){
        NoteFacade note=new NoteFacade();
        List tID=note.idParcours(id);
        List <Parcours> Listp=this.findAll();
        List <Parcours> p=null;
         
        // on parcours notre tableau d id, pour chaque id de tID
        //on parcours la table de parcours, si on le trouve on l ajoute
        //au tableau a retourner
        for (Object i :tID){
             for(Parcours k : Listp){
                 if(i.equals(k.getIdParcours())){
                     p.add(k);
                 }
             }         
         }       
        return p; 
        
        
        

    }
    
    
    
    
    /**
     * Methode pour supprimer un parcours
     * @param id l id du parcours
     */
        public void supprimerParcours(int id){
        tParcours=this.findAll();
        
        for(Parcours p: tParcours){
            if(p.getIdParcours().equals(id)){
                this.remove(p);
            }
        } 
        
    
    }

    
    
    /**
     * Cette methode permet de mettre a jour le champ distance lors de la 
     * creation d un parcours
     * @param dist la distance calculee une fois toutes les notes selectionnees
     * @param nom le nom du parcours
     * @return 1 si succes 0 sinon
     */
        public int distance(double dist, String nom){
        int b=0;
        
      tParcours=this.findAll();
        
        //on recherche le parcours et on met a jour le champ
        for (Parcours p: tParcours){
            if(p.getNomParcours().equals(nom)){
                p.setDistance(dist);
                b=1;
            }
        }   
        return b;
    
    
    }
    /**
     * Cette methode permet de recuperer un liste de parcours par theme
     * @param theme le theme du parcours
     * @return la liste de parcours pour ce theme
     */
   public List afficherParcoursTheme(String theme){

    
    Query query;
   
        query = em.createQuery("select p from Parcours p " + 
                              "where p.theme = ?1") ;
        query.setParameter(1,  theme) ;
        tParcours=query.getResultList();
                   
        return tParcours;
    
    }
    
}
