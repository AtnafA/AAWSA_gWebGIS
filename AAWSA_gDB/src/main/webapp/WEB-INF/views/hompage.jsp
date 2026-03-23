<%@page import="java.util.zip.ZipEntry"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.nio.file.Files"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page import="net.AAWSAgDB.fileupload.Reset.RestResponseStatusCode"%>
<%@page import="net.AAWSAgDB.fileupload.Reset.RestResponseStatus"%>
<%@page import="net.AAWSAgDB.fileupload.model.File"%>
<%@page import="net.AAWSAgDB.fileupload.Reset.RestResponse"%>
<%@page import="java.util.List"%>
<%String baseUrl = getServletContext().getInitParameter("BaseUrl");%>
<html>
<head>
<meta name="viewport" http-equiv="Content-Type" content="text/html; charset=UTF-8; width=device-width; initial-scale=1.0"/> 
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="dist/tree.css">
<link rel="stylesheet" href="dist/screen_sizesmall_mediacontroll.css">
<link rel="shortcut icon" type="image/png" href="dist/img/aawsa_icon.png"/>
<title>DB User page</title>
 <%
 @SuppressWarnings("unchecked")
 List<File>storedWellInfo= (List<File>)request.getAttribute("msg1");
 @SuppressWarnings("unchecked")
 List<File>updateMessage = (List<File>)request.getAttribute("updateMsg");	
 @SuppressWarnings("unchecked")
 List<File>grant_pro= (List<File>)request.getAttribute("gr_pro");	
					 %>					 
