var user = JSON.parse(sessionStorage.getItem('loggedUser'));
var inv_counter = 0;
$(document).on('click','#genSeats',function(e){
	//
	$("#destinationtimes").attr('disabled',true);
	$(this).attr('disabled',true);
	var list=[];
	var destinationId = $('#destination option:selected').attr('id')
	var dateId = $('#destdates option:selected').attr('id')
	var timeId = $('#destinationtimes option:selected').attr('id')
	//console.log("vrijemeID"  + timeId);
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
			//console.log(data[1].flight.rows);
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
	//
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
	
	generateTakenSeats();
	
	//Make all available 'c' seats unavailable
	sc.find('c.available').status('unavailable');
	
	/*
	Get seats with ids 2_6, 1_7 (more on ids later on),
	put them in a jQuery set and change some css
	*/
	/*sc.get(['2_6', '1_7']).node().css({
		color: '#ffcfcf'
	});*/
	
	//console.log('Seat 1_2 costs ' + sc.get('1_2').data().price + ' and is currently ' + sc.status('1_2'));
});

function generateTakenSeats(){
	var destinationId = $('#destination option:selected').attr('id')
	var dateId = $('#destdates option:selected').attr('id')
	var timeId = $('#destinationtimes option:selected').attr('id')
	var sc = $('#seat-map').seatCharts();
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
}

$(document).on('click','#reserveProjection',function(e){
	var destinationId = $('#destination option:selected').attr('id')
	var dateId = $('#destdates option:selected').attr('id')
	var timeId = $('#destinationtimes option:selected').attr('id')
	var sc = $('#seat-map').seatCharts();
	$(this).attr('disabled',true);
	$.ajax({
		 url: "../api/destinations/"+destinationId+"/destinationDates/"+dateId+"/destinationTimes/"+timeId+"/seats",
		 method: "POST",
		 contentType : 'application/json',
		 dataType : "json",
		 data:JSON.stringify(sc.find('a.selected').seatIds),
		 success: function(res){
			 	$("#invitediv").empty();
			 	$("#invitediv").attr("name",res.id)
			 	inv_counter = res.seats.length;
			 if(res.seats.length>1){
				 $("#invitediv").append(`<br><p> You have reserved `+res.seats.length+` seats. Do you want to invite friends?</p>
				 <button onclick="generateFriendsForInv()" type="button" id="inviteFriends" class="btn btn-primary">Invite friends</button>`)
			 }
			 sc.find('a.selected').status('unavailable');
			 getReservations();
			 getVisits();
			 toastr.success("Reservation completed! Check your email or your reservations tab for more informations!");
		 },
		 error: function(){
			 alert("Error while reserving seats!");
		 }
	});
    
});

function generateFriendsForInv(){
	$("#inviteFriends").attr('disabled',true);
	$.ajax({
		 url: "../api/users/getFriends/"+user.id,
		 method: "GET",
		 success: function(data){
			 $("#invitediv").append(`<table class="table table-hover table-striped"><tbody id ="invtable">
			 
			 </tbody></table>`);
			 if(data.length > 0){
				 for(i=0;i<data.length;i++){
					 $("#invtable").append(`<tr><td>`+data[i].name+`</td>
					 							<td>`+data[i].surname+`</td>
					 							<td>`+data[i].email+`</td>
					 							<td>`+data[i].city+`</td>
					 							<td><button type="button" id=`+data[i].id+` class="btn btn-primary btn-sm invbutton">Invite</button></td></tr>`);
				 }
			 }else{
				 toastr.info("You have no friends.")
			 }
		 },
		 error: function(){
			 alert("Error while getting friends!");
		 }
	});
}


$(document).on('click','.invbutton',function(e){
	var resId = $("#invitediv").attr("name");
	var userId = $(this).attr("id");
	$(this).attr('disabled',true);
	if(inv_counter-1 > 0){
		$.ajax({
			 url: "../api/reservations/"+resId+"/sendInvite/"+userId,
			 method: "GET",
			 success: function(data){
				 
				 toastr.success("Friend invited!");
				 inv_counter-=1;
			 },
			 error: function(){
				 alert("Error while sending invite!");
			 }
		});
		
	}else{
		toastr.error("You have no more seats!")
	}
});