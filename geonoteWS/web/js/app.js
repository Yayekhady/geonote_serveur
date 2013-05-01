//variables globales
	  var malatitude;
	  var malongitude;
	/**************************************D�finition du Backbone*******************************************************************/
    var geonote = (function(){
    	// on d�clare les url qui nous permettent de joindre nos webservices
    	var urlws = "http://localhost:35342/Users/Users";
    	var urlws2 = "http://localhost:35342/Notes/Notes";
    	var urlws3=  "http://localhost:35342/Parcours/Parcours";
    // On instancie les variables utilis�s dans nos vues
    	var i=0;
    	var latitude=233.5;
    	var longitude=45;
    	var theme="sport";
		var  Ville="Paris";
		var IDParcours=0;
	

		var donnees = {
		    stores: {},
		    views: {}  
	    };
	  donnees.stores.User = new Store('User');
	
	/************************************************D�claration du mod�le ********************************************************/
	   var geonoteModel = Backbone.Model.extend({
	
        // on d�clare tous les arguments par d�faut qui peuvent �tre utilis� dans notre modele, notamment pour la r�cup�ration des donn�es de la base
		      defaults: {
		        
			      		nom:null,
			      		pwd:null,
			      		id:null,
			      		login: null,
			      		mdp: null,
			      		valeur:null,

		      },
		  // le Local storage permet de stocker les donn�es relatives � un utilisateur en local pendant un certains temps 
	    localStorage: donnees.stores.User,
	    initialize: function() {	
		}
		});
	  
	/************************************************** D�clarattion de la collection ********************************************/
	  
    	var geonoteCollection = Backbone.Collection.extend({
		    model: geonoteModel,
		    localStorage: donnees.stores.User,
		    initialize: function(){}
      
 	    });
 	
 	 /**************************************************la Vue pour un nouvel inscrit*****************************************************/
    
var NewUserView = Backbone.View.extend({
 
      // on d�clare l'�v�nement li� au clic sur le bouton valider
       events:{
 			   'click #inscription':'creercompte'
		      },
		
      creercompte:function(event){
      // on r�cup�re les valeurs rentr�es par l'utilisateur par la fonction getAtributes
			 var attrs = this.getAttributes();
			// on instancie un nouveau model 
			 var user = new geonoteModel();
			// la fonction validate d�crite plus loin  et  utilis�e ici renvoie true si les mots de passe rentr�s coincident 
             if (!this.validate(attrs)){                      
			     alert("Confirmation du mot de passe incorrect! ");
				 event.preventDefault();
				 event.stopPropagation();
			 }
			 else{				              
				  // si les mots de passes coincident, on peut remplir notre base en d�clarant une requete soap
                  var that = this;                        
                  // Ici on d�finit la "soap request" qui fait appel � la fonction creer compte de nos webservice
                  var soapRequest = 
                  	'<?xml version="1.0" encoding="UTF-8"?>' +
                    '<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">'+ 
		    			'<S:Header/>'+ 
		    		    	'<S:Body>' +
		        		    	'<ns2:creerCompte xmlns:ns2="http://webservice.geonote/"> '+
		        		          // On d�finit les arguments de la fonction creercompte
		        		          '<log>'+attrs.username+'</log> '+
		        			      '<mdp>'+attrs.pwd+'</mdp>'+
               		             '</ns2:creerCompte> '+
		    		         '</S:Body>'+
				      '</S:Envelope>' ;   
                        
                        
                         // ici on envoie la requete vers le serveur
                  $.ajax({
		    	  	type: "POST",
		    		 
		    		 //On d�clare l url pour joindre le serveur et donc acc�der au Webservice associ� au User
		    		 	url: urlws,
					    contentType: "text/xml",
					    dataType: "xml",
					    data: soapRequest,
                          
                     //Si la requete est un succes 					              
					 	success: function(data) {
					    	$(data).find('return').each(function() {
                             // si le retour de la requete est 0, l utilisateur a bien �t� enregistr�                                                     
                             	if($(this).text()==0){
        						    alert("L'utilisateur a ete cree");
        						    
        						    //On affiche la page relative � MesParcours
        						    window.location.href="#Connexion";
 					            }
 					            
                              // Si le retour est , utilisateur non enregistr�
        					     else{
       						          alert("L'utilisateur n'a pas ete cree");
       						          window.location.href="#S_inscrire";
                   			     }

					});	
  		     },
             
             // Si erreur dans l'envoi de la requete au serveur
			  error: function(data) {
              	alert("Erreur : "+$(data.responseXML).find('faultstring').text());
                	console.log(data.statusText);
			  }
		    });
		 }
				             	
				             	
		   // on vide les champs � remplir lors de l'inscription
		   this.reset();
	    }, //fin de la fonction  �venement 
			
      // Fonction d�finie pour r�cup�rer les valeurs rentr�es par un utilisateur
			getAttributes: function(){
			   return{
				    username: this.$('form [name="login"]').val(),
				    pwd: this.$('form [name="mdp"]').val(),
				    cpwd: this.$('form [name="confirm_mdp"]').val(),
				  }
			},
			
			// pour clearer les champs de notre page apres validation des donn�es d inscription
			reset: function(){
			       this.$('input').val('');
			},
			
			//Fonction pour comparer les 2 mots de passes entr�s
            validate: function(attrs){   
			
            	if (attrs.pwd ==attrs.cpwd && attrs.pwd!="") return true;
			    else return false;
            }
  });
  
  /************************************************La vue pour se connecter************************************************************/
  
  var ConnexionView = Backbone.View.extend({
 // On d�clare l'�venement associ� au clic sur le bouton Ok
	    events:{
 			    'click #seconnecter':'SeConnecter'
		        },
	  
	     SeConnecter:function(event){
	        
	        // on r�cup�re les valeurs rentr�es par l'utilisateur par la fonction getAtributes
		 	var attrs = this.getAttributes();
	       
	       // On instancie la collection 
	        this.geonoteUserList = new geonoteCollection();
			   // On d�finit la requete soap, permettant de verifier si l utilisateur est enregistr� dans la base en faisant appel � la fonction seconnecter
			   var soapRequest = 

			   	'<?xml version="1.0" encoding="UTF-8"?>'+
			    '<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">' +
		    	  '<S:Header/>' +
		    		'<S:Body>' +
		        	  '<ns2:seConnecter xmlns:ns2="http://webservice.geonote/">'+
		        		 '<log>'+attrs.username+'</log> '+
		        	     '<mdp>'+attrs.pwd+'</mdp>'+
		        	   '</ns2:seConnecter> '+
		    		  '</S:Body>'+
			      '</S:Envelope>';
			        
			         // On envoie la requete au serveur
			     $.ajax({
		    		   type: "POST",
		    		              
		    		   //On d�clare l url pour joindre le serveur et donc acc�der au Webservice associ� au User
					   url: urlws,
					   contentType: "text/xml",
					   dataType: "xml",
					   data: soapRequest,
                       
                       //On r�cupere le r�sultat de la requete Soap					              
					       success: function(data) {
						       $(data).find('return').each(function() {                         
                                  
                                  // si l utilisateur est enregistr�, on recupere son id 
                                  if($(this).text()==0){
                                 
                                  // on fait une nouvelle requete soap pour recuperer l'id en  appelant la fonction getUserId qui prend en parametres le login et le mot de passe rentr� par l 'utilisateur
                                     var soapRequest = 

			                            '<?xml version="1.0" encoding="UTF-8"?>'+
			                            '<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">' +
		                                  '<S:Header/>' +
		    		                         '<S:Body>' +
		        		                        '<ns2:getUserID xmlns:ns2="http://webservice.geonote/">'+
		        		                            '<log>'+attrs.username+'</log> '+
		        			                        '<mdp>'+attrs.pwd+'</mdp>'+
		        			                     '</ns2:getUserID> '+
		    		                           '</S:Body>'+
			                             '</S:Envelope>';
			                            $.ajax({
		    		                        type: "POST",
		    		                        
		    		                        //On d�clare l url associ� au webservice du User pour joindre le serveur et donc acc�der au Webservice
					                         url: urlws,
					                         contentType: "text/xml",
					                         dataType: "xml",
					                         data: soapRequest,
                                             
                                             // Si la requete a �t� bien enregistr�e 					              
					                         success: function(data) {
						                        $(data).find('return').each(function() {
                                                     
                                                 // On r�cupere dans la r�ponse de la soaprequest l'id du User                          
                                                    var usermodelconnecter = new geonoteModel({id: $(this).text()});
                                                 
                                                 //On enregistre l'id du user connect� ds le localStorage
					                                usermodelconnecter.save();
					                                var key = "User";
					                                console.log(localStorage.getItem(key));
					                              });
					                          },
					                         
					                         
					                         // Si probl�me de connexion avec le serveur renvoi "erreur"
					                         error: function(data) {
					                            alert("Erreur : "+$(data.responseXML).find('faultstring').text());
				                                    console.log(data.statusText);
				                             }
				                          }); 
                                      // On envoie un alert pour annoncer une bonne connexion de l 'utilisateur    	     
                                     alert("L'utilisateur est connecte")
        						                 //On affiche la page relative � MesParcours
        						                  window.location.href="#MesParcours";
 					                        }

        					                else{
       						                 alert("L'utilisateur n'a pas ete cree");
                   					      }

						   });	
  				},
                            
               // Si erreur dans la validation de la requete
		        error: function(data) {
                    alert("Erreur : "+$(data.responseXML).find('faultstring').text());
                	    console.log(data.statusText);
				}
			});
			
			// on vide les champs pour la connexion en faisant appel � la fonction reset	
		     this.reset();
		},
				
				
		// Fonction pour r�cup�rer les donn�es utilisateurs rentr�es 
		getAttributes: function(){
			return{
				    username: this.$('form [name="login"]').val(),
				    pwd: this.$('form [name="mdp"]').val(),				
				   }
			   },
	    // Fonction pour clearer les champs pour la connexion 
		reset: function(){
			this.$('input').val('');
		}	          
});
	  
/*********************************************************la vue pour une cr�ation de Note ****************************************/
  
var NewNoteView = Backbone.View.extend({ 
   
   // On d�clare l'�venement associ� au clic sur le bouton OK pour la cr�ation de note
    events:{
 			'click #ValiderNote':'creernote'
		},
		
		// On d�crit la fonction associ�e au clic sur le bouton ok pour creer une note
	creernote:function(event){
		var Note = new geonoteModel();
		
		// on r�cup�re les valeurs rentr�es par l'utilisateur par la fonction getAtributes
		var attrs = this.getAttributes();
		
		// On r�cupere le th�me s�lectionn� 
        var selectionNote=this.getSelection().toString();
        
        //console.log(selectionNote);
        var key = "User";
        
        // On r�cupere l'id du User enregistr� dans le local storage
        id=localStorage.getItem(key);
        
        //console.log(id);   
        var that = this;
                        
      // Ici on d�finit  la "soap request"  permettant de faire appel � la fonction creerNote du webservice li� � Note
        var soapRequest = 
          '<?xml version="1.0" encoding="UTF-8"?>' +
              '<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">'+ 
		          '<S:Header/>'+ 
		             '<S:Body>' +
		                '<ns2:creerNote xmlns:ns2="http://webservice.geonote/"> '+		    		                    
		        			 '<nom>'+attrs.Nom+'</nom> '+
		        			 '<them>'+selectionNote+'</them>'+
		        			 '<cmt>'+attrs.Commentaires+'</cmt>'+
		        			 '<lal>'+latitude+'</lal>'+
		        			 '<lgt>'+longitude+'</lgt>'+
		        			 '<vil>'+Ville+'</vil>'+
		        			 '<id>'+id+'</id> '+
               		     '</ns2:creerNote> '+
		    		   '</S:Body>  '+
				'</S:Envelope>' ;   
                         
       // ici on envoie la requete vers le serveur
          $.ajax({
		  		type: "POST",
		        
		        //On d�clare l url aspour joindre le serveur et donc acc�der au Webservice
				url: urlws2,
			    contentType: "text/xml",
			    dataType: "xml",
			    data: soapRequest,
                
                // Si la requete a �t� bien enregistr�e 					              
				success: function(data) {
					$(data).find('return').each(function() {                         
                            
                       if($(this).text()==1){
	                       alert("La note a ete enregistre");
	                       
	                       // on vide les champs 
	                       this.reset();  
 					   }

 					   else{
       						alert("La note n a pas ete enregistre");
       						this.reset();
       				   }

			 });	
		  },
          
          // Si erreur dans la validation de la requete
          error: function(data) {
          		alert("Erreur : "+$(data.responseXML).find('faultstring').text());
                	console.log(data.statusText);
		  }
	});
	
 },
		
    // Fonction permettant de r�cuperer la s�lection du choix de theme de l utilisateur	
    getSelection: function(){     
        var doc= document.getElementById("themeNote");
        return doc.options[doc.selectedIndex].value;
      
    }, 
		
		// Fonction permettant de r�cuperer le nom de la note et les commentaires rentr�s 
    getAttributes: function(){
		return{
				Nom: this.$('form [name="nom"]').val(),
				Commentaires: this.$('form [name="textarea"]').val()
		}
	},
		// Fonction permettant de clearer les champs de cr�ation de note	
	reset: function(){
		this.$('input').val('');
		this.$('textarea').val('');
	}
});
  
  /******************************************La vue pour cr�ation de parcours**********************************************************/
  
var NewParcoursView = Backbone.View.extend({
      
        // On d�clare les �venements li� au clic des diff�rents bouton de la page creer Parcours
    events:{
         
       //  �venement associ� au clic sur le bouton Valider Parcours 
 	   'click #ValiderParcours':'creerparcours',
 	  
 	  // �venement associ� au clic sur le bouton Ajouter Note 
 	   'click #testNote':'selectionnerNote',
 	   
 	   // �venement associ� au clic sur le bouton valider du pop up permettant de valider les notes � ajouter au parcours
 		'click #ValiderNote':'validationNote'
	},
 
      // Fonction permettant de r�cuperer la s�lection du choix de theme de l utilisateur	
        getSelection: function(){     
            var doc= document.getElementById("themeParcours");
            return doc.options[doc.selectedIndex].value;
      
        }, 
		
		    // Fonction permettant de r�cuperer le nom du parcours et les commentaires rentr�s 
        getAttributes: function(){
			     return{
			     	Nom: this.$('form [name="nom"]').val(),
				    Commentaires: this.$('form [name="textarea"]').val()
				  }
		},
			   
		    // Fonction permettant de clearer les champs de cr�ation de parcours	
		reset: function(){
		      	this.$('input').val('');
			    this.$('textarea').val('');
	    },
	       	
		    // �venement associ� au clic sur le bouton Ajouter Note 
	   selectionnerNote:function(event){
              
			var that = this;
			
			//la collection ou sera recueillie les donn�es prises de la base 
			this.NoteUserList = new geonoteCollection();
			var key="User";
			idUser=localStorage.getItem(key);
			var choixParcours=this.getSelection().toString();
			console.log(choixParcours);
			   
			// On d�finit la requete soap, ici on utilise afficherNoteTheme pour r�cuperer la liste des notes d utilisateur  par theme
		    var soapRequest = 

			   '<?xml version="1.0" encoding="UTF-8"?>'+
			   '<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">' +
		    	  '<S:Header/>' +
		    		 '<S:Body>' +
		        		'<ns2:afficherNoteTheme xmlns:ns2="http://webservice.geonote/">'+
		        		     '<id>'+idUser+'</id>'+
		        		     '<theme>'+choixParcours+'</theme>'+		        		          
		        		'</ns2:afficherNoteTheme>'+
		    		  '</S:Body>'+
			    '</S:Envelope>';
				    
             $.ajax({
		    	    type: "POST",
		    	    
		    	    //ici on donner l'url associ� au webservice associ� � Note, urlws2 est d�clar� tt au d�but
				    url: urlws2,
				    contentType: "text/xml",
				    dataType: "xml",
				    data: soapRequest,
				    
				    //si la requete est un succes, on r�cupere la liste de notes
				    success: function(data) {
					      $(data).find('return').each(function() {
					           
					           //  on r�cupere la r�ponse de la soap request
					           var userNotemodel = new geonoteModel({nom: $(this).find('nomNote').text()}); 						        
						       
						       //ON met les donn�es dans la liste
						       that.NoteUserList.add(userNotemodel);                    
						       
						       //console.log(that.NoteUserList);
						      for (var i=0; i<that.NoteUserList.length;i++){                       
							      console.log(that.NoteUserList.at(i).get("nom"))
							      myDiv = document.getElementById("affichage");
							      var checkbox = document.createElement("input"); 
							      checkbox.setAttribute("type", "checkbox");
							      checkbox.setAttribute("name", "dd");
							      checkbox.setAttribute("value", that.NoteUserList.at(i).get("nom"));
							      var text = document.createTextNode(that.NoteUserList.at(i).get("nom"));
							      myDiv.appendChild(checkbox); 
							      myDiv.appendChild(text);     
               
                            }						         
				});
					 
			},
          
         //Si erreur de l'envoi de la requete
         error: function(data) {
			alert("Erreur : "+$(data.responseXML).find('faultstring').text());
				 console.log(data.statusText);
		}              
              
    });         
                          
   }, 
 
   	// pour r�cuperer les notes s�lectionn�s pour un parcours
        validationNote:function(event) {
          var that=this;
          this.NoteList = new geonoteCollection();
      
          //console.log(that.NoteUserList);
          var test=document.getElementsByName("dd");
          
            for(i=0;i<test.length; i++){ 
                 if (test.item(i).checked){
                        var notemodel = new geonoteModel({valeur: test[i].value});
                        that.NoteList.add(notemodel);
                        console.log(test[i].value);
                     }
        
            }
    
        }, 
        
        // On d�clare la fonction �venement associ� au clic sur le bouton Valider  pour enregistrer un nouveau parcours
     creerparcours:function(event){
       
			var Parcours = new geonoteModel();								
			
			// on r�cup�re les valeurs rentr�es par l'utilisateur par la fonction getAtributes
			var attrs = this.getAttributes();
			
			// On r�cupere le th�me s�lectionn� 
			var selectionParcours=this.getSelection().toString();
			console.log(selectionParcours);
			
			// on r�cupere l'id du User enregistr� dans le local storage
			var key = "User";
			id=localStorage.getItem(key);
			var that = this; 
			
			//console.log(that.NoteList); 
                                           
            // Ici on d�finit  la "soap request" associ�e � la fonction creerparcours 
            var soapRequest = 
                '<?xml version="1.0" encoding="UTF-8"?>' +
                '<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">'+ 
		    		'<S:Header/>'+ 
		    		   '<S:Body>' +
		        		  '<ns2:creerParcours xmlns:ns2="http://webservice.geonote/"> '+		        		                    		 	'<nom>'+attrs.Nom+'</nom> '+
		        			    '<theme>'+selectionParcours+'</theme>'+
		        			    '<cmt>'+attrs.Commentaires+'</cmt>'+
               		       '</ns2:creerParcours> '+
		    		    '</S:Body>  '+
				   '</S:Envelope>' ;   
               
               // ici on envoie la requete vers le serveur
               $.ajax({
		    		type: "POST",
		    		              
		    		//On d�clare l url pour joindre le serveur et donc acc�der au Webservice Parcours
					 url: urlws3,
					 contentType: "text/xml",
					 dataType: "xml",
	                 data: soapRequest,
 
                    // Si l'envoi de la raquete au serveur est un succes					              
	                 success: function(data) {
		                  $(data).find('return').each(function() {                         
                   
                   // Si la requete a �t� bien enregistr�e 
                         if($(this).text()==1){
                            var soapRequest = 
                            '<?xml version="1.0" encoding="UTF-8"?>' +
                            '<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">'+ 
		    		           '<S:Header/>'+ 
		    		              '<S:Body>' +
		        		             '<ns2:returnIDParcours xmlns:ns2="http://webservice.geonote/"> '+		        		                 '<nom>'+attrs.Nom+'</nom> '+		        			                                   
               		                  '</ns2:returnIDParcours> '+
		    		               '</S:Body>  '+
				             '</S:Envelope>' ;
				         
				         $.ajax({
		    		          type: "POST",
		    		          
		    		          //On d�clare l url aspour joindre le serveur et donc acc�der au Webservice
					          url: urlws3,
					          contentType: "text/xml",
					          dataType: "xml",
					          data: soapRequest,
                              
                              // Si la requete a �t� bien enregistr�e 					              
					          success: function(data) {
						           $(data).find('return').each(function() {                                                     
                                          IDParcours=$(this).text();
                                          console.log(IDParcours);
						             });	
						             for(i=0; i<that.NoteList.length;i++){
						                  console.log(that.NoteList.at(i).get("valeur"));
                                    
                            // Ici on d�finit  la "soap request"  permettant de faire appel � la fonction creerNote du webservice li� � Note
                                    var soapRequest = 
                                        '<?xml version="1.0" encoding="UTF-8"?>' +
                                        '<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">'+ 
		                                   '<S:Header/>'+ 
		                                      '<S:Body>' +
		                                         '<ns2:affecterParcours xmlns:ns2="http://webservice.geonote/">'+		    		                    
		        			                         '<p>'+IDParcours+'</p> '+
		        			                         '<note>'+that.NoteList.at(i).get("valeur")+'</note> '+
		        			                         '<id>'+id+'</id> '+
               		                             '</ns2:affecterParcours> '+
		    		                            '</S:Body>  '+
				                          '</S:Envelope>' ;
				                                                  
                                      $.ajax({
		    		                      type: "POST",
		    		                      
		    		                      
		    		                      //On d�clare l url aspour joindre le serveur et donc acc�der au Webservice
					                       url: urlws2,
					                       contentType: "text/xml",
					                       dataType: "xml",
					                       data: soapRequest,
                                          
                                          // Si la requete a �t� bien enregistr�e 					              
					                      success: function(data) {
						                      $(data).find('return').each(function() {                                                    
                                                    if($(this).text()==1){
        						                        console.log("La note a �t� mise � jour ");      						                 
 					                                 }
        					                        else{
       						                            console.log("La note n a pas ete mise � jour");
       						                        }

						                 });	
  							     },
                              
                          // Si erreur dans la validation de la requete
					                 error: function(data) {
                                         alert("Erreur : "+$(data.responseXML).find('faultstring').text());
                					     console.log(data.statusText);
					                  }
				                  });
	                         
                             }
  			          },
                                                  
                         // Si erreur dans la validation de la requete
					                 error: function(data) {
                                         alert("Erreur : "+$(data.responseXML).find('faultstring').text());
                					     console.log(data.statusText);
					                  }
				   });
 
        		     alert("Le parcours a ete enregistre");                                           						                 
 			  }

              else{
       						                           
                 alert("Le parcours n'a pas ete enregistre");
              }

	     });	
  	  },
    
     // Si erreur dans la validation de la requete
       error: function(data) {
            alert("Erreur : "+$(data.responseXML).find('faultstring').text());
            console.log(data.statusText);
       }
     });
	   
	   this.reset();
        
	},
 
  }); 
  
  /******************************************Une vue pour afficher les notes de l'utilisateur*******************************************/
  var MesNotesView = Backbone.View.extend({
  
	  // On d�clare l'�venement associ� au clic sur le bouton note d�ja cr��					         
      events:{
 			  'click #notescrees':'Mesnotes'
		  },
		  
	   // Fonction d'initialisation permettant de faire la requete soap
   
      initialize: function() {  
			   var that = this;
			   
			   //la collection ou sera recueillie les donn�es prises de la base 
			   this.NoteUserList = new geonoteCollection();
			   
			   // On r�cupere l'id du User dans le local storage
			   var key="User";
			   idUser=localStorage.getItem(key);
         		   
			   // On d�finit la requete soap, ici on utilise afficherNote pour r�cuperer la liste des notes d'un utilisateur
				 var soapRequest = 
			       '<?xml version="1.0" encoding="UTF-8"?>'+
			       '<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">' +
		    	       '<S:Header/>' +
		    		      '<S:Body>' +
		        		     '<ns2:afficherNote xmlns:ns2="http://webservice.geonote/">'+
		        		         '<id>'+idUser+'</id>'+
		        		     '</ns2:afficherNote>'+
		    		       '</S:Body>'+
			         '</S:Envelope>';
			         
				    // On fait la requete pour l envoi vers le serveur
            $.ajax({
		    	  type: "POST",
		    	  
		    	  //ici on donne l'url associ� au webservice Note
				  url: urlws2,
				  contentType: "text/xml",
				  dataType: "xml",
				  data: soapRequest,
				    
				  //si la requete est un succes, on r�cupere la liste de notes
				  success: function(data) {
					     $(data).find('return').each(function() {
					           
					      	//  on r�cupere la return de la soap request				           
                            var userNotemodel = new geonoteModel({nom: $(this).find('nomNote').text()});
                        						        
						    //on met les donn�es dans la liste
						         
						    that.NoteUserList.add(userNotemodel);                    
						    console.log(that.NoteUserList);
						         
						    });
					 
				 },
               
                // Si erreur lors de l'envoie de la requete au serveur
				 error: function(data) {
					   alert("Erreur : "+$(data.responseXML).find('faultstring').text());
				       console.log(data.statusText);
				  }

			 });
			
			},
	
	// On d�finit la fonction associ� au clic sur le bouton notes d�ja cr��s
	   'Mesnotes': function() {
        
            var that = this;
            
         // Par une boucle on parcours toutes les notes et on l affiche dynamiquement sur la page
	          for (var i=0; i<that.NoteUserList.length; i++){		    
						          console.log(that.NoteUserList.at(i).get("nom"));
						          // testlist est l'id de la div o� sera affich�e les note					          
						          $("<li>"+that.NoteUserList.at(i).get("nom")+"</li>").appendTo("#testlist");      
		        	}  
         
       }
    }); 
    
    
    
    /********************************************la vue pour afficher la postition de l utilisateur**************************************/
var positionView = Backbone.View.extend({
 
      // on d�clare l'�v�nement li� au clic sur le bouton valider
       events:{
 			          'click #testposition':'afficherposition',
 			          'click #position':'afficherposition'
	   },
	   
	   afficherposition:function(event){
		    this.MesCoords();
			//$("<label>latitude:"+malatitude+"</label>").appendTo("#affichagePosition");
			//$("<label>longitude:"+malongitude+"</label>").appendTo("#affichagePosition");
			console.log(malatitude);
			console.log(malongitude) ;
		},
			MesCoords:function(){
		
 			if (navigator.geolocation){
  				
          var point = navigator.geolocation.getCurrentPosition(positionCallback, null);
  			}
        else{
  				alert("Votre navigateur ne prend pas en compte la g�olocalisation HTML5");    
  			}			
	   }

 });
		          
/***************************************************La vue pour se d�connecter**********************************************************/
    var DeconnexionView = Backbone.View.extend({
        event:{
           
          'click #logout1' : 'seDeconnecter',    
          'click #logout2' : 'seDeconnecter',    
          'click #logout3' : 'seDeconnecter',    
          'click #logout4' : 'seDeconnecter',    
          'click #logout5' : 'seDeconnecter',    
          'click #logout6' : 'seDeconnecter',    
          'click #logout7' : 'seDeconnecter',    
          'click #logout8' : 'seDeconnecter',    
          'click #logout9' : 'seDeconnecter',   
          'click #logout10' : 'seDeconnecter',   
          'click #logout11' : 'seDeconnecter',    
          'click #logout12' : 'seDeconnecter',    
          'click #logout13' : 'seDeconnecter'
        },
   	    initialize: function() {
			 var that = this;
			 this.geonoteList = new geonoteCollection();

		},

		seDeconnecter: function(event){
			 localStorage.clear();

		}
  
}); 
   
	/**************************D�claration des blocs o� seront affich�s nos diff�rentes vues***************************************/
$(document).ready(function(){

      donnees.views.new_form = new NewUserView({
        	el: $("div#S_inscrire")  	
      });
      donnees.views.new_form = new ConnexionView({
        	el: $("div#Connexion")
      });
       donnees.views.new_form = new NewNoteView({
        	el: $("div#MesNotes")
      });
      donnees.views.new_form = new NewParcoursView({
        	el: $("div#CreerParcours")
      });
      donnees.views.new_form = new MesNotesView({
        	el: $("div#MesNotes")
      });
       donnees.views.new_form = new positionView({
        	el: $("body")
      });
      donnees.views.new_form = new DeconnexionView({
        	el: $("body")
      });  
      });
}) ();
	    