<STYLE type="text/css">
td{
white-space: nowrap;
}
.table_ {
white-space: nowrap; 
table-layout: fixed;
left: 0px;
top: 0px;
margin-left:1%;
width: 101%;
}
.table_div{
	margin-left: 2.55%;
    margin-top: -2%;
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
.table_ tbody {
  display: block;
  min-height: calc(280px + 1 px);
  max-height: 355px;
  /*use calc for fixed ie9 & <*/
  overflow-y:scroll;
  color: #000;
}
.table_ tr{
  display: block;
  overflow: hidden;
}
.table_ tr:nth-child(even) {
  background-color: #0000000b;
}
.table_{
border: 1px solid black;
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
.table_ td:nth-child(1),.table_ th:nth-child(1){
width: 0%;
}
.table_ td:nth-child(2),.table_ th:nth-child(2){
width: 8%;
}
.table_ td:nth-child(3),.table_ th:nth-child(3){
width: 9%;
}
.table_ td:nth-child(4),.table_ th:nth-child(4){
width: 9%;
}
.table_ td:nth-child(5),.table_ th:nth-child(5){
width: 7%;
}
.table_ td:nth-child(6),.table_ th:nth-child(6){
width: 7%;
}
.table_ td:nth-child(7),.table_ th:nth-child(7){
width: 7%;
}
.table_ td:nth-child(8),.table_ th:nth-child(8){
width: 8%;
}
.table_ td:nth-child(9),.table_ th:nth-child(9){
width: 6%;
}
.table_ td:nth-child(10),.table_ th:nth-child(10){
width: 6%;
}
.table_ td:nth-child(11),.table_ th:nth-child(11){
width: 6%;
}
.table_ td:nth-child(12),.table_ th:nth-child(12){
width: 7.6%;
}
.table_ td:nth-child(13),.table_ th:nth-child(13){
width: 5%;
}
.table_ td:nth-child(14),.table_ th:nth-child(14){
width: 5%;
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

.msg1 {
    padding: 7px;
    margin-top: 10px;
    margin-bottom: 10px;
    border: 1px solid transparent;
    border-radius: 4px;
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
	form {
  width: 78.7%;
  border: 0px solid #f1f1f1;
  margin-left: 1%;
  margin-right: auto;
  margin-top: -4px;
  margin-bottom: 5px;
  text-align: center;
  white-space: nowrap;}
  form input[type="text"]:hover{
  background-color: #F1F0F0;
  border-color: black;
  border-radius: 8px; /* Rounded corners */	
  }
  form input[type="date"]:hover{
  background-color: #F1F0F0;
  border-color: black;
  border-radius: 8px; /* Rounded corners */
  }
   form select:hover{
    background-color: #F1F0F0;	
    border-color: black;
    border-radius: 8px; /* Rounded corners */
  }
  
  
        .selectBox {
            position: relative;}
        .selectBox select {
            width: 98%;
            font-weight: normal;
            height: 20.5px;}
        .overSelect {
            position: absolute;
            left: 0;
            right: 0;
            top: 0;
            bottom: 0;}
        #checkBoxes {
            display: none;
            border: 1px #8DF5E4 solid;}
        #checkBoxes label {
            display:block;}
        #checkBoxes label:hover {
            background-color: #4F615E;
            color: white;
            /* Added text color for better visibility */}
</STYLE>
<script type="text/javascript">  
var dataExCsv="Detail Operation Data form Excel or CSV";
var dataDrill="Drilling Pump Test Historical Data";
var WellRepo="Well Report Data";
var pumpData="Pump and Generator Specification Data";

//for project report
var typeforDPTHD=[".pdf",".docx"]
var checkWellReportFileT=new RegExp("([a-zA-Z0-9\S_\\.\-:])+("+typeforDPTHD.join('|')+")$")
var fileTypeAllowedforDPTHD=["{.pdf}","{.docx}"]

var all_ExcelfileType=[".xlsx",".xls",".csv"];
var rexpr_gwData= new RegExp("([a-zA-Z0-9\S_\\.\-:])+("+all_ExcelfileType.join('|')+")$");
var all_fileTypeAllowed=["{.xlsx}","{.xls}","{.csv}"];

function checkgwExcelform(check_gwExcel){
	if((check_gwExcel.file1.files.length === 0) && (check_gwExcel.file2.files.length === 0) && (check_gwExcel.file3.files.length === 0) && 
			(check_gwExcel.file4.files.length === 0) && (check_gwExcel.file5.files.length === 0)){
		alert("Upload Atleast One Excel file for the Excel Sheet Data.... ");
		return false;
	}
	if(check_gwExcel.file1.files.length != 0){
		for(var i=0;i<check_gwExcel.file1.files.length;i++){
			if(!rexpr_gwData.test(check_gwExcel.file1.files.item(i).name)){	
		alert("Note: You can only Upload Excel Sheet or CSV file; Such as <<"+ all_fileTypeAllowed.join(' or ') +">> file types for "+dataExCsv)
			//alert("Note: You can only Upload: <<"+ vector.join(', ') +">> file and Its Supportive file Types for "+datarsa)
				return false;
				}
			}
		if(check_gwExcel.log1.checked == false){
			alert("Check List for Operational (Real Time Data) Data is Not Checked! Check It and upload your file ");
			mform1.file1.focus();
			return false;
			}
	}
	if(mform1.file2.files.length!=0){
		for(var i=0;i<mform1.file2.files.length;i++){
		if(!rexpr_gwData.test(mform1.file2.files.item(i).name)){	
			alert("Note: You can only Upload Excel Sheet or CSV file; Such as <<"+ all_fileTypeAllowed.join(' or ') +">> file types for "+dataExCsv);
			return false;
			}
		}
		if(mform1.log2.checked == false){
			alert("Check List for Microbiological Data is Not Checked! Check It and Upload Your file ");
			mform1.file2.focus();
			return false;	
		}
	}
	if(mform1.file3.files.length!=0){
		for(var i=0;i<mform1.file3.files.length;i++){
		if(!rexpr_gwData.test(mform1.file3.files.item(i).name)){	
			alert("Note: You can only Upload Excel Sheet or CSV file; Such as <<"+ all_fileTypeAllowed.join(' or ') +">> file types for "+dataExCsv);
			return false;
			}
		}
		if(mform1.log3.checked == false){
			alert("Check List for Physical Data is Not Checked! Check It and Upload Your File ");
			mform1.file3.focus();
			return false;	
		}
	}
	if(mform1.file4.files.length != 0){
		for(var i=0;i<mform1.file4.files.length;i++){
			if(!rexpr_gwData.test(mform1.file4.files.item(i).name)){	
			alert("Note: You can only Upload Excel Sheet or CSV file; Such as <<"+ all_fileTypeAllowed.join(' or ') +">> file types for "+dataExCsv);
			}
				return false;
				}
		if(mform1.log4.checked == false){
			alert("Check List for Chemical Data is Not Checked! Check It and upload Your File ");
			mform1.file4.focus();
			return false;
			}
		}
	if(mform1.file5.files.length != 0){
		for(var i=0;i<mform1.file5.files.length;i++){
			if(!rexpr_gwData.test(mform1.file5.files.item(i).name)){	
			alert("Note: You can only Upload Excel Sheet or CSV file; Such as <<"+ all_fileTypeAllowed.join(' or ') +">> file types for "+dataExCsv);
			}
				return false;
				}
		if(mform1.log5.checked == false){
			alert("Check List for Rehabilitation Data is Not Checked! Check It and upload Your File ");
			mform1.file5.focus();
			return false;
			}
		}
	return true;
}
function chechform(check_){
	const limitation=1000;
	if(document.getElementById('regionEn').selectedIndex == 0){
		alert("Well Address not selected, Please Select One!");
		return false;
		}
	if(document.getElementById('regionCityID').selectedIndex == 0){
		alert("City Or Region not selected, Please Select One!");
		return false;
		}
	if(document.getElementById('subcityzoneId').selectedIndex == 0){
		alert("Sub City Or Zone not selected, Please Select!");
		return false;
		}
	if(document.getElementById('Inv_id').selectedIndex == 0){
		alert("Well Field Not Selected, Please Select One!");
		return false;
		}
	var validate_location=document.getElementById("geoLocationId").value.split(",");
  	 if(validate_location.length < 3){
  		alert("Please Insert the Coordinates Latitude(Y), Longtude(X) and Elevation by separating Comma On a Well Position Box!");
		return false; 
  	 }
  	    var locY=parseFloat(validate_location[0]);
  	    var locX=parseFloat(validate_location[1]);
  	 if(locY < 8.25 || locY > 9.43 || locX < 38.20 || locX > 39.31){
  		alert("Please Correct the Coordinate (Latitude (Lt), Longitude(Ln)), Let Make it in Ethiopian Coordinate Range!");
		return false;	
		}
  	if(document.getElementById('owendByID').selectedIndex == 0){
		alert("Well Ownership Category not Selected, Please Select One!");
		return false;
		}
  	var validate_owner=document.getElementById("owendBy");
	if(document.getElementById('owendByID').selectedIndex > 0 && validate_owner.value.split(",").length < 3){
		alert("Make Sure the Company Name, Email and Phone Number are Separating by Comma, and Inserted In the Owner Address Box!")
		return false;	
	}
	if(document.getElementById('drillingPermit').selectedIndex == 0){
		alert("Drilling License Issued not Selected, Please Select One!");
		return false;
		}
	if(document.getElementById("wellDethId").value === ""){
		alert("Make Sure Well Depth is Inserted In Well Depth Box!")
		return false;	
	}
	if(document.getElementById("wellDethId").value > limitation){
		alert("Well Depth you entered is above maximum, Please correct your entry!")
		return false;	
	}
	if(document.getElementById("casingId").value===""){
		alert("Make Sure Large Casing ID Inserted In a Large Casing ID Box!")
		return false;	
	}
	if(document.getElementById("casingId").value > limitation){
		alert("Large Casing ID you entered is above maximum, Please correct your entry!")
		return false;	
	}
	if(document.getElementById("telesCasingId").value===""){
		alert("Make Sure Telescoped Casing ID Inserted In a Telescoped Casing ID Box!")
		return false;	
	}
	if(document.getElementById("telesCasingId").value > limitation){
		alert("Telescoped Casing ID you entered is above maximum, Please correct your entry!")
		return false;	
	}
	if(document.getElementById('casingMaterId').selectedIndex == 0){
		alert("Casing Material not Selected, Please Select One!");
		return false;
		}
	if(document.getElementById("wellYield").value === ""){
			alert("Well Yield Must Not be Empty!");
			return false;
			}
	if(document.getElementById("wellYield").value > limitation){
		alert("Well Yield you entered is above maximum, Please correct your entry!");
		return false;
		}
	if(document.getElementById("staticWL").value === ""){
		alert("Static Water Level Must Not be Empty!");
		return false;
		}
	if(document.getElementById("staticWL").value > limitation){
		alert("Static Water Level you entered is above maximum, Please correct your entry!");
		return false;
		}
	if(document.getElementById("dynamicWL").value === ""){
		alert("Dynamic Water Level Must Not be Empty!");
		return false;
		}
	if(document.getElementById("dynamicWL").value > limitation){
		alert("Dynamic Water Level you entered is above maximum, Please correct your entry!");
		return false;
		}
	if(document.getElementById("waterLevelId").value === ""){
		alert("Make shure The Well's Specific Capacity is Defined!")
		return false;	
	}
	if(document.getElementById("waterLevelId").value > limitation){
		alert("The Well's Specific Capacity you entered is above maximum, Please correct your entry")
		return false;	
	}
	if(document.getElementById('mainAqiefer').value === ""){
		alert("Main Aquifer Must Defined, Please Define!");
		return false;
		}
	if(document.getElementById('wellType').selectedIndex == 0){
		alert("Well Type not Selected, Please Select One!");
		return false;
		}
		
  	if(document.getElementById('wellIndex').value === ""){
		alert("Well Index Must Defined, Please Define!");
		return false;
		}
  	if(document.getElementById('wellCode').value === ""){
		alert("Well Code Must Defined, Please Define!");
		return false;
		}
	if(document.getElementById('wellStatus').selectedIndex == 0){
		alert("Well Status not selected, Please Select One!");
		return false;
	}
	
	//Productive Well Status Controll
	if((document.getElementById('wellStatus').value === "Productive") && (document.getElementById('prodSId').selectedIndex == 0)){
		alert("Current Well Status not Defined, Please Select One!");
		return false;
		}
	if((document.getElementById('wellStatus').value === "Productive") && (document.getElementById('prodSId').value === "Functional") &&
			(document.getElementById('welleConditionId').selectedIndex == 0)){
		alert("Well Functionality Status not Defined, Please Select One!");
		return false;
		}
	if((document.getElementById('wellStatus').value === "Productive") && (document.getElementById('pumpStatusId').selectedIndex == 0)){
		alert("Pumping System Status Not Selected, Please Select One!");
		return false;
		}
	if((document.getElementById('wellStatus').value === "Productive") && (document.getElementById('pumpStatusId').value==="Installed" || 
			document.getElementById('pumpStatusId').value==="Changed") && (document.getElementById('designCapacityId').value === "")){
		alert("Pumping Capacity Not Defined, Please Fill it in the Pump Capacity Box!");
		return false;
		}
	if((document.getElementById('wellStatus').value === "Productive") && (document.getElementById('pumpStatusId').value==="Installed" || 
			document.getElementById('pumpStatusId').value==="Changed") && (document.getElementById('pumpInstalledID').value === "")){
		alert("Pump Head Position Not Defined, Please Fill it in the Pump Head Box!");
		return false;
		}
	if((document.getElementById('wellStatus').value === "Productive") && (document.getElementById('pumpStatusId').value==="Installed" || 
			document.getElementById('pumpStatusId').value==="Changed") && (document.getElementById('pumpPostionId').value === "")){
		alert("Pumping Position Not Defined, Please Fill it in the Pumping Position Box!");
		return false;
		}
	if((document.getElementById('wellStatus').value === "Productive") && (document.getElementById('pumpStatusId').value==="Installed" || 
			document.getElementById('pumpStatusId').value==="Changed") && (document.getElementById('pumpDischargeR').value === "")){
		alert("Pump Discharge Rate Not Defined, Please Fill it in the Pump  Discharge Box!");
		return false;
		}
	if((document.getElementById('wellStatus').value === "Productive") && (document.getElementById('powerId').selectedIndex == 0)){
		alert("Power Source not Selected, Please Select One!");
		return false;
		}
	if((document.getElementById('wellStatus').value === "Productive") && (document.getElementById('powerId').value == "National Grid") && 
			(document.getElementById('genAvalId').selectedIndex==0)){
		alert("Avialable Generator Status not Defined, Please Select One!");
		return false;
		}
	if((document.getElementById('wellStatus').value === "Productive") && (document.getElementById('powerId').value == "Generator Set") && 
			(document.getElementById('genStatId').selectedIndex==0)){
		alert("Generator Status not Defined, Please Select One!");
		return false;
		}
	if((document.getElementById('wellStatus').value === "Productive") && (document.getElementById('powerId').value == "Generator Set") && 
			(document.getElementById('genPowerId').value=="")){
		alert("Generator Size not Defined, Please Define the Size!");
		return false;
		}
	if((document.getElementById('wellStatus').value === "Productive") && (document.getElementById('scadaId').selectedIndex == 0)){
		alert("SCADA Connection not Defined, Please Select One!");
		return false;
		}
	if((document.getElementById('wellStatus').value === "Productive") && (document.getElementById('scadaId').value == "YES") && 
			(document.getElementById('scadaSId').selectedIndex==0)){
		alert("SCADA Connection Status not Defined, Please Select One!");
		return false;
		}
	
	const validate_empId=document.getElementById("enumId");
	if((document.getElementById('wellStatus').value === "Productive") && (validate_empId.value.split(",").length < 4)){
		alert("Make Sure Enumerator Company, Enumerator Name, Email and Phone Number are Separating by Comma, and Inserted In the Data Enumerator Box!");
		return false;
		}
	//Abandoned Condition Control
	if((document.getElementById('wellStatus').value === "Abandoned") && (document.getElementById('aReason').selectedIndex === 0)){
		alert("Well Abandoned Reason Not Selected, Please Select One!");
		return false;
		}
	if((document.getElementById('wellStatus').value === "Abandoned") && (document.getElementById('sealed_by').selectedIndex === 0)){
		alert("Select YES If the Abandoned Well is Seald or Not if Not Sealed, Please Select One!");
		return false;
		}
	var validate_reportedBy=document.getElementById("reported_by");
	if((document.getElementById('wellStatus').value === "Abandoned") && (validate_reportedBy.value.split(",").length < 3)){
		alert("Make Sure the Company Name, Email and Phone Number are Separated by Comma, and Inserted In a Reported by Box!")
		return false;	
	}
	var validate_EnumAban=document.getElementById("enumIdAban");
	if((document.getElementById('wellStatus').value === "Abandoned") && (validate_EnumAban.value.split(",").length < 4)){
		alert("Make Sure Enumerator Company, Enumerator Name, Email and Phone Number are Separated by Comma, and Inserted In a Data Enumrator Box!")
		return false;	
	}
	//validate Well Report data attachments
	var display_pwellInfo=document.getElementById("pwell_span");
	if(document.getElementById('wellStatus').value === "Productive" && display_pwellInfo.style.display !== 'none'){ 
		if(mform.pwell_id.files.length === 0){
			alert("Upload Drilling Pump Test History Data, Note the Attachment is in Excel or CSV File.... ");
			return false;
		}
		//validate productive well
		if(mform.pwell_id.files.length != 0){
			for(var i=0;i<mform.pwell_id.files.length;i++){
				if(!rexpr_gwData.test(mform.pwell_id.files.item(i).name)){	
			alert("Note: You can only Upload Excel Sheet or CSV file; Such as <<"+ all_fileTypeAllowed.join(' or ') +">> file types for "+dataDrill)
				//alert("Note: You can only Upload: <<"+ vector.join(', ') +">> file and Its Supportive file Types for "+datarsa)
					return false;
					}
				}
			if(mform.pwell_check.checked == false){
				alert("Check List for Well Drilling Pump Test History Data Attachment is Not Checked! Check It and upload your Data");
				mform.pwell_id.focus();
				return false;
				}
		}
	}
	//Pumping System data attachments
	if(document.getElementById('wellStatus').value === "Productive"){
		if(mform.pump_id.files.length === 0 || mform.reportDataid.files.length === 0){
			if(confirm("Pump and Generator Factory Spec Document  Or Well Report Document not attached. If you Click Ok, Your Submision will be without attachment")){
				return true;
				}
			else{
				return false;	
			}
			return false;	
		}
		if(mform.pump_id.files.length != 0){
			for(var i=0;i<mform.pump_id.files.length;i++){
				if(!rexpr_gwData.test(mform.pump_id.files.item(i).name)){	
			alert("Note: You can only Upload Excel Sheet or CSV file; Such as <<"+ all_fileTypeAllowed.join(' or ') +">> file types for "+pumpData)
				//alert("Note: You can only Upload: <<"+ vector.join(', ') +">> file and Its Supportive file Types for "+datarsa)
					return false;
					}
				}
			if(mform.pump_check.checked == false){
				alert("Check List for Pump and Generator Specification Report Attachment is Not Checked! Check It and upload your Data ");
				mform.pump_id.focus();
				return false;
				}
		}
		if(mform.reportDataid.files.length != 0){
			for(var i=0;i<mform.reportDataid.files.length;i++){
				if(!checkWellReportFileT.test(mform.reportDataid.files.item(i).name)){	
			alert("Note: You can only Upload PDF or Word file; Such as <<"+fileTypeAllowedforDPTHD.join(' or ') +">> file types for "+WellRepo)
				//alert("Note: You can only Upload: <<"+ vector.join(', ') +">> file and Its Supportive file Types for "+datarsa)
					return false;
					}
				}
			if(mform.report_check.checked == false){
				alert("Check List for Well Report is Not Checked! Check It and upload your Data ");
				mform.pump_id.focus();
				return false;
				}
		}
		
	}
	//Validate Abandoned wells file Attachment
	var display_abanwellInfo=document.getElementById("aban_span");
	if(document.getElementById('wellStatus').value === "Abandoned" && display_abanwellInfo.style.display !=='none'){
		//validate Abandoned Well Report Attachments 
		if(mform.aban_id.files.length === 0){
			alert("Upload Drilling Pump Test History Data, Note the Attachment is in Excel or CSV File.... ");
			return false;
		}
		if(mform.aban_id.files.length != 0){
			for(var i=0;i<mform.aban_id.files.length;i++){
				if(!rexpr_gwData.test(mform.aban_id.files.item(i).name)){	
			alert("Note: You can only Upload Excel Sheet or CSV file; Such as <<"+ all_fileTypeAllowed.join(' or ') +">> file types for "+dataExCsv)
				//alert("Note: You can only Upload: <<"+ vector.join(', ') +">> file and Its Supportive file Types for "+datarsa)
					return false;
					}
				}
			if(mform.aban_check.checked == false){
				alert("Check List for Drilling Pump Test History Data is Not Checked! Check It and upload your Data ");
				mform.aban_id.focus();
				return false;
				}
		}
	}
	//Pumping System data attachments
	if(document.getElementById('wellStatus').value === "Abandoned"){
		if(mform.abanWellreport.files.length === 0){
			if(confirm("Well Report Document not attached. If you Click Ok, Your Submision will be without attachment")){
				return true;
				}
			else{
				return false;	
			}
			return false;	
		}
		if(mform.abanWellreport.files.length != 0){
			for(var i=0;i<mform.abanWellreport.files.length;i++){
				if(!checkWellReportFileT.test(mform.abanWellreport.files.item(i).name)){	
			alert("Note: You can only Upload PDF or Word file; Such as <<"+fileTypeAllowedforDPTHD.join(' or ') +">> file types for "+WellRepo)
				//alert("Note: You can only Upload: <<"+ vector.join(', ') +">> file and Its Supportive file Types for "+datarsa)
					return false;
					}
				}
			if(mform.checkAbanWellRepo.checked == false){
				alert("Check List for Well Report is Not Checked! Check It and upload your Data ");
				mform.pump_id.focus();
				return false;
				}
		}	
	}
	return true;
}
<!--
function isNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : evt.keyCode;
   if (charCode != 46 && charCode > 31 
     && (charCode < 48 || charCode > 57))
      return false;

   return true;
}
//-->
/*Create text inpute for process*/
function showprofield(name){
	  if(name=='1')document.getElementById('div5').innerHTML='Process Name: <input type="text" name="other4" style="width: 190px;'
	  +'height: 25px;" required minlength="5" maxlength="30"/> <span class="validity"></span>';
	  else document.getElementById('div5').innerHTML='';
	}
/*Create text inpute for Basin*/
function showproIdfield(name){
	  if(name=='1')document.getElementById('div8').innerHTML='Project ID: <input type="text" name="u_proid" style="width: 190px;'
	  +'height: 25px;" required minlength="5" maxlength="30"/> <span class="validity"></span>';
	  else document.getElementById('div8').innerHTML='';
	}
function showusergroup(name){
	 if(name=='1')document.getElementById('divU').innerHTML='Project Group: <input type="text" name="u_group" style="width: 190px;'
		  +'height: 25px;" required minlength="5" maxlength="30"/> <span class="validity"></span>';
		  else document.getElementById('divU').innerHTML='';
		}
 </script>    
<title>Home page</title>
</head>
<body>
    <nav class="titbarnav">
    <ul>
    <li> <a href="#">About Us</a> </li>
     <li> <a href="#">Contact</a> </li>
      <li id="c_logout"> <a href="#">Accounts</a> 
      <div id="dis_logout" style="display: none;float: right; ">
      <ul>
      <li><a href="<c:url value="/logout" />">Logout</a></li>
      </ul>
      </div> 
      </li>
    </ul>
	</nav>
	<%int choice= (Integer)session.getAttribute("U_id1"); %>
<h4 style="white-space: nowrap;"><%= session.getAttribute("U_auto")%>: Database User</h4>
<div class="content" style="margin-top: -0.01%;">
     <ul class="tree" id="myUL">
     <li><span style="font: bold;">AAWSA gDB</span>
     <ul>
      <li><a>Store Data</a>
         <ul>
         <li><a>Water Well Data</a>
         <div class="dy_change">
         <ul>
         <li style="font-size: 18px;" id="gtech_id"><a data-li="geo_tech" href="#">Drilling Pumping Test History Data</a></li>
         <li style="font-size: 18px;" id="operational_id"><a data-li="oprate_tech" href="#">Detail Operation Well Data</a></li>
         </ul>
         </div>
         </li>
         
         <!-- <li style="font-size: 16px;" id="pro_repo_id"><div class="dy_change"><a data-li="pro_report" href="#">Spring Water Data</a></div></li> -->
         </ul>
     </li>
     <li><a>Manage Repository</a>
           <div class="dy_change">
           <ul>
          <!--  <li id="grant_pro" style="font-size: 16px;">Grant Data Sheet</li> -->
           <li id="mange_rawdata" style="font-size: 16px;"><a data-li= "corpo_data" href="#">Drilling Pumping Test History Data</a></li>
           <li id="mange_otherdoc" style="font-size: 16px;"><a data-li= "spa_data" class="active_li" href="#">Detail Operation Well Data</a></li>
           <li id="mange_addtional" style="font-size: 16px;"><a data-li= "add_param" href="#">Additional New Attribute</a></li>   
           </ul>
           </div>
           <a data-li="return_error" href="#"><i></i> </a>
           <a data-li="return_result" href="#"><i></i> </a>
     </li>
          </ul>
          </li></ul></div>
          <div class="main-content">
       <div class="table_div item_li add_param" style="display: none; margin-left: 2.7%;margin-top:-2.6%;">
       <p style="text-align: center;margin-top: 1.5%; font-weight: bold;margin-left:-10%; font-size: 17px;">Add New Attribute</p>
            <input type="text" id="addedUserID" name="user_idNP" value="<%=choice%>" style="display: none;" />
            <div class="WCRFormDiv" style="margin-top: -0.05%; margin-left: 0.75%">
            <table style="width: 40%; text-align: center; white-space: nowrap; table-layout: fixed; margin-left:25%;"border="0">
            <tr>
            <td style="text-align: left;width: 20%;border-right:none; 
            border-collapse:separate;"><label style="margin-left: 8%;">Content Name:</label></td>
            <td style="border-collapse:separate;">
            <select id="addtionalParamid" name="center_id" class="form-control" style="width:250px; height:21px; margin-left: 10%;" 
            onchange="addtionalParamF(); addtionalParamF2();">
            <option value="0">----------Select Content Needed----------</option>
            <option value="City_OroZone">City/Oromia Zone</option>
            <option value="Well Field">Well Field</option>
            <option value="Well Owner">Well Owner</option>
            <option value="Casing Material">Casing Material</option>
            <option value="Well Type">Well Type</option>
            <option value="Abandoned Reason">Abandoned Reason</option>
            <option value="Pump Information">Pump Information</option>
            <option value="Power System">Power System</option>
            <option value="SCADA Connection">SCADA Connection</option>
            </select>
            </td>
            </tr>
            <tr id="city_ZoneID" style="display: none;">
				<td><span style="margin-left: -13%;">City /Zone Name:</span></td>
				<td><select id="selectedCZID" name="cityZname" class="form-control" style="width:250px; height:21px;margin-left: -4%;">
            <option value="0">--------Select City/ Zone Name-------</option>
            <option value="6">Addis Ababa City Admin</option>
            <option value="7">Sheger City</option>
            <option value="8">North Shewa</option>
            <option value="9">West Shewa</option>
            <option value="10">East Shewa</option>
            <option value="11">South West Shewa</option>
            </select></td>
            </tr>
            <tr id="addParametersId" style="display: none;" >
				<td style="border-right:none;border-collapse:separate;"><label style="margin-left: 8%;">Add Attribute:</label></td>
					<td class="td" style="border-right:none;border-collapse:separate;">
					<input type="text" id="additionalDataID" name="additionalDataName"style="height: 21px;width: 250px;
					margin-left:5.5%;margin-top: 2px;"/></td>
				</tr>
             </table>
             <input type="submit" value="Save" id="addparam_button" style="height: 20px; width: 100px;text-align: center;margin-left: 40%;"/>
             <div id="addParamresponse" style="margin-left:8%;margin-top:1%;font-size: 17px;"></div>
             <div id="errorMessage" style="margin-left:18%;margin-top:1%;font-size: 17px;"></div>
             </div>
            </div>
        <div class="table_div item_li grant_proj" style="display: none;">
       <p style="text-align: center;margin-top: 1.5%; font-weight: bold; font-size: 17px;">Grant Data Sheet</p>
            <input type="text" id="user_idGDS" name="user_idGDS" value="<%=choice%>" style="display: none;" />
            <input type="text" id="hold_filename" value="" style="display: none;"> 
            <table style="width: 45%; text-align: center; white-space: nowrap; table-layout: fixed; margin-left: 2%;" border="0">
            <tr style="background-color: white;">
            <td style="text-align: left;padding-right: 0px;padding-top:6px;font-size: 13px; width: 20%;border-right:none; border-collapse:separate;">Devision:</td>
            <td style="text-align: left;padding-right: 0px;padding-top:6px;font-size: 13px; width: 70%; border-right:none; border-collapse:separate;">
            <select id="center_id" name="center_id" class="form-control" style="width: 260px; height: 20%;">
<option value="0">-----------------------Select Devision-------------------</option>
<% 
		if(grant_pro!=null){
	      for(File files:grant_pro){
	         %>
		<option value="<%=files.regnam%>"><%=files.regnam%></option>
		<% }}%>
</select></td>
</tr>
<tr style="background-color: white;">
		<td style="text-align: left;padding-right: 0px;padding-top:6px;font-size: 13px; width: 20%; border-right:none; border-collapse:separate;">Team:</td>
		<td style="text-align: left;padding-right: 0px;padding-top:6px;font-size: 13px;width: 70%; border-right:none; border-collapse:separate;">
				<select id="process_id" name="process_id" class="form-control" style="width: 260px; height: 25px;"
				onchange="showprofield(this.options[this.selectedIndex].value);">
	<option value="0" style="font-weight: bold;">-----------------------Select Team---------------------</option>
					</select>
				</td>
				</tr>
				<tr style="background-color: white;">
		<td style="text-align: left;padding-right: 0px;padding-top:6px;font-size: 13px; width: 20%; border-right:none; border-collapse:separate;">User Name:</td>
		<td style="text-align: left;padding-right: 0px;padding-top:6px;font-size: 13px;width: 70%; border-right:none; border-collapse:separate;">
				<select id="User_togrant" name="User_togrant" class="form-control" style="width: 260px; height: 25px;"
				onchange="showprofield(this.options[this.selectedIndex].value);">
	<option value="0" style="font-weight: bold;">-----------------------Select User---------------------</option>
					</select>
				</td>
				</tr>
				<tr style="background-color: white;">
					<td class="td" style="text-align: left;padding-right: 0px;padding-top:6px;font-size: 13px; width:20%; border-right:none; border-collapse:separate;">Project Name:</td>
					<td style="text-align: left;padding-right: 0px;padding-top:6px;font-size: 13px; width: 70%; border-right:none; border-collapse:separate;">
				<select id="pro_name7" name="pro_name7" class="form-control" style="width: 260px; height: 25px;">
	<option value="0" style="font-weight: bold;">-----------------------Select Name-------------------------</option>
					</select>
					</td>
				</tr>
             </table>
             <input type="submit" id="grant_button_id" value="Submit" style="text-align: center;margin-left: 20%;color: blue;font-size: 20px;">
            </div>
            
            <!--Manage Water Well Data  -->
            <div class="table_div item_li corpo_data" style="display: none;" id="displaywellCRD">
       <p style="text-align: center;margin-top: 1.5%; font-weight: bold; font-size: 17px;">Manage Drilling Pumping Test History Data</p>
    <input type="search" class="searchByWellIndex" id="DPTHDwellIndex" name="DPTHDwellIndex" placeholder="Search by Well Index" style="width: 200px; 
    height:25px; border-bottom-color:gray; position: relative; float: right; margin-top: 0%; margin-right: -1.90%;" 
            class="form-control"/>  
            <input type="text" id="user_idWCR" name="user_idWCR" value="<%=choice%>" style="display: none;" />
            <input type="text" id="hold_wellIndex" value="" style="display: none;">
             <input type="text" id="ORwell_id" value="" style="display: none;">
            <table id="accessby_userId" class="table_ tab_projectr">
           <thead>
           <tr>
           <th style="display: none;"></th>
       <th>
       <select class="form-control dropdown_size" name="clCo_id" id="clCo_id">
          <option class="option_css" value="0">Region</option>
        </select>
        </th>
         <th>
        <select class="form-control dropdown_size" name="selCo_id" id="selCo_id">
        <option class="option_css" value="0">Well Field</option>
        </select> 
        </th>
        <th>
        <select class="form-control dropdown_size" name="Cor_cat_id" id="Cor_cat_id">
          <option class="option_css" value="0">Well Owner Group</option>
        </select>
        </th>
        <th>Well Owner</th>
        <th>Well Index</th>
        <th>Well Code</th>
        <th>Construction Year</th>
        <th>Well Status</th>
        <th>Well Depth</th>
        <th>Well Yield</th>
        <th>Data</th>
        <th>Update</th>
        <th>Delete</th>
           </tr>
           </thead>
           <tbody>
           </tbody>
           </table>
            </div> 
            <!--Manage Groundwater Data  -->
            <div class="table_div item_li spa_data" style="display: none;" id="displaywellOGWD">
       <p style="text-align: center;margin-top: 1.5%; font-weight: bold; font-size: 17px;">Manage Detail Operation Well Data</p>
    <input type="search" class="searchByWellIndex" id="DOWDWellIndex" name="DOWDWellIndex" placeholder="Search by Well Index" style="width: 200px; 
    height:25px; border-bottom-color:gray; position: relative; float: right; margin-top: 0%; margin-right: -1.90%;" 
            class="form-control"/>  
            <input type="text" id="user_idOR" name="user_idOR" value="<%=choice%>" style="display: none;" />
            <input type="text" id="hold_wellIndexOP" value="" style="display: none;">
            <input type="text" id="hold_wellIdOR" value="" style="display: none;">
            <table id="access_OpData" class="table_ tab_projectr">
           <thead>
           <tr>
           <th style="display: none;"></th>
       <th>
       <select class="form-control dropdown_size" name="clCo_id" id="clCo_id">
          <option class="option_css" value="0">Region</option>
        </select>
        </th>
         <th>
        <select class="form-control dropdown_size" name="selCo_id" id="selCo_id">
        <option class="option_css" value="0">Well Field</option>
        </select> 
        </th>
        <th>
        <select class="form-control dropdown_size" name="Cor_cat_id" id="Cor_cat_id">
          <option class="option_css" value="0">Well Owner Group</option>
        </select>
        </th>
        <th>Well Owner</th>
        <th>Well Index</th>
        <th>Well Code</th>
        <th>Well Status</th>
        <th>Well Depth</th>
        <th>Data</th>
        <th>Data Status</th>
        <th>Last Updated</th>
        <th>Update</th>
        <th>Delete</th>
           </tr>
           </thead>
           <tbody>
           </tbody>
           </table>
            </div> 
            
            <!--Basic Well Characteristics Data Sheet-->
            <div class="table_div item_li geo_tech main-content" style="display:none;">
            <p style="text-align: center; margin-top: 1.1%; margin-left:-13%; font-weight: bold; font-size: 17px;">
              Store Drilling Pumping Test History Data</p>
              		<form method="post" action="Upload" enctype="multipart/form-data"
			name="mform" onsubmit="return(chechform(this));">
			<input id="uu_id" name="db_userId" type="text" value="<%=choice%>" style="display:none;">
              <input id="Na" name="Na" type="text" value="Water Well" style="display: none;">
			<div id="basicWelltTabDiv" class="WCRFormDiv">
			<table border="0" style="margin-left: 3%; width: 50%;">
			<tr>
             <td>Well Index </td>
             <td><input type="text" id='wellIndex' name="wellIndex" style="width: 98%; height: 21.5px;margin-left: 0.5px;margin-top: -1px;"
             placeholder="Add Well Index" required="required" minlength="4" maxlength="70"/></td></tr>
             <tr>
             <td>Well Code </td>
             <td><input type="text" id= 'wellCode' name="wellCode" style="width: 98%; height: 21.5px; margin-top: -0.5px;margin-left: 0.5px;"
             placeholder="Add Well Code" required="required" minlength="4" maxlength="70"  /></td></tr>
             <tr>
					<td>Well Address </td>
					<td><select id="regionEn" name="waterWellLocation" class="form-control"style="width: 97.6%; height: 21.5px;">
					<option value="0" style="font-weight: bold;">---------Select Well Address--------</option>
					</select>
                        <table style="margin-left:-25.4%;">
						<tr>
						<td id="Id_div"></td>
						<td><select style=" display: none;width: 100%;height: 21.5px; margin-top: -0.5px;" id="regionCityID"name="regionCityID" 
						class="form-control">
						<option value="0" style="font-weight: bold;">---- Select Region or City----</option>
						</select>
						</td>
						</tr>
						<tr>
						<td id="pr_type"></td>
						<td>
						<select style=" display: none;width: 100%;height: 21.5px; margin-top: -0.5px;" id="subcityzoneId" 
						name="subcityzoneId" class="form-control">
						<option value="0" style="font-weight: bold;">---- Select Sub City or Zone----</option>
						</select>
						</td>
						</tr>
						</table>
						<table id="div1" style="margin-left: -4.6%; margin-top:-2%; display: none">
						<tr><td>Wereda Name</td>
						<td><input type="text" id="wr_id" name="woredaName" style="width:100%;height: 21.5px;" required minlength="2" 
						maxlength="300"/></td></tr>
						<tr><td>Local Name</td>
						<td><input type="text" id="loc_id" name="localName" style="width:100%;height: 21.5px; margin-top:-0.5%;" required minlength="3" 
						maxlength="300"/></td></tr>
						</table>
						</td>
						</tr>
						<tr>
					<td class="td" style="white-space: nowrap;">Well Field: <span  style="color:red;"></span></td>
					<td><select id="Inv_id" name="wellField" class="form-control" style="width: 97.5%; height: 21.5px; margin-top: -5.5px;"
						onchange="showfieldInv(this.options[this.selectedIndex].value)">
						<option value="0" style="font-weight: bold;"> ------- Select Well Field------</option>
				     </select>
						</td></tr>
						<tr>
                    <td id="well_pos"></td>
                    <td>
                    <input type="text" id='geoLocationId' name="geoLocationId" style="width:99%; height: 21.5px; margin-top: -0.5px; 
                    margin-left: -1px;"required="required" minlength="15" maxlength="70"/><br></td></tr>
					<tr style="display: none;">
					<td class="td">GWD: </td>
					<td>
					<select id="fsource" name="gwDataname" class="form-control">
					</select>
					<div id="divpRname"></div></td></tr>
				<tr>
                    <td>Owner Category:</td>
                    <td>
                    <select id="owendByID" name="owendByName" class="form-control" style="width: 98%; height: 21.5px; margin-top:3px;" 
                    onchange="oenerAdderssControllerF()">
                    <option value="0" style="font-weight: bold;">---------Select Well Owner---------</option>
                    <option value="AAWSA">AAWSA</option>
                    <option value="Governmental">Governmental</option>
                    <option value="Private">Private</option>
                    <option value="Non Governmental">Non Governmental</option>
                    </select>
                    <table id="ownerAddress" style="margin-left: 0px; display: none; margin-left: 7%;">
                    <tr>
                    <td>Address: </td>
                    <td> <input type="text" id='owendBy' name="owendBy" style="width: 100%; height: 21.5px;margin-top: -0.5px;"
                    placeholder="Company Name,Email,Phone"/></td>
                    </tr>
                    </table>
                   </td>
                </tr>
                <tr>
                    <td>Drilling License Issued:</td>
                    <td>
                    <select id="drillingPermit" name="drillingPermit" class="form-control" style="width: 98%; height: 21.5px;">
                    <option value="0" style="font-weight: bold;">-----Select One-----</option>
                    <option value="YES">YES</option>
                    <option value="NO">NO</option>
                    </select></td>
                </tr>
                <tr>
				<td>Construction Year:</td>
				<td> <input type="date" id="maxDateC" name="constDate" value="1950-07-22" min="1960-01-01" style="width:98%;height: 21.5px;
				text-align: center;"></td></tr>
                <tr>
                <td>Well Depth (m):</td>
                <td><input type="text" id="wellDethId" name="wellDethName" onkeypress="return isNumberKey(event)" style="width: 98%; height: 21.5px; 
                margin-top: -0.5px;margin-left: 0.5px;" 
                placeholder="Well Depth "></td>
                </tr>
                 <tr>
                    <td>Large Casing ID (in.): </td>
                    <td><input type="text" id='casingId' name="largeCasingName" onkeypress="return isNumberKey(event)" style="width: 98%; height: 21.5px;
                    margin-top: -0.5px;margin-left: 0.5px;"
                     placeholder="Large Casing ID"/></td></tr>
                <tr>
                 <tr>
                    <td>Telescoped casing ID (in.): </td>
                    <td><input type="text" id='telesCasingId' name="telescopedCasingName" onkeypress="return isNumberKey(event)" style="width: 98%; height: 21.5px;
                    margin-top: -0.5px;margin-left: 0.5px;"
                     placeholder="Telescoped Casing ID"/></td></tr>
                     <tr>
                    <td>Casing Material: </td>
                    <td>
                    <select id="casingMaterId" name="casingMaterName" class="form-control" style="width: 98%; height: 21.5px; margin-top: -0.5px;">
                    <option value="0" style="font-weight: bold;">-----Select Casing Materials----</option>
                    <option value="Steel">Steel</option>
                    <option value="Mild steel">Mild steel</option>
                    <option value="UPVC">UPVC</option>
                    </select></td></tr>
                <tr>
                <tr>
				<td>Well Yield (L/s): </td>
				<td><input type="text" id="wellYield" name="yieldLs" onkeypress="return isNumberKey(event)" style="height: 21.5px; width: 98%; margin-top: -1px;margin-left:0.5px;"
				placeholder="Well Yield"/></td>
				</tr>
				<tr>
				<td>Static Water Level (m): </td>
				<td><input type="text" id="staticWL" name="SWLName" onkeypress="return isNumberKey(event)" style="height: 21.5px; width: 98%; margin-top: -1px;margin-left:0.5px;"
				placeholder="Static Water Level"/></td>
				</tr>
				<tr>
				<td>Dynamic Water Level (m): </td>
				<td><input type="text" id="dynamicWL" name="DWLName" onkeypress="return isNumberKey(event)" style="height: 21.5px; width: 98%; margin-top: -1px;margin-left:0.5px;"
				placeholder="Dynamic Water Level"/></td>
				</tr>
				<tr>
                <td>Specific Capacity (L/s/m): </td>
                <td><input type="text" id="waterLevelId" name="waterLevelName" onkeypress="return isNumberKey(event)" style="width: 98%; height: 21.5px; margin-top: -0.5px;margin-left: 0.5px;" 
                placeholder="Specific Capacity li/s/m"></td>
                </tr>
                <tr>
                    <td>Main Aquifer:</td>
                    <td><input type="text" id='mainAqiefer' name="mainAqiefer" style="width: 98%; height: 21.5px;margin-left: 0.5px; margin-top: -0.5%;" 
                    placeholder="Main Aquifer"/></td></tr>
                <tr>
                    <td>Well Type: </td>
                    <td><select id="wellType" name="wellType" class="form-control" style="width: 98%; height: 21.5px;">
                    <option value="0" style="font-weight: bold;">------Select well Type------</option>
                    <option value="Production Well">Production Well</option>
                    <option value="Monitoring Well">Monitoring Well</option>
                    <option value="Test Well">Test Well</option>
                    <option value="Replacement Well">Replacement Well</option>
                    <option value="Pilot Production well">Pilot Production well</option>
                    </select></td></tr>
				     <tr>
                    <td>Well Status After Construction:</td>
                    <td>
                    <select style="width: 98%;height: 21.5px;margin-top: -0.5px" id="wellStatus" name="wellStatus" class="form-control" onchange="togleTable()">
						<option value="0" style="font-weight: bold;">---- Select Well Status----</option>
						<option value="Abandoned">Abandoned</option>
						<option value="Productive">Productive</option>
						</select></td>
						</tr>
						<tr>
						<td>
						</td>
						</tr>
						<table id="abandoned" border="1" style="display: none; margin-left: 52.7%;margin-top: -14.60%;">
						<thead> 
						<tr> <th colspan="2" style="text-align: center;">Abandoned Well Data</th></tr>
						</thead>
						<tbody>
						<tr>
						<td>Abandoned Reason: </td>
						<td>
						<select style="width: 190px;height: 21.5px;margin-top: -1px;" id="aReason" name="aReason" class="form-control">
						<option value="0" style="font-weight: bold;">----Select Reason----</option>
						<option value="Dry well">Dry Well</option>
						<option value="Low Yield">Low Yield</option>
						<option value="Poor Water Puality">Poor Water Quality</option>
						<option value="Casing Collapse">Casing Collapse</option>
						<option value="Lithology Collapse">Lithology Collapse</option>
						<option value="High temperature">High temperature</option>
						<option value="Contamination">Contamination</option>
						</select></td></tr>
						<tr>
						<td>Sealed: </td>
						<td>
						<select style="width: 190px;height: 21.5px;margin-top: -1px;" id="sealed_by" name="sealed_by" class="form-control">
						<option value="0" style="font-weight: bold;">----Select One----</option>
						<option value="YES">YES</option>
						<option value="NO">NO</option>
						</select></td></tr>
						<tr>
						<td>Sealed Date: </td>
						<td> <input type="date" id="maxDateS" name="sealedDate" value="1980-07-22" min="1960-01-01" 
						style="width: 189px;height: 20.5px; text-align: center;margin-top: -1px;"></td>
						</tr>
						<tr>
						<td>Reported By: </td>
						<td><input type="text" id="reported_by" name="reported_by" style="width: 190px; height: 20.5px;margin-top: -1px;margin-left:0.5px;" 
						placeholder="Company Name,Email,Phone"/></td>
						</tr>
						<tr>
						<td>Data Enumerator: </td>
						<td> <input type="text" id="enumIdAban" name="enumNameAban" style="width: 190px; height: 20.5px;margin-top: -1px;" 
						placeholder="Enumerator Company,Enumerator Name,Email,Phone"/></td>
						</tr>
						</tbody>
						</table>
						<!--Productive Well  -->
						<table id="productive" border="1" style="display: none; margin-left:52.7%;  margin-top: -14.50%;">
						<thead> 
						<tr><th colspan="2" style="text-align: center;">Well Status and Electromechanical Information</th></tr>
						</thead>
						<tbody>
						<tr>
						<td>Current Well Status: </td>
						<td>
						<select style="width: 170px;height: 20.5px;margin-top: -1px;" id="prodSId" name="prodwellStatus" class="form-control" onchange="fuctionalWellSatatus();
						controlTopMargin();">
						<option value="0" style="font-weight: bold;">----Select Status----</option>
						<option value="Functional">Functional</option>
						<option value="Non Functional">Non Functional</option>
						<option value="Under Construction">Under Construction</option>
						<option value="Under Rehabilitation">Under Rehabilitation</option>
						<!--  
						<option value="Under Construction">Under Construction</option>
						<option value="Under Rehabilitation">Under Rehabilitation</option>-->
						</select>
									<!--Control the following function using Current Well status  -->
						<table id="funcWellStatusT" style="display: none;">
						<tr>
						<td>Functioning Condition: </td>
						<td>
						<select style="width: 100%;height: 20.5px;margin-top: -1px;" id="welleConditionId" name="wellCondtionName" class="form-control">
						<option value="0" style="font-weight: bold;">---- Select One----</option>
						<option value="Active">Active</option>
						<option value="InActive">Inactive</option>
						</select>
						</td>
						</tr>
						</table>
						<!-- End of Observation PIP Controlling -->
						
						</td></tr>
						<tr>
						<td>Pump Information: </td>
						<td>
						<select id="pumpStatusId" name="pumpStatusName" class="form-control" style="width: 170px;height: 20.5px;margin-top: -1px;" 
						onchange="pumpSystemCon(); controlTopMargin();">
						<option value="0"style="font-weight: bold;">----Select Pump Status----</option>
						<option value="Installed">Pump Status</option>
						<option value="Not Installed">Not Installed</option>
						<option value="Changed">Pump Replaced</option>
						<option value="Damaged">Damaged</option>
						</select>
						<!--Control changed Pump Status -->
						<table id="tablepumpStatus" style="display: none;">
						<tr>
						<td>Pump Installed Date: </td>
						<td><input type="date" id="pumpInstDate" name="installedDate" style="height: 20.5px; width: 100%; margin-top: -1px;"
						value="" min="1970-01-01"/></td>
						</tr>
						<tr>
						<td>Pump Capacity(KW): </td>
						<td><input type="text" id="designCapacityId" name="pumpCapacityName" onkeypress="return isNumberKey(event)" style="height: 20.5px; width: 100%; margin-top: -1px;"/></td>
						</tr>
						<tr>
						<td>Pump Head(m): </td>
						<td><input type="text" id="pumpInstalledID" name="pumpHead" onkeypress="return isNumberKey(event)" style="height: 20.5px; width: 99.5%; margin-top: -1px;"/></td>
						</tr>
						<tr>
						<td><label>Pump Position(m): </label></td>
						<td>
						<input type="text" style="width: 100%;height: 20.5px;margin-top: -1px;" onkeypress="return isNumberKey(event)" id="pumpPostionId" name="pumPosition"
						placeholder="Pump Current Position"/>
						</td>
						</tr>
						<tr>
						<td>Discharge Rate(m3/hr) </td>
						<td><input type="text" id="pumpDischargeR" name="dischargeRate" onkeypress="return isNumberKey(event)" style="height: 20.5px; width: 99.5%; margin-top: -1px;"/></td>
						</tr>
						</table>
						<table id="tablepumpChangeStatus" style="display: none;">
						<tr>
						<td>Replaced Date </td>
						<td><input type="date" id="pumpreplaceD" name="pumpReplacedDate" style="height: 20.5px; width: 99.5%; margin-top: -1px;"
						value="" min="1971-01-01"/></td>
						</tr>
						<tr>
						<td>Pump Capacity(KW): </td>
						<td><input type="text" id="designCapacityId" name="pumpCapacityName" onkeypress="return isNumberKey(event)" style="height: 20.5px; width: 100%; margin-top: -1px;"/></td>
						</tr>
						<tr>
						<td>Pump Head(m): </td>
						<td><input type="text" id="pumpInstalledID" name="pumpHead" onkeypress="return isNumberKey(event)" style="height: 20.5px; width: 99.5%; margin-top: -1px;"/></td>
						</tr>
						<tr>
						<td><label>Pump Position(m): </label></td>
						<td>
						<input type="text" style="width: 100%;height: 20.5px;margin-top: -1px;" onkeypress="return isNumberKey(event)" id="pumpPostionId" name="pumPosition"
						placeholder="Pump Current Position"/>
						</td>
						</tr>
						<tr>
						<td>Discharge Rate(m3/hr) </td>
						<td><input type="text" id="pumpDischargeR" name="dischargeRate" style="height: 20.5px; width: 99.5%; margin-top: -1px;"/></td>
						</table>
						</td></tr>
						<tr>
						<td>Power System: </td>
						<td><select id="powerId" name="powerName" class="form-control" style="width: 170px;height: 20.5px; margin-top:-2px;" 
						onchange="powerSystemCon(); controlTopMargin();">
						<option value="0"style="font-weight: bold;">----Select Power Source----</option>
						<option value="National Grid">National Grid</option>
						<option value="No Power Source">No Power Source</option>
						<option value="Generator Set">Generator Set</option>
						</select>
						<!--Control the following function using power system  -->
						<table id="powerStatus" style="display: none;">
						<tr>
						<td>Generator Status: </td>
						<td>
						<select style="width: 100%;height: 20.5px;margin-top: -1px;" id="genStatId" name="genStatName" class="form-control">
						<option value="0" style="font-weight: bold;">---- Select One----</option>
						<option value="Functional">Functional</option>
						<option value="Non Functional">Non Functional</option>
						</select>
						</td>
						</tr>
						<tr>
						<td>Generator Capacity(KVA): </td>
						<td><input type="text" id="genPowerId" name="genPowerName" onkeypress="return isNumberKey(event)" style="width: 100%;height:20.5px;margin-left:1px;margin-top:-1px;"></td>
						</tr>
						</table>
						<table id="avialableGene" style="display: none;">
						<tr>
						<td>Available Generator: </td>
						<td>
						<select style="width: 100%;height: 20.5px;margin-top: -1px;" id="genAvalId" name="genAvaiName" class="form-control">
						<option value="0" style="font-weight: bold;">---- Select Status----</option>
						<option value="Stand By">Stand By</option>
						<option value="Non Stand By">Non Stand By</option>
						</select>
						</td>
						</tr>
						</table>
						<!-- End of Power system Controlling -->
						</td>
						</tr>
						<tr>
						<td>SCADA Connection: </td>
						<td><select id="scadaId" name="scadaName" class="form-control" style="width: 170px;height: 20.5px;margin-top: -1px;" onchange="scadaSystemCon(); 
						controlTopMargin();">
						<option value="0"style="font-weight: bold;">----Select One----</option>
						<option value="YES">Available</option>
						<option value="NO">Not Available</option>
						</select>
						<!--Controlled by SCADA system  -->
						<table id="scadaStatus" style="display: none;">
						<tr>
						<td>Status: </td>
						<td>
						<select style="width: 100%;height: 20.5px;margin-top: -1px;" id="scadaSId" name="scadaSName" class="form-control">
						<option value="0" style="font-weight: bold;">---- Select Status----</option>
						<option value="Functional">Functional</option>
						<option value="Partially Functional">Partially Functional</option>
						<option value="Non Functional">Non Functional</option>
						</select>
						</td>
						</tr>
						</table>
						<!-- End of SCADA system -->
						<tr>
						<td>Data Enumerator: </td>
						<td> <input type="text" id="enumId" name="enumName" style="width: 170px; height: 20.5px;margin-top: -1px;" 
						placeholder="Enumerator Company,Enumerator Name,Email,Phone"/></td>
						</tr>
						</tbody>
						</table>
						<tr>
					 <td>
					 <div id="proWellReport" style="display: none;font-size:18px" class="displayinBlock">
					 <input id="prochangeText" value="Add Well Report" readonly="readonly" style="border: none;outline: none;box-shadow:none; 
					 background-color:#ceffce;cursor:pointer;width:100px;">
					 
					 <!--Productive Well Report  -->
					 <span id="pwell_span" style="font-size: 14px;font-style: italic; display: none;">
					 <input type="checkbox" id="pwell_check" name="checkwellReport" value="Productive Well"/>Drilling Pumping History...
					 <input type="file" name="productiveWelldata" multiple="multiple" id="pwell_id" style="font-size: 12px;font-style: italic; width: 168px;"/></span>
					 
					 <!-- pumping system  -->
					 <span id="pumpSytemdiv" style="margin-left: 0%; display: none; font-size: 14px; font-style: italic;">
					 <input type="checkbox" id="report_check" name="reportCheck" value="wellRepoDoc"/>Well Report:
					 <input type="file" name="reportProWell" multiple="multiple" id="reportDataid" style="font-size: 12px;font-style: italic;width: 168px;"/>
					 <input type="checkbox" id="pump_check" name="pumpingCheck" value="pumping_doc"/>Pump and Generator Spec
					 <input type="file" name="pumpingSdata" multiple="multiple" id="pump_id" style="font-size: 12px;font-style: italic;width: 168px;"/></span>
					 </div>
					 </td>
					 
					 <!-- Abandoned Well Report  -->
					 <td>
					 <div id="abanWellReport" style="display: none; font-size: 18px"><input id="abanchangeText" value="Add Well Report" 
					 readonly="readonly" style="border: none;outline: none;box-shadow:none; background-color:#ceffce;cursor:pointer;width:100px;">
					 
					 <span id="aban_span" style="margin-left:3%;display:none;font-size: 14px;font-style: italic;">
					 <input type="checkbox" id="aban_check" name="checkwellReport" value="Abandoned Well"/>Drilling Pumping History...
					 <input type="file" name="abandata" multiple="multiple" id="aban_id" style="font-size: 12px;font-style: italic;width: 168px;"/>
					 </span>
					 <span id="abanWellRepoSpan" style="margin-left: 0%; display: none; font-size: 14px; font-style: italic;">
					 <input type="checkbox" id="checkAbanWellRepo" name="reportCheck" value="abanwellRepoDoc"/>Well Report:
					 <input type="file" name="abanWellreport" multiple="multiple" id="abanWellreport" style="font-size: 12px;font-style: italic;width: 168px;"/>
					 </span>
					 </div>
					 </td>
				</tr>
			</table>
	<div id="btndiv_id" style="margin-top: 0.25%;align-content:center;">
    <input type="submit" value="Save"style="height: 20px; width: 100px;"id="click_me"/>
    <input id="reset" type="reset" value="Cancel" style="height: 20px; width: 100px; margin-left: 0px;"/></div>
			</div>
		 </form>
            </div>
            <!-- Upload Excel Sheet that contains Additional Groundwater Data -->
                        <div class="table_div item_li oprate_tech main-content" style="display: none;">
            <p style="text-align: center;margin-top: 1.1%; margin-left:-15%; font-weight: bold; font-size: 17px;">
              Store Detail Operation Well Data</p>
              		<form method="post" action="Upload" enctype="multipart/form-data"
			name="mform1" onsubmit="return(checkgwExcelform(this));">
			<div class="WCRFormDiv">
               <input id="uu_id" name="db_userIdII" type="text" value="<%=choice%>" style="display:none;">
			<div class="formup">
			<table border="0" class="xxxx" style="margin-left: 3%; margin-top: 1%;margin-bottom: 1%;">	
				<tr>
				<td class="td"></td>
				<td class="td" id="control_check" ><span style="font-size:22px; margin-left:-0.2em;margin-bottom: 1%;">Excel Sheet Data: </span></td>
				</tr>
				<tr>
					<td class="td" id="control_check"></td>
					<td style="white-space: nowrap;"><input type="checkbox" id="log1" name="aditionalGWlog" value="Operation_data" style="margin-left: 10.0em;"/> 
					<span style="margin-left: 11.1em;">Detail Operational Well Data: </span>
					<input type="file" name="operationalFile" multiple="multiple" id="file1" /></td>
					</tr>
					<tr>
					<td class="td"></td>
					<td style="white-space: nowrap;"><input type="checkbox" id="log5" name="aditionalGWlog" value="rehab_data" style="margin-left: 10.0em;"/>
					<span style="margin-left: 10.8em;">Rehablitation Data (Optional): </span>
					<input type="file" name="fileupload5" multiple="multiple" id="file5" /></td>
				</tr>
				<tr>
					<td class="td"></td>
					<td style="white-space: nowrap;"><input type="checkbox" id="log4" name="aditionalGWlog" value="chemical_data" style="margin-left: 10.0em;"/>
					<span style="margin-left: 6.5em;">Chemical Water Quality Data (Optional): </span>
					<input type="file" name="fileupload4" multiple="multiple" id="file4" /></td>
				</tr>
				<tr>
					<td class="td"></td>
					<td style="white-space: nowrap;"><input type="checkbox" id="log3" name="aditionalGWlog" value="physical_data" style="margin-left: 10.0em;"/>
					<span style="margin-left: 6.9em;">Physical Water Quality Data (Optional): </span>
					<input type="file" name="fileupload3" multiple="multiple" id="file3" /></td>
				</tr>
				<tr>
					<td class="td"></td>
					<td style="white-space: nowrap;"><input type="checkbox" id="log2" name="aditionalGWlog" value="microBio_data" style="margin-left: 10.0em;"/> 
					<span style="margin-left: 0.3em;">Biological & Radiogenic Water Quality Data (Optional): </span>
					<input type="file" name="fileupload2" multiple="multiple" id="file2" /></td>
				</tr>
				<tr style="display:none;">
					<td class="td">Prepared by:</td>
					<td><input type="text" id="U_id" name="U_id" value="<%=choice%>" /></td>
				</tr>
			</table>
			<span style="align-content:center; margin-top: 0.25%">
			<input type="submit" value="Save" style="height: 20px; width: 100px;"id="click_me"/>
			<input type="reset" value="Cancel" id="reset" style="height: 20px; width: 100px; margin-left: 0px;"/></span>
			</div>
			 </div>
		 </form>
            </div>
            <!--Update WCR Data-->
            <div class="table_div item_li updateWCR main-content" style="display: none;" id="displayUpdateContent">
            <p style="text-align: center;margin-top: 1.1%; margin-left:-15%; font-weight: bold; font-size: 17px;">
              Drilling Pump Test History Data Error Correction Page </p>
              <div id="wellIDforUpdate" style="display: none;"></div>
            <form action="updateWCR" method="post" name="WCRUpdateform" onsubmit="return(chechWCRUpdate(this));">
            <input id="uu_id" name="db_userUpdateWCR" type="text" value="<%=choice%>" style="display:none;">
            <div class="WCRFormDiv">
            <div class="formup">
            <table id="updateContent" border="0" class="updateData">
            <thead>
            </thead>
            <tbody>
            </tbody>
            </table>
            <span style="align-content:center;margin-top: 0.25%;">
            <input type="submit" value="Confirm" style="height: 20px; width: 100px;"/>
            <input type="reset" value="Cancel" id="back_to_table" style="height: 20px; width: 100px;"></span>
            </div>
            </div>
            </form>
            </div>
            <!--Update WO Data-->
            <div class="table_div item_li updateOGWD main-content" style="display: none;" id="displayOGWUpdateContent">
            <p style="text-align: center;margin-top: 1.1%; margin-left:-15%; font-weight: bold; font-size: 17px;">
              Detail Operation Well Data Error Correction Page </p>
              <div id="wellIDforOGWCUpdate" style="display: none;"></div>
            <form action="updateGWOD" method="post">
            <div class="WCRFormDiv">
            <div class="formup">
            <table id="updateOGWContent" border="0" class="updateData">
            <thead>
           </thead>
            <tbody>
            </tbody>
            </table>
            <span style="align-content:center;margin-top: 0.25%;">
            <input type="submit" value="Confirm" style="height: 20px; width: 100px;"/>
            <input type="reset" value="Cancel" id="back_to_OGDtable" style="height: 20px; width: 100px;"></span>
            </div>
            </div>
            </form>
            </div>
            <!-- Spring Water Data Sheet-->
              <div class="table_div item_li pro_report main-content" style="display: none;">
              <p style="text-align: center;margin-top: 1.1%; margin-left:-15%; font-weight: bold; font-size: 17px;">
              Add Spring Water Data </p>
             <form method="post" action="Upload" enctype="multipart/form-data"
			name="mformSW" onsubmit="return(checkSWform(this));">
			<div class="WCRFormDiv">
			<input id="action1" name="action1" type="text" value="Geotechnical" style="display: none;">
             <input id="vaal" name="vaal" type="text" value="Corporation Project" style="display: none;">
              <input id="Na1" name="Na1" type="text" value="Defualt" style="display: none;">
              <input id="u_id" name="u_id" type="text" value="<%=choice%>" style="display: none;">
			<div class="formup">
			<table border="0" align="center" class="xxxx" style="margin-left: 3%;">
				<tr>
				<td>Location(<span  style="color:red;"> *</span>):</td>
				<td>
				<select id="fcenter" name="fcenter" class="form-control" style="width: 280px; height: 25px;">
				<option value="0" style="font-weight: bold;">--------------------Select Center----------------------</option>
					</select>
				</td>
				</tr>
				<tr>
				<td>Owner Name(<span  style="color:red;"> *</span>):</td>
				<td>
				<select id="fprocess" name="fprocess" class="form-control" style="width: 280px; height: 25px;"
				onchange="showprofield(this.options[this.selectedIndex].value);">
				<option value="0" style="font-weight: bold;">-----Select Process or Use Add New option---------</option>
				<option value="1" style="font-weight: bolder;">-----------------( Add New )-----------------</option>
					</select>
						<div id="div5"></div>
				</td>
				</tr>
				<tr>
					<td class="td">Contractor(<span  style="color:red;"> *</span>):</td>
					<td>
				<select id="drfin_fid" name="drfin_fid" class="form-control" style="width: 280px; height: 25px;">
			<option value="0" style="font-weight: bold;">-------Select Project or Add New Project------</option>
			<option value="1" style="font-weight: bolder;">-----------------( Add New )-----------------</option>
					</select>
					    <table>
						<tr>
						<td id="Id_pro"></td>
						<td>
						<select style=" display: none;width: 170px;height: 25px; margin-left: -2px;" id="repopro_id" name="repopro_Tname" class="form-control">
						<option value="0" style="font-weight: bold;">---- Select Project Type----</option>
						</select>
						</td>
						</tr>
						</table>
						<table>
						<tr>
						<td id="div_ptype"></td>
						<td>
						<select style=" display: none;width: 170px;height: 25px; margin-left: -2px;" id="boundary_id" name="rep_site" class="form-control">
						<option value="0" style="font-weight: bold;">---- Select Project Types Component----</option>
						</select>
						</td>
						</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td">Consultant (<span  style="color:red;"> *</span>):</td>
					<td style="white-space: nowrap;">
				<select id="ds_fid" name="ds_fid" class="form-control" style="width: 280px; height: 25px;">
			<option value="0" style="font-weight: bold;">-----Select Name or Use Add New option--------</option>
					</select>
					<div id="add_des_sup"></div>
						<select style="display: none;" id="super_desi" name="super_desi" class="form-control">
						<option value="0" style="font-weight: bold;">------- Select Data Type------</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="td">Springer Name(<span  style="color:red;"> *</span>):</td>
					<td style="white-space: nowrap;">
				<select id="br_fid" name="br_def" class="form-control" style="width: 280px; height: 25px;">
			<option value="0" style="font-weight: bold;">-----Select Name or Use Add New option--------</option>
					</select>
					<select style="display: none;" id="super_default" name="br_id_defualt" class="form-control">
						<option value="0" style="font-weight: bold;">------- Select Data Types Sub Branch------</option>
						</select>
					 <div id="Branchname" style="width:280px;"></div>
					</td>
				</tr>
               <tr>
					<td class="td" id="control_check">Spring Size(<span  style="color:red;"> *</span>): </td>
					<td style="white-space: nowrap;"><input type="checkbox" id="in_dr" name="log" value="Draft"/>
					<span style="margin-left: 0.0em;"> Further Information: </span>
					<input type="file" name="fileuploaddr" multiple="multiple" id="indrfile" /></td>
				</tr>
				<tr>
					<td class="td"></td>
					<td style="white-space: nowrap;"><input type="checkbox" id="in_fn" name="log" value="Final"/> 
					<span style="margin-left: 0.0em;"> Additional Materials: </span>
					<input type="file" name="fileuploadfin" multiple="multiple" id="inffile" /></td>
				</tr>
				<tr>
				<td>Found Date:</td>
				<td> <input type="date" id="start" name="pr_date" value="1950-07-22" min="1960-01-01" max="2090-12-31"></td>
				</tr>
				<tr style="display: none;">
					<td class="td">Prepared by:</td>
					<td><input type="text" id="U_id" name="U_id" value="<%=choice%>" style="height: 25px;" /></td>
				</tr>
				<tr>
				<td></td>
					<td class="td"><input id="reset" type="reset" style="height: 20px; width: 100px; margin-left: 0px; margin-top: 2px;"/>
					<input type="submit" value="Upload" style="height: 20px; width: 100px; margin-left: 5px;"/></td>
				</tr>
			</table>
			</div>
			</div>
		</form>
            </div>
            <div id="hold_response" class="main-content messageContainer" style="display: none;">
            <form action="successPage" method="get">
            <div class="WCRFormDiv" style="margin-left: -1.55%; margin-top: 0.25%;">
             <%
            String return_rpo="";
            String defualt_vaalue="";
 	 if(storedWellInfo!=null){
 		 for(File getfile:storedWellInfo){
 			 if(getfile.wellIndexforReport!=null){
 				if(getfile.wellIDforReport >= 1){
 			 		 return_rpo=getfile.wellIndexforReport;
 			 		 defualt_vaalue=getfile.WellWCRSubDiscription;	
 			 			 }
		 				else if(getfile.wellIDforReport==0001){
				 				return_rpo=getfile.wellIndexforReport; 
				 				defualt_vaalue=getfile.WellWCRSubDiscription;	
				 			 }
 			 			 else if(getfile.wellIDforReport==0){
 			 				return_rpo=getfile.wellIndexforReport; 
 			 				defualt_vaalue=getfile.WellWCRSubDiscription;	
 			 			 }
 			 }
 			 else if(getfile.regnam!=null){
  				if(getfile.regid > 1){
  	 				return_rpo=getfile.regnam;
  	 				defualt_vaalue=getfile.dsicription;
  	 			 }else if(getfile.regid==0){
  	 				return_rpo=getfile.regnam;
  	 				defualt_vaalue=getfile.dsicription;
  	 			 }else if(getfile.regid==0001){
  	 				return_rpo=getfile.regnam;
  	 				defualt_vaalue=getfile.dsicription;
  	 			 }else if(getfile.regid==00001){
  	 				return_rpo=getfile.regnam;
  	 				defualt_vaalue=getfile.dsicription;
  	 			 }
  			 }
 		} 
 		 %>
 		 <label style="font-weight: bold; font-size: 27px;">Status</label><br>
 		 <p id="jspContent"><label style="font-weight: bold; font-size: 19px;"><%=return_rpo%></label><br>
 		 <label style="font-weight: normal; font-size: 16px;"><%=defualt_vaalue %></label></p>
 		 <%
 	 }
	 if(updateMessage!=null){
		 for(File updateResponse:updateMessage){
			 if(updateResponse.updateWCRMessage!=null){
				 if(updateResponse.wellIdUWCRMessage!=0){
	 	 				return_rpo=updateResponse.updateWCRMessage;
	 	 				defualt_vaalue=updateResponse.updateWCRDicussion;
	 	 				}
				 else if(updateResponse.wellIdUWCRMessage==0){
					 return_rpo=updateResponse.updateWCRMessage;
					 defualt_vaalue=updateResponse.updateWCRDicussion;
					 }	 
				 }
			 else if(updateResponse.updateGOPUMessage!=null){
				 if(updateResponse.wellIdGWOUMessage!=0){
	 	 				return_rpo=updateResponse.updateGOPUMessage;
	 	 				defualt_vaalue=updateResponse.updateGWODicussion;
	 	 				}
				 else if(updateResponse.wellIdGWOUMessage==0){
					 return_rpo=updateResponse.updateGOPUMessage;
					 defualt_vaalue=updateResponse.updateGWODicussion;
					 }
			 }
 				
			 } 
		 %>
 		<label style="font-weight: bold; font-size: 27px;">Status</label><br>
 		 <p id="jspContent"><label style="font-weight: bold; font-size: 19px;"><%=return_rpo%></label><br>
 		 <label style="font-weight: normal; font-size: 16px;"><%=defualt_vaalue %></label></p>
 		 <%
		 }
 	 %>
 	 </div>
 	 </form>
            </div>
           </div>           
        <script type="text/javascript" src="dist/homepag_dynamic_tabledisplay.js"></script>
        <script type="text/javascript" src="dist/jquery-3.4.1.min.js"></script>
        <script type="text/javascript" src="dist/hompage_script.js"></script>
     <script>
     document.addEventListener('DOMContentLoaded', function() {
    	    // Select all div elements that are containers for the p elements
    	    const containerDivs = document.querySelectorAll('.messageContainer'); // Replace with your actual class or selector

    	    containerDivs.forEach(div => {
    	        const pElement = document.getElementById("jspContent"); // Get the p element within the current div

    	        if (pElement && pElement.textContent.trim() !== '') {
    	            // If p element exists and its text content is not empty (after trimming whitespace)
    	            div.style.display = 'block';
    	        }
    	    });
    	});
     const checkboxes = document.getElementById("checkBoxes");
     function showCheckboxes(){
    	//document.getElementById("checkBoxes").classList.add("show");
         if (show){
             checkboxes.style.display = "block";
             show = false;
         } else {
             checkboxes.style.display = "none";
             show = true;
         }
     }
     var datePickerId=document.getElementById("maxDateC");
     var datePickerIdS=document.getElementById("maxDateS");
     var pumpInstDate=document.getElementById("pumpInstDate");
     var pumpreplaceD=document.getElementById("pumpreplaceD");
     
     datePickerId.max = new Date().toISOString().split("T")[0];
     datePickerIdS.max = new Date().toISOString().split("T")[0];
     pumpInstDate.max=new Date().toISOString().split("T")[0];
     pumpreplaceD.max=new Date().toISOString().split("T")[0];
     //main page nested dropdown Id
     const ownerAddress=document.getElementById("ownerAddress");
     const ownerAddressCont=document.getElementById("owendByID");
     const table1 = document.getElementById("abandoned");
	 const table2 = document.getElementById("productive");
     //nested Page dropdown Id
     const PumpStatus = document.getElementById("pumpStatusId");
     const changedPumpCapacity = document.getElementById("tablepumpStatus");
     const powerSytem=document.getElementById("powerId");
     const tablePower = document.getElementById("powerStatus");
     const scadaSytem=document.getElementById("scadaId");
     const tableScada = document.getElementById("scadaStatus");
     const obsPIPSytem=document.getElementById("obpipId");
     const tableObserPIP = document.getElementById("obPIPStatus");
     const wellHeadId = document.getElementById("wellHId");
     const poorHeadRTable = document.getElementById("poorHeadRTable");
     const functionalWell=document.getElementById("prodSId");
     const functionalWellstatus=document.getElementById("funcWellStatusT");
 
     var margin_btndivid=document.getElementById("btndiv_id");
     var display_abanSpan=document.getElementById("aban_span");
     var display_pwellSpan=document.getElementById("pwell_span");
     var abanwellreportControl=document.getElementById("abanWellReport");
     var prowellreportControl=document.getElementById("proWellReport");
     var pumpSystemDiv=document.getElementById("pumpSytemdiv");
     var abanchangeText=document.getElementById("abanchangeText");
     var prochangeText=document.getElementById("prochangeText");
     const tablepumpChangeStatus = document.getElementById("tablepumpChangeStatus");
     const addParametersId = document.getElementById("addParametersId");
     const addtionalParamid = document.getElementById("addtionalParamid");
     const city_ZoneID = document.getElementById("city_ZoneID");
     const selectedCZID = document.getElementById("selectedCZID");
     const abanWellRepoSpan = document.getElementById("abanWellRepoSpan");
     const avialableGenerator=document.getElementById("avialableGene");
     
     function addtionalParamF(){
			if(addtionalParamid.value!=="0"){
				addParametersId.style.display="block";
			} 
			else{
				addParametersId.style.display="none";
			}
		 }
     function addtionalParamF2(){
		 if(addtionalParamid.value === "City_OroZone"){
				city_ZoneID.style.display="block";
				addParametersId.style.display="block";
			} 
			else{
				selectedCZID.value = "0";
				city_ZoneID.style.display="none";
			}
		 }
	 function oenerAdderssControllerF(){
			if(ownerAddressCont.value!=="0"){
				ownerAddress.style.display="table";
			} 
			else{
				ownerAddress.style.display="none";	
			}
		 }
	 function poorHeadReasonF(){
			if(wellHeadId.value==="Poor"){
				poorHeadRTable.style.display="table";
			} 
			else{
				poorHeadRTable.style.display="none";	
			}
		 }
	 function pumpSystemCon(){
		if(PumpStatus.value==="Installed"){
			changedPumpCapacity.style.display="table";
			tablepumpChangeStatus.style.display="none";
		} 
		else if(PumpStatus.value==="Changed"){
			tablepumpChangeStatus.style.display="table";
			changedPumpCapacity.style.display="none";
		} 
		else {
			changedPumpCapacity.style.display="none";
			tablepumpChangeStatus.style.display="none";
		}
	 }
	 function fuctionalWellSatatus() {
	      	if(functionalWell.value === "Functional"){
	      		functionalWellstatus.style.display = "table";
	      	}else{
	      		functionalWellstatus.style.display = "none";
	      	}
	  	}
	 
     function obsPiPChangeCon() {
      	if(obsPIPSytem.value === "YES"){
      	    tableObserPIP.style.display = "table";
      	}else{
      		tableObserPIP.style.display = "none";
      	}
  	}
     function powerSystemCon(){
     	if(powerSytem.value === "Generator Set"){
     		tablePower.style.display = "table";
     		avialableGenerator.style.display = "none";
     	}else if(powerSytem.value === "National Grid"){
     		avialableGenerator.style.display = "table";
     		tablePower.style.display = "none";
     	}
     	else{
     		tablePower.style.display = "none";
     		avialableGenerator.style.display = "none";
     	}
 	}
     function scadaSystemCon() {
    	if(scadaSytem.value === "YES"){
    		getScadaValue=tableScada.style.display = "table";
    	}else{
    		tableScada.style.display="none";
    	}
	}
     function togleTable(){
 			//logic here...
 			const dropdown = document.getElementById("wellStatus");
 			if (dropdown.value === "Abandoned") {
 		        table1.style.display = "table"; // Show the table
 		        abanwellreportControl.style.display = "block";
 		        abanWellRepoSpan.style.display = "inline-block";
 		        prowellreportControl.style.display = "none";
 		        pumpSystemDiv.style.display = "none";
 		        abanchangeText.value="Add DPTH Data:"
 		        abanchangeText.style.color="black";
 		        abanwellreportControl.style.marginLeft = '-56%';
 		        table2.style.display = "none"; // Hide the table
 		        display_pwellSpan.style.display = "none";
 		        margin_btndivid.style.marginTop = '0%';
		    	//display_pumpSpan.style.display = "none";
 		      } else if(dropdown.value === "Productive"){
 	 		    table2.style.display = "table"; // Show the table
 	 		    prowellreportControl.style.display = "block";
 	 		    pumpSystemDiv.style.display = "inline-block";
 	 		    abanWellRepoSpan.style.display = "none";
 	 		    //display_pwellSpan.style.display = "block";
 		        table1.style.display = "none"; // Hide the table
 		        display_abanSpan.style.display = "none";
 		        abanwellreportControl.style.display = "none";
 		        prowellreportControl.style.marginLeft = '-23%';
 		        prochangeText.value="Add DPTH Data:"
 		        prochangeText.style.color="black";
 		        
 		       //margin_btndivid.style.marginTop = '1.8%';
 		     
 		      }
 		      else{
 		    	table1.style.display = "none"; // Hide the table
 		    	table2.style.display = "none"; // Hide the table
 		    	margin_btndivid.style.marginTop = '0.25%';
 		    	display_pwellSpan.style.display = "none";
 		    	display_abanSpan.style.display = "none";
 		    	abanwellreportControl.style.display = "none";
 		    	abanWellRepoSpan.style.display = "none";
 		    	prowellreportControl.style.display = "none";
 		    	pumpSystemDiv.style.display = "none";
 		      }
     }
     //Add well Report controller (Abandoned)
     document.addEventListener('DOMContentLoaded', function() {
    	 abanchangeText.addEventListener('click', function() {
            if (display_abanSpan.style.display === 'none') {
            	display_abanSpan.style.display = 'inline-block'; // Or 'flex', 'grid', etc.
            	abanchangeText.value="Clear Content:"
            	abanchangeText.style.color="red";
            	abanwellreportControl.style.marginLeft = '-23%';
            	display_abanSpan.style.marginLeft = '-1.5%';
            } else {
            	display_abanSpan.style.display = 'none';
            	abanchangeText.value="Add DPTH Data:"
            	abanchangeText.style.color="black";
            	abanwellreportControl.style.marginLeft = '-56%';
            }
        });
    });
     //Add well Report controller (Productive)
     document.addEventListener('DOMContentLoaded', function() {
    	 prochangeText.addEventListener('click', function() {
            if (display_pwellSpan.style.display === 'none') {
            	display_pwellSpan.style.display = 'inline-block'; // Or 'flex', 'grid', etc.
            	display_pwellSpan.style.marginLeft = '-1.4%';
            	prowellreportControl.style.marginLeft = '3.1%';
            	prochangeText.value="Clear Content:"
            	prochangeText.style.color="red";
            } else {
            	display_pwellSpan.style.display = 'none';
            	prochangeText.value="Add DPTH Data:"
            	prochangeText.style.color="black";
            	prowellreportControl.style.marginLeft = '-22.1%';
            }
        });
    });
    	  
    function controlTopMargin(){
    	if((PumpStatus.value==="Installed"||PumpStatus.value==="Changed") && scadaSytem.value === "YES" && powerSytem.value === "Generator Set" && 
       			functionalWell.value==="Functional"){
    		table2.style.marginTop = '-36%';
    		}
    	else if((PumpStatus.value==="Installed"||PumpStatus.value==="Changed") && scadaSytem.value === "YES" && powerSytem.value === "National Grid" && 
       			functionalWell.value==="Functional"){
    		table2.style.marginTop = '-33.423%';
    		}
    	else if((PumpStatus.value==="Installed"||PumpStatus.value==="Changed") && powerSytem.value === "Generator Set" && scadaSytem.value === "YES"){
     		 table2.style.marginTop = '-29.855%';
     		 }
    	 else if((PumpStatus.value==="Installed"||PumpStatus.value==="Changed")&& powerSytem.value ==="Generator Set" && 
    			 functionalWell.value==="Functional"){
    		 table2.style.marginTop = '-33.423%'; 
    		 }
    	 else if((PumpStatus.value==="Installed"||PumpStatus.value==="Changed")&& powerSytem.value ==="National Grid" && 
    			 functionalWell.value==="Functional"){
    		 table2.style.marginTop = '-31.023%'; 
    		 }
    	 else if((PumpStatus.value==="Installed"||PumpStatus.value==="Changed") && scadaSytem.value === "YES" && functionalWell.value==="Functional"){
    		 table2.style.marginTop = '-31.010%'; 
    		 }
    	 else if(scadaSytem.value === "YES" && (PumpStatus.value==="Installed"||PumpStatus.value==="Changed")){
      		 table2.style.marginTop = '-30.820%'; 
      		 }
    	else if(powerSytem.value === "Generator Set" && (PumpStatus.value==="Installed"||PumpStatus.value==="Changed")){
      		 table2.style.marginTop = '-33.025%';
      		 }
    	else if(functionalWell.value==="Functional" && (PumpStatus.value==="Installed"||PumpStatus.value==="Changed")){
    		table2.style.marginTop = '-28.585%';
    		}
    	else if(functionalWell.value==="Functional" && powerSytem.value === "Generator Set"){
    		 table2.style.marginTop = '-24.125%'; 
    		 }
    	else if(functionalWell.value==="Functional" && scadaSytem.value === "YES"){
    		 table2.style.marginTop = '-20%'; 
    		 }
    	else if(powerSytem.value === "Generator Set" && scadaSytem.value === "YES"){
         	table2.style.marginTop = '-22.369%';
         	}
    	else if(PumpStatus.value==="Installed" || PumpStatus.value==="Changed"){
   		  table2.style.marginTop = '-25.99%';
   		  }
    	else if(scadaSytem.value==="YES"){
   		  table2.style.marginTop = '-17.325%';
   		  }
    	else if(powerSytem.value==="Generator Set"){
   		  table2.style.marginTop = '-19.539%';
   		  }
    	else if(functionalWell.value==="Functional"){
   		  table2.style.marginTop = '-17.095%';
   		  }
    	else{
    		 table2.style.marginTop = '-14.50%'; // preveous -39.25%
    		 }
   	  }
     //control Well Location Display
     function locationDisplay(){
    	 const locationParameter = document.getElementById("regionEn");
    	 const regionCityName= document.getElementById("Id_div");
    	 const regionCityId= document.getElementById("regionCityID");
    	 const subCityZoneName= document.getElementById("pr_type");
    	 const cubCityZoneId= document.getElementById("subcityzoneId");
    	 const woredaName= document.getElementById("pr_client")
    	 const localName= document.getElementById("div1")
    	 if(locationParameter.value === "0"){
    		 regionCityName.style.display = "none";
    		 regionCityId.style.display = "none";
    		 subCityZoneName.style.display = "none";
    		 cubCityZoneId.style.display = "none";
    		 woredaName.style.display = "none";
    		 localName.style.display = "none"; 
    	     }else{
    	    	 regionCityName.style.display = "block";
        		 regionCityId.style.display = "block";
        		 subCityZoneName.style.display = "block";
        		 cubCityZoneId.style.display = "block";
        		 woredaName.style.display = "block";
        		 localName.style.display = "block"; 	 
    	     } 
     }
     var pro_dataResponse=document.getElementById("gtech_id");
     if (pro_dataResponse.addEventListener) {
    	 pro_dataResponse.addEventListener("click", function() {
	     document.getElementById("hold_response").style.display='none';	
	        }, false);}
     /* var pro_reportResponse=document.getElementById("pro_repo_id");
     if (pro_reportResponse.addEventListener) {
    	 pro_reportResponse.addEventListener("click", function() {
	      document.getElementById("hold_response").style.display='none';	
	        }, false);} */
     var mange_otherdoc=document.getElementById("mange_otherdoc");
     if (mange_otherdoc.addEventListener) {
    	 mange_otherdoc.addEventListener("click", function() {
	      document.getElementById("hold_response").style.display='none';	
	        }, false);}
     var mange_addtional=document.getElementById("mange_addtional");
     if (mange_addtional.addEventListener) {
    	 mange_addtional.addEventListener("click", function() {
	      document.getElementById("hold_response").style.display='none';	
	        }, false);}
     
     var manage_rawdata=document.getElementById("mange_rawdata");
     if (manage_rawdata.addEventListener) {
    	 manage_rawdata.addEventListener("click", function() {
	      document.getElementById("hold_response").style.display='none';	
	        }, false);}
     var pro_operational_id=document.getElementById("operational_id");
     if (pro_operational_id.addEventListener) {
    	 pro_operational_id.addEventListener("click", function() {
	      document.getElementById("hold_response").style.display='none';	
	        }, false);}
     
     
     /*Total station or GPS file Checke box controller */
      function Gps_checkbx(check_gps){
    	  check_gps=document.getElementById("log7");
    if(check_gps.checked == true){
		document.getElementById('GPS_file').innerHTML='<span style="margin-left: 5.3em;"> GPS data:</span>'
		+' <input type="checkbox" id="gps_data" name="ts_gps" value="GPS Data"/>'
	    +' <span class="validity"></span>';
	    document.getElementById('GPS_file').style.display="block";
	    document.getElementById('TS_file').innerHTML='<span style="margin-left: 2.0em;"> Total Station data:</span>'
		+' <input type="checkbox" id="TS_data" name="ts_gps" value="Total Station Data"/>'
		+' <span class="validity"></span>';
		document.getElementById('TS_file').style.display="block";	
	} 
    else{
    	document.getElementById('TS_file').innerHTML='';
    	document.getElementById('GPS_file').innerHTML='';
    	document.getElementById('GPS_file').style.display="none";
    	document.getElementById('TS_file').style.display="none";
    }
     }		
     
     function get_check(checkme){
    checkme=document.getElementById("log6");
    if(checkme.checked == true){
		document.getElementById('no_station').innerHTML='<span style="margin-left: 1.8em;"> Number of Station:</span>'
		+' <input type="number" id="st_no" name="st_no" style="width: 180px;height: 20px;" required min="1" max="999"/>'
	    +' <span class="validity"></span>';
	    document.getElementById('no_station').style.display="block";
	    document.getElementById('name_station').innerHTML='<span style="margin-left: 2.7em;"> Name of Station:</span>'
		+' <input type="text" id="st_name" name="st_name" style="width: 180px;height: 20px;" required minlength="6" maxlength="1000"/>'
		+' <span class="validity"></span>';
		document.getElementById('name_station').style.display="block";	
	} 
    else{
    	document.getElementById('name_station').innerHTML='';
    	document.getElementById('no_station').innerHTML='';
    	document.getElementById('no_station').style.display="none";
    	document.getElementById('name_station').style.display="none";
    }
     }		
     /*selection option clicking on button*/
    	 var toggler = document.getElementsByClassName("manage");
    	    var i;
    	    for (i = 0; i < toggler.length; i++) {
    	      toggler[i].addEventListener("click", function() {
    	        this.parentElement.querySelector(".sel1").classList.toggle("actives");
    	      });
    	    }
    	    </script>
         <script type="text/javascript" src="dist/jquery-3.4.1.min.js"></script>
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
    });
    </script> 
    <script>
    </script>
    <script type="text/javascript" src="scripts/scripts.js"></script>
</body>
</html>