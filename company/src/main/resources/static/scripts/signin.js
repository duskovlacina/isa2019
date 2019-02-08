login_url="../api/users/login"

$(document).on('submit','.form-signin', function(e) {
	e.preventDefault();
	$.ajax({
		type : 'POST',
		url : login_url,
		contentType : 'application/json',
		dataType : "json",
		data:formToJSON(),
		success : function(data) {
			if(data != null){
			sessionStorage.setItem('loggedUser',JSON.stringify(data));
			//window.location.href='index.html';
			passChange(data);
			}else{
				
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			toastr.error("Username or password not valid!");
		}
	});
});

function passChange(data) {
	var tip = data.usertype;
	console.log("passChange!")
	console.log("tip " + tip);
	console.log("changePass " + data.changedPassword);
	if(tip == "FANZONEADMIN" && data.changedPassword == false) {
		window.location.href='changePass.html';
	} else if(tip == "VENUEADMIN" && data.changedPassword == false) {
		window.location.href='changePass.html';
	} else {
		window.location.href='index.html';
	}
}


function formToJSON() {
	return JSON.stringify({
    "email":$('#inputEmail').val(),
    "password":$('#inputPassword').val()
	});
}