/*********$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$Gestion de la geolocalisation $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$*****************/
 



	  
	  
/***************************** Cette partie permet d'avoir les positions pour la creation d'une note***************************/
	

	function positionCallback(position){
		
		new google.maps.LatLng(position.coords.latitude, position.coords.longitude)
		
		//on r�cup�re les coordonn�es
		
		malatitude=position.coords.latitude;
		malongitude=position.coords.longitude;
		
	}
	

	//permet d'afficher la carte lorsqu'on fait appel a la page Descriptif
	//  $('#CreerNote').live("pageshow",function(event, ui){
      //         MesCoords();
        //   })






/************************* Cette partie permet d'afficher la carte avec un descriptif des diff�rents etapes ***************************/

	
	function MaPosition(){	
  			if (navigator.geolocation){
  				var point = navigator.geolocation.getCurrentPosition(successCallback, null);
  			}else{
  				alert("Votre navigateur ne prend pas en compte la g�olocalisation HTML5");    
  			}
  		}

  	// si le navigateur supporte la geolocalisation, on recupere l'element de l apage html ou doit s'afficher la carte
  	//on affiche la carte et la position
		
	function successCallback(position){
	  	var map = new google.maps.Map(document.getElementById("mapCarte"),{
  				center: new google.maps.LatLng(position.coords.latitude, position.coords.longitude),
  		        zoom: 12,
  		        mapTypeId: google.maps.MapTypeId.ROADMAP
  		    });    
  		     // on cr�e un marqueur pour specifier sa position
  	var marker = new google.maps.Marker({
    				position:new google.maps.LatLng(position.coords.latitude, position.coords.longitude), 
    				map: map
     			 		 
 		 });
 		 
 		 
 /********************************** itineraire *************************************/

 		 //chemin du trac� du polyline
 		 	var parcours = [
 		 	new google.maps.LatLng(position.coords.latitude, position.coords.longitude),
 		 	new google.maps.LatLng(48.790, 2.250),
 		 	new google.maps.LatLng(48.7817, 2.2202),
 		 	new google.maps.LatLng(48.8005, 2.1291)
 		 	];

 		  for (var i in parcours){
		    	 	new google.maps.Marker({
    				position:parcours[i], 
    				map: map
     			 		 
    				});
			}
			
 		 	
 		 	var traceParcours = new google.maps.Polyline({
	 		 	path: parcours, //chemin du trac�
	 		 	strokeColor: "#FF0000", //couleur du trac�
	 		 	strokeOpacity: 1.0, //opacit� du trac�
	 		 	strokeWeight: 2 //grosseur du trac�
	 		 	});
	 		 	
	 		//lier le trac� � la carte
	 		//ceci permet au trac� d'�tre affich� sur la carte
	 		traceParcours.setMap(map);
 		 	
 		 	
 	 
 		 }
 		  
 		//permet d'afficher la carte lorsqu'on fait appel a la page Descriptif
	  	$('#Descriptif').live("pageshow",function(event, ui){
               MaPosition();
           })
           
