var loggeduser = JSON.parse(sessionStorage.getItem('loggedUser'));
var edit_url="../api/users/"+loggeduser.id
var requests_url = "../api/users/getRequests/"+loggeduser.id
var friends_url = "../api/users/getFriends/"+loggeduser.id
var AVC_url = "../api/avioCompanies/"
var reservations_url = "../api/reservations/getAllForLogged"
var visits_url = "../api/reservations/getVisitsForLogged"

$(document).on('submit','.editform', function(e) {
	e.preventDefault();
	var p = $('#password').val();
	var cp = $('#password-confirm').val();
	if(p == cp){
		$.ajax({
			type : 'PUT',
			url : edit_url,
			contentType : 'application/json',
			dataType : "json",
			data:formToJSON(),
			success : function(data) {
				toastr.success("Your information was successfuly edited!");
				location.reload();
				sessionStorage.setItem('loggedUser',JSON.stringify(data));
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX ERROR: " + errorThrown+"signin");
			}
		});
	}else{
		toastr.warning("Passwords must match!");
	}
});

function generateNavbar2(){
	$.ajax({
		 url: islogged_url,
		 method: "GET",
		 success: function(data){
			 var user = data;
			 if(user.email!=null){
				 $(".navitems").empty();
				 $(".navitems").append(`<li class="nav-item active">
	                <a class="nav-link" href="#">Home
	                  <span class="sr-only">(current)</span>
	                </a>
	              </li>
	              <li class="nav-item">
	                <a class="nav-link" href="#">About</a>
	              </li>
	              <li class="nav-item">
	                <a class="nav-link" href="#">Services</a>
	              </li>
	              <li class="nav-item">
	                <a class="nav-link" href="userpage.html">User `+user.name+` signed in</a>
	              </li>
	              <li class="nav-item">
	                <a class="nav-link" href="index.html" onclick="logout()">Sign out</a>
	              </li>`);
			 }else{
			    	$(".navitems").empty();
			    	$(".navitems").append(`<li class="nav-item active">
			                <a class="nav-link" href="#">Home
			                  <span class="sr-only">(current)</span>
			                </a>
			              </li>
			              <li class="nav-item">
			                <a class="nav-link" href="#">About</a>
			              </li>
			              <li class="nav-item">
			                <a class="nav-link" href="register.html">Register</a>
			              </li>
			              <li class="nav-item">
			                <a class="nav-link" href="signin.html">Sign in</a>
			              </li>`);
			 }
	        
	    },
	    error:function(data){
	    	
	    }
	 });
}

function formToJSON() {
	return JSON.stringify({
	"id":loggeduser.id,
	"email":loggeduser.email,
    "name":$('#name').val(),
    "surname":$('#surname').val(),
    "city":$('#city').val(),
    "phone":$('#phone').val(),
    "password":$('#password').val()
	});
}

function generateUserInfo(){
	document.getElementById("name").value = loggeduser.name;
	document.getElementById("surname").value = loggeduser.surname;
	document.getElementById("city").value = loggeduser.city;
	document.getElementById("phone").value = loggeduser.phone;
	document.getElementById("password").value = loggeduser.password;
	document.getElementById("password-confirm").value = loggeduser.password;
}

function generateProfile(){
	$("#profilediv").append(`<div class="row">
						    <div class="col-md-2">
						      <label><h6>Email:</h6></label>
						    </div>
						    <div class="col-md-10">
						      <i>`+loggeduser.email+`</i>
						    </div>
						  </div>
						  <div class="row">
						    <div class="col-md-2">
						      <label><h6>Name:</h6></label>
						    </div>
						    <div class="col-md-10">
						      <i>`+loggeduser.name+`</i>
						    </div>
						  </div>
						  <div class="row">
						    <div class="col-md-2">
						      <label><h6>Surname:</h6></label>
						    </div>
						    <div class="col-md-10">
						      <i>`+loggeduser.surname+`</i>
						    </div>
						  </div>
						  <div class="row">
						    <div class="col-md-2">
						      <label><h6>Address:</h6></label>
						    </div>
						    <div class="col-md-10">
						      <i>`+loggeduser.city+`</i>
						    </div>
						  </div>
						  <div class="row">
						    <div class="col-md-2">
						      <label><h6>Phone:</h6></label>
						    </div>
						    <div class="col-md-10">
						      <i>`+loggeduser.phone+`</i>
						    </div>
						  </div>`);
}

