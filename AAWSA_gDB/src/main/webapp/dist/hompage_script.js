/**
 * 
 */	
     var xmlHttp=new XMLHttpRequest();
     var xmlhttpdata=new XMLHttpRequest();
     var aReason=document.getElementById("aReason");
     var wellType=document.getElementById("wellType");
     var casingMaterId=document.getElementById("casingMaterId");
     var owendByID=document.getElementById("owendByID");
     var GHMetrology=document.getElementById("Inv_id");
     var geotech=document.getElementById("regionEn");
     var georegion=document.getElementById("fsource");
     document.getElementById("gtech_id").onclick = function(evt){
      		 if (typeof XMLHttpRequest != "undefined"){
      	         xmlHttp= new XMLHttpRequest();
      	         }
      	         else if (window.ActiveXObject){
      	         xmlHttp= new ActiveXObject("Microsoft.XMLHTTP");
      	         }
      	         if (xmlHttp==null){
      	         alert("Browser does not support XMLHTTP Request")
      	         return;
      	         }
      	var idfor_natio=document.getElementById("Na").value;
      	var urlt= "gWDBParam?Na="+idfor_natio;
      	xmlhttpdata.open("POST", urlt, true);
      	xmlhttpdata.onload = function () {
      	var pro_name= JSON.parse(xmlhttpdata.responseText);
      	     getGWdata(pro_name);
      	     getregionCity(pro_name);
      	     getWfield(pro_name);
      	     additionalWellfieldParam(pro_name);
      	     additionalWellOwnerParam(pro_name);
      	     additionalCasingMParam(pro_name)
      	     additionalWellTParam(pro_name);
      	     additionalAbanRParam(pro_name);
      	};
      	xmlhttpdata.send();		
     };	 
   function getGWdata(proname){
	var pro='';
	for(var i=0;i<proname.length;i++){
		if(proname[i].folderid!=0){
	pro+='<option value="' + proname[i].folderid + '">' + proname[i].raw_data_avialable + '</option>';	
		}
	}
	document.mform.fsource.options.length=0;	
	georegion.insertAdjacentHTML('beforeend',pro);	
   }  
   function getregionCity(proname){
		var pro='';
		for(var i=0;i<proname.length;i++){
			if(proname[i].regid!=0){
		pro+='<option value="' + proname[i].regid + '">' + proname[i].regnam + '</option>';	
			}
		}
		document.mform.regionEn.options.length=1;	
		geotech.insertAdjacentHTML('beforeend',pro);	
	   } 
   function getWfield(proname){
		var pro='';
			for(var i=0;i<proname.length;i++){
				if(proname[i].ptId!=0){
				pro+='<option value="' + proname[i].ptId + '">' + proname[i].ptName + '</option>';		
				}	
		}
		document.mform.Inv_id.options.length=1;	
		GHMetrology.insertAdjacentHTML('beforeend',pro);	
	   };  
   function additionalWellfieldParam(getheader){
	   var datadrop='';
	   for(var i=0; i< getheader.length; i++){
		   if(getheader[i].addwellFieldId!=0){
			datadrop+='<option value="' + getheader[i].addwellFieldId + '">' + getheader[i].addwellFieldName + '</option>';
			}}
			GHMetrology.insertAdjacentHTML('beforeend',datadrop);
			}
	function additionalWellOwnerParam(getheader){
	   var datadrop='';
	   for(var i=0; i< getheader.length; i++){
		   if(getheader[i].addwellOwnerId!=0){
			datadrop+='<option value="' + getheader[i].addwellOwnerName + '">' + getheader[i].addwellOwnerName + '</option>';
			}}
			document.mform.owendByID.options.length=5;
			owendByID.insertAdjacentHTML('beforeend',datadrop);
			}
	function additionalCasingMParam(getheader){
	   var datadrop='';
	   for(var i=0; i< getheader.length; i++){
		   if(getheader[i].addCasingMId!=0){
			datadrop+='<option value="' + getheader[i].addCasingMName + '">' + getheader[i].addCasingMName + '</option>';
			}}
			document.mform.casingMaterId.options.length=4;
			casingMaterId.insertAdjacentHTML('beforeend',datadrop);
			}
	function additionalWellTParam(getheader){
	   var datadrop='';
	   for(var i=0; i< getheader.length; i++){
		   if(getheader[i].addwellTId!=0){
			datadrop+='<option value="' + getheader[i].addwellTName + '">' + getheader[i].addwellTName + '</option>';
			}}
			document.mform.wellType.options.length=6;	
			wellType.insertAdjacentHTML('beforeend',datadrop);
			}
	function additionalAbanRParam(getheader){
	   var datadrop='';
	   for(var i=0; i< getheader.length; i++){
		   if(getheader[i].addAbanRId!=0){
			datadrop+='<option value="' + getheader[i].addAbanRName + '">' + getheader[i].addAbanRName + '</option>';
			}}
			document.mform.aReason.options.length=8;
			aReason.insertAdjacentHTML('beforeend',datadrop);
			}	
				        
  /* Access Data Investigated by Project Id*/
          var xml_getselectedGHM=new XMLHttpRequest();
       	document.getElementById("regionEn").onchange = function(ghm_event){
       		var id_regionCity=ghm_event.target.value;  
      
       		document.getElementById('Id_div').innerHTML='<span style="">Zone/ City:</span>';
       		document.getElementById('Id_div').style.display="block";
       		document.getElementById("regionCityID").style.display="block";
       		 
 if (typeof XMLHttpRequest != "undefined"){
      	         xml_getselectedGHM= new XMLHttpRequest();
      	         }
      	         else if (window.ActiveXObject){
      	         xml_getselectedGHM= new ActiveXObject("Microsoft.XMLHTTP");
      	         }
      	         if (xml_getselectedGHM==null){
      	         alert("Browser does not support XMLHTTP Request")
      	         return;
      	         }
   	var urlt= "regionCity?regionC="+id_regionCity;
   	xml_getselectedGHM.open("POST", urlt, true);
   	xml_getselectedGHM.onload = function () {
   	var pro_name= JSON.parse(xml_getselectedGHM.responseText);
   	get_ptName(pro_name);
   	};
   	xml_getselectedGHM.send();	
  } 
    var ptNameN=document.getElementById("regionCityID");
    function get_ptName(ptname){
	var pro='';
	for(var i=0;i<ptname.length;i++){
		if(ptname[i].regClassId!=0){
		pro+='<option value="' + ptname[i].regClassId + '">' + ptname[i].gerClassName + '</option>';		
		}
	}
	document.mform.regionCityID.options.length=1;
    ptNameN.insertAdjacentHTML('beforeend',pro);
   } 
   //Sub city Zone dropdown
       		var subCityZone=document.getElementById("subcityzoneId");
       		var subCityZoneXml= new XMLHttpRequest();
       		 document.getElementById("regionCityID").onchange=function(evnt){
			var regionCityValue=evnt.target.value;
			document.getElementById('pr_type').innerHTML='<span style="">Sub City/ Oromia zone:</span>';
       		document.getElementById('pr_type').style.display="block";
       		document.getElementById('subcityzoneId').style.display="block";
    		   if (typeof subCityZoneXml!= "undefined"){
           			subCityZoneXml= new XMLHttpRequest();
           	         }
           	         else if (window.ActiveXObject){
           	        	subCityZoneXml= new ActiveXObject("Microsoft.XMLHTTP");
           	         }
           	         if (subCityZoneXml==null){
           	         alert("Browser does not support XMLHTTP Request")
           	         return;
           	         }
    		      	var urlt="subcityZone?cityZone_id="+regionCityValue;
    		      	subCityZoneXml.open("POST", urlt, true);
    		      	subCityZoneXml.onload=function(){
    		  	        var sczdata=JSON.parse(subCityZoneXml.responseText);
    		  	      subCityZoneDropdoun(sczdata);
    		  	        	 }
    		      	subCityZoneXml.send();
    		    };
    	   function subCityZoneDropdoun(pOtput_data){
			   var pro_O='';
			   for(var i=0;i<pOtput_data.length;i++){
				if(pOtput_data[i].scz_Id!=0){
		pro_O+='<option value="' + pOtput_data[i].scz_Id + '">' + pOtput_data[i].scz_name + '</option>';
		}      
			   }
			document.mform.subcityzoneId.options.length=1;	
			subCityZone.insertAdjacentHTML('beforeend',pro_O);  
		   }
		document.getElementById("subcityzoneId").onchange=function(evnt){
       	document.getElementById('div1').style.display="table";
		   }
//degree grid controller
var UTMLatLong="(Lat, Long, Z)";
document.getElementById('well_pos').innerHTML='<label>Position</label><label id="utmLaLoControl">'+UTMLatLong+': </label>';
   		document.getElementById('well_pos').style.display="block";	        
		 	  	
 	/*Access Well ComplarionReport Data*/
 	var access_del_modefy=document.getElementById("accessby_userId");
 	var xmldelmod_access=new XMLHttpRequest();
 	document.getElementById("mange_rawdata").onclick = function(evt){
 		if (typeof xmldelmod_access != "undefined"){
 			xmldelmod_access= new XMLHttpRequest();
 	         }
 	         else if (window.ActiveXObject){
 	        	xmldelmod_access= new ActiveXObject("Microsoft.XMLHTTP");
 	         }
 	         if (xmldelmod_access==null){
 	         alert("Browser does not support XMLHTTP Request")
 	         return;
 	         }
 	         var access_user_id=document.getElementById("user_idWCR").value;
 	         var url="access_mod_del?access_id="+access_user_id;
 	        xmldelmod_access.open("POST", url,true);
 	       xmldelmod_access.onload=function(){
 	        var prodata=JSON.parse(xmldelmod_access.responseText);
 	       access_dele_modefy(prodata);
 	        	 }
 	      xmldelmod_access.send();
 	         }
 	//Access by well Index
var XMLRequestByWellIndex=new XMLHttpRequest();
 document.getElementById('DPTHDwellIndex').onchange=function(element){
	 var wellIndexName=element.target.value
          //alert(nonc_id); 
	 if (typeof XMLRequestByWellIndex != "undefined"){
		 XMLRequestByWellIndex= new XMLHttpRequest();
       }
       else if (window.ActiveXObject){
    	   XMLRequestByWellIndex = new ActiveXObject("Microsoft.XMLHTTP");
       }
       if (XMLRequestByWellIndex == null){
       alert("Browser does not support XMLHTTP Request")
       return;
       }
    var access_user_id=document.getElementById("user_idWCR").value;
    if(access_user_id!==""){
	var url1="accessByWellIndex?wellIndex="+wellIndexName+"&userId="+access_user_id;
	XMLRequestByWellIndex.open("POST", url1, true);
	XMLRequestByWellIndex.onload= function (){
	var wellProfile = JSON.parse(XMLRequestByWellIndex.responseText);	
	wellProfileByWellIndex(wellProfile);}
	XMLRequestByWellIndex.send();
	}
}
 	function access_dele_modefy(datafrom){
 		var ret='';
 	for(var i=0; i<datafrom.length; i++){
    	if(datafrom[i].well_ID!==0){
    		ret+='<tr><td data-Eco="Spa" style="display:none;"><div class="long">'+datafrom[i].well_ID+'</div></td>'+
    		      '<td data-Eco="Spa"><div class="long">' + datafrom[i].regionNameUpdate + '</div></td>'+
   		          '<td data-Eco="Cat"><div class="long">' + datafrom[i].wellFieldUpdate + '</div></td>'+
   		          '<td data-Eco="Clu"><div class="long">' + datafrom[i].ownerGroupUpdate + '</div></td>'+
   		          '<td data-Eco="Yea"><div class="long">' + datafrom[i].OunerNameUpdate + '</div></td>'+
   		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].wellIndexUpdate+ '</div></td>'+
   		          '<td data-Eco="Bas"><div class="long">' + datafrom[i].wellCodeUpdate+ '</div></td>'+
   		          '<td data-Eco="Bas"><div class="long">' + datafrom[i].constractYearUpdate + '</div></td>'+
   		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].wellStatusUpdate+ '</div></td>'+
   		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].wellDepthUpdate+ '</div></td>'+
   		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].wellYieldUpdate+ '</div></td>'+
 		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].dataCollectCaseUpdate + '</div></td>'+
 		          '<td><a href="#" onclick="updateCWR(this)"><strong>Update</strong></a></td>'+
 		          '<td><a href="#" onclick="deleteCWR(this)"><strong>Delete</strong></a></td>'+
   		          '</tr>';
   		}
 	}
 	var table = document.getElementById("accessby_userId");
	for (var i = table.rows.length; i>0 ; i--) {   //iterate through rows
	  if(i>1) {
	    row = table.rows[i-1];
	    row.parentNode.removeChild(row);
	  }
	}
	access_del_modefy.insertAdjacentHTML('beforeend',ret);
 		}
 		var accessbyWellIndex=document.getElementById("accessby_userId");
 	 	function wellProfileByWellIndex(datafrom){
 		var ret='';
 	for(var i=0; i<datafrom.length; i++){
    		ret+='<tr><td data-Eco="Spa" style="display:none"><div class="long">'+datafrom[i].well_IDsearch4M+'</div></td>'+
    		      '<td data-Eco="Spa"><div class="long">' + datafrom[i].regionName+ '</div></td>'+
   		          '<td data-Eco="Cat"><div class="long">' + datafrom[i].wellField+ '</div></td>'+
   		          '<td data-Eco="Clu"><div class="long">' + datafrom[i].ownerGroup+ '</div></td>'+
   		          '<td data-Eco="Yea"><div class="long">' + datafrom[i].OunerName+ '</div></td>'+
   		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].wellIndex+ '</div></td>'+
   		          '<td data-Eco="Bas"><div class="long">' + datafrom[i].wellCode+ '</div></td>'+
   		          '<td data-Eco="Bas"><div class="long">' + datafrom[i].constractYear + '</div></td>'+
   		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].wellStatus+ '</div></td>'+
   		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].wellDepth+ '</div></td>'+
   		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].wellYieldM+ '</div></td>'+
 		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].dataCollectCase + '</div></td>'+
 		          '<td><a href="#" onclick="updateCWR(this)"><strong>Update</strong></a></td>'+
 		          '<td><a href="#" onclick="deleteCWR(this)"><strong>Delete</strong></a></td>'+
   		          '</tr>';
 	}
 	var table = document.getElementById("accessby_userId");
	for (var i = table.rows.length; i>0 ; i--) {   //iterate through rows
	  if(i>1) {
	    row = table.rows[i-1];
	    row.parentNode.removeChild(row);
	  }
	}
	accessbyWellIndex.insertAdjacentHTML('beforeend',ret);
 		}
 		//Update DPTH Data
 		var xmlaccessWCRforUpdate=new XMLHttpRequest();
  		function updateCWR(buttonHit){
			  if(typeof xmlaccessWCRforUpdate != "undefined"){
				  xmlaccessWCRforUpdate=new XMLHttpRequest();
			  }else if(window.ActiveXObject){
				  xmlaccessWCRforUpdate= new ActiveXObject("Microsoft.XMLHTTP");
			  }
			  if(xmlaccessWCRforUpdate==null){
				alert("Browser does not support XMLHTTP Request");
				return;
			  }
			 const row = buttonHit.parentNode.parentNode;
			 const firstCell = row.cells[0];
			 var diveValue=document.getElementById('wellIDforUpdate').value=firstCell.innerText;
			 var paratoSend="accessDatatoUpdate?well_id="+diveValue;
			 xmlaccessWCRforUpdate.open("POST",paratoSend,true);
			 xmlaccessWCRforUpdate.onload=function(){
				 var dataAccessed=JSON.parse(xmlaccessWCRforUpdate.responseText);
				 displayWCRContent(dataAccessed);
			 }
			 xmlaccessWCRforUpdate.send();
			 }
			  var updateContent=document.getElementById("updateContent");
			 function displayWCRContent(diveValue){
			 var ret='';
			 for(let i=0;i < diveValue.length; i++){
				 if(diveValue[i].well_idUpdate>0){
    		ret+='<tr style="display:none"><td>Well ID: </td><td><input type="text" id="CROwnerIdUId" name="CROwnerIdUName" value="'+diveValue[i].ownerID+'"/></td></tr>'+
    		      '<tr style="display:none"><td>Well ID: </td><td><input type="text" id="CRGeolocUId" name="CRGeolocUName" value="'+diveValue[i].geolocID+'"/></td><td></td><td></td></tr>'+
    		      '<tr style="display:none"><td>Well ID: </td><td><input type="text" id="CRwellIdUId" name="CRwellIdUName" value="'+diveValue[i].well_idUpdate+'"/></td><td></td><td></td></tr>'+
    		      '<tr><td>Well Index: </td><td><input type="text" id="CRwellIndexUId" name="CRwellIndexUName" value="'+diveValue[i].wellIndexUpdate+'" readonly/></td>'+
    		      '<td> <span style="margin-left: 30px;">Main Aquifer:</span></td><td><input type="text" id="CRmainaquiferUId" name="CRmainaquiferName" value="'+diveValue[i].mainAquiferUp+'"></td></tr>'+
    		      '<tr><td>Well Code: </td><td><input type="text" id="CRwellCodeUId" name="CRwellCodeUName" value="'+diveValue[i].wellCodeUpdate+'"/></td>'+
    		      '<td> <span style="margin-left: 30px;">Well Type: </span></td><td><input type="text" id="CRwellTypeId" name="CRwellTypeName" value="'+diveValue[i].wellType+'"></td></tr>'+
   		          '<tr><td>Easting: </td><td><input type="text" id="CREastingUId" name="CRweEastingUName" value="'+diveValue[i].easting+'"/></td>'+
   		          '<td><span style="margin-left: 30px;"> Abandoned Reason:</span></td><td><input type="text" id="CRabanWellRId" name="CRabanRWellName" value="'+diveValue[i].abandonedReason+'"></td></tr>'+
   		          '<tr><td>Northing: </td><td><input type="text" id="CRNorthingUId" name="CRNorthingUName" value="'+diveValue[i].northing+'"/></td>'+
   		          '<td><span style="margin-left: 30px;">Sealed (YES/ NO): </span></td><td><input type="text" id="CRSealedYNId" name="CRsealedYNName" value="'+diveValue[i].sealedYN+'"></td></tr>'+
   		          '<tr><td>Longitude: </td><td><input type="text" id="CRLongTUId" name="CRLongTUName" value="'+diveValue[i].longT+'"/></td>'+
   		          '<td><span style="margin-left: 30px;">Sealed Date:</span></td><td><input type="text" id="CRSealedDateId" name="CRSealedDateName" value="'+diveValue[i].dateSealed+'"></td></tr>'+
   		          '<tr><td>Latitude: </td><td><input type="text" id="CRLatId" name="CRLatUName" value="'+diveValue[i].lat+'"/></td>'+
   		          '<td><span style="margin-left: 30px;">Functional Well Condition:</span></td><td><input type="text" id="CRfuncWellConId" name="CRfunWellConName" value="'+diveValue[i].functionWellCon+'"></td></tr>'+
   		          '<tr><td>Elevation: </td><td><input type="text" id="CRElevationUId" name="CRElevationUName" value="'+diveValue[i].elivation+'"/></td>'+
   		          '<td><span style="margin-left: 30px;">Pump Status: </span></td><td><input type="text" id="CRpumpStatusID" name="CRpumpstatusName" value="'+diveValue[i].pumpStatusUpdate+'"/></td></tr>'+
   		          '<tr><td>Owner Category: </td><td><input type="text" id="CRSOwnerCatId" name="CRSOwnerCatName" value="'+diveValue[i].ownerCat+'"/></td>'+
   		          '<td><span style="margin-left: 30px;">Pump Installed Date:</span></td><td><input type="text" id="CRinstalledDateID" name="CRpumpInstalledDateName" value="'+diveValue[i].pumpInstalledDate+'"/></td></tr>'+
   		          '<tr><td>Well Owner Company Name:  </td><td><input type="text" id="CROwnerCUId" name="CROwnerCUName" value="'+diveValue[i].userCompany+'"/></td>'+
   		          '<td><span style="margin-left: 30px;">Pumping Capacity(kw)</span></td><td><input type="text" id="CRpumpCapacityID" name="CRpumpCapacityName" value="'+diveValue[i].pumpCapacity+'"/></td></tr>'+
   		          '<tr><td>Well Owner Email: </td><td><input type="text" id="CREmailUId" name="CREmailUName" value="'+diveValue[i].functionCondition+'"/></td>'+
   		          '<td><span style="margin-left: 30px;">Pump Head(m)</span></td><td><input type="text" id="CRpumpHeadID" name="CRpumpHeadName" value="'+diveValue[i].pumpheadUpdate+'"/></td></tr>'+
   		          '<tr><td>Well Owner Phone: </td><td><input type="text" id="CRPhoneUId" name="CRPhoneUName" value="'+diveValue[i].pumpStatus+'"/></td>'+
   		          '<td><span style="margin-left: 30px;">Pump Position(m): </span></td><td><input type="text" id="CRpumpPosID" name="CRpumpPosiName" value="'+diveValue[i].pumpPositionUpdate+'"/></td></tr>'+
   		          '<tr><td>Construction Date: </td><td><input type="text" id="CRConstYUId" name="CRConstYUName" value="'+diveValue[i].constYearUpdate+'"/></td>'+
   		          '<td><span style="margin-left: 30px;">Discharge Rate(m3/hr)</span></td><td><input type="text" id="CRdischargeRateID" name="CRdischargeRateName" value="'+diveValue[i].dischargeRate+'"/></td></tr>'+
   		          '<tr><td>Drilling License: </td><td><input type="text" id="CRDrillingLUId" name="CRDrillingLUName" value="'+diveValue[i].drillingLicense+'"/></td>'+
   		          '<td><span style="margin-left: 30px;">Generator Status</span></td><td><input type="text" id="CRgeneratorstaID" name="CRgeneratorStaName" value="'+diveValue[i].gen_status+'"/></td></tr>'+
   		          '<tr><td>Well Depth: </td><td><input type="text" id="CRWellDepthUId" name="CRwellDepthUName" value="'+diveValue[i].wellDepth+'"/></td>'+
   		          '<td><span style="margin-left: 30px;">Generator Capacity(kva)</span></td><td><input type="text" id="CRgeneratorCapaID" name="CRcapacityCapaName" value="'+diveValue[i].gen_power+'"/></td></tr>'+
   		          '<tr><td>Well Yield: </td><td><input type="text" id="CRwellYieldUId" name="CRwellYieldUName" value="'+diveValue[i].wellYieldUpdate+'"/></td>'+
   		          '<td><span style="margin-left: 30px;">SCADA Connection Status</span></td><td><select id="scadaID" name="SCADAName" class="form-control" '+
   		          ' style="width: 98%; height: 20.5px;">'+
   		          '<option value="Available">Available</option>'+
   		          '<option value="Not Avialable">Not Avialable</option></select></td></tr>'+
   		          '<tr><td>Static Water Level: </td><td><input type="text" id="CRSWLUId" name="CRSWLUName" value="'+diveValue[i].swlUpdate+'"/></td>'+
   		          '<td><span style="margin-left: 30px;">SCADA Functionality Status</span></td><td><select id="SCADACStatusID" name="SCADACStatusName" '+
   		          'class="form-control" style="width: 98%; height: 20.5px;">'+
   		          '<option value="Functional">Functional</option>'+
   		          '<option value="Partially Functional">Partially Functional</option>'+
   		          '<option value="Non Functional">Non Functional</option></select></td></tr>'+
   		          '<tr><td>Dynamic Water Level: </td><td><input type="text" id="CRDWLUId" name="CRDWLUName" value="'+diveValue[i].dwlUpdate+'"/></td>'+
   		          '<td><div style="margin-left: 30px;" id="errorHandlling"></div></tr>'+
   		          '<tr><td>SPecific Capacity: </td><td><input type="text" id="CRSpecificCUId" name="CRSpecificCUName" value="'+diveValue[i].specificCapacityUpdate+'"/></td></tr>'+
   		          '<tr><td>Large Casing ID(in): </td><td><input type="text" id="CRlargeCasingID" name="CRlargeCasingName" value="'+diveValue[i].largCasing+'"/></td></tr>'+
   		          '<tr><td>Telescopied Casing ID(in): </td><td><input type="text" id="CRtelCasingId" name="CRtelCasingName" value="'+diveValue[i].telescopCasing+'"/></td></tr>'+
   		          '<tr><td>Casing Material: </td><td><input type="text" id="CRCasingMaterId" name="CRCasingMaterName" value="'+diveValue[i].casingMaterial+'"/></td></tr>'
   		          ;
			 }}
   		          document.getElementById("displaywellCRD").style.display='none';
   		          document.getElementById("displayUpdateContent").style.display="block";
   		          document.getElementById("updateContent").innerHTML = '';
   		          updateContent.insertAdjacentHTML('beforeend',ret);
			 }
	//var access_del_respond=document.getElementById("accessby_userId");
	document.getElementById("back_to_table").onclick=function(){
		document.getElementById("displayUpdateContent").style.display='none';
		document.getElementById("displaywellCRD").style.display='block';
		}
			 
  	//delete well Completion Report
    var access_del_respond=document.getElementById("accessby_userId");
 	var xml_deleteCR=new XMLHttpRequest();
 	function deleteCWR(buttonClicked){
			const row=buttonClicked.parentNode.parentNode;
				var file_name=document.getElementById("hold_wellIndex").value=row.cells[5].innerText;
				var ORwell_id=document.getElementById("ORwell_id").value=row.cells[0].innerText;
 	 			if(confirm("If you Click Ok, "+file_name+" Well will be Deleted")){
 	 	 	 		//alert(this.rowIndex);
 	 	 			var access_user_id=document.getElementById("user_idWCR").value;
 	 	 	 		//alert(file_name);
 	 	 	 		var ulr_="delete_WOfile?well_id="+ORwell_id+"&user_id="+access_user_id;
 	 	 	 		xml_deleteCR.open("POST",ulr_,true);
 	 	 	 		xml_deleteCR.onload=function(){
 	 	 	 			var dataget=JSON.parse(xml_deleteCR.responseText);
 	 	 	 		updateWCRTableafterDelete(dataget);
 	 	 	 		}
 	 	 	 		xml_deleteCR.send();
 					return true;
 					}
 				else{
 					return false;	
 				}
 				return false;	
	}
	function updateWCRTableafterDelete(datafrom){
 		var ret='';
 		for(var i=0; i<datafrom.length; i++){
			 ret+='<tr><td data-Eco="Spa" style="display:none;"><div class="long">'+datafrom[i].well_IDsearch4M+'</div></td>'+
    		      '<td data-Eco="Spa"><div class="long">' + datafrom[i].regionName + '</div></td>'+
   		          '<td data-Eco="Cat"><div class="long">' + datafrom[i].wellField + '</div></td>'+
   		          '<td data-Eco="Clu"><div class="long">' + datafrom[i].ownerGroup + '</div></td>'+
   		          '<td data-Eco="Yea"><div class="long">' + datafrom[i].OunerName + '</div></td>'+
   		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].wellIndex+ '</div></td>'+
   		          '<td data-Eco="Bas"><div class="long">' + datafrom[i].wellCode+ '</div></td>'+
   		          '<td data-Eco="Bas"><div class="long">' + datafrom[i].constractYear + '</div></td>'+
   		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].wellStatus+ '</div></td>'+
   		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].wellDepth+ '</div></td>'+
   		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].wellYieldM+ '</div></td>'+
 		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].dataCollectCase + '</div></td>'+
 		          '<td><a href="#">Update</a></td>'+
 		          '<td><a href="#">Delete</a></td>'+
   		          '</tr>';
 	}
 	var table = document.getElementById("accessby_userId");
	for (var i = table.rows.length; i>0 ; i--) {   //iterate through rows
	  if(i>1) {
	    row = table.rows[i-1];
	    row.parentNode.removeChild(row);
	  }
	}
	access_del_respond.insertAdjacentHTML('beforeend',ret);
 		}
 /*Access Operational Report*/
 	var access_gw_data=document.getElementById("access_OpData");
 	var xmldelmod_access_GWDtata=new XMLHttpRequest();
 	document.getElementById("mange_otherdoc").onclick = function(evt){
		         	//var spa_id=evt.target.value;
		            //alert("clicked "+spa_id);
 		if (typeof xmldelmod_access_GWDtata != "undefined"){
 			xmldelmod_access_GWDtata= new XMLHttpRequest();
 	         }
 	         else if (window.ActiveXObject){
 	        	xmldelmod_access_GWDtata= new ActiveXObject("Microsoft.XMLHTTP");
 	         }
 	         if (xmldelmod_access_GWDtata==null){
 	         alert("Browser does not support XMLHTTP Request")
 	         return;
 	         }
 	         var access_user_id=document.getElementById("user_idOR").value;
 	         var url="access_mod_gwData?access_id="+access_user_id;
 	        xmldelmod_access_GWDtata.open("POST", url,true);
 	       xmldelmod_access_GWDtata.onload=function(){
 	        var prodata=JSON.parse(xmldelmod_access_GWDtata.responseText);
 	       access_dele_gwData(prodata);
 	        	 }
 	      xmldelmod_access_GWDtata.send();
 	         }
 	//Access by well Index
var XMLRequestGWByWellIndex=new XMLHttpRequest();
 document.getElementById('DOWDWellIndex').onchange=function(element){
	 var wellIndexName=element.target.value
          //alert(nonc_id); 
	 if (typeof XMLRequestGWByWellIndex != "undefined"){
		 XMLRequestGWByWellIndex= new XMLHttpRequest();
       }
       else if (window.ActiveXObject){
    	   XMLRequestGWByWellIndex = new ActiveXObject("Microsoft.XMLHTTP");
       }
       if (XMLRequestGWByWellIndex == null){
       alert("Browser does not support XMLHTTP Request")
       return;
       }
    var access_user_id=document.getElementById("user_idOR").value;
    if(access_user_id!==""){
	var url1="accessGWByWellIndex?wellIndex="+wellIndexName+"&userId="+access_user_id;
	XMLRequestGWByWellIndex.open("POST", url1, true);
	XMLRequestGWByWellIndex.onload= function (){
	var wellProfile = JSON.parse(XMLRequestGWByWellIndex.responseText);	
	GWdataByWellIndex(wellProfile);}
	XMLRequestGWByWellIndex.send();
	}
}
 	function access_dele_gwData(datafrom){
 		var ret='';
 	for(var i=0; i<datafrom.length; i++){
    	if(datafrom[i].well_IDsearch4MOp!==0){
    		ret+='<tr><td data-Eco="Spa" style="display:none"><div class="long">'+datafrom[i].well_IDsearch4MOp+'</div></td>'+
    		      '<td data-Eco="Spa"><div class="long">' + datafrom[i].regionName4MOp+ '</div></td>'+
   		          '<td data-Eco="Cat"><div class="long">' + datafrom[i].wellField4MOp+ '</div></td>'+
   		          '<td data-Eco="Clu"><div class="long">' + datafrom[i].ownerGroup4MOp+ '</div></td>'+
   		          '<td data-Eco="Yea"><div class="long">' + datafrom[i].OunerName4MOp+ '</div></td>'+
   		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].wellIndex4MOp+ '</div></td>'+
   		          '<td data-Eco="Bas"><div class="long">' + datafrom[i].wellCode4MOp+ '</div></td>'+
   		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].wellStatus4MOp+ '</div></td>'+
   		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].wellDepth4Mop+ '</div></td>'+
   		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].dataCollectCase + '</div></td>'+
 		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].dataCondition4MOp+'</div></td>'+
 		          '<td data-Eco="Bas"><div class="long">' + datafrom[i].lastUpdatedDate4MOp  + '</div></td>'+
 		          '<td><a href="#" onclick="updateOD(this)"><strong>Update</strong></a></td>'+
 		          '<td><a href="#" onclick="deleteOD(this)"><strong>Delete</strong></a></td>'+
   		          '</tr>';
   		}
 	}
 	var table = document.getElementById("access_OpData");
	for (var i = table.rows.length; i>0 ; i--) {   //iterate through rows
	  if(i>1) {
	    row = table.rows[i-1];
	    row.parentNode.removeChild(row);
	  }
	}
	access_gw_data.insertAdjacentHTML('beforeend',ret);
 		}
 			var accessbyGWDataWellIndex=document.getElementById("access_OpData");
 	 	function GWdataByWellIndex(datafrom){
 		var ret='';
 	for(var i=0; i<datafrom.length; i++){
    		ret+='<tr><td data-Eco="Spa" style="display:none"><div class="long">'+datafrom[i].well_IDGW+'</div></td>'+
    		      '<td data-Eco="Spa"><div class="long">' + datafrom[i].regionNameGW+ '</div></td>'+
   		          '<td data-Eco="Cat"><div class="long">' + datafrom[i].wellFieldGW+ '</div></td>'+
   		          '<td data-Eco="Clu"><div class="long">' + datafrom[i].ownerGroupGW+ '</div></td>'+
   		          '<td data-Eco="Yea"><div class="long">' + datafrom[i].OunerNameGW+ '</div></td>'+
   		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].wellIndexGW+ '</div></td>'+
   		          '<td data-Eco="Bas"><div class="long">' + datafrom[i].wellCodeGW+ '</div></td>'+
   		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].wellStatusGW+ '</div></td>'+
   		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].wellDepthGW+ '</div></td>'+
   		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].dataCollectCaseGW + '</div></td>'+
   		          '<td data-Eco="Bas"><div class="long">' + datafrom[i].dataCondition+ '</div></td>'+
   		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].lastUpdateGW+ '</div></td>'+
 		          '<td><a href="#" onclick="updateOD(this)"><strong>Update</strong></a></td>'+
 		          '<td><a href="#" onclick="deleteOD(this)"><strong>Delete</strong></a></td>'+
   		          '</tr>';
 	}
 	var table = document.getElementById("access_OpData");
	for (var i = table.rows.length; i>0 ; i--) {   //iterate through rows
	  if(i>1) {
	    row = table.rows[i-1];
	    row.parentNode.removeChild(row);
	  }
	}
	accessbyGWDataWellIndex.insertAdjacentHTML('beforeend',ret);
 		}
 		//Update Groundwater Operational Data
 		var xmlAccessGWODforUpdate=new XMLHttpRequest();				
 		function updateOD(buttonUpdate){
			 if(typeof xmlAccessGWODforUpdate!=="undefined"){
				 xmlAccessGWODforUpdate=new XMLHttpRequest(); 
			 }
			 else if(window.ActiveXObject){
				 xmlAccessGWODforUpdate = new ActiveXObject("Microsoft.XMLHTTP");	 
			 }
			 if(xmlAccessGWODforUpdate==null){
				 alert("Browser does not support XMLHTTP Request");
				 return;
			 } 
			var row = buttonUpdate.parentNode.parentNode;
			const cellText=row.cells[0].innerText
			var diveValue=document.getElementById('wellIDforOGWCUpdate').value=cellText;
			uri="accessGWODforUpdate?well_id="+diveValue;
			xmlAccessGWODforUpdate.open("POST",uri,true);
			xmlAccessGWODforUpdate.onload=function(){
				dataAccessed=JSON.parse(xmlAccessGWODforUpdate.responseText);
				displayOGWDContent(dataAccessed);
			}
			 xmlAccessGWODforUpdate.send(); 
		 }
		 function displayOGWDContent(diveValue){
			 document.getElementById("displayOGWUpdateContent").style.display="block";
			 var updateOGWContent=document.getElementById("updateOGWContent");
			 var ret='';
			 for(let i=0;i<diveValue.length;i++){
				 if(diveValue[i].well_idforODUpdate!==0){
    		ret+='<tr style="display:none"><td>enumID: </td><td><input type="text" id="enumID" name="enumIdName" value="'+diveValue[i].enumID+'"/></td></tr>'+
    		      '<tr style="display:none"><td>Well Code: </td><td><input type="text" id="wellID" name="wellIdName" value="'+diveValue[i].well_idforODUpdate+'"/></td></tr>'+
   		          '<tr><td>Well Index: </td><td><input type="text" id="wellIndexID" name="wellIndexName" value="'+diveValue[i].wellIndexforODUpdate+'" readonly/></td></tr>'+
   		          '<tr><td>Water Usege License: </td><td><input type="text" id="waterUseLID" name="waterUserLName" value="'+diveValue[i].waterUseLicense+'"/></td></tr>'+
   		          '<tr><td>Operation Start Date: </td><td><input type="text" id="opStartDID" name="opStartDName" value="'+diveValue[i].oprerationStartDate+'"/></td></tr>'+
   		          '<tr><td>Enumerator Company Name: </td><td><input type="text" id="enumCompanyID" name="enumCompanyName" value="'+diveValue[i].enumCompanyName+'"/></td></tr>'+
   		          '<tr><td>Enumrator Email: </td><td><input type="text" id="enumEmailID" name="enumEmailName" value="'+diveValue[i].enumEmail+'"/></td></tr>'+
   		          '<tr><td>Enumrator Phone: </td><td><input type="text" id="enumEmailPhoneID" name="enumEmailPhoneName" value="'+diveValue[i].enumPhone+'"/></td></tr>';
			 }}
   		          document.getElementById("displaywellOGWD").style.display='none';
   		          document.getElementById("updateOGWContent").innerHTML = '';
   		          updateOGWContent.insertAdjacentHTML('beforeend',ret);
		 }
		 document.getElementById("back_to_OGDtable").onclick=function(){
			 document.getElementById("displayOGWUpdateContent").style.display='none';
			 document.getElementById("displaywellOGWD").style.display='block';
			 }
		 
 		//delete well Operational Data
 	var access_del_respondOP=document.getElementById("access_OpData");
 	var xml_delete=new XMLHttpRequest();
 	function deleteOD(buttonDelete){
			var row=buttonDelete.parentNode.parentNode;	
				var file_name=document.getElementById("hold_wellIndexOP").value=row.cells[5].innerText;
				var hold_wellIdOR=document.getElementById("hold_wellIdOR").value=row.cells[0].innerText;
 	 			if(confirm("If you Click Ok, "+file_name+" Well will be Deleted")){
 	 	 	 		//alert(this.rowIndex);
 	 	 			var access_user_id=document.getElementById("user_idOR").value;
 	 	 	 		
 	 	 	 		//alert(file_name);
 	 	 	 		var ulr_="delete_ODfile?well_id="+hold_wellIdOR+"&user_id="+access_user_id;
 	 	 	 		xml_delete.open("POST",ulr_,true);
 	 	 	 		xml_delete.onload=function(){
 	 	 	 			var dataget=JSON.parse(xml_delete.responseText);
 	 	 	 		updateODTableafterDeletion(dataget);
 	 	 	 		}
 	 	 	 		xml_delete.send();
 					return true;
 					}
 				else{
 					return false;	
 				}
 				return false;	
	}
	 	function updateODTableafterDeletion(datafrom){
 		var ret='';
 	for(var i=0; i<datafrom.length; i++){
    		ret+='<tr><td data-Eco="Spa" style="display:none"><div class="long">'+datafrom[i].well_IDsearch4MOp+'</div></td>'+
    		      '<td data-Eco="Spa"><div class="long">' + datafrom[i].regionName+ '</div></td>'+
   		          '<td data-Eco="Cat"><div class="long">' + datafrom[i].wellField+ '</div></td>'+
   		          '<td data-Eco="Clu"><div class="long">' + datafrom[i].ownerGroup+ '</div></td>'+
   		          '<td data-Eco="Yea"><div class="long">' + datafrom[i].OunerName+ '</div></td>'+
   		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].wellIndex+ '</div></td>'+
   		          '<td data-Eco="Bas"><div class="long">' + datafrom[i].wellCode+ '</div></td>'+
   		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].wellStatus+ '</div></td>'+
   		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].wellDepth+ '</div></td>'+
   		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].dataCollectCase + '</div></td>'+
 		          '<td data-Eco="Fil"><div class="long">' + datafrom[i].dataCondition+'</div></td>'+
 		          '<td data-Eco="Bas"><div class="long">' + datafrom[i].lastUpdatedDate  + '</div></td>'+
 		          '<td><a href="#" onclick="updateOD(this)"><strong>Update</strong></a></td>'+
 		          '<td><a href="#" onclick="deleteOD(this)"><strong>Delete</strong></a></td>'+
   		          '</tr>';
 	}
 	var table = document.getElementById("access_OpData");
	for (var i = table.rows.length; i>0 ; i--) {   //iterate through rows
	  if(i>1) {
	    row = table.rows[i-1];
	    row.parentNode.removeChild(row);
	  }
	}
	access_del_respondOP.insertAdjacentHTML('beforeend',ret);
 		}
 /*Add New Drop Down Paramaters*/
 	var hold_response=document.getElementById("addParamresponse");
 	var xml_forgrantresponse=new XMLHttpRequest();
 	document.getElementById("addparam_button").onclick = function(evt){
		// alert("Submit Button is clicked");
		 //console.log("What is Wrong");
 		if (typeof xml_forgrantresponse != "undefined"){
 			xml_forgrantresponse= new XMLHttpRequest();
 	         }
 	         else if (window.ActiveXObject){
 	        	xml_forgrantresponse= new ActiveXObject("Microsoft.XMLHTTP");
 	         }
 	         if (xml_forgrantresponse==null){
 	         alert("Browser does not support XMLHTTP Request")
 	         return;
 	         }
 	         var additionalDataID=document.getElementById("additionalDataID").value;	
 	        var addedUserID=document.getElementById("addedUserID").value;
 	        var addtionalParamid=document.getElementById("addtionalParamid").value;
 	        var selectedCZID=document.getElementById("selectedCZID").value;
 	        const errorMessageDiv = document.getElementById('errorMessage');
            errorMessageDiv.textContent = ''; // Clear previous error messages
            document.getElementById("addParamresponse").innerHTML='';
         if (addtionalParamid === '0') {
             errorMessageDiv.textContent = 'Select from Attribute Name List';
             errorMessageDiv.style.color='red';
         }
         else if (selectedCZID === '0' && addtionalParamid === 'City_OroZone') {
             errorMessageDiv.textContent = 'Select From City Or Oromia Zone Name List';
             errorMessageDiv.style.color='red';
         }
         else if (additionalDataID.trim() === '') {
             errorMessageDiv.textContent = 'Add New Attribute Name';
             errorMessageDiv.style.color='red';
         }else{
 	        var url="addParameter?data_id="+additionalDataID+"&user_id="+addedUserID+"&contentID="+addtionalParamid+"&cityZID="+selectedCZID;
 	        xml_forgrantresponse.open("POST", url,true);
 	        xml_forgrantresponse.onload=function(){
 	        var prodata=JSON.parse(xml_forgrantresponse.responseText);
 	        get_reponse(prodata);
 	        	 }
 	        xml_forgrantresponse.send(); 
           }
 	       }
 	function get_reponse(datap){
 	var ret='';	
 	for(var i=0; i<datap.length; i++){
 		ret+='<p>' + datap[i].newlyAddeddParam + '</p>';
 	}
 	document.getElementById("addParamresponse").innerHTML='';	
 	hold_response.insertAdjacentHTML('beforeend',ret);
 		}
 		 
 	/*Handle data from process*/
 	var access_del_prodata=document.getElementById("projectData_id");
 	xmlprodata_access=new XMLHttpRequest();
 	document.getElementById("").onclick = function(evt){
 		if (typeof xmlprodata_access != "undefined"){
 			xmlprodata_access= new XMLHttpRequest();
 	         }
 	         else if (window.ActiveXObject){
 	        	xmlprodata_access= new ActiveXObject("Microsoft.XMLHTTP");
 	         }
 	         if (xmlprodata_access==null){
 	         alert("Browser does not support XMLHTTP Request")
 	         return;
 	         }
 	         var access_user_id=document.getElementById("user_idpro").value;
 	         var url="access_mod_pro?access_id="+access_user_id;
 	        xmlprodata_access.open("POST", url,true);
 	       xmlprodata_access.onload=function(){
 	        var prodata=JSON.parse(xmlprodata_access.responseText);
 	       access_dele_prodata(prodata);
 	        	 }
 	      xmlprodata_access.send();
 	         }
 	function access_dele_prodata(datafrom){
 		var ret='';
 	for(var i=0; i<datafrom.length; i++){
    	if(datafrom[i].file_ext === "{shp}"||datafrom[i].file_ext === "{xlsx}" ||datafrom[i].file_ext === "{csv}"
       	 || datafrom[i].file_ext === "{docx}" || datafrom[i].file_ext === "{pdf}" || datafrom[i].file_ext === "{png}"||datafrom[i].file_ext === "{tif}"
          	  || datafrom[i].file_ext === "{jpg}" || datafrom[i].file_ext === "{gif}" || datafrom[i].file_ext === "{dwg}"){
    		ret+='<tr><td data-Eco="Spa" style="width: 9%;"><div class="long">' + datafrom[i].process + '</div></td>'+
   		          '<td data-Eco="Cat" style="width: 18%;"><div class="long">' + datafrom[i].project_name + '</div></td>'+
   		          '<td data-Eco="Clu"><div class="long">' + datafrom[i].data_type + '</div></td>'+
   		          '<td data-Eco="Reg"><div class="long">' + datafrom[i].branch_name + '</div></td>'+
   		          '<td data-Eco="Bas"><div class="long">' + datafrom[i].sub_branch + '</div></td>'+
   		          '<td data-Eco="Bas"><div class="long">' + datafrom[i].name + '</div></td>'+
   		          '<td data-Eco="Yea"><div class="long">' + datafrom[i].user_name + '</div></td>'+
   		          '<td data-Eco="Fil">' + datafrom[i].date + '</td>'+
   		          '<td><a href="#">Delete</a></td>'+
   		          '<td><a href="downloadfile?file_id=' 
          			+ datafrom[i].user_name+'&project_name='+datafrom[i].project_name+'&project_id='+datafrom[i].data_type
        			+'&cat_name='+datafrom[i].branch_name+'">Download</a></td></tr>';
   		}
 	}
 	var table = document.getElementById("projectData_id");
	for (var i = table.rows.length; i>0 ; i--) {   //iterate through rows
	  if(i>1) {
	    row = table.rows[i-1];
	    row.parentNode.removeChild(row);
	  }
	}
	access_del_prodata.insertAdjacentHTML('beforeend',ret);
 		}
 	//delete raw data file in the repository
 	onclick=function(){prodata()};
 	var access_del_prodataresponse=document.getElementById("projectData_id");
 	var xml_delete_proj=new XMLHttpRequest();
 	function prodata(){
 		var table_project=document.getElementById('projectData_id');
 	 	for(var i=1;i < table_project.rows.length; i++){
 	 				table_project.rows[i].onclick = function(){
 	 	 					getvale(this);	
 	 			};					
 	 	}	
 	};
 	function getvale(row){
 			row.cells[8].onclick=function(){	
				    var file_name=document.getElementById("hold_project_filename").value=row.cells[6].innerText;
	 				if(confirm("If you Click Ok,File with file name:.. "+file_name+" ... will be Deleted")){
	 					var access_user_id=document.getElementById("user_idpro").value;
	 		 	 		
	 		 	 		//alert(file_name);
	 		 	 		var ulr_="delete_profile?file_id="+file_name+"&user_id="+access_user_id;
	 		 	 	xml_delete_proj.open("POST",ulr_,true);
	 		 	    xml_delete_proj.onload=function(){
	 		 	 			var dataget=JSON.parse(xml_delete_proj.responseText);
	 		 	 		access_dele_prodata_response(dataget);
	 		 	 		}
	 		 	    xml_delete_proj.send();
	 					return true;
	 					}
	 				else{
	 					return false;	
	 				}
	 				return false;		
	 		
			};	
 	}
 	
 		
 	function access_dele_prodata_response(datafrom){
 		var ret='';
 	for(var i=0; i<datafrom.length; i++){
    	if(datafrom[i].file_ext === "{shp}"||datafrom[i].file_ext === "{xlsx}" ||datafrom[i].file_ext === "{csv}"
       	 || datafrom[i].file_ext === "{docx}" || datafrom[i].file_ext === "{pdf}" || datafrom[i].file_ext === "{png}"||datafrom[i].file_ext === "{tif}"
          	  || datafrom[i].file_ext === "{jpg}" || datafrom[i].file_ext === "{gif}" || datafrom[i].file_ext === "{dwg}"){
    		ret+='<tr><td data-Eco="Spa" style="width: 9%;"><div class="long">' + datafrom[i].process + '</div></td>'+
   		          '<td data-Eco="Cat" style="width: 18%;"><div class="long">' + datafrom[i].project_name + '</div></td>'+
   		          '<td data-Eco="Clu"><div class="long">' + datafrom[i].data_type + '</div></td>'+
   		          '<td data-Eco="Reg"><div class="long">' + datafrom[i].branch_name + '</div></td>'+
   		          '<td data-Eco="Bas"><div class="long">' + datafrom[i].sub_branch + '</div></td>'+
   		          '<td data-Eco="Bas"><div class="long">' + datafrom[i].name + '</div></td>'+
   		          '<td data-Eco="Yea"><div class="long">' + datafrom[i].user_name + '</div></td>'+
   		          '<td data-Eco="Fil">' + datafrom[i].date + '</td>'+
   		          '<td><a href="#">Delete</a></td>'+
   		          '<td><a href="downloadfile?file_id=' 
          			+ datafrom[i].user_name+'&project_name='+datafrom[i].project_name+'&project_id='+datafrom[i].data_type
        			+'&cat_name='+datafrom[i].branch_name+'">Download</a></td></tr>';
   		}
 	}
 	var table = document.getElementById("projectData_id");
	for (var i = table.rows.length; i>0 ; i--) {   //iterate through rows
	  if(i>1) {
	    row = table.rows[i-1];
	    row.parentNode.removeChild(row);
	  }
	}
	access_del_prodataresponse.insertAdjacentHTML('beforeend',ret);
 		}
 	/*process name*/
 	var hold_process=document.getElementById("process_id");
 	xml_forgrantprocess=new XMLHttpRequest();
 	document.getElementById("center_id").onchange = function(evt){
 		var datatosend = evt.target.value;
 		if (typeof xml_forgrantprocess != "undefined"){
 			xml_forgrantprocess= new XMLHttpRequest();
 	         }
 	         else if (window.ActiveXObject){
 	        	xml_forgrantprocess= new ActiveXObject("Microsoft.XMLHTTP");
 	         }
 	         if (xml_forgrantprocess==null){
 	         alert("Browser does not support XMLHTTP Request")
 	         return;
 	         }	
 	         var url="grant_pro?cent_id="+datatosend;
 	        xml_forgrantprocess.open("POST", url,true);
 	       xml_forgrantprocess.onload=function(){
 	        var prodata=JSON.parse(xml_forgrantprocess.responseText);
 	       grant_holdprocess(prodata);
 	        	 }
 	      xml_forgrantprocess.send();
 	         }
 	function grant_holdprocess(datap){
 	var ret='';	
 	for(var i=0; i<datap.length; i++){
 		ret+='<option value="' + datap[i].regnam + '">' + datap[i].regnam + '</option>';
 	}
 	document.getElementById("process_id").options.length=1;	
 	hold_process.insertAdjacentHTML('beforeend',ret);
 		}
 	/*Handle data from process*/
 	var hold_user=document.getElementById("User_togrant");
 	var hold_projectname=document.getElementById("pro_name7");
 	xml_forgrantuser=new XMLHttpRequest();
 	document.getElementById("process_id").onchange = function(evt){
 		var datatosend = evt.target.value;
 		if (typeof xml_forgrantuser != "undefined"){
 			xml_forgrantuser= new XMLHttpRequest();
 	         }
 	         else if (window.ActiveXObject){
 	        	xml_forgrantuser= new ActiveXObject("Microsoft.XMLHTTP");
 	         }
 	         if (xml_forgrantuser==null){
 	         alert("Browser does not support XMLHTTP Request")
 	         return;
 	         }	
 	         var user_id=document.getElementById("user_idUN").value;
 	         var url="process_granted?process_id="+datatosend+"&user_id="+user_id;
 	        xml_forgrantuser.open("POST", url,true);
 	       xml_forgrantuser.onload=function(){
 	        var prodata=JSON.parse(xml_forgrantuser.responseText);
 	       grant_holduser(prodata);
 	      grant_holdproject(prodata);
 	        	 }
 	      xml_forgrantuser.send();
 	         }
 	function grant_holduser(datap){
 	var ret='';	
 	for(var i=0; i<datap.length; i++){
 		if(datap[i].regid!=0 && datap[i].class_id==0){
 		ret+='<option value="' + datap[i].regid + '">' + datap[i].regnam + '</option>';	
 		}
 	}
 	document.getElementById("User_togrant").options.length=1;	
 	hold_user.insertAdjacentHTML('beforeend',ret);
 		}
 	function grant_holdproject(datap){
 	 	var ret='';	
 	 	for(var i=0; i<datap.length; i++){
 	 		if(datap[i].class_id!=0 && datap[i].class_name=='YES'){
 	 		ret+='<option value="' + datap[i].class_id + '">' + datap[i].cata_gory + '</option>';	
 	 		}
 	 	}
 	 	document.getElementById("pro_name7").options.length=1;	
 	 	hold_projectname.insertAdjacentHTML('beforeend',ret);
 	 		} 