/************************* Cette partie permet de suivre le deplacement et le dirige sur les diff�rents etapes ***************************/


/************************* Permet de localiser le d�placement ***************************/


	function Localiser(){
	
	
	  		var userPosition = navigator.geolocation.watchPosition(parcoursCallback, null);
	}



	

	//permet d'afficher la carte lorsqu'on fait appel a la page Descriptif
	 $('#Parcours').live("pageshow",function(event, ui){
           Localiser();
      })
      
      function parcoursCallback(position){
        	var map = new google.maps.Map(document.getElementById("mapParcours"), {
	        zoom: 19,
	        center: new google.maps.LatLng(position.coords.latitude, position.coords.longitude),
	        mapTypeId: google.maps.MapTypeId.ROADMAP
	       });
      
      
      
	      map.panTo(new google.maps.LatLng(position.coords.latitude, position.coords.longitude));
	      
	      //a mettre en commentaire
	      var marker = new google.maps.Marker({
		      position: new google.maps.LatLng(position.coords.latitude, position.coords.longitude),
 
		      map: map
		      }); 
		    
		    //le tableau contenant les diff�rents points de passage
		    var parcours = [
 		 		new google.maps.LatLng(position.coords.latitude, position.coords.longitude),
 		 		new google.maps.LatLng(48.790, 2.250),
 		 		new google.maps.LatLng(48.7817, 2.2202),
 		 		new google.maps.LatLng(48.8005, 2.1291)
 		 	];
 		 	
		    //on affiche un marqueur pour chaque point de passage
		    for(var i in parcours){
		    	 	new google.maps.Marker({
    				position:parcours[i], 
    				map: map
     			 		 
    				});
			}
		    
		    //on cree un second tableau qui contient les etapes (sans l'origine et la destination)		    
		    var waypts = [];    		    
		    for(var i=1; i<parcours.length-1; i++){
			    
			          waypts.push({
				          location:parcours[i],
				          stopover:true
				       });
			    
		    }
		    
		    
		    //pour le calcul du trajet
		   var directionsService = new google.maps.DirectionsService();	 
		   
		   //on recupere le moyen par lequel il veut effectuer le parcours
		   var selectedMode = document.getElementById("mode").value;

		     var request = {
			     origin:parcours[0],
			     destination:parcours[parcours.length-1],
			     waypoints: waypts,
                 optimizeWaypoints: true,
                 travelMode: google.maps.TravelMode[selectedMode]			 
              };      
              
              //variable pour afficher l'itin�raire
              var itineraireAffichage = new google.maps.DirectionsRenderer();
              
			 
			 directionsService.route(request, function(result, status) {
				 if (status == google.maps.DirectionsStatus.OK) {
					 		//on renseigne le r�sultat pour l'affichage
					 		 itineraireAffichage.setDirections(result);
					 		 
					 		 //on lui demande d'afficher sur la carte
					         itineraireAffichage.setMap(map);

				}
				else
					alert("Oups!, parcours non disponible dans le mode choisi");
			});
	
	
	 }
		  
		  