function getFriendRequests(){
	$.ajax({
		 url: requests_url,
		 method: "GET",
		 success: function(data){
			 $(".freqtable").empty();
			 for(i=0;i<data.length;i++){
				 $(".freqtable").append(`<tr>
                                <td>
                                   <span class="pull-xs-right font-weight-bold">`+data[i].name+` `+data[i].surname+`</span> sent you a friend request
                                   <span class="float-right"><button type="button" name=`+data[i].id+` class="btn btn-success btn-sm accreq">Accept</button>
                                   <button type="button" name=`+data[i].id+` class="btn btn-danger btn-sm decreq">Decline</button></span>
                                </td>
                            </tr>`);
			 }
		 },
		 error: function(){
			 alert("Error while getting friend requests!");
		 }
	});
}

function getFriends(){
	$.ajax({
		 url: friends_url,
		 method: "GET",
		 success: function(data){
			 $(".friendsTable").empty();
			 for(i=0;i<data.length;i++){
				 $(".friendsTable").append(`<tr>
                               <td>
                                  <span class="font-weight-bold">`+data[i].name+`  `+data[i].surname+` | `+data[i].email+` | `+data[i].city+` | `+data[i].phone+`</span>
                                  <span class="float-right"><a href="#"><img onclick="removeFriend(`+data[i].id+`)" src="images/remove.png" title="remove from friends"></img></a></span>
                               </td>
                           </tr>`);
			 }
		 },
		 error: function(){
			 alert("Error while getting Friends!");
		 }
	});
}

$(document).on('click','.accreq', function(e) {
	e.preventDefault();
	var id = $(this).attr('name');
	$.ajax({
		 url: "../api/users/approveFriendRequest/"+id,
		 method: "GET",
		 success: function(){
			 
				 getFriendRequests();
				 getFriends();
				 toastr.success("You have accepted a friend request!")
		 },
		 error: function(){
			 alert("Error approving request");
		 }
	});
});

$(document).on('click','.decreq', function(e) {
	e.preventDefault();
	//alert($(this).attr('name'));
	var id = $(this).attr('name');
	$.ajax({
		 url: "../api/users/declineRequest/"+id,
		 method: "GET",
		 success: function(){
			 
				 getFriendRequests();
				 toastr.info('You have declined a friend request!');
				 
			 
		 },
		 error: function(){
			 alert("Error approving request");
		 }
	});
});

$(document).on('click','#searchPeople',function(e){
	e.preventDefault();
	var name = $('#searchname').val();
	var surname = $('#searchsurname').val();
	var loggedId = loggeduser.id;
	if(name == ''){
		name = "nema";
	}
	if(surname ==''){
		surname="nema";
	}
	
	$.ajax({
		 url: "../api/users/search/"+name+"/"+surname,
		 method: "GET",
		 success: function(data){
			 $(".searchedpeople").empty();
			 for(i=0;i<data.length;i++){
				 if(data[i].id != loggedId){
					 $(".searchedpeople").append(`<tr>
	                         <td>
	                            <span class="font-weight-bold">`+data[i].name+`  `+data[i].surname+` | `+data[i].email+` | `+data[i].city+` | `+data[i].phone+`</span>
	                            <span class="float-right"><a href="#"><img src="images/sendRequest.png" onclick="sendFriendRequest(`+data[i].id+`)" title="send friend request"></img></a></span>
	                         </td>
	                     </tr>`);
				 }
			 }
				 
		 },
		 error: function(){
			 alert("Error searching people");
		 }
	});
});

function sendFriendRequest(receiverid){
	$.ajax({
		 url: "../api/users/sendFriendRequest/"+receiverid,
		 method: "GET",
		 success: function(){
			 toastr.success("Friend request sent!");
				 
		 },
		 error: function(){
			 toastr.error("Sending request failed. You have already sent one!");
		 }
	});
}

