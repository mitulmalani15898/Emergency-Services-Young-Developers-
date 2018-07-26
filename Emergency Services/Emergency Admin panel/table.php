
	
	
	<?php include('head.php'); ?>

     <!-- MENU SECTION END-->
	 
    <div class="content-wrapper">
         <div class="container">
        <div class="row pad-botm">
            <div class="col-md-12">
                <h4 class="header-line" align="center">User Details</h4>
                
                            </div>

        </div>
            <div class="row">
                <div class="col-md-12">
                    <!-- Advanced Tables -->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                             
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
							
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                       
									   <tr>
                                            <th>User ID</th>
											<th>Name</th>
                                            <th>Aadhar No</th>
                                            <th>Email ID</th>
                                            <th>Password</th>
                                            <th>Mobile No</th>
											<th>Gender</th>
											<th>Status</th>
											<th>Lati.</th>
											<th>Lang.</th>
											<th>Service</th>
                                        </tr>
                                    </thead>
									
                                    <tbody id="tab">
                                        
									</tbody>
                                </table>
                            </div>
                            
                        </div>
                    </div>
                    <!--End Advanced Tables -->
                </div>
            </div>
                <!-- /. ROW  -->
            
    </div>
    </div>
     <!-- CONTENT-WRAPPER SECTION END-->
	 
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
		
		var row = table.insertRow(0);
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
		cell11.innerHTML = Service;

    });
</script>
    
	<?php include('footer.php'); ?>