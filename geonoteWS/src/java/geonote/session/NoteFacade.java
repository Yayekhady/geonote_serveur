/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geonote.session;

import geonote.entity.Note;
import geonote.entity.Parcours;
import geonote.entity.Users;
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
public class NoteFacade extends AbstractFacade<Note> {
    @PersistenceContext(unitName = "geonoteWSPU")
    private EntityManager em;
    private List<Note> tNote;
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NoteFacade() {
        super(Note.class);
    }
     /**
     * Cette methode permet de creer une note an prenant compte si le champ 
     * commentaire est vide, elle prend en parametre 
     * 
     * @param nom Le nom de la note
     * @param theme le theme de la note 
     * @param commentaire le commentaire si besoin
     * @param lalitude les coordonnees
     * @param longitude les coordonnees
     * @param ville la ville de la note
     * @param id qui est l id du user
     * @return 1 si la note est enregistrée 0 sinon
     */
        public int creerNote(String nom, String them, String cmt, double lal, double lgt, String vil, int id){
        Users ut=new Users(id);
        Note note=new Note();
        int b=0;
        //on verifie si le champ commentaire est vide ou pas
        if ("".equals(cmt)){
            note.setNomNote(nom);
            note.setLatitude(lal);
            note.setLongitude(lgt);
            note.setVille(vil);
            note.setTheme(them);
            note.setUsersID(ut);
           
	create(note);
        b=1;
        
        }
        else{
        
            note.setNomNote(nom);
            note.setLatitude(lal);
            note.setLongitude(lgt);
            note.setVille(vil);
            note.setTheme(them); 
            note.setCommentNote(cmt);
            note.setUsersID(ut);
            
	   create(note);
           b=1;
        
        }
        
        return b;
    }
    
     /**
     *Permet d affecter un parcours a une note
     * @param p l id du parcours
     * @param note la liste de nom de notes selectionnee
     * @param id  du user
     * @return 1 si la note est enregistrée 0 sinon 
     */
       //public int affecterParcours(int p, List<String> note, int id){
        public int affecterParcours(int p, String note, int id){
        int b=0;
        List <Note>nt=this.afficherNote(id);
        Parcours parcours=new Parcours(p);
        
        //on cherche les id de note correspondant a la liste en parametre
        //on met a jour l id du parcours
        //for (String nom : note){
            for (Note nt2: nt){
                if (nt2.getNomNote().equals(note)){
                    nt2.setParcoursID(parcours);
                    em.merge(nt2);
                    b=1;
                }
            }       
        //}
                
        return b;  
    }
    
      /**
     *Permet de recuperer toutes les notes qui appartiennent a un user
     * @param id l'id du user
     * @return une liste contenant les notes 
     */
    
    public List <Note> afficherNote(int id){
        Users u=new Users(id);
        
        Query query;
   
        query = em.createQuery("select n from Note n " + 
                              "where n.usersID = ?1") ;
        query.setParameter(1,  u) ;
        tNote=query.getResultList();
                   
        return tNote;
    }
    
    /**
     * Cette methode permet d avoir la liste des id de parcours d un user
     * @param id l utisateur concerne 
     * @return un tableau d id de parcours
     */
       public List <Integer> idParcours(int id){
        List <Integer> tID;
        tID = null;
        Users u=new Users(id);
        tNote=this.findAll();
        
        //on parcours le tableau de note et on regarde si le champ user correspond 
        //a celui en parametre
        for (Note nt : tNote){
            if (nt.getUsersID().getIdUser().equals(u.getIdUser())){
                tID.add(nt.getIdNote());
            }
        } 
        return tID;
         
     }
    
    /**
     * Permet de supprimer une note
     * @param id l id de la note
     */
        public void supprimerNote(int id){
        tNote=this.findAll();
        
        for(Note nt : tNote){
            if(nt.getIdNote().equals(id)){
                this.remove(nt);
            }
        }
    
    }
    
    /**
     * Cette methode permet d avoir une liste de note en fonction du theme de
     * parcours selectionne
     * @param id l id du user
     * @param theme le theme selectionne
     * @return une liste de note correspondant et au user et au theme
     */
        public List <Note> afficherNoteTheme(int id, String theme){
        //on recupere la liste des notes d un user
  
        Users u=new Users(id);
        
        Query query;
   
        query = em.createQuery("select n from Note n " + 
                              "where n.usersID = ?1 and n.theme = ?2") ;
        query.setParameter(1,  u) ;
        query.setParameter(2,  theme) ;

        tNote=query.getResultList();
                   
        return tNote;
            
    }
    
    /**
     * Cette methode retourne les notes d'un parcours
     * 
     * @param id l id du parcours
     * @return la liste de note
     */
        public List retourneNoteParcours(int id){
       
        List <Note> note=this.findAll();

                Parcours p=new Parcours(id);
        
        Query query;
   
        query = em.createQuery("select n from Note n " + 
                              "where n.parcoursID = ?1") ;
        query.setParameter(1,  p) ;
        tNote=query.getResultList();
        return tNote;
    
    }
    
}
