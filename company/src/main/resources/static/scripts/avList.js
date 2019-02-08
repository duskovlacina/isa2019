var AVC_url = "../api/avioCompanies/"

	function getCompanies(){
	var user = JSON.parse(sessionStorage.getItem("loggedUser"));
	$.ajax({
		 url: AVC_url,
		 method: "GET",
		 success: function(data){
			 $(".avTable").empty();
			 if(user == null) {
				 for(i=0;i<data.length;i++){
					 $(".avTable").append(`<tr>
	                              <td><span class="font-weight-bold">`+data[i].name+`</span></td>
	                              <td><span class="font-weight-bold">`+data[i].address+`</span></td>
	                              <td><span class="font-weight-bold">`+data[i].description+`</span></td>
	                              <td><span class="font-weight-bold">`+data[i].averageGrade+`</span></td>
	                              
	                              
	                          </tr>`);
				 }
			 } else if(user.usertype == "AVIOADMIN" ) {
			 for(i=0;i<data.length;i++){
				 $(".avTable").append(`<tr>
                              <td><span class="font-weight-bold">`+data[i].name+`</span></td>
                              <td><span class="font-weight-bold">`+data[i].address+`</span></td>
                              <td><span class="font-weight-bold">`+data[i].description+`</span></td>
                              <td><span class="font-weight-bold">`+data[i].averageGrade+`</span></td>
                              <td><button onclick="editID(this)" id=`+data[i].id+` type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#exampleModalR">Edit</button></td>
                              
                          </tr>`);
			 }
			 } else {
				 for(i=0;i<data.length;i++){
					 $(".avTable").append(`<tr>
	                              <td><span class="font-weight-bold">`+data[i].name+`</span></td>
	                              <td><span class="font-weight-bold">`+data[i].address+`</span></td>
	                              <td><span class="font-weight-bold">`+data[i].description+`</span></td>
	                              <td><span class="font-weight-bold">`+data[i].averageGrade+`</span></td>
	                              
	                              
	                          </tr>`);
				 }
			 }
		 },
		 error: function(){
			 alert("Error while getting airlines!");
		 }
	});
}

function editID(data) {
	var id = data.id;
	sessionStorage.setItem("idIzmjenaAirline",id);
	window.location.href="editAV.html";
}

function editCinema(id) {
	$.ajax({
		type : 'PUT',
		url : "../api/props/"+id,
		contentType : 'application/json',
		dataType : "json",
		data:formToJSON2(),
		success : function(data) {
			if(data==null) {
				alert("Neuspjesno!");
			}else {
				window.location.href = "cinemaList.html";
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
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