/************************************* Permet d'arreter de localiser le deplacement ***************************/


	function stopWatch(){
		navigator.geolocation.clearWatch(userPosition);
    }


/****************************************** la page autour   ********** ***************************/
	
	function autourMoi(){	
  			if (navigator.geolocation){
  				var point = navigator.geolocation.getCurrentPosition(autourCallback, null);
  			}else{
  				alert("Votre navigateur ne prend pas en compte la g�olocalisation HTML5");    
  			}
  		}

  	// si le navigateur supporte la geolocalisation, on recupere l'element de l apage html ou doit s'afficher la carte
  	//on affiche la carte et la position
		
	function autourCallback(position){
	  	var map = new google.maps.Map(document.getElementById("carteAutour"),{
  				center: new google.maps.LatLng(position.coords.latitude, position.coords.longitude),
  		        zoom: 12,
  		        mapTypeId: google.maps.MapTypeId.ROADMAP
  		    });    
  		     // on cr�e un marqueur pour specifier sa position
  	var marker = new google.maps.Marker({
    				position:new google.maps.LatLng(position.coords.latitude, position.coords.longitude), 
    				map: map
     			 		 
 		 });
 	}


 		//permet d'afficher la carte lorsqu'on fait appel a la page Autour
	  	$('#Autour').live("pageshow",function(event, ui){
               autourMoi()();
           }) 
	  
   