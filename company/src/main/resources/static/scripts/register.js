register_url="../api/users/register"

	$(document).on('submit','.form-register', function(e) {
		e.preventDefault();
		var flag = false;
		var p = $('#password').val();
		var cp = $('#password-confirm').val();
		if(p == cp){
			$.ajax({
				type : 'POST',
				url : register_url,
				contentType : 'application/json',
				dataType : "json",
				data:formToJSON(),
				success : function(data) {
					$('.form-register').empty();
					$('.form-register').append(`<h3>User registered, verification mail sent.</h3><hr><i><a href="index.html">Return to homepage</a></i>`);
				
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					toastr.error("A user with this email already exists!");
				}
			});
		}else{
			alert("Passwords must match");
		}
	});


function formToJSON() {
	return JSON.stringify({
    "email":$('#email').val(),
    "password":$('#password').val(),
    "name":$('#name').val(),
    "surname":$('#surname').val(),
    "city":$('#city').val(),
    "phone":$('#phone').val(),
    "usertype": 0
	});
}