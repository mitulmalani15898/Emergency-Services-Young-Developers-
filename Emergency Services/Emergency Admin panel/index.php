
<?php include("head.php"); ?>

<script src="https://www.gstatic.com/firebasejs/4.12.1/firebase.js"></script>
<script>
  // Initialize Firebase
  var config = {
    apiKey: "AIzaSyD4JdcfQeuE0KR74em2zWTb8HeJ1G2kZZw",
    authDomain: "emergencyservice-5b9e8.firebaseapp.com",
    databaseURL: "https://emergencyservice-5b9e8.firebaseio.com",
    projectId: "emergencyservice-5b9e8",
    storageBucket: "emergencyservice-5b9e8.appspot.com",
    messagingSenderId: "301362770578"
  };
  firebase.initializeApp(config);
</script>
<script>
	function myFunction()
	{
	var lati;
	var lang;
	var table =document.getElementById("tab");
	
	var rtref = firebase.database().ref().child("user");
	rtref.on("child_added", snap => {
		
		var name = snap.child("Name").val();
		var ad_no = snap.child("Aadhar No").val();
		var email = snap.child("Email Id").val();
		var gender = snap.child("Gender").val();
		var mo_no = snap.child("Mobile No").val();
		var password = snap.child("Password").val();
		var uid=name+" "+ad_no;
		var Status = snap.child("Status").val();
		var Latitude = snap.child("Latitude").val();
		var Longitude = snap.child("Longitude").val();
		var Service = snap.child("Service").val();
		//alert(Status);
		if(Status == "Yes")
		{
			//alert(name);
			lati =  Latitude;
			lang = Longitude;
			document.getElementById("user_name").innerHTML = name;
			document.getElementById("user_mob").innerHTML = mo_no;
			document.getElementById("user_aadhar").innerHTML = ad_no;
			document.getElementById("required-services").innerHTML = Service;
				initMap(lati,lang);
		}
		/*var row = table.insertRow(0);
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		var cell3 = row.insertCell(2);
		var cell4 = row.insertCell(3);
		var cell5 = row.insertCell(4);
		var cell6 = row.insertCell(5);
		var cell7 = row.insertCell(6);
		var cell8 = row.insertCell(7);
		var cell9 = row.insertCell(8);
		var cell10 = row.insertCell(9);
		var cell11 = row.insertCell(10);
		
		
		cell1.innerHTML =uid;
		cell2.innerHTML = name;
		cell3.innerHTML = ad_no;
		cell4.innerHTML = email;
		cell5.innerHTML = password;
		cell6.innerHTML = mo_no;
		cell7.innerHTML = gender;
		cell8.innerHTML = Status;
		cell9.innerHTML = Latitude;
		cell10.innerHTML = Longitude;
		cell11.innerHTML = Service;*/

    });
	}
</script>   

    <div class="content-wrapper">
         <div class="container">
        <div class="row pad-botm">
            <div class="col-md-12">
                <h4 class="header-line">ADMIN DASHBOARD</h4>
                
                            </div>

        </div>
             
             <div class="row">
            
                 <div class="col-md-3 col-sm-3 col-xs-6">
                      <div class="alert alert-info back-widget-set text-center">
                            <i class="fa fa-user fa-2x"></i>
                            <h3>User Name</h3>
                          <p id="user_name" style="font-size:15px"></p>
                        </div>
                    </div>
              <div class="col-md-3 col-sm-3 col-xs-6">
                      <div class="alert alert-success back-widget-set text-center">
                            <i class="fa fa-info-circle fa-2x"></i>
                            <h3>Other Info.</h3>
                            Mobile No:<p id="user_mob" style="font-size:15px"></p>
							Aadhar No:<p id="user_aadhar" style="font-size:15px"></p>
                        </div>
                    </div>
				<div class="col-md-3 col-sm-3 col-xs-6">
                      <div class="alert alert-warning back-widget-set text-center">
                            <i class="fa fa fa-compass fa-2x"></i>
                            <h3>Current Location</h3>
                           <p id="current-location" style="font-size:15px"></p>
                        </div>
                </div>
               
			   <div class="col-md-3 col-sm-3 col-xs-6">
                      <div class="alert alert-warning back-widget-set text-center">
                            <i class="fa fa-map-marker fa-2x"></i>
                            <h3>Required Services</h3>
                           <p id="required-services" style="font-size:15px"></p>
                        </div>
                </div>
        </div>              
             <div class="row">

              <div class="col-md-8 col-sm-8 col-xs-12">
                    <div id="carousel-example" class="carousel slide slide-bdr" data-ride="carousel" >
                   
                    <div class="carousel-inner">
                        <div class="item active">

                            
							<div id="map" style="width:1130px;height:450px"></div> 
        <script>
      function initMap(lati,lang) {
	  //window.myFunction();
	  //var x = new myFunction();
	  var l = lati;
	  var n = lang;
        var pointA = {lat: l, lng: n};
		 var infowindow = new google.maps.InfoWindow();
        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 20,
          center: pointA
		  
        });
       var markerA = new google.maps.Marker({
          position: pointA,
      title: "point A",
      label: "A",
      map: map
		});   
       var service = new google.maps.places.PlacesService(map);
        service.nearbySearch({
          location: pointA,
          radius: 3000,
          type: ['hospital']
        }, callback);
      

      function callback(results, status) {
        if (status == google.maps.places.PlacesServiceStatus.OK) {
          for (var i = 0; i < results.length; i++) {
            createMarker(results[i]);
          }
        }
      }

      function createMarker(place) {
        var placeLoc = place.geometry.location;
        var marker = new google.maps.Marker({
          map: map,
		  label: "H",
          position: place.geometry.location
        });

        google.maps.event.addListener(marker, 'click', function() {
          infowindow.setContent(place.name);
          infowindow.open(map, this);
        });
      }
	  
		var geocoder = new google.maps.Geocoder;
		var infowindow = new google.maps.InfoWindow;
		
		geocoder.geocode({'location': pointA}, function(results, status) {
		//alert("Hello");
		//alert(results[0]);
          if (status == 'OK') {
            if (results[0]) {
              map.setZoom(13);
			  
              var marker = new google.maps.Marker({
                position: pointA,
                map: map
              });
			  
			  infowindow.setContent(results[0].formatted_address);
			  var add = results[0].formatted_address;
			
				document.getElementById("current-location").innerHTML = add;
			}
		  }
		}
	  );
		 

	}
    </script>
</script>	
    <script async
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDMMlv-8cjEiTCnBED6wdiOppQ0N2MsLwA&libraries=places&callback=myFunction">
    </script>
                           
                        </div>
                        
                    </div>
                    <!--INDICATORS-->
                                         <!--PREVIUS-NEXT BUTTONS-->
                    
                </div>
              </div>
                 
                 
             </div>
            </div>
            

             
     
                </div>

            </div>

    </div>
    </div>

 
<?php include("footer.php"); ?>

