*****************************************************************************************************************************************************************
								 Application Geonote

****************************************************************************************************************************************************************






Ce présent dossier regroupe toutes les données d'implémentation de l'application géonote développée par Khady Dieng et Yaye Khady SYLLA.




Vous trouverez aux emplacements indiquées:


******************************************************************************************************************************************************************
						
geonote_serveur/geonoteWS/build/web qui regroupe toute la partie HTML

******************************************************************************************************************************************************************

->les feuilles de styles utilisées notamment celles jquery et bootstrap dans le dossier css

->les fichiers javascripts disponible dans le dossier js et qui contient d'une part les fichiers propres au développement avec Jquery 
		
  et d'autre part notre modèle de backbone(fichier app.js).Pour les besoins de notre modèle de backbone nous avons également ajouté 

  le fichier underscore. 
  app.js regroupe en plus du backbone, les fonctions de géolocalisation.

->Les images utilisées dans notre pages html dans le dossier img

->le fichier index. html qui regroupe toutes nos pages html:

	La page Accueil qui est l'interface d'accueil de l'application 
		 
	La page connexion qui est l'interface de connexion à l'application
	
	La page inscription pour une nouvelle inscription 
	
	La page Recherche qui est l'interface sur lequel on accede en saisissant par erreur un parcours et qui propose un choix par thème

	La page CreerNote pour creer une nouvelle note

	La page Creer Parcours pour creer un nouveau parcours 
 
	La page Mes parcours pour visualiser ces parcours effectués 

	La page top 5 pour voir les meilleurs parcours 

	La page autour qui nous localise

	La page Descriptif parcours qui donnes les informations relatives à un parcours notamment la distance, la durée...
	
	La page Débuter Parcours qui utilise les données de géolocalisation et qui trace le parcours de l'utilisateur.


******************************************************************************************************************************************************

geonote-serveur/geonoteWS/src/java/geonote qui regroupe nos webservices ainsi que les ejb entity et sessions 

******************************************************************************************************************************************************

->Les ejb entity permettent l'intraction avec la base de données et présentent des methodes getters et setters de chaque champs de notre base. 

On a défini autant d'entités que l'on a de table dans notre base. Ces entités sont au nombre de 4 


	Note  qui décrit la table Note

	Users qui décrit la table users 

	Parcours qui décrit la table parcours 

	Parcourseffectués qui décrit la table parcours effectué


->Les ejb sessions permettent de manipuler la base au travers des ejb entity et en utilisant des méthodes implémentées en fonction de nos besoins. Nous avons 

créés comme pour les entités, 4 ejb sessions sous le dossier/sessions

	NoteFaçade où sont décrit toutes les méthodes relatives à la gestion des notes

	usersFaçade où sont décrit toutes les méthodes relatives à la gestion des users

	parcoursFaçade où sont décrit toutes les méthodes relatives à la gestion des parcours

	ParcourseffectuésFaçade où sont décrit toutes les méthodes relatives à la gestion des parcours effectués par l'utilisateur
 

->Les webservices permettent l'interaction avec notre application cliente. Ils sont appelés dans notre backbone pour faire le lien avec les serveurs par
  
des requetes soap. Lew webservices sont sous le repertoire /webservice et sont au nombre de 3

	Users pour faire appel aux méthodes spéciques aux users 

	Parcours pour faire appel aux méthodes spéciques à parcours

	Notes pour faire appel aux méthodes spécifiques à Notes
