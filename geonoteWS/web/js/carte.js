
/************************* Cette partie permet d'afficher la carte avec un descriptif des différents etapes ***************************/

	
	function MaPosition(){	
  			if (navigator.geolocation){
  				var point = navigator.geolocation.getCurrentPosition(successCallback, null);
  			}else{
  				alert("Votre navigateur ne prend pas en compte la géolocalisation HTML5");    
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
  		     // on crée un marqueur pour specifier sa position
  	var marker = new google.maps.Marker({
    				position:new google.maps.LatLng(position.coords.latitude, position.coords.longitude), 
    				map: map
     			 		 
 		 });
 		 
 		 
 /********************************** itineraire *************************************/

 		 //chemin du tracé du polyline
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
	 		 	path: parcours, //chemin du tracé
	 		 	strokeColor: "#FF0000", //couleur du tracé
	 		 	strokeOpacity: 1.0, //opacité du tracé
	 		 	strokeWeight: 2 //grosseur du tracé
	 		 	});
	 		 	
	 		//lier le tracé à la carte
	 		//ceci permet au tracé d'être affiché sur la carte
	 		traceParcours.setMap(map);
 		 	
 		 	
 	 
 		 }
 		  
 		//permet d'afficher la carte lorsqu'on fait appel a la page Descriptif
	  	$('#Descriptif').live("pageshow",function(event, ui){
               MaPosition();
           })
           
/************************* Cette partie permet de suivre le deplacement et le dirige sur les différents etapes ***************************/


/************************* Permet de localiser le déplacement ***************************/


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
		    
		    //le tableau contenant les différents points de passage
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
              
              //variable pour afficher l'itinéraire
              var itineraireAffichage = new google.maps.DirectionsRenderer();
              
			 
			 directionsService.route(request, function(result, status) {
				 if (status == google.maps.DirectionsStatus.OK) {
					 		//on renseigne le résultat pour l'affichage
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
  				alert("Votre navigateur ne prend pas en compte la géolocalisation HTML5");    
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
  		     // on crée un marqueur pour specifier sa position
  	var marker = new google.maps.Marker({
    				position:new google.maps.LatLng(position.coords.latitude, position.coords.longitude), 
    				map: map
     			 		 
 		 });
 	}


 		//permet d'afficher la carte lorsqu'on fait appel a la page Autour
	  	$('#Autour').live("pageshow",function(event, ui){
               autourMoi()();
           })