<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="net.AAWSAgDB.fileupload.model.File" %>
<%String baseUrl = getServletContext().getInitParameter("BaseUrl");%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="dist/tree.css">
<link rel="shortcut icon" type="image/png" href="dist/img/aawsa_icon.png"/>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<title>AAWSA gDB</title>
<style>
.clickable{
    display: block;
}
.error {
    padding: 15px;
    margin-bottom: 20px;
    border: 1px solid transparent;
    border-radius: 4px;
    color: #a94442;
    background-color: #f2dede;
    border-color: #ebccd1;
}

.msg {
    padding: 15px;
    margin-bottom: 20px;
    border: 1px solid transparent;
    border-radius: 4px;
    color: #31708f;
    background-color: #d9edf7;
    border-color: #bce8f1;
}
.pwChangeLink{
margin-top: 0.45rem; 
margin-left: -5%;
font-size: 1.25rem; 
background-color:#728FCE;
border: none;
outline: none; 
cursor: pointer; 
font-style: italic;
}
.pwChangeLink:hover{
text-decoration: underline;
background-color:#98AFC7
}
</style>
<%
String changeFailer = (String) request.getAttribute("changeFailed");
String changeSuccess =(String) request.getAttribute("changeSuccess");
String errorMessage = (String) request.getAttribute("denay");
String successMessage =(String) request.getAttribute("success");
%>
<script type="text/javascript">
function check_lis(){
	var newPw = document.getElementById('newPassword');
	var confirmPW = document.getElementById('confirmPassword');
	var passwordMatchMessage=document.getElementById('passwordMatchM'); 
	if (newPw.value !== confirmPW.value) {
		   passwordMatchMessage.textContent = 'New Password and Confirmation Password Mismatch!';
		   passwordMatchMessage.style.color = 'red';
	    return false;
	    }
	return (true);
	}
</script>
</head>
<body>
<nav class="titbarnav">
    <ul>
    <li> <a href="#">About Us</a> </li>
     <li><a href="#">Contact</a> 
     </li>
      <li id="cliclit"><a href="#">Accounts</a> 
      <div class="fire_login" id="list_it" style="display: none; float: right;">
     <ul>
     <li><a data-li="con_list" class="active_log" href="#">Login</a></li>
     <li><a data-li="con_disc"></a></li>
     </ul>
     </div>
      </li>
    </ul>
	</nav>
	<% 
		if(errorMessage!=null){
	         %>
		<p style="color: red; font-size: 16px; text-align: center;" id="errorMessage_01"> <%=errorMessage%></p>
		<%}%>
		<% 
		if(changeFailer!=null){
	         %>
		<p style="color: red; font-size: 16px; text-align: center;" id="errorMessage_02"> <%=changeFailer%></p>
		<%}%>
	<%if(response.getStatus()==500){
	%>
	<p style="color: red; text-align: center;">System Error! Please, Report to System Administrator team!</p><%}
	else if(response.getStatus()==404 || response.getStatus()==405){%>
		<p style="color: red; text-align: center;">Error! Connection Time Out or Server Restarted, Please Login!</p><%}%>
	<h2 style="margin-left:6%;margin-top:0.7%;margin-bottom:0%;text-align: center;">AAWSA Groundwater Data Management System</h2>
	<div class="item_service con_list" style="display: none;">
	 <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <c:if test="${not empty msg}">
            <div class="msg">${msg}</div>
        </c:if>
		<form name='loginForm'action="<c:url value='login' />" method="post" id="loginForm">
		<div>
	<% 
		if(successMessage!=null){
	         %>
		<p style="color:green; font-size: 17px; text-align: center;"> <%=successMessage%></p>
		<%}%>
	</div>
	<div>
	<% 
		if(changeSuccess!=null){
	         %>
		<p style="color:green; font-size: 17px; text-align: center;"> <%=changeSuccess%></p>
		<%}%>
	</div>
  <div class="container" style="margin-top: -0.12%;">
   <div class="imgcontainer">
    <img src="dist/img/aawsa_icon.png" alt="Avatar" class="avatar">
  </div>
    <label for="username" style="margin-left: auto; margin-right: auto;"><b>User name:</b></label>
    <input type="text" placeholder="Enter Username" name="username" required>
     <br>
    <label for="password" style="margin-left: auto; margin-right: auto;"><b>Password:</b></label>
    <input type="password" placeholder="Enter Password" name="password" required>
    <br>
    <button type="submit">Login</button> <button type="button" class="cancelbtn">Cancel</button>
    <div>
    <input type="button" value="forgot password ?" id="changePassword" class="pwChangeLink">
  </div>
  </div>
	</form>
	<form action="changeForgatenPassword" method="post" onsubmit="return(check_lis());" id="PWchangingForm" style="display: none; justifay-content: center;">
    <div class="container">
    <table class="table_psw" style="text-align: center; margin-left: 30%;">
        <tr>
            <td>Full Name:</td>
            <td><input type="text" id="fullName" name="changerFullName" required="required" style="height: 25px; width: 250px;"></td>
        </tr>
        <tr>
            <td>User Name:</td>
            <td><input type="text" id="userName" name="changerUserName" required="required" style="height: 25px; width: 250px;"></td>
        </tr>
        <tr>
            <td>Email:</td>
            <td><input type="text" id="userEmail" name="changerEmail" required="required" style="height: 25px; width: 250px;"></td>
        </tr>
        <tr>
            <td>Phone Number</td>
            <td><input type="text" id="phoneNumber" name="changerPhoneNumber" required="required" style="height: 25px; width: 250px;"></td>
        </tr>
        <tr>
            <td>New Password</td>
            <td><input type="password" id="newPassword" name="changernewPassword" style="height: 25px; width: 250px;"
            pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" 
            title="Must contain at least one number, one uppercase and lowercase letter, and at least 8 or more characters"/></td>
        </tr>
        <tr>
            <td>Confirm Password</td>
            <td><input type="password" id="confirmPassword" name="confirmchangerPassword" required="required"  style="height: 25px; width: 250px;"></td>
        </tr>
        <tr>
        <td>
        <input type="text" name="userID" value="" style="display: none;"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Confrim" style="height: 25px; width: 100px;
            margin-left: 30%; margin-top: 1.5%;"></td>
            <td><input type="reset" value="Cancel" style="height: 25px; width: 100px;margin-left: 15%; margin-top: 1.5%;" id="calcelChange"></td>
        </tr>
    </table>
    </div>
    <div id="passwordMatchM" style="margin-left:25%;margin-top:1%;font-size: 17px;"></div>
</form>
	</div>
	<div class="item_service con_disc" style="margin-top:-1%;">
	<div class="slideshow-container">
	<div class="mySlides fade">
	<img src="dist/img/slide_img/gw_img.jpg" alt="Akaki GW Testing">
	</div>
	<div class="mySlides fade">
	<img src="dist/img/slide_img/slidePic.jpg">
	</div>
	<div class="mySlides fade">
	<img src="dist/img/slide_img/gwater_raw.jpg">
	</div>
	<div class="mySlides fade">
	<img src="dist/img/slide_img/Abuilding.jpg">
	</div>
	<div class="mySlides fade">
	<img src="dist/img/slide_img/labc.jpg">
	</div>
	<div class="mySlides fade">
    <img src="dist/img/slide_img/waterc.jpg">
	</div>
	<div class="mySlides fade">
	<img src="dist/img/slide_img/boundary.png">
	</div>
	</div>
	<br>
<div style="text-align:center; margin-top: 1.9%;">
  <span class="dot"></span> 
  <span class="dot"></span> 
  <span class="dot"></span> 
  <span class="dot"></span>
  <span class="dot"></span> 
  <span class="dot"></span> 
  <span class="dot"></span> 
</div>
	</div>
	<script>
		 document.getElementById("cliclit").addEventListener("click", function(){
			 var item_ser=document.getElementById("list_it");
			 if(item_ser.style.display == "none"){
				 item_ser.style.display='block'; 
			 }
			 else{
				 item_ser.style.display='none';  
			 }
		 });
		 /*login display*/
		 var li_elements1 = document.querySelectorAll(".fire_login a");
	        var item_elements1 = document.querySelectorAll(".item_service");
	        for (var i = 0; i < li_elements1.length; i++) {
	          li_elements1[i].addEventListener("click", function() {
	            li_elements1.forEach(function(li) {
	              li.classList.remove("active_log");
	            });
	            this.classList.add("active_log");
	            var li_value = this.getAttribute("data-li");
	            item_elements1.forEach(function(item) {
	              item.style.display = "none";
	            });
	            if (li_value == "con_list") {
	              document.querySelector("." + li_value).style.display = "block";
	            } 
	            else  if (li_value == "con_disc") {
	              document.querySelector("." + li_value).style.display = "block";
	            } 
	            else {
	              console.log("");
	            }
	          });
	        }
	        /*slid controll*/
var slideIndex = 0;
showSlides();
function showSlides() {
  var i;
  var slides = document.getElementsByClassName("mySlides");
  var dots = document.getElementsByClassName("dot");
  for (i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";  
  }
  slideIndex++;
  if (slideIndex > slides.length) {
	  slideIndex = 1
	  }    
  for (i = 0; i < dots.length; i++) {
    dots[i].className = dots[i].className.replace("active_slide", "");
  }
  slides[slideIndex-1].style.display = "block";  
  dots[slideIndex-1].className += " active_slide";
  setTimeout(showSlides, 4100); // Change image every 3 seconds
}
var passwordChange=document.getElementById("PWchangingForm");
var loginForm=document.getElementById("loginForm");
document.getElementById("changePassword").onclick=function(){
	passwordChange.style.display='block';
	loginForm.style.display='none'
}
document.getElementById("calcelChange").onclick=function(){
	passwordChange.style.display='none';
	loginForm.style.display='block'
}
	</script> 
</body>
<footer>
	<script type="text/javascript" src="hdfoter/foot.js"></script>
	</footer>  
</html>