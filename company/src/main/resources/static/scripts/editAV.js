function citaj() {		
		$.ajax({
			 url: "../api/avioCompanies/",
			 method: "GET",
			 success: function(data){
				airlines(data);
			 },
			 error: function(){
				 alert("Error while getting airlines!");
			 }
		});	
}

function airlines(data) {
	var air = sessionStorage.getItem("idIzmjenaAirline");
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$.each(list, function(index, airline) {
		if(airline.id==air) {
			$('#name').val(airline.name);
			$('#description').val(airline.description);
			$('#address').val(airline.address);
		}
	});	
	$.ajax({
		 url: "../api/destinations",
		 method: "GET",
		 success: function(data1){
			 var list1 = data1 == null ? [] : (data1 instanceof Array ? data1 : [ data1 ]);
			 $.each(list1, function(index, destination) {
				 	if(air == destination.avioCompany.id) {
				 		$('#spisakDestination').append(`<li><b>`+destination.name+`</b>
						 		<a style="margin-left:20px;background-color:red" id="delete`+destination.id+`" onclick="deleteDestination(this)" class="btn">Delete</a>
						 		<a style="margin-left:20px;background-color:yellow" id="edit`+destination.id+`" onclick="editDestination(this)" class="btn">Edit</a></li>`);
				 		
				 	}
				 	
			 });
		 },
		 error: function(){
			 alert("Error while getting cinemas!");
		 }
	});	
	
}

function editEvent(obj) {
	var id1 = obj.id;
	var id = id1.split("edit")[1];
	sessionStorage.setItem("idIzmjenaDestination",id);
	window.location.href="editDestination.html";
}

function deleteDestination(obj){
	var id1 = obj.id;
	var id = id1.split("delete")[1];
		$.ajax({
			method : 'GET',
			url : "../api/destinations/delete/"+id,
			success : function(data){
				console.log("uspjesno!");
				window.location.href = 'editAV.html';
			},
			error: function(){
				console.log("neuspesno!!!");
			}
		});
}
/*
function prebaciMe(obj){
	var id1 = obj.id;
	sessionStorage.setItem("vrtsaVenue",id1);
	window.location.href = "createEvent.html";
}
/*
function editEvent(obj) {
	var id1 = obj.id;
	var id = id1.split("edit")[1];
	sessionStorage.setItem("idIzmjenaEvent",id);
	window.location.href="editEvent.html";
}
*/

function promjeni() {
	var id = sessionStorage.getItem("idIzmjenaAirline");
	$.ajax({
		type : 'PUT',
		url : "../api/avioCompanies/"+id,
		contentType : 'application/json',
		dataType : "json",
		data:formToJSON2(),
		success : function(data) {
			if(data==null) {
				alert("Neuspjesno!");
			}else {
				//alert("promjeniiiii");
				window.location.href = "avList.html";
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("corak");
			window.location.href = "avList.html";
		}
	});
	
}

function formToJSON2() {
	return JSON.stringify({
		"name" : $('#name').val(),
		"description" : $('#description').val(),
		"address" : $('#address').val(),		
	});
}
/*
function getDestinations(){
	var id = sessionStorage.getItem("idIzmjenaAirline");
	$.ajax({
		 url:"../api/destinations/",
		 method: "GET",
		 success: function(data){
			 $(".destinationsTable").empty();
			 for(i=0;i<data.length;i++){
				 $(".destinationsTable").append(`<tr>
                               <td><span class="font-weight-bold">`+data[i].name+`</span></td>
                           </tr>`);
			 }
		 },
		 error: function(){
			 alert("Error while getting AvioCompanies!");
		 }
	});
}
*/