function removeFriend(removingId){
	$.ajax({
		 url: "../api/users/removeFriend/"+removingId,
		 method: "GET",
		 success: function(){
			 toastr.info("Friend removed!");
			 getFriends();
		 },
		 error: function(){
			 toastr.error("Error removing friend");
		 }
	});
}

function getAVC(){
	$.ajax({
		 url: AVC_url,
		 method: "GET",
		 success: function(data){
			 $(".AVCTable").empty();
			 for(i=0;i<data.length;i++){
				 $(".AVCTable").append(`<tr>
                               <td><span class="font-weight-bold">`+data[i].name+`</span></td>
                              <td><span class="font-weight-bold">`+data[i].address+`</span></td>
                              <td><span class="font-weight-bold">`+data[i].description+`</span></td>
                              <td><button onclick="generateDestinations(`+data[i].id+`)" id=`+data[i].id+` type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#exampleModalR">Look</button></td>
                           </tr>`);
			 }
		 },
		 error: function(){
			 alert("Error while getting AvioCompanies!");
		 }
	});
}


$(document).on('click','#searchDestination',function(e){
	e.preventDefault();
	var destination = $('#DestinationName').val();
	var date1 = $('#DepartureDate').val();
	var date = new Date(date1);
	var date2 = date.toUTCString()
	var date3 = formatDate(date2)
	$.ajax({
		 url: "../api/destinationTimes/searchDestination/"+destination+"/"+date3,
		 method: "GET",
		 success: function(data){
			 $(".DestinationTable").empty();
			 for(i=0;i<data.length;i++) {
				 $(".DestinationTable").append(`<tr>
				 <td><span class="font-weight-bold">`+data[i].destinationDate.destination.avioCompany.name+`</span></td>
				 <td><span class="font-weight-bold">`+data[i].time+`</span></td>
				 <td><span class="font-weight-bold">`+data[i].arrivalDate+`</span></td>
				 <td><span class="font-weight-bold">`+data[i].arrivalTime+`</span></td>
				 <td><span class="font-weight-bold">`+data[i].transferNo+`</span></td>
				 <td><span class="font-weight-bold">`+data[i].transferPlace+`</span></td>
				 <td><span class="font-weight-bold">`+data[i].flightLength+`</span></td>
				 <td><span class="font-weight-bold">`+data[i].price+`</span></td>
				 <td><button type="button" onclick="reserveDestinations(`+data[i].id+`, `+data[i].destinationDate.id+`,`+data[i].destinationDate.destination.id+` )" class="btn btn-info btn-sm" id="resSeats" data-toggle="modal" data-target="#exampleModalR">Reserve</button></td>
				 </tr>`);
			 }
				 
		 },
		 error: function(){
			 alert("Error searching DESTINATIONS");
		 }
	});
});

function reserveDestinations(timeId,dateId,destinationId){
	var list=[];
	$.ajax({
		 url: "../api/destinations/"+destinationId+"/destinationDates/"+dateId+"/destinationTimes/"+timeId+"/seats",
		 method: "GET",
		 async : false,
		 success: function(data){
			 $("#seatsdiv").empty();
			 $("#invitediv").empty();
			 $("#seatsdiv").append(`<div id="seat-map">
									<div class="front">Flight</div>	
								</div>
									<button type="button" id="reserveProjection" class="btn btn-primary">Reserve</button>
								`);
			 if(data.length != 0){
				 
			 }
			 
			var flightrows = data[1].flight.rows;
			var seatsperrow = data[1].flight.seatsPerRow;
			
			for(i=0;i<flightrows;i++){
				var row ='';
				for(j=0;j<seatsperrow;j++){
						row+='a';
				}
				list.push(row);
			}
			flag = true;
		 },
		 error: function(){
			 alert("Error while getting seats!");
		 }
	});
	
	var sc = $('#seat-map').seatCharts({
		map:list,
		seats: {
			a: {
				price   : 99.99,
				classes : 'front-seat'
			}
		
		},
		click: function () {
			if (this.status() == 'available') {
				//do some stuff, i.e. add to the cart
				return 'selected';
			} else if (this.status() == 'selected') {
				//seat has been vacated
				return 'available';
			} else if (this.status() == 'unavailable') {
				//seat has been already booked
				return 'unavailable';
			} else {
				return this.style();
			}
		}
	});
	
	$.ajax({
		 url: "../api/destinations/"+destinationId+"/destinationDates/"+dateId+"/destinationTimes/"+timeId+"/takenSeats",
		 method: "GET",
		 success: function(data){
			 for(i=0;i<data.length;i++){
				 var temp = data[i].row+"_"+data[i].seatInRow;
				 sc.get(temp).status('unavailable');
			 }
		 },
		 error: function(){
			 alert("Error while getting taken seats!");
		 }
	});
	
	sc.find('c.available').status('unavailable');
}



