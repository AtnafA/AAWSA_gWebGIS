<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="net.AAWSAgDB.fileupload.model.File"%>
<html>
<head>
<meta name="viewport" http-equiv="Content-Type" content="text/html; charset=UTF-8; width=device-width; initial-scale=1.0"/> 
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="dist/tree.css">
<link rel="stylesheet" href="dist/screen_sizesmall_mediacontroll.css">
<link rel="shortcut icon" type="image/png" href="dist/img/aawsa_icon.png"/>
<link href="dist/d3-geomap.css" rel="stylesheet">
<title>Admin Page </title>
<style>
.slidecontainer {
  width: 100%;
}
.slider {
  -webkit-appearance: none;
  width: 80%;
  height: 8px;
  border-radius: 5px;
  background: #d3d3d3;
  outline: none;
  opacity: 0.7;
  -webkit-transition: .2s;
  transition: opacity .2s;
}
.slider:hover {
  opacity: 1;
}
.slider::-webkit-slider-thumb {
  -webkit-appearance: none;
  appearance: none;
  width: 17px;
  height: 17px;
  border-radius: 50%;
  background: #04AA6D;
  cursor: pointer;
}
.slider::-moz-range-thumb {
  width: 17px;
  height: 17px;
  border-radius: 50%;
  background: #04AA6D;
  cursor: pointer;
}
.table_{
border: 1px solid black;
width: 98%;
white-space: nowrap;
}
.manageAtable{
border: 1px solid black;
width: 98%;
white-space: nowrap;
}
.table_ tbody {
  display: block;
  min-height: calc(280px + 1 px);
  max-height: 500px;
  /*use calc for fixed ie9 & <*/
  overflow-y:scroll;
  color: #000;
}
.manageAtable tbody {
  display: block;
  min-height: calc(280px + 1 px);
  max-height: 500px;
  /*use calc for fixed ie9 & <*/
  overflow-y:scroll;
  color: #000;
}

.table_ thead {
text-align: center;
background-color: #28527b;
color: white;
z-index: 2;
}
.manageAtable thead {
text-align: center;
background-color: #28527b;
color: white;
z-index: 2;
}
.table_ thead tr {
  padding-right: 17px;
  box-shadow: 0 4px 6px rgba(0,0,0,.6);
  z-index: 2;
}
.manageAtable thead tr {
  padding-right: 17px;
  box-shadow: 0 4px 6px rgba(0,0,0,.6);
  z-index: 2;
}
.table_ tr{
  display: block;
  overflow: hidden;
}
.manageAtable tr{
  display: block;
  overflow: hidden;
}
.table_ tr:nth-child(even) {
  background-color: #0000000b;
}
.manageAtable tr:nth-child(even) {
  background-color: #0000000b;
}

.table_ tbody tr:nth-child(odd):hover{
background-color: #F1F0F0;	
}
.manageAtable tbody tr:nth-child(odd):hover{
background-color: #F1F0F0;	
}

.table_ tbody tr:nth-child(even):hover{
background-color: #DDD9D9;	
}
.manageAtable tbody tr:nth-child(even):hover{
background-color: #DDD9D9;	
}

.table_ th,.table_ td {
border-right: 1px solid rgba(0,0,0,.3);
border-collapse: collapse;
text-align: left;
padding-right: 6px;
padding-top:6px;
font-size: 13px;
width: 0%;
float:left;
}
.manageAtable th,.manageAtable td {
border-right: 1px solid rgba(0,0,0,.3);
border-collapse: collapse;
text-align: left;
padding-right: 6px;
padding-top:6px;
font-size: 13px;
width: 0%;
float:left;
}

.table_ tbody tr:nth-child(odd):hover{
background-color: #F1F0F0;	
}
.manageAtable tbody tr:nth-child(odd):hover{
background-color: #F1F0F0;	
}

.table_ td:nth-child(1),.table_ th:nth-child(1){
width: 5%;
}
.table_ td:nth-child(2),.table_ th:nth-child(2){
width: 25%;
}
.table_ td:nth-child(3),.table_ th:nth-child(3){
width: 13%;
}
.table_ td:nth-child(4),.table_ th:nth-child(4){
width: 30%;
}
.table_ td:nth-child(5),.table_ th:nth-child(5){
width: 11%;
}
.table_ td:nth-child(6),.table_ th:nth-child(6){
width: 10%;
}
.manageAtable td:nth-child(1),.manageAtable th:nth-child(1){
width: 6%;
}
.manageAtable td:nth-child(2),.manageAtable th:nth-child(2){
width: 20%;
}
.manageAtable td:nth-child(3),.manageAtable th:nth-child(3){
width: 12%;
}
.manageAtable td:nth-child(4),.manageAtable th:nth-child(4){
width: 14%;
}
.manageAtable td:nth-child(5),.manageAtable th:nth-child(5){
width: 11%;
}
.manageAtable td:nth-child(6),.manageAtable th:nth-child(6){
width: 13%;
}
.manageAtable td:nth-child(7),.manageAtable th:nth-child(7){
width: 10%;
}
.manageAtable td:nth-child(8),.manageAtable th:nth-child(8){
width: 7%;
}
	.error {
    padding: 1px;
    margin-bottom: 10px;
    border: 1px solid transparent;
    border-radius: 4px;
    color: #a94442;
    border-color: #ebccd1;
    margin-left:10%;
    width: 50%;
}
.error1 {
    padding: 1px;
    margin-bottom: 10px;
    border: 1px solid transparent;
    border-radius: 4px;
    color: #a94442;
    border-color: #ebccd1;
    margin-left:10%;
    width: 50%;
}
.noneexists{
 padding: 1px;
    margin-bottom: 10px;
    border: 1px solid transparent;
    border-radius: 4px;
    color: #a94442;
    border-color: #ebccd1;
    margin-left:10%;
    width: 50%;
}
.msg {
    padding: 7px;
    margin-top: 10px;
    margin-bottom: 10px;
    border: 1px solid transparent;
    border-radius: 4px;
    color: #31708f;
    background-color: #d9edf7;
    border-color: #bce8f1;
    margin-left:10%;
    width: 60%;
}
.existes {
    padding: 7px;
    margin-top: 10px;
    margin-bottom: 10px;
    border: 1px solid transparent;
    border-radius: 4px;
    color: black;
    background-color: #d9edf7;
    border-color: #bce8f1;
    margin-left:10%;
    width: 60%;
}
.existes1 {
    padding: 7px;
    margin-top: 10px;
    margin-bottom: 10px;
    border: 1px solid transparent;
    border-radius: 4px;
    color: black;
    background-color: #d9edf7;
    border-color: #bce8f1;
    margin-left:10%;
    width: 60%;
}
.table_div{
	margin-left: 2.55%;
    margin-top: -2.4%;
	}
form {
  width: 78.7%;
  border: 0px solid #f1f1f1;
  margin-left: 1%;
  margin-right: auto;
  margin-top: -4px;
  margin-bottom: 5px;
  text-align: center;
  white-space: nowrap;}
  form input[type=text]:hover{
  background-color: #F1F0F0;
  border-color: black;
  border-radius: 8px; /* Rounded corners */	
  }
 form input[type=email]:hover{
  background-color: #F1F0F0;
  border-color: black;
  border-radius: 8px; /* Rounded corners */	
  }
  form input[type=date]:hover{
  background-color: #F1F0F0;
  border-color: black;
  border-radius: 8px; /* Rounded corners */
  }
  form input[type=tel]:hover{
  background-color: #F1F0F0;
  border-color: black;
  border-radius: 8px; /* Rounded corners */
  }
  form input[type=Password]:hover{
  background-color: #F1F0F0;
  border-color: black;
  border-radius: 8px; /* Rounded corners */
  }
   form select:hover{
    background-color: #F1F0F0;	
    border-color: black;
    border-radius: 8px; /* Rounded corners */
  }
  .long{
  display: inline-block;
  position: relative;
  width: 99%;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  vertical-align: top;
}
.long:hover {
   z-index: 1;
  width: auto;
  background-color: #FFFFCC;
}
</style>
<script type="text/javascript">
var stringpat="[A-Za-z]{0-9}@*[.com]";
function patternmatch(){
	if(!stringpat.matcher(uform.U_email).matches){
		alert("This is not standard Email Address!!");
		return false;
	}
		return(true)
}
function check_lis(){
	var passwordInput = document.getElementById('passwordId');
	var confirmPWInput = document.getElementById('confirmPwId');
	var passwordMatchMessage=document.getElementById('passwordMatchM'); 
	if(uformcreate.eli_sup.selectedIndex == 0){
		var aa='Note: Select User Type to creat the User Account';
		alert(aa);
	return false;
	}
	if (passwordInput.value!== confirmPWInput.value) {
		   passwordMatchMessage.textContent = 'Passwords Miss Match!';
		   passwordMatchMessage.style.color = 'red';
	    return false;
	    }
	if(document.getElementById("eli_sup").value === "Administrator"){
		if(confirm("Note: You Are Creating the Administrator Privileged User! If it is Unnecessary, Please Cancel and Change User type!!")){
			return true;
			}
		else{
			return false;
			}
		return false;
		}
	return (true);
	}
</script>
<script src="dist/echarts.js"></script>
 <!-- Make sure you put this AFTER Leaflet's CSS -->
<%
	@SuppressWarnings("unchecked")
List<File>reg= (List<File>)request.getAttribute("data1");
%>
</head>
<body>
<nav class="titbarnav">
    <ul>
    <li> <a href="#">About Us</a> </li>
     <li> <a href="#">Contact</a> </li>
      <li id="cpr_logout"> <a href="#">Accounts</a> 
      <div id="pr_logout" style="display: none; float: right;">
      <ul>
      <li><a href="<c:url value="/logout" />">Logout</a></li>
      </ul>
      </div>
      </li>
    </ul>
	</nav>
	<%String centertype=(String)session.getAttribute("pr_typ");%>
	<%int choice= (Integer)session.getAttribute("U_id1"); %>
	<h4 style="white-space: nowrap; font-size: 15px;font-weight: bold; margin-left:-5%;"><%= session.getAttribute("U_auto")%>: <%=centertype%></h4>
	<div class="content">
	<ul class="tree" id="myUL">
	<li><span style="font: bold;">AAWSA gDB</span>
	<div class="sup_manger">
	<ul>
	<li id="ask_request"> <a data-li="ask_req" href="#">Database Log</a></li>
	<li><span class="caret">User Management</span>
	<ul class="nested">
	<li id="create_user"> <a data-li="c_elite" href="#"> Create user</a></li>
	<li id="manage_user"><a data-li="manage_us" href="#">Manage Account</a></li>
	</ul>
	</li>
	<li id="ask_report"> <a data-li="c_report" class="active_li" href="#"> Reports</a></li>
	</ul>
	</div>
	</li>
	</ul>
	</div>
	<div class="main-content">
	<div class="sup_manger item_co ask_req table_div" style="display: none;">
	<p style="text-align: center; margin-top: 1.1%; margin-left:-13%; font-weight: bold; font-size: 17px;">Database Log</p>
	<input id="usertype" name="usertype" value="<%=centertype%>" style="display: none;">
	 <table id="CoVrastar_id" class="table_" style="margin-left: 1%;">
           <thead>
           <tr>
        <th>Record No.</th>
        <th>Well Name</th>
        <th>Data</th>
        <th>Action</th>
        <th>By</th>
        <th class="slidecontainer"><input type="range" min="0" max="31" placeholder="DD" value="10" id="dateSlider" class="slider">
        <output for="dateSlider" id="outputValue" style="margin-left: -5px;">10</output>
        </th>
           </tr>
           </thead>
           <tbody class="tablePr">	
           </tbody>
           </table>
	</div>
	<div id="hold_DBresponse">
	<c:if test="${not empty preved1}">
            <div class="msg">${preved1}</div>
        </c:if>
         <c:if test="${not empty existes1}">
            <div class="existes">${existes1}</div>
        </c:if>
         <c:if test="${not empty noneexists}">
            <div class="noneexists">Warning: ${noneexists}</div>
        </c:if>
        <c:if test="${not empty error1}">
            <div class="error">Error: ${error1}</div>
        </c:if>
	</div>
	<div class="table_div sup_manger item_co c_elite main-content" style="display: none;">
	<p style="text-align: center; margin-top: 1.1%; margin-left:-13%; font-weight: bold; font-size: 17px;">Create User</p>
	<form action="userInformation" method="post" name="uformcreate" onsubmit="return(check_lis());">
	<div class="WCRFormDiv">
	<table border="0" style="margin-left: 24%; margin-bottom:0.5%; width: 50%;">
	<tr><td>Full Name:</td>
	<td><input type="text" name="fullName" placeholder="Enter Full name" style="width: 280px; height: 20px;" required minlength="8" maxlength="70"></td>
	</tr>
	<tr><td>User Name:</td>
	<td><input type="text" name="userName" placeholder="Enter User Name" style="width: 280px; height: 20px;" required minlength="4" maxlength="70"></td>
	</tr>
	<tr>
	<td class="td">User Type: </td>
	<td><select id="eli_sup" name="userType" class="form-control" style="width: 280px;">
	<option value="0">----------------Select User Type-------------</option>
	<option value="Administrator">Administrator</option>
	<option value="Database User">Database User</option>
	<option value="Common User">Common User</option>
	</select>
	</td>
	</tr>
	<tr><td>Email:</td>
	<td><input type="email" name="userEmail" placeholder="Enter users email" style="width: 280px; height: 20px;" required></td>
	</tr>
	<tr><td>Password:</td>
	<td><input type="password" id="passwordId" name="password" placeholder="Enter Password " style="width: 280px; height: 20px;" required 
	minlength="5" maxlength="50"></td>
	</tr>
	<tr><td>Confirm Password:</td>
	<td><input type="password" id="confirmPwId" name="confirmPW" placeholder="Confirm Password" style="width: 280px; height: 20px;" required></td>
	</tr>
	<tr><td>Phone Number:</td>
	<td><input type="tel" name="phone" placeholder="Enter Phone Number" style="width: 280px; height: 20px;" required></td>
	</tr>
</table>
<span style="align-content:center;">
			<input type="submit" value="Save" style="height: 20px; width: 100px;"id="click_me"/>
			<input type="reset" value="Cancel" id="reset" style="height: 20px; width: 100px;"/></span>
<div id="passwordMatchM" style="margin-left:25%;margin-top:1%;font-size: 17px;"></div>
</div>
</form>
	</div>
	<div class="sup_manger item_co manage_us table_div" style="display: none;">
	<p style="text-align: center; margin-top: 1.1%; margin-left:-13%; font-weight: bold; font-size: 17px;">Manage Account</p>
	<input id="usertype" name="usertype" value="<%=centertype%>" style="display: none;">
	<input id="1404ID" name="1404ID" value="<%=choice%>" style="display: none;">
	 <input type="text" id="privilage" value="" style="display: none;">
      <input type="text" id="activStatus" value="" style="display: none;">
       <input type="text" id="accountUserId" value="" style="display: none;">
       <input type="text" id="userFullName" value="" style="display: none;">
	 <table id="manageAccount" class="manageAtable" style="margin-left: 1%;">
           <thead>
           <tr>
        <th>Record No.</th>
        <th>Full Name</th>
        <th>User Name</th>
        <th>User Privilege</th>
        <th>Status</th>
        <th>Change Privilege</th>
        <th>Change Status</th>
        <th>Save Change</th>
           </tr>
           </thead>
           <tbody class="tablePr">	
           </tbody>
           </table>
	</div>
	
	<div>
	<table id="hold_user_profile" style="text-align: center;margin-top: 1%;margin-left:  70%;width: 30%;">
        </table>
	</div>
	<div class="sup_manger item_co c_report" style="margin-left: 1%;">
	<div>
	<div id="bar_chart" style="position:absolute;margin-left:1.7%; width:400px;height:400px;"></div>
	<div id="pi_chart" style="position: relative;margin-top:1%; margin-left:40%; width:500px;height:400px;"></div> 
	</div>  
	</div>
	</div>
<script type="text/javascript" src="dist/control_confrimation.js"></script>
<script type="text/javascript">
//report generate
// Initialize the bar chart instance
    var pi_Chart = echarts.init(document.getElementById('pi_chart'));
	var myChart1 = echarts.init(document.getElementById('bar_chart'));
	var CRreport_xml=new XMLHttpRequest();
	document.getElementById("ask_report").onclick = function(evt){
		if (typeof CRreport_xml != "undefined"){
			CRreport_xml= new XMLHttpRequest();
	         }
	         else if (window.ActiveXObject){
	        	 CRreport_xml= new ActiveXObject("Microsoft.XMLHTTP");
	         }
	         if (CRreport_xml==null){
	         alert("Browser does not support XMLHTTP Request")
	         return;
	         }
	  var url1="R_rport";
	         CRreport_xml.open("POST", url1,true);
	         CRreport_xml.onload=function(){
	        var design_report=JSON.parse(CRreport_xml.responseText);
	        CR_rport(design_report);
	        pi_rport(design_report);
	        	 }
	         CRreport_xml.send();
	         }
function CR_rport(detail){
	var gr_report=[];
	var cout_grreport=[];
	for(var i=0;i<detail.length;i++){
		gr_report.push(detail[i].file_group);
		cout_grreport.push(detail[i].count_fgroup);
	}
	var option = {
	  title: {
	    text: 'Data contents(#)'
	  },
	  tooltip: {},
	  legend: {
	    data: ['GW Data']
	  },
	  xAxis: {
	    data:gr_report
	  },
	  yAxis: {},
	  series: [
	    {
	      name: 'GW Data',
	      type: 'bar',
	      data:cout_grreport
	    }
	  ]
	};
	// Display the bar chart.
	myChart1.setOption(option);		
}
//Initialize the bar chart instance
function pi_rport(detail){
var gr_reportpi=[];
var gr_data_size=[];
for(var i=0;i<detail.length;i++){
	gr_reportpi[i]={
			value:detail[i].file_size,
			name:''+detail[i].file_group+''
	};
	gr_data_size.push(''+detail[i].file_size)+'';
}
//alert(gr_data_size);
var optionpi = {
		 title: {
			    text: 'Statistics'
			  },
			  tooltip: {},
		 series: [
			    {
			      type: 'pie',
			      data:gr_reportpi,
			      radius: '50%'
			    }
			  ]
};
// Display the pie chart.
pi_Chart.setOption(optionpi);		
}
</script>
<script type="text/javascript">
var toggler = document.getElementsByClassName("caret");
var i;
for (i = 0; i < toggler.length; i++) {
  toggler[i].addEventListener("click", function() {
    this.parentElement.querySelector(".nested").classList.toggle("active");
    this.classList.toggle("caret-down");
  });
}
document.getElementById("cpr_logout").addEventListener("click", function(){
	var logout=document.getElementById("pr_logout");
	if(logout.style.display=="none"){
		logout.style.display="block";
	}
	else{
		logout.style.display="none";	
	}
})
var li_elements1 = document.querySelectorAll(".sup_manger a");
        var item_elements1 = document.querySelectorAll(".item_co");
        for (var i = 0; i < li_elements1.length; i++) {
          li_elements1[i].addEventListener("click", function() {
            li_elements1.forEach(function(li) {
              li.classList.remove("active_li");
            });
            this.classList.add("active_li");
            var li_value = this.getAttribute("data-li");
            item_elements1.forEach(function(item) {
              item.style.display = "none";
            });
            if (li_value == "manage_us"){
                document.querySelector("." + li_value).style.display = "block";
              }
            if (li_value == "ask_req"){
                document.querySelector("." + li_value).style.display = "block";
              }
            else if (li_value == "c_report") {
              document.querySelector("." + li_value).style.display = "block";
            } else if (li_value == "c_elite") {
              document.querySelector("." + li_value).style.display = "block";
            } 
            else {
              console.log("");
            }
          });
        }
        	// Get the slider and the output elements
        	var slider = document.getElementById("dateSlider");
        	var output = document.getElementById("outputValue");
        	// Display the default slider value when the page loads
        	output.innerHTML = slider.value;
        	// Update the output value every time the slider handle is dragged
        	slider.oninput = function() {
        	  output.innerHTML = this.value;
        	}

        	
        	//page display Controller
	var pro_dataResponse=document.getElementById("ask_request");
    if (pro_dataResponse.addEventListener) {
   	 pro_dataResponse.addEventListener("click", function() {
     document.getElementById("hold_DBresponse").style.display='none';	
        }, false);}
    var pro_reportResponse=document.getElementById("create_user");
    if (pro_reportResponse.addEventListener) {
   	 pro_reportResponse.addEventListener("click", function() {
      document.getElementById("hold_DBresponse").style.display='none';	
        }, false);}
    var mange_otherdoc=document.getElementById("ask_report");
    if (mange_otherdoc.addEventListener) {
   	 mange_otherdoc.addEventListener("click", function() {
      document.getElementById("hold_DBresponse").style.display='none';	
        }, false);}
        
</script>
</body>
</html>