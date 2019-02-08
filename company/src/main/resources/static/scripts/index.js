var islogged_url = "../api/users/isLoggedIn"
var logout_url = "../api/users/logout"

function createAV(data) {
var id = data.id;
sessionStorage.setItem('createAV',id);
window.location.href = "createAV.html";
}	
	
function generateNavbar(){
	var user = JSON.parse(sessionStorage.getItem("loggedUser"));
	//if(user!=null) {
	//vratiKorisnike();
	
	if(user.usertype == "AVIOADMIN") {
		console.log("admir samm");
		document.getElementById("AIRLINE").hidden = "";
	} else {
		console.log("admir nisam");
		document.getElementById("AIRLINE").hidden = "hidden";
	}
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

function genNav(){
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

function logout(){
	$.ajax({
		 url: logout_url,
		 method: "GET",
		 success: function(){
			 sessionStorage.removeItem('loggedUser');
		 }
	});
}