function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [year, month, day].join('-');
}

$(document).on('click','#searchAVC',function(e){
	e.preventDefault();
	var name = $('#AVCName').val();
	var address = $('#AVCAddress').val();
	var loggedId = loggeduser.id;
	if(name == ''){
		name = "nema";
	}
	if(address ==''){
		address="nema";
	}
	
	$.ajax({
		 url: "../api/avioCompanies/searchAVC/"+name+"/"+address,
		 method: "GET",
		 success: function(data){
			 $(".AVCTable").empty();
			 for(i=0;i<data.length;i++){
				 $(".AVCTable").append(`<tr>
                         <td><span class="font-weight-bold">`+data[i].name+`</span></td>
                        <td><span class="font-weight-bold">`+data[i].address+`</span></td>
                        <td><span class="font-weight-bold">`+data[i].description+`</span></td>
                        <td><button onclick="generateDestinations(`+data[i].id+`)" id=`+data[i].id+` type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#exampleModalR">Look</button></td>
                     </tr>`);
			 }
				 
		 },
		 error: function(){
			 alert("Error searching AvioCompanies");
		 }
	});
});

function reserve(data) {
	var destinationId = data.destinationDate.destination.name;
	console.log(destinationId);
	
}

function generateDestinations(id){
	$("#destinations").attr('disabled',false);
	$("#genDestDates").attr('disabled',false);
	$.ajax({
		 url: "../api/avioCompanies/"+id+"/getDestinations",
		 method: "GET",
		 success: function(data){
			 $("#destinations").empty();
			 $("#datesdiv").empty();
			 $("#timesdiv").empty();
			 $("#seatsdiv").empty();
			 $("#invitediv").empty();
			 for(i=0;i<data.length;i++){
				 $("#destinations").append(`<option id=`+data[i].id+`>`+data[i].name+`</option>`);
			 }
		 },
		 error: function(){
			 alert("Error while getting repertoire!");
		 }
	});
	
}


$(document).on('click','#genDestDates',function(e){
	e.preventDefault();
	$("#destinations").attr('disabled',true);
	$(this).attr('disabled',true);
	var id = $('#destinations option:selected').attr('id')
	//console.log(id);
	$.ajax({
		 url: "../api/destination/"+id+"/destDates",
		 method: "GET",
		 success: function(data){
			 $("#datesdiv").empty();
			 $("#timesdiv").empty();
			 $("#seatsdiv").empty();
			 $("#invitediv").empty();
			 if(data.length != 0){
				 $("#datesdiv").append(`<label for="destdates">Date: </label>`);
				 $("#datesdiv").append(`<select id="destdates">
                       	</select>
                       	<button type="button" class="btn btn-info btn-sm" id="genDestinationTimes">Continue</button>`);
			 }
			 for(i=0;i<data.length;i++){
				 $("#destdates").append(`<option id=`+data[i].id+`>`+data[i].destinationDate+`</option>`);
			 }
		 },
		 error: function(){
			 alert("Error while getting dates!");
		 }
	});
});

$(document).on('click','#genDestinationTimes',function(e){
	$("#destdates").attr('disabled',true);
	$(this).attr('disabled',true);
	var destinationId = $('#destinations option:selected').attr('id')
	var dateId = $('#destdates option:selected').attr('id')
	//console.log("datumID" + dateId);
	$.ajax({
		 url: "../api/destinations/"+destinationId+"/destinationDates/"+dateId+"/destinationTimes",
		 method: "GET",
		 success: function(data){
			 $("#timesdiv").empty();
			 $("#seatsdiv").empty();
			 $("#invitediv").empty();
			 if(data.length != 0){
				 $("#timesdiv").append(`<label for="destinationtimes">Time and flight details: </label>`);
				 $("#timesdiv").append(`<select id="destinationtimes">
	                              	</select>
	                              	<button type="button" class="btn btn-info btn-sm" id="genSeats">Continue</button>`);
			 }
			 for(i=0;i<data.length;i++){
				 $("#destinationtimes").append(`<option name=`+data[i].flights.id+` id=`+data[i].id+`>`+data[i].time+` FlightNo:`+data[i].flights.flightId+` ArrivalDate:`+data[i].arrivalDate+` ArrivalTime:`+data[i].arrivalTime+` FlightTime:`+data[i].flightTime+` TransferNo:`+data[i].transferNo+` Price:`+data[i].price+`</option>`);
			 }
		 },
		 error: function(){
			 alert("Error while getting times!");
		 }
	});
});

function getReservations(){
	$.ajax({
		 url: reservations_url,
		 method: "GET",
		 success: function(data){
			 $(".reservationsTable").empty();
			 for(i=0;i<data.length;i++){
				 $(".reservationsTable").append(`<tr>
                              <td><span class="font-weight-bold">`+data[i].destinationTime.destinationDate.destinationDate+`</span></td>
                              <td><span class="font-weight-bold">`+data[i].destinationTime.time+`</span></td>
                              <td><span class="font-weight-bold">`+data[i].destinationTime.destinationDate.destination.name+`</span></td>
                              <td><button onclick="generateDetails(`+data[i].id+`)" name=`+data[i].id+` type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#reservationsModal">Look</button></td>
                              <td><button onclick="cancelReservation(`+data[i].id+`)" type="button" class="btn btn-danger btn-sm" >Cancel</button></td>
                          </tr>`);
			 }
		 },
		 error: function(){
			 alert("Error while getting reservations!");
		 }
	});
}

function cancelReservation(id){
	$.ajax({
		 url: "../api/reservations/cancel/"+id,
		 method: "DELETE",
		 success: function(data){
			 getReservations();
			 toastr.warning("Reservation has been canceled");
		 },
		 error: function(){
			 toastr.error("You can't cancel the reservation. Flight starts in less than 3 hours!");
		 }
	});
}

function generateDetails(id){
	$.ajax({
		 url: "../api/reservations/"+id,
		 method: "GET",
		 success: function(data){
			 var seats = [];
			 for(i=0;i<data.seats.length;i++){
				 var seatInfo = data.seats[i].row+"_"+data.seats[i].seatInRow;
				 seats.push(seatInfo);
			 }
			 $("#rmodalbody").empty();
				 $("#rmodalbody").append(`<form>  loggeduser
						 				  <div class="form-row">
										    <div class="col">
										      <h5>Name:</h5>
										    </div>
										    <div class="col">
										      <h5>`+loggeduser.name+`</h5>
										    </div>
										  </div>
										   <div class="form-row">
										    <div class="col">
										      <h5>Surname:</h5>
										    </div>
										    <div class="col">
										      <h5>`+loggeduser.surname+`</h5>
										    </div>
										  </div>
										  <div class="form-row">
										    <div class="col">
										      <h5>Date:</h5>
										    </div>
										    <div class="col">
										      <h5>`+data.destinationTime.destinationDate.destinationDate+`</h5>
										    </div>
										  </div>
										  <div class="form-row">
										    <div class="col">
										      <h5>Time:</h5>
										    </div>
										    <div class="col">
										      <h5>`+data.destinationTime.time+`</h5>
										    </div>
										  </div>
										  <div class="form-row">
										    <div class="col">
										      <h5>Destination:</h5>
										    </div>
										    <div class="col">
										      <h5>`+data.destinationTime.destinationDate.destination.name+`</h5>
										    </div>
										  </div>
										  <div class="form-row">
										    <div class="col">
										      <h5>FlightNo:</h5>
										    </div>
										    <div class="col">
										      <h5>`+data.destinationTime.flights.flightId+`</h5>
										    </div>
										  </div>
										  <div class="form-row">
										    <div class="col">
										      <h5>Arrival Date:</h5>
										    </div>
										    <div class="col">
										      <h5>`+data.destinationTime.arrivalDate+`</h5>
										    </div>
										  </div>
										  <div class="form-row">
										    <div class="col">
										      <h5>Arrival Time:</h5>
										    </div>
										    <div class="col">
										      <h5>`+data.destinationTime.arrivalTime+`</h5>
										    </div>
										  </div>
										  <div class="form-row">
										    <div class="col">
										      <h5>FlightTime:</h5>
										    </div>
										    <div class="col">
										      <h5>`+data.destinationTime.flightTime+` hours</h5>
										    </div>
										  </div>
										  <div class="form-row">
										    <div class="col">
										      <h5>FlightLenght:</h5>
										    </div>
										    <div class="col">
										      <h5>`+data.destinationTime.flightLength+` kilometers</h5>
										    </div>
										  </div>
										    <div class="form-row">
										    <div class="col">
										      <h5>TranferNumbers:</h5>
										    </div>
										    <div class="col">
										      <h5>`+data.destinationTime.transferNo+` stop</h5>
										    </div>
										  </div>
										  </div>
										    <div class="form-row">
										    <div class="col">
										      <h5>TranferDestination:</h5>
										    </div>
										    <div class="col">
										      <h5>`+data.destinationTime.transferPlace+`</h5>
										    </div>
										  </div>
										  <div class="form-row">
										    <div class="col">
										      <h5>Seats:</h5>
										    </div>
										    <div class="col">
										      <h5>`+seats+`</h5>
										    </div>
										  </div>
										 
										  <div class="form-row">
										    <div class="col">
										      <h5>Total price:</h5>
										    </div>
										    <div class="col">
										      <h5>`+data.totalprice+` RSD</h5>
										    </div>
										  </div>
										</form>`);
		 },
		 error: function(){
			 alert("Error while getting reservation!");
		 }
	});
}

function getVisits(){
	$.ajax({
		 url: visits_url,
		 method: "GET",
		 success: function(data){
			 $("#visits").empty();
			 for(i=0;i<data.length;i++){
				// console.log(data[i].destinationTime.destinationDate.destinationDate);
				 $("#visits").append(`<tr>
                             <td><span class="font-weight-bold">`+data[i].destinationTime.destinationDate.destinationDate+`</span></td>
                             <td><span class="font-weight-bold">`+data[i].destinationTime.time+`</span></td>
                             <td><span class="font-weight-bold">`+data[i].destinationTime.destinationDate.destination.name+`</span></td>
                             <td><span class="font-weight-bold">`+data[i].destinationTime.destinationDate.destination.avioCompany.name+`</span></td>
                             <td><button onclick="generateDetails(`+data[i].id+`)" name=`+data[i].id+` type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#reservationsModal">Look</button></td>
                             <td><select id="combo`+data[i].destinationTime.destinationDate.destination.id+`">
                             	<option id="5" name="5" value=5>5</option>
                             	<option id="4" name="4" value=4>4</option>
                             	<option id="3" name="3" value=3>3</option>
                             	<option id="2" name="2" value=2>2</option>
                             	<option id="1" name="1" value=1>1</option>
                             </select></td>
                             <td><button onclick="rateDestination(this)" name="ocjeni`+data[i].destinationTime.destinationDate.destination.id+`" id="toggler" value="Toggler" type="button" class="btn btn-success">Confirm</button></td>
                         </tr>`);
			 }
		 },
		 error: function(){
			 alert("Error while getting visits!");
		 }
	});
}


function rateDestination(obj) {
	var pokusaj = obj.name;
	var id = pokusaj.split("ocjeni")[1];
	//alert(id);
	var ocjena = $("#combo"+id+" option:selected").attr('name');
	//alert(ocjena);
	$.ajax({
		method : 'GET',
		url : "../api/destinations/rate/"+id+"/"+ocjena,
		success : function(data){
			//console.log("uspjesno!");
			toastr.success("Destination has beed rated!");
			//window.location.href = "userpage.html";
			//document.getElementById('toggler').style.visibility = 'hidden';
			
		},
		error: function(){
			console.log("neuspesno");
		}
	});
}