<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.time.Year"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.function.Function"%>
<%@page import="java.util.List"%>
<%@page import="net.AAWSAgDB.fileupload.Reset.RestResponse"%>
<%@page import="net.AAWSAgDB.fileupload.model.File" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.10.1/html2pdf.bundle.min.js"></script>
<script src='dist/script.js' type='text/javascript'></script>
<link rel="stylesheet" href="dist/tree.css">
<link rel="stylesheet" href="https://unicons.iconscout.com/release/v4.0.0/css/line.css">
<link rel="stylesheet" href="dist/commanUserstyle.css">
<link rel="stylesheet" href="dist/screen_sizesmall_mediacontroll.css">
<link rel="stylesheet" href="dist/leaflet/leaflet.css"/>
<link rel="shortcut icon" type="image/png" href="dist/img/aawsa_icon.png"/>
<title>Home page</title>
 <STYLE type="text/css">
.wellReport tr:nth-child(6) td {
  border-bottom: 1px solid black;
}
.wellReport tr:nth-child(7) td:nth-child(1) {
  border-right: 1px solid black;
}
.wellReport tr:nth-child(n+8):nth-child(-n+20) td:nth-child(2) {
  border-right: 1px solid black; /* Adjust style, width, and color as needed */
}
.wellReport th, td{
padding-bottom: 3px;
padding-left: 1px;
}
.removerightBor td{
border-right-style: none !important;
}
.content.tree span{
background-color: #466799; color: #fff
}
.info {
    padding: 6px 8px;
    font: 14px/16px Arial, Helvetica, sans-serif;
    background: white;
    background: rgba(255,255,255,0.8);
    box-shadow: 0 0 15px rgba(0,0,0,0.2);
    border-radius: 5px;
}
.info h4 {
    margin: 0 0 5px;
    color: #777;
    white-space: nowrap; 
}
.marker-pin {
  width: 9px;
  height: 9px;
  margin:0 auto;
  display: flex;
  justify-content: center;
  align-items:center;
  border-radius: 50%;
  position: absolute;
  transform: rotate(-45deg);
  z-index: 10;
  box-shadow:1px;
  }
.wc-custom-div-icon {
        background-color: #187adb;
        border-radius: 50%;
        box-sizing: border-box;border: solid 1px black;
    }

.marker-content {
        /* Further styling for content within the icon */
        padding: 0px;
        
  }
.waterM{
background-color:black;
margin: 4px 4px 4px;
background: black;
box-shadow: rgba(0,49,83, 0.1) 3px 3px, rgba(0,139,139, 0.3) 3px 3px, rgba(0,49,83, 1) 5px 5px, rgba(0,139,139, 0.8) 8px 8px;
}
.leaflet-container .combined-control {
  background-color: rgba(255, 255, 255, 0.7);
  box-shadow: 0 0 5px #bbb;
  padding: 0 5px;
  margin:0;
  margin-top:-6px;
  color: #333;
  font: 13px/1.5 "Helvetica Neue", Arial, Helvetica, sans-serif;
}
.combined-control {
border-top: 2px solid #333333;
border-right: 2px solid #333333;
border-left: 2px solid #333333;
}
.myClass {
            font-size: 1.1em;
            color: #5500FF;
            background-size: 0px;
            box-sizing: inherit;
            display: inline-block;
            white-space: nowrap; 
            position: relative;
            /* Use color, background, set margins for offset, etc */
        }
 .proOverlay i{
   position: absolute;
   width: 12px;
   font-size: 12px;
   left: 0;
   right: 0;
   margin: 10px auto;
   text-align: center;
   white-space: nowrap;
        }             
        .legend {
    width:190px;
    line-height: 215%;
    color: #555;
    font-weight:bold;
    background: rgba(255,255,255,0.6);
    background-color:white;
    cursor: pointer;
    overflow-x: auto; 
    overflow-y: auto;
    white-space: nowrap;
    font:12px; Arial, Helvetica, sans-serif;
    box-shadow: 0 0 15px rgba(0,0,0,0.2);
    
}
.legend i {
    font-size:13px;
    width: 15px;
    height: 16px;
    float: left;
    margin-right: 2px;
    margin-top: 4px;
    opacity: 0.6;
    overflow-x: auto; 
    overflow-y: visible;
} 
.textBox{
background-color:white;
margin: 0 0 0;
background: rgba(255,255,255,0.6);
box-shadow: 0 0 0 2px rgba(0, 0, 0, .1);
}
input[type="search"] {
  font-size:15px;
  border-radius: inherit;
}
input[type="search"]::placeholder {
  color: #4a4949;
} 
input[type="search"]:focus {
  box-shadow: rgba(255,255,255,0.6);
  border-color: #1183d6;
  outline: none;
}
input.icon_mark {
  border: 1px solid #555;
  width: 20px;
  height: 20px;
  padding: 9px 4px 9px 30px;
  background: transparent url("dist/img/search1.png") no-repeat;
  background-position: 4px;
} 
.multSearch{
  width: 27px;
  height: 25px;
  padding-bottom:2px;
  background:url("dist/img//iconMs.png") no-repeat;
  cursor: pointer;
  display: inline-block;
}
.wellReport table{
white-space: nowrap; 
table-layout: fixed;
left: 0px;
top: 0px;
margin-left:1.18%;
width: 98.5%;
height: 100%
}
.wellReport thead{
text-align: center;
background-color: #28527b;
color: white;
z-index: 2;
}
.wellReport thead tr {
  padding-right: 17px;
  box-shadow: 0 4px 6px rgba(0,0,0,.6);
  z-index: 2;
}
.wellReport th {
  border-right: 1px solid rgba(0,0,0,.3);
  font-size: 1.5rem;
  font-weight: normal;
}
.wellReport tbody {
  display: block;
  min-height: calc(280px + 1 px);
  /*use calc for fixed ie9 & <
  overflow-y:scroll;*/
  color: #000;
}
.wellReport tbody tr:hover{
background-color: #fff6;	
}
.table_{
border: 1px solid black;
width: 98%;
white-space: nowrap;
margin-left: 2%;
}
.table_ tbody {
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
.table_ thead tr {
  padding-right: 17px;
  box-shadow: 0 4px 6px rgba(0,0,0,.6);
  z-index: 2;
}
.table_ tr{
  display: block;
  overflow: hidden;
}
.table_ tr:nth-child(even) {
  background-color: #0000000b;
}
.table_ tbody tr:nth-child(odd):hover{
background-color: #F1F0F0;	
}

.table_ tbody tr:nth-child(even):hover{
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
.table_ tbody tr:nth-child(odd):hover{
background-color: #F1F0F0;	
}

.table_ td:nth-child(1),.table_ th:nth-child(1){
width: 5%;
}
.table_ td:nth-child(2),.table_ th:nth-child(2){
width: 30%;
}
.table_ td:nth-child(3),.table_ th:nth-child(3){
width: 34%;
}
.table_ td:nth-child(4),.table_ th:nth-child(4){
width: 8%;
}
.table_ td:nth-child(5),.table_ th:nth-child(5){
width: 9%;
}
.table_ td:nth-child(6),.table_ th:nth-child(6){
width: 8%;
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
</STYLE>   
</head>
<body>
<%
      @SuppressWarnings("unchecked")
      List<File>reg= (List<File>)request.getAttribute("data1");	
      @SuppressWarnings("unchecked")
      List<File>geo_reference=(List<File>)request.getAttribute("georef");
      @SuppressWarnings("unchecked")
      List<File>geo_legend=(List<File>)request.getAttribute("legend");
    %>
    <nav class="titbarnav">
    <ul>
    <li> <a href="#">About Us</a> </li>
     <li> <a href="#">Contact</a> </li>
      <li id="c_logout"> <a href="#">Accounts</a> 
      <div id="dis_logout" style="display: none; float: right;">
      <ul>
      <li><a data-li="con_logout" href="<c:url value="/logout" />">Logout</a></li>
      </ul>
      </div>
      </li>
    </ul>
	</nav>
<h4 style="white-space: nowrap; width: 80.849%"><%= session.getAttribute("U_auto")%>: Common User</h4>
   <div class="content">
     <ul class="tree" id="myUL">
     <li><span style="font: bold;">AAWSA gDB</span>
     <ul>
           <li><span>Access Well Information</span>
           <div class="table_div">
           <ul>
           <li id="geo_ref"><a data-li= "All_aa" class="active_l" href="#">GW Information(Spatial)</a></li>
           <li>
           <select id="wms_mapTrial" onchange="changeLayerOnMap(this.value)" class="distributionAnalysisMap">
           <option value="" style="font-weight: bold;">Custom Map Layer</option>
           </select></li>
           <li><span class="multSearch caret avoidcaratCont"></span>
           <ul class="nested" id="wellInformation">
           <li><span class="caret">Current well status:</span>
           <ul class="nested">
        <li><label class="multSearchContainer">Functional<input type="radio" name="radioch" id="userQueryId" value="Functional">
        <span class="checkmark cl1"></span></label></li>
        <li><label class="multSearchContainer">Active<input type="radio" name="radioch" id="userQueryId1" value="Active">
        <span class="checkmark cl2"></span></label></li>
        <li><label class="multSearchContainer">In Active<input type="radio" name="radioch" id="userQueryId2" value="Inactive" >
        <span class="checkmark cl3"></span></label></li>
        <li><label class="multSearchContainer">Abandoned<input type="radio" name="radioch" id="userQueryId3" value="Non Functional">
        <span class="checkmark cl4"></span></label></li>
        </ul>
        </li>
        <li><span class="caret">Well owner category:</span>
        <ul class="nested">
        <li> <label class="multSearchContainerKey2"><input class="sKey2" type="radio"  name="wellsWater" id="aawsaW" value="AAWSA" style="color: green;">
         AAWSA</label></li>
        <li><label class="multSearchContainerKey2"><input class="sKey2" type="radio"  name="wellsWater" id="governmet" value="Governmental">
         Government</label></li>
        <li><label class="multSearchContainerKey2"><input class="sKey2" type="radio"  name="wellsWater" id="nonGovern" value="Non Governmental">
         Non Government</label></li>
         <li><label class="multSearchContainerKey2"><input class="sKey2" type="radio"  name="wellsWater" id="private" value="Private">
          Private</label></li>
        </ul>
        </li>
        <li><span class="caret">Well depth (m):</span>
        <ul class="nested">
        <li> <label class="multSearchContainerKey2"><input class="sKey3" type="radio"  name="wellsDepth" id="100" value="100"> <=100</label></li>
        <li><label class="multSearchContainerKey2"><input class="sKey3" type="radio"  name="wellsDepth" id="350" value="350"> 101 to 350</label></li>
        <li><label class="multSearchContainerKey2"><input class="sKey3" type="radio"  name="wellsDepth" id="500" value="500"> 351 to 500</label></li>
         <li><label class="multSearchContainerKey2"><input class="sKey3" type="radio"  name="wellsDepth" id="501" value="501"> Above 500</label></li>
        </ul>
        </li>
        <li><span class="caret">Current Discharge (L/s):</span>
        <ul class="nested">
        <li> <label class="multSearchContainerKey2"><input class="sKeyQ" type="radio"  name="Discharge" id="15" value="15"> 1 to 15</label></li>
        <li><label class="multSearchContainerKey2"><input class="sKeyQ" type="radio"  name="Discharge" id="15.01" value="40"> 15.01 to 40</label></li>
        <li><label class="multSearchContainerKey2"><input class="sKeyQ" type="radio"  name="Discharge" id="40.01" value="70"> 40.01 to 70</label></li>
         <li><label class="multSearchContainerKey2"><input class="sKeyQ" type="radio"  name="Discharge" id="70.01" value="120"> 70.01 to 120</label></li>
        </ul>
        </li>
        <li><span class="caret">Current SWL (m):</span>
        <ul class="nested">
        <li> <label class="multSearchContainerKey2"><input class="sKeySWL" type="radio"  name="SWL" id="0.01" value="0.1"> 0.001 to 0.01</label></li>
        <li><label class="multSearchContainerKey2"><input class="sKeySWL" type="radio"  name="SWL" id="50" value="50"> 1 to 50</label></li>
        <li><label class="multSearchContainerKey2"><input class="sKeySWL" type="radio"  name="SWL" id="100" value="100"> 50.001 to 100</label></li>
         <li><label class="multSearchContainerKey2"><input class="sKeySWL" type="radio"  name="SWL" id="200" value="200"> 100.001 to 200</label></li>
        </ul>
        </li>
        <li><span class="caret">Current DWL (m):</span>
        <ul class="nested">
        <li> <label class="multSearchContainerKey2"><input class="sKeyDWL" type="radio"  name="DWL" id="50" value="50"> 0.3 to 50</label></li>
        <li><label class="multSearchContainerKey2"><input class="sKeyDWL" type="radio"  name="DWL" id="100" value="100"> 50.01 to 100</label></li>
        <li><label class="multSearchContainerKey2"><input class="sKeyDWL" type="radio"  name="DWL" id="150" value="150"> 100.01 to 150</label></li>
         <li><label class="multSearchContainerKey2"><input class="sKeyDWL" type="radio"  name="DWL" id="250" value="250"> 150.01 to 250</label></li>
        </ul>
        </li>
        <li><span class="caret">Transmissivity (m<sup>2</sup>/d):</span>
        <ul class="nested">
        <li> <label class="multSearchContainerKey2"><input class="sKeyTRMT" type="radio"  name="Transmissivity" id="5" value="5"> 1.4 to 5</label></li>
        <li><label class="multSearchContainerKey2"><input class="sKeyTRMT" type="radio"  name="Transmissivity" id="50" value="50"> 5.001 to 50</label></li>
        <li><label class="multSearchContainerKey2"><input class="sKeyTRMT" type="radio"  name="Transmissivity" id="500" value="500"> 50.001 to 500</label></li>
         <li><label class="multSearchContainerKey2"><input class="sKeyTRMT" type="radio"  name="Transmissivity" id="501" value="1000000"> Above 500</label></li>
        </ul>
        </li>
        </ul>
        <ul class="nested" id="hydrochemistry">
        <li><span class="caret">TDS (Mg/l):</span>
        <ul class="nested">
        <li> <label class="multSearchContainerKey2"><input class="sKeyTDS" type="radio"  name="TDS" id="500" value="500"> 30 to 500</label></li>
        <li><label class="multSearchContainerKey2"><input class="sKeyTDS" type="radio"  name="TDS" id="1000" value="1000"> 501 to 1000</label></li>
        <li><label class="multSearchContainerKey2"><input class="sKeyTDS" type="radio"  name="TDS" id="1001" value="1000000"> Above 1000</label></li>
        </ul>
        </li>
        <li><span class="caret">EC (µs/cm):</span>
        <ul class="nested">
        <li> <label class="multSearchContainerKey2"><input class="sKeyEC" type="radio"  name="EC" id="1500" value="1500"> 45 to 1500</label></li>
        <li><label class="multSearchContainerKey2"><input class="sKeyEC" type="radio"  name="EC" id="2500" value="2500"> 1500 to 2500</label></li>
        <li><label class="multSearchContainerKey2"><input class="sKeyEC" type="radio"  name="EC" id="2501" value="1000000"> Above 2500</label></li>
        </ul>
        </li>
        <li><span class="caret">Fluoride (Mg/l):</span>
        <ul class="nested">
        <li> <label class="multSearchContainerKey2"><input class="sKeyF" type="radio"  name="Fluoride" id="1.5" value="1.5"> 0.01 to 1.5</label></li>
        <li><label class="multSearchContainerKey2"><input class="sKeyF" type="radio"  name="Fluoride" id="3" value="3"> 1.51 to 3</label></li>
        <li><label class="multSearchContainerKey2"><input class="sKeyF" type="radio"  name="Fluoride" id="3.01" value="1000000"> Above 3</label></li>
        </ul>
        </li>
        <li><span class="caret">Iron (Mg/l):</span>
        <ul class="nested">
        <li> <label class="multSearchContainerKey2"><input class="sKeyFe" type="radio" name="Iron" id="0.3" value="0.3"> 0.01 to 0.3</label></li>
        <li><label class="multSearchContainerKey2"><input class="sKeyFe" type="radio"  name="Iron" id="0.31" value="5"> 0.31 to 5</label></li>
        <li><label class="multSearchContainerKey2"><input class="sKeyFe" type="radio"  name="Iron" id="5.01" value="1000000"> Above 5</label></li>
        </ul>
        </li>
        <li><span class="caret">Nitrate (Mg/l):</span>
        <ul class="nested">
        <li> <label class="multSearchContainerKey2"><input class="sKeyNo3" type="radio" name="Nitrate" id="45" value="45"> 0.01 to 45</label></li>
        <li><label class="multSearchContainerKey2"><input class="sKeyNo3" type="radio"  name="Nitrate" id="45.01" value="100"> 45.01 to 100</label></li>
        <li><label class="multSearchContainerKey2"><input class="sKeyNo3" type="radio"  name="Nitrate" id="100.01" value="240"> 100.01 to 240</label></li>
        </ul>
        </li>
        <li><span class="caret">Temperature (&#8451):</span>
        <ul class="nested">
        <li> <label class="multSearchContainerKey2"><input class="sKeytemp" type="radio" name="Temperature" id="37" value="37"> 18 to 37</label></li>
        <li><label class="multSearchContainerKey2"><input class="sKeytemp" type="radio"  name="Temperature" id="50" value="50"> 37.01 to 50</label></li>
        <li><label class="multSearchContainerKey2"><input class="sKeytemp" type="radio"  name="Temperature" id="90" value="90"> 50.01 to 90</label></li>
        </ul>
        </li>
        </ul>
           </li>  
           </ul>
           </div>
           </li>    
           <li style="margin-top: -5px;"><span>Secured Document</span>
           <ul>
           <li><span class="caret">Access Secured Document</span>
           <div class="table_div">
            <ul class="nested">
           <li id="DPTHData"><a data-li= "Otn_we" href="#">Groundwater Dataset</a></li>
           <li id="pro_clearbu"><a data-li= "Otn_bu" href="#">Well Completion Report</a></li>
           <li id="pro_cleargg"><a data-li= "Otn_gg" href="#">Miscellaneous Data</a></li>
           </ul>
           </div>
           </li> 
           <li id="" class="table_div"><a data-li= "Acc_wr" href="#">Request Data</a></li>
           </ul>
           </li>
           </ul>
           </li>
          </ul>       
        </div> 
        <div class="main-content">
             <div id="pro_displwe" class="table_div item_li Otn_we" style="display: none;">
             <input type="text" name="ux_name" id="ux_name" value="${userid}" style="display: none;"/>
             <input type="text" name="DPTHDataName" id="DPTHDataId" value="DPTHData" style="display: none;">
      <h1 style="text-align: center;">Data set Available - Groundwater Dataset</h1>
       <input type="search" id="pf1_namewe" name="pf1_namewe" placeholder="Search by File Name" 
        style="width: 200px; font-size:11px; height:25px; border-bottom-color:gray; position: relative; float: right; 
        margin-top: -2%; margin-right: 0%;"/>  
           <table id="DPTHDataTeble" class="table_ updateData"> 
           <thead>
           <tr>
         <th>Record No.</th>
        <th>Data Category</th>
        <th>File Name</th>
        <th>
        <span id="rs-bullet-we" class="rs-label">2010</span>
        <input class="dropdown_size" type="range" id="rs-range-line-we" name="rs-range-line-we" min="2010"
         max="<%=Year.now()%>"
         value="2010">
         </th>
        <th>File Type</th>
        <th>Download</th>
           </tr>
           </thead>
           <tbody>		
           </tbody>
        </table>
            </div>
            <div id="pro_displbu" class="table_div item_li Otn_bu" style="display: none;">
             <input type="text" name="uxbu_name" id="uxbu_name" value="${userid}" style="display: none;"/>
            <h1 style="text-align: center;">Data set Available - Well Completion Report</h1>
       <input type="search" id="pf1_namebu" name="pf1_namebu" placeholder="Search by File Name" 
        style="width: 200px; font-size:11px; height:25px; border-bottom-color:gray; position: relative; float: right; 
        margin-top: -2%; margin-right: 0%;" class="form-control"/>  
           <table id="Tpro_idbu" class="table_ updateData"> 
           <thead>
           <tr>
           <th>
           <select class="form-control dropdown_size" name="process_idbu" id="process_idbu">
        <option class="option_css" value="0">Record No.</option>
        </select> 
        </th>
        <th>
        <select class="form-control dropdown_size" name="prosepr_idbu" id="prosepr_idbu">
        <option class="option_css" value="0"> Well Field</option>
        </select>
        </th>
        <th>
        <select class="form-control dropdown_size" name="prosel_idbu" id="prosel_idbu">
        <option class="option_css" value="0">File Name</option>
        </select> 
        </th>
        <th>
        <select class="form-control dropdown_size" name="procat_idbu" id="procat_idbu">
          <option class="option_css" value="0">File Type</option>
        </select>
        </th>
        <th>
        <span id="rs-bullet-bu" class="rs-label">2011</span>
        <input class="dropdown_size" type="range" id="rs-range-line-bu" name="rs-range-line-bu" min="2011"
         max="<%=Year.now()%>"
         value="2011">
         </th>
        <th>Download</th>
           </tr>
           </thead>
           <tbody>		
           </tbody>
        </table>
            </div>
            <div id="pro_displgg" class="table_div item_li Otn_gg" style="display: none;">
            <input type="text" name="uxgg_name" id="uxgg_name" value="${userid}" style="display: none;"/>
            <h1 style="text-align: center;">Data set Available - Miscellaneous Data</h1>
       <input type="search" id="pf1_namegg" name="pf1_namegg" placeholder="Search by File Name" 
        style="width: 200px; font-size:11px; height:25px; border-bottom-color:gray; position: relative; float: right; 
        margin-top: -2%; margin-right: 0%;" class="form-control"/>  
           <table id="Tpro_idgg" class="table_"> 
           <thead>
           <tr>
           <th>
           <select class="form-control dropdown_size" name="process_idgg" id="process_idgg">
        <option class="option_css" value="0">Record No.</option>
        </select>
        </th>
        <th>
        <select class="form-control dropdown_size" name="prosepr_idgg" id="prosepr_idgg">
        <option class="option_css" value="0">Well Field</option>
        </select>
        </th>
        <th>
        <select class="form-control dropdown_size" name="prosel_idgg" id="prosel_idgg">
        <option class="option_css" value="0">File Name</option>
        </select>
        </th>
        <th>
        <select class="form-control dropdown_size" name="procat_idgg" id="procat_idgg">
          <option class="option_css" value="0">File Type</option>
        </select>
        </th>
        <th>
        <span id="rs-bullet-ggu" class="rs-label">2011</span>
        <input class="dropdown_size" type="range" id="rs-range-line-ggu" name="rs-range-line-ggu" min="2011" 
        max="<%=Year.now()%>"
         value="2011">
         </th>
        <th>Download</th>
           </tr>
           </thead>
           <tbody>		
           </tbody>
        </table>
            </div>
            <div class="table_div item_li All_aa main-content">
            <div id="map" style="position: absolute;width: 60.50%;margin-left: 1.55575%;height: 90%;margin-top:0.55%;border-radius: 6px;">
             <%
         if(geo_reference!=null){
        	 for(File geo_ele:geo_reference){
        		 if(geo_ele.Id_pro!=0 && geo_ele.locx!=0.0 && geo_ele.locy!=0.0){		 
         %>
           <ul style="display: none;"> <li class="geo"> <%= geo_ele.pro_name_ov+","+geo_ele.locx+", "+geo_ele.locy+","+geo_ele.pro_code+","
         +geo_ele.Id_pro+","+geo_ele.Pyear+","+geo_ele.Gpro_type %></li></ul>
         <%  }}}
             //legend
             if(geo_legend!=null){
        	 for(File geo_lege:geo_legend){		 
         %>
           <ul style="display: none;"> <li class="geo_legend"><%=geo_lege.pro_type+","+geo_lege.count_project%></li></ul>
         <%  }}
         %>
            </div>
            <div style="position:relative; margin-left: 76.30%;margin-top: -0.65%;width: 33.2%;height:100%;position:relative;">
            <table id="data_by_yrp" class="wellReport" style="width: 100%;height:100%; text-align:center;white-space:nowrap;overflow-x:auto;
            overflow-y: auto;text-align: center; font-size: 11.6px;" border="0">
            <thead>
           </thead>
            <tbody>
            </tbody>
            </table>
            </div>
            </div>
            <div id="legend_div" class="Super_legend"></div>
         <div id="" class="table_div item_li Acc_wr" style="display: none;">
            <h1 style="margin-left: 10%;">Requesting Well Report</h1>
 <div style="position: absolute; width: 30%; margin-left: 3%; ">
  <table style="width: 100%; text-align: center; white-space: nowrap; table-layout: fixed;" border="0">
<tr style="background-color: white;">
<td style="text-align: left;padding-right: 0px;padding-top:6px;font-size: 13px; width: 20%;border-right:none; border-collapse:separate;">Division:</td>
<td style="text-align: left;padding-right: 0px;padding-top:6px;font-size: 13px; width: 70%; border-right:none; border-collapse:separate;">
<select id="usertype2" name="usertype2" class="form-control" style="width: 280px; height: 20%;">
<option value="0">-----------------------Devision Name-------------------</option>
<% 
		if(reg!=null){
	      for(File files:reg){
	       if(files.na_id!=0)
		     {
	         %>
		<option value="<%=files.na_id%>"><%=files.na_name%></option>
		<% }}}%>
</select></td>
</tr>
<tr style="background-color: white;">
		<td style="text-align: left;padding-right: 0px;padding-top:6px;font-size: 13px; width: 20%; border-right:none; border-collapse:separate;">Team:</td>
		<td style="text-align: left;padding-right: 0px;padding-top:6px;font-size: 13px;width: 70%; border-right:none; border-collapse:separate;">
				<select id="processf2" name="processf2" class="form-control" style="width: 280px; height: 25px;"
				onchange="showprofield(this.options[this.selectedIndex].value);">
	<option value="0" style="font-weight: bold;">-----------------------Team---------------------</option>
					</select>
				</td>
				</tr>
				<tr style="background-color: white;">
					<td class="td" style="text-align: left;padding-right: 0px;padding-top:6px;font-size: 13px; width:20%; border-right:none; border-collapse:separate;">Project Name:</td>
					<td style="text-align: left;padding-right: 0px;padding-top:6px;font-size: 13px; width: 70%; border-right:none; border-collapse:separate;">
				<select id="pro_name2" name="pro_name2" class="form-control" style="width: 280px; height: 25px;">
	<option value="0" style="font-weight: bold;">-----------Groundwater Report Type-----------</option>
					</select>
					</td>
				</tr>
</table>
 </div>
 <ul id="design_superv" style="position: relative; margin-left: 38%; margin-top: 8%;">
 </ul>
 <div style="position: relative; margin-left: 45%;margin-top: 0%;white-space:nowrap;height: 70%;overflow-x: auto; overflow-y: visible;">
  <input id="user_id" name="user_id" value="${userid}" style="display:none;">
 <input id="fname" name="fname" value="${U_auto}" style="display:none;">
 <input id="lname" name="lname" value="${U_lname}" style="display:none;">
 <ul id="Data_details" >
 </ul>
 <div id="hold_res">
 </div>
 </div>
        </div>
        </div>
        
        <script src="dist/leaflet/leaflet.js"></script>
        <script src="dist/studyArea.js" type="text/javascript"></script>
        <script src="dist/overLay_projects.js" type="text/javascript"></script>
        <%--Searches National data to the table using titles of the table--%>
        <script type="text/javascript" src="dist/tablemanage_nationaldoc.js"></script>
        <%--Searches corporation project data to the table using titles of the table--%>
        <%--search control for other office doc table --%>
      <script type="text/javascript" src="dist/tablemanage_officdoc.js"></script>
        <%--Automatic table change control--%>
        <script type="text/javascript" src="dist/dynamic_page_display.js"></script>
    	    <%--controls table title --%>
         <script type="text/javascript" src="dist/jquery-3.4.1.min.js"></script>
     <script> 
     /*selection option of Table header*/
    	 var toggler = document.getElementsByClassName("manage");
    	    var i;
    	    for (i = 0; i < toggler.length; i++) {
    	      toggler[i].addEventListener("click", function(){
    	      });
    	    }                    
        </script>
        <%--manages and controls tree content --%>
    <script>
    var toggler = document.getElementsByClassName("caret");
    var i;
    for (i = 0; i < toggler.length; i++) {
      toggler[i].addEventListener("click", function() {
        this.parentElement.querySelector(".nested").classList.toggle("active");
        this.classList.toggle("caret-down");
      });
    }
    document.getElementById("c_logout").addEventListener("click", function(){
    	var logout=document.getElementById("dis_logout");
    	if(logout.style.display=="none"){
    		logout.style.display="block";
    	}
    	else{
    		logout.style.display="none";	
    	}
    })
//GGUD
var rangeSlider_ggu = document.getElementById("rs-range-line-ggu");
var rangeBullet_ggu = document.getElementById("rs-bullet-ggu");
rangeSlider_ggu.addEventListener("input", showSliderValue_ggu, false);
var slidervalue_ggu;
var slidermax_ggu;
function showSliderValue_ggu(){
	rangeBullet_ggu.innerHTML = rangeSlider_ggu.value;
	slidervalue_ggu=rangeSlider_ggu.value-2010;
	slidermax_ggu=rangeSlider_ggu.max-2010;
  var bulletPosition = (slidervalue_ggu /slidermax_ggu);
  rangeBullet_ggu.style.left = (bulletPosition * 60) + "px";
}
//water and Energy
var rangeSlider_we = document.getElementById("rs-range-line-we");
var rangeBullet_we = document.getElementById("rs-bullet-we");
rangeSlider_we.addEventListener("input", showSliderValue_we, false);
var slidervalue;
var slidermax;
function showSliderValue_we(){
	rangeBullet_we.innerHTML = rangeSlider_we.value;
	slidervalue=rangeSlider_we.value-2010;
	slidermax=rangeSlider_we.max-2010;
  var bulletPosition = (slidervalue/slidermax);
  rangeBullet_we.style.left = (bulletPosition * 60) + "px";
}
//building 
var rangeSlider_bu = document.getElementById("rs-range-line-bu");
var rangeBullet_bu = document.getElementById("rs-bullet-bu");
rangeSlider_bu.addEventListener("input", showSliderValue_bu, false);
function showSliderValue_bu(){
  rangeBullet_bu.innerHTML = rangeSlider_bu.value;
  var bulletPosition = ((rangeSlider_bu.value-2010) /(rangeSlider_bu.max-2010));
  rangeBullet_bu.style.left = (bulletPosition * 60) + "px";
}
    </script> 
</body>
</html>