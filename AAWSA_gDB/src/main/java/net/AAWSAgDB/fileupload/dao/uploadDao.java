package net.AAWSAgDB.fileupload.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.print.DocFlavor.STRING;
import javax.tools.JavaFileObject;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import net.AAWSAgDB.fileupload.ValuePair;
import net.AAWSAgDB.fileupload.model.File;
public class uploadDao {
	static InputStream ion;
static private Connection conn=null;
//save users
public static File saveusers(String username,String Psw, String pr_code,String Center_type,String Fname,String Lname
		,int centerno,int cl_code){
	String pushdata="insert intogw_db_schema.db_user(username,password,pr_code,pr_type,fname,lname,center,gr_id)"
			+ "values(?,?,?,?,?,?,?,?)";
	try{
conn=connection.dbconnection();
PreparedStatement prestatment=conn.prepareStatement(pushdata);
prestatment.setString(1, username);
prestatment.setString(2, Psw);
prestatment.setString(3, pr_code);
prestatment.setString(4, Center_type);
prestatment.setString(5, Fname);
prestatment.setString(6, Lname);
prestatment.setInt(7, centerno);
prestatment.setInt(8, cl_code);
prestatment.executeUpdate();
conn.close();
	}catch(Exception ex){
		ex.printStackTrace();
	}
	File ff=null;
	return ff;
}
//Access Center
public static List<File> center_name(){
	List<File>files=new ArrayList<File>();
	String pushdata="select usertype from gw_db_schema.db_user group by usertype";
	try{
conn=connection.dbconnection();
Statement stmnt=conn.createStatement();
ResultSet rs=stmnt.executeQuery(pushdata);
while(rs.next()){
	//int user_id=rs.getInt(1);
String c_name=rs.getString(1);	
File ff=new File(0, c_name,"",0);
files.add(ff);
}
	}catch(Exception ex){
		ex.printStackTrace();
	}
	
	return files;
}
//Access process
public static List<File> process_name(String center_name){
	List<File>files=new ArrayList<File>();
	String pushdata="select fname from gw_db_schema.db_user where usertype='"+center_name+"' group by fname";
	try{
conn=connection.dbconnection();
Statement stmnt=conn.createStatement();
ResultSet rs=stmnt.executeQuery(pushdata);
while(rs.next()){
	//int user_id=rs.getInt(1);
String c_name=rs.getString(1);	
File ff=new File(0, c_name,"",0);
files.add(ff);
}
	}catch(Exception ex){
		ex.printStackTrace();
	}
	
	return files;
}
//Access user and project
public static List<File> username_project(String process_name,int user_id){
	List<File>files=new ArrayList<File>();
	String pushdata="select id,fname from gw_db_schema.db_user where l_name='"+process_name+"' and pr_code='120' and id!="+user_id+"";
	try{
conn=connection.dbconnection();
Statement stmnt=conn.createStatement();
ResultSet rs=stmnt.executeQuery(pushdata);
while(rs.next()){
int user_id_db=rs.getInt(1);
String c_name=rs.getString(2);	
File ff=new File(user_id_db,c_name,"",0);
files.add(ff);
}
	}catch(Exception ex){
		ex.printStackTrace();
	}
	return files;
}
//grant project
public static List<File> grant_project(String username, String[]sup_desi_id){
	//System.out.println("Requester Name = "+username);
	List<File>files=new ArrayList<File>();
	int user_id = 0;
	try {
	conn=connection.dbconnection();
	Statement stmnt=conn.createStatement();
String sqlsearch="select l_id from hydrogeos.login11 where user1='"+username+"'";
ResultSet rs=stmnt.executeQuery(sqlsearch);
while(rs.next()){
user_id=rs.getInt("l_id");	
}
stmnt.close();
rs.close();
Statement stmnt1=conn.createStatement();
ResultSet rs1=null;
List<String>desi_super=Arrays.asList(sup_desi_id);
for(String dessuplist_: desi_super){
	//System.out.println("Name of Supervision or Design is = "+dessuplist_);
	if(dessuplist_!=""){
		String checkprivilage="select l_id,pr_id from hydrogeos.privileged where l_id="+user_id+" and pr_id="+dessuplist_+"";
		rs1=stmnt1.executeQuery(checkprivilage);
		if(rs1.next()){
			File ff=new File("<font color=#FF0000>File is Already Granted!! </font>");
			files.add(ff);
		}
		else{
			String queryInsert="insert into hydrogeos.privileged(l_id,pr_id)values("+user_id+","+dessuplist_+")";
			stmnt1.executeUpdate(queryInsert);
			File ff=new File("<font color=#31708f>File is Successfully Granted!! </font>");
			files.add(ff);
		}
	}
}
rs1.close();
stmnt1.close();
conn.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return files;
}
//Add Parameters
public static List<File> addParameter(String parameter,int user_id,String content,int cityZID){
	List<File>files=new ArrayList<File>();
	try{
		if(content.equals("City_OroZone")){
			String getvalue="select count(*) from gw_db_schema.sub_city_zone as scz,gw_db_schema.region_class rcl,gw_db_schema.region_city as rc"
					+ " where scz.cl_id=rcl.cl_id and rcl.reg_id=rc.reg_id and scz.cl_id="+cityZID+" and "
							+ "SIMILARITY(sub_city_zone_name,'"+parameter+"')>0.80";
			conn=connection.dbconnection();
			Statement stmnt=conn.createStatement();
			ResultSet rs=stmnt.executeQuery(getvalue);
			rs.next();
			int checkInsert=rs.getInt(1);
			if(checkInsert>0){
			Object obj = null;
			File ff=new File(0,"The Parameter <font color=#FF0000>"+parameter+"</font> is Already avialable in the "
					+ "<font color=#FF0000>"+content+"</font> List!",obj);
			files.add(ff);
			}else{
				// Get current date and time using java.time
			    LocalDateTime currentDateTime = LocalDateTime.now();
			    // Convert to java.sql.Timestamp
			    Timestamp sqlTimestamp = Timestamp.valueOf(currentDateTime);
				String pushdata="insert into gw_db_schema.sub_city_zone(cl_id,sub_city_zone_name,user_id,created_at)values("+cityZID+","
						+"'"+parameter+"',"+user_id+",?)";
				PreparedStatement prstmnt=conn.prepareStatement(pushdata);
				prstmnt.setTimestamp(1,sqlTimestamp);
				prstmnt.executeUpdate();
		String query="select rcl.cl_name from gw_db_schema.region_class rcl,gw_db_schema.region_city as rc where rcl.reg_id=rc.reg_id and "
				+ "rcl.cl_id="+cityZID+"";
			   Statement statmnt = conn.createStatement();
				ResultSet rs_user = statmnt.executeQuery(query);
				while(rs_user.next()){
					String cityZName=rs_user.getString(1);	
					String defintion=parameter+" is Added to the "+cityZName+" Content List.";
					Object obj = null;
					File ff=new File(0,"<font color=#31708f>"+defintion+"</font>",obj);
					files.add(ff);
				}
				}
		}else if(content.equals("Well Field")) {
			String getvalue="select count(*) from gw_db_schema.well_field where SIMILARITY(wf_name,'"+parameter+"')>0.80";
			conn=connection.dbconnection();
			Statement stmnt=conn.createStatement();
			ResultSet rs=stmnt.executeQuery(getvalue);
			rs.next();
			int checkInsert=rs.getInt(1);
			if(checkInsert>0){
			Object obj = null;
			File ff=new File(0,"The Attribute <font color=#FF0000>"+parameter+"</font> is Already avialable in the "
					+ "<font color=#FF0000>"+content+"</font> List!",obj);
			files.add(ff);
			}else{
				// Get current date and time using java.time
			    LocalDateTime currentDateTime = LocalDateTime.now();
			    // Convert to java.sql.Timestamp
			    Timestamp sqlTimestamp = Timestamp.valueOf(currentDateTime);
				String pushdata="insert into gw_db_schema.well_field(wf_name,user_id,created_at)values('"+parameter+"',"+user_id+",?)";
				PreparedStatement prstmnt=conn.prepareStatement(pushdata);
				prstmnt.setTimestamp(1,sqlTimestamp);
				prstmnt.executeUpdate();
				int update = prstmnt.getUpdateCount();
				if(update>0){
					String defintion=parameter+" is Added to the "+content+" Content List.";
					Object obj = null;
					File ff=new File(0,"<font color=#31708f>"+defintion+"</font>",obj);
					files.add(ff);	
				}else {
					Object obj = null;
					String defintion=parameter+" is Failed to add into "+content+" Content List.";
					File ff=new File(0,"<font color=#FF0000>"+defintion+"</font>",obj);
					files.add(ff);
				}
			}
		}
		else {
			String getvalue="select count(*) from gw_db_schema.addtionalparm where SIMILARITY(parameterp,'"+parameter+"')>0.80 and "
					+ "SIMILARITY(contentp,'"+content+"')>0.80";
			conn=connection.dbconnection();
			Statement stmnt=conn.createStatement();
			ResultSet rs=stmnt.executeQuery(getvalue);
			rs.next();
			int checkInsert=rs.getInt(1);
			if(checkInsert>0){
			String defintion=parameter;
			String contentN=content;
			Object obj = null;
			File ff=new File(0,"The Parameter <font color=#FF0000>"+defintion+"</font> is Already avialable in the "
					+ "<font color=#FF0000>"+contentN+"</font> List!",obj);
			files.add(ff);
			}else{
				// Get current date and time using java.time
			    LocalDateTime currentDateTime = LocalDateTime.now();
			    // Convert to java.sql.Timestamp
			    Timestamp sqlTimestamp = Timestamp.valueOf(currentDateTime);
				String pushdata="insert into gw_db_schema.addtionalparm(parameterp,contentp,created_at,user_id)values('"+parameter+"',"
						+ "'"+content+"',?,"+user_id+")";
				PreparedStatement prstmnt=conn.prepareStatement(pushdata);
				prstmnt.setTimestamp(1,sqlTimestamp);
				prstmnt.executeUpdate();
				String defintion=parameter+" is Added to the "+content+" Content List.";
				Object obj = null;
				File ff=new File(0,"<font color=#31708f>"+defintion+"</font>",obj);
				files.add(ff);
				}
		}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return files;
	}
//calculate and compare if sheet name are equivalent
public static double calculateSimilarity(String s1, String s2) {
    // Normalize the strings (optional: convert to lowercase, remove extra spaces, etc.)
    s1 = s1.toLowerCase().trim();
    s2 = s2.toLowerCase().trim();

    int maxLength = Math.max(s1.length(), s2.length());
    if (maxLength == 0) {
        return 1.0; // Both empty, so 100% similar
    }

    // Calculate the Levenshtein distance
    LevenshteinDistance levenshtein = new LevenshteinDistance();
    int distance = levenshtein.apply(s1, s2);

    // Calculate similarity percentage: (Max Length - Distance) / Max Length
    double similarity = (double) (maxLength - distance) / maxLength;
    return similarity;
}
//check if sheet is empty
public static  boolean isSheetEmpty(Sheet sheet) {
	if(sheet==null) {
		return true;
	}
	return true;
}
//Insertion
private static void addwellextraInfo(Connection connection,int wellCell_id,int contractorID,int consultID,double wellCell_largeDrillId,
		double wellCell_TeleDrillId,double wellcellDiameter,String project_name,String data_source,String drillingMethod[])
		throws Exception {
String query = "insert into gw_db_schema.extrawellinfo(well_id,const_by,consult_by,largdrillid,telescopdrillid,welldiameter,"
		+ "project_name,data_source,drill_method)values(?,?,?,?,?,?,?,?,?)";
Array sqlArray_drillingM=conn.createArrayOf("text",drillingMethod);
try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	preparedStatement.setInt(1, wellCell_id);
	preparedStatement.setInt(2, contractorID);
	preparedStatement.setInt(3, consultID);
	preparedStatement.setDouble(4, wellCell_largeDrillId);
	preparedStatement.setDouble(5, wellCell_TeleDrillId);
	preparedStatement.setDouble(6, wellcellDiameter);
	preparedStatement.setString(7, project_name);
	preparedStatement.setString(8, data_source);
	preparedStatement.setArray(9, sqlArray_drillingM);
    preparedStatement.executeUpdate();
}
}
private static void addgroundwaterData(Connection connection, int well_id,String wellcellUsageStatus,String nonPotableReason,String datacollectCase,
		java.sql.Date recordDate,int dbUserId)throws Exception {
String query = "insert into gw_db_schema.gw_data(well_id,potability_status,non_potable_reason,data_colln_case,recordate,db_userid)values(?,?,?,?,?,?)";
try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
	    preparedStatement.setInt(1, well_id);
		preparedStatement.setString(2, wellcellUsageStatus);
		preparedStatement.setString(3, nonPotableReason);
		preparedStatement.setString(4, datacollectCase);
		preparedStatement.setDate(5, recordDate);
		preparedStatement.setInt(6, dbUserId);
    preparedStatement.executeUpdate();
}
}
private static void addproductivewellStatusInfo(Connection connection,int pow_id,int scada_id,int obpip_id,int pro_id,
		String prodwellFuncSName,String wellCondtionName,String reason_inactive,String reason_nonfun,java.sql.Date funStopeDate,
		String datacondition)
		throws Exception {
String query = "insert into gw_db_schema.productive_well_status(pow_id,sca_id,pip_id,pro_id,wellfunstatus,func_condition,"
		+ "reason_inactive,reason_nonfun,fun_stoped_date,datacondtion)values(?,?,?,?,?,?,?,?,?,?)";
try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
	preparedStatement.setInt(1,pow_id ); //wellCondtionName
	preparedStatement.setInt(2,scada_id );
	preparedStatement.setInt(3, obpip_id);
	preparedStatement.setInt(4, pro_id);
	preparedStatement.setString(5,prodwellFuncSName);
	preparedStatement.setString(6,wellCondtionName);
	preparedStatement.setString(7, reason_inactive);
	preparedStatement.setString(8, reason_nonfun);
	preparedStatement.setDate(9,funStopeDate);
	preparedStatement.setString(10, datacondition);
    preparedStatement.executeUpdate();
}
}
//Update Well characteristics
private static void updateWellcasingInfo(Connection conn,String wellCell_totalScreen,String wellCellTopScreen,String wellCellBottomScreen,
		java.sql.Date wellCellCaseInstalDate,String permanentCasing,int casing_id) throws Exception {
	String updateCasing="update gw_db_schema.casing_info set pmnt_casing=?,totl_scrin_l=?,top_scrin_l=?,botm_scrin_l=?,caseinstall_date=? where "
			+ "ci_id=?";
	try(PreparedStatement preparedSatament = conn.prepareStatement(updateCasing)){
		preparedSatament.setString(1, permanentCasing);
		preparedSatament.setString(2, wellCell_totalScreen);
		preparedSatament.setString(3,wellCellTopScreen ); //wellCondtionName
		preparedSatament.setString(4,wellCellBottomScreen );
		preparedSatament.setDate(5, wellCellCaseInstalDate);
		preparedSatament.setInt(6, casing_id);
		preparedSatament.executeUpdate();
	}
}
//Insert into Step draw down Test table
private static void insertStepDrawDownTest(Connection connection,int noOfSteps,int eachStepDuration,double yieldsForSteps[],
		double commulativeDD,String data_collenction_cases,java.sql.Date recordDate,int chCell_id)
		throws Exception {
	 Array sqlArray_pumpHeadName=conn.createArrayOf("float8",allYesldsTObjectArray(yieldsForSteps));
String query = "insert into gw_db_schema.step_drawdown_test(ch_id,number_of_steps,each_step_duration,yields_for_steps,comulative_dd,"
		+ "data_colln_case,recordate)values(?,?,?,?,?,?,?)";
try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	preparedStatement.setInt(1, chCell_id);
	preparedStatement.setInt(2, noOfSteps);
	preparedStatement.setInt(3, eachStepDuration);
	preparedStatement.setArray(4, sqlArray_pumpHeadName);
	preparedStatement.setDouble(5, commulativeDD);
		preparedStatement.setString(6, data_collenction_cases);
		preparedStatement.setDate(7, recordDate);
    preparedStatement.executeUpdate();
}
}
//Insert into Constant Discharge Rate
private static void updateconstantDischargeTest(Connection connection,int testDurationHrs,double dischargeTotalDD,String dataCOndition,int chCell_id)
		throws Exception {
String query = "update gw_db_schema.con_discharge_test set test_duration=?,total_drawdown=?,datacondtion=? where ch_id=?";
try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	preparedStatement.setInt(1, testDurationHrs);
	preparedStatement.setDouble(2, dischargeTotalDD);
	preparedStatement.setString(3, dataCOndition);
	preparedStatement.setInt(4, chCell_id);
    preparedStatement.executeUpdate();
}
}
//Insert to Physical Table
private static void insertToPhysicalTable(Connection connection,int gwCell_id,double temprature,double turbidity,double totalDissolvedSolid,
		double colorTrueColor,double electricConductivity, String dataCollectedCase,java.sql.Date recorDate)
		throws Exception {
String query = "insert into gw_db_schema.physical_da(gw_id,temprature,turibidity,tot_disolved_solid,true_color,elec_condu,"
		+ "recordate,data_colln_case)values(?,?,?,?,?,?,?,?)";
try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	preparedStatement.setInt(1, gwCell_id);
	preparedStatement.setDouble(2, temprature);
	preparedStatement.setDouble(3, turbidity);
	preparedStatement.setDouble(4, totalDissolvedSolid);
	preparedStatement.setDouble(5, colorTrueColor);
	preparedStatement.setDouble(6, electricConductivity);
		preparedStatement.setDate(7, recorDate);
		preparedStatement.setString(8, dataCollectedCase);
    preparedStatement.executeUpdate();
}
}
//Insert into Chemical Table
private static void insertToChemicalTable(Connection connection,int gwCell_id,java.sql.Date recorDate,double pH,double total_Hardness_Ca_Mg_CaCO3,
		double F_,double Na_,double Cl_,double Ca_,double Fe_,double Mn_,double K_,double PO4_,double Mg_,double NO3PlusNO2_,
		double SO4_2_,double Hc03,double No3,double NH4PlusNH3,double As,double Pb, String dataCollectedCase)
		throws Exception {
String query = "insert into gw_db_schema.chemical_da(gw_id,recordate,ph,totalh_ca_mg_caco3,f_,na_,cl_,ca_,fe_,mn_,k_,p_,mg_,no3plusno2_,so4_2_,"
		+ "hco3_,no3_,nh4plusn,as_,pb_,data_colln_case)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	preparedStatement.setInt(1, gwCell_id);
	preparedStatement.setDate(2, recorDate);
	preparedStatement.setDouble(3, pH);
	preparedStatement.setDouble(4, total_Hardness_Ca_Mg_CaCO3);
	preparedStatement.setDouble(5, F_);
	preparedStatement.setDouble(6, Na_);
	preparedStatement.setDouble(7, Cl_);
	preparedStatement.setDouble(8, Ca_);
	preparedStatement.setDouble(9, Fe_);
	preparedStatement.setDouble(10, Mn_);
	preparedStatement.setDouble(11, K_);
	preparedStatement.setDouble(12, PO4_);
	preparedStatement.setDouble(13, Mg_);
	preparedStatement.setDouble(14, NO3PlusNO2_);
	preparedStatement.setDouble(15, SO4_2_);
	preparedStatement.setDouble(16, Hc03);
	preparedStatement.setDouble(17, No3);
	preparedStatement.setDouble(18, NH4PlusNH3);
	preparedStatement.setDouble(19, As);
	preparedStatement.setDouble(20, Pb);
		preparedStatement.setString(21, dataCollectedCase);
    preparedStatement.executeUpdate();
}
}
//Insert Into Microbiological and Radiology data
private static void insertToMicroRadioTable(Connection connection,int gwCell_id,double totalColiforms,double eColSpecific,double radon,
		double uranium,String dataCollectedCase,java.sql.Date recorDate)
		throws Exception {
String query = "insert into gw_db_schema.micro_radio(gw_id,tot_coliforms,e_coli,radon,uranium,"
		+ "recordate,data_colln_case)values(?,?,?,?,?,?,?)";
try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	preparedStatement.setInt(1, gwCell_id);
	preparedStatement.setDouble(2, totalColiforms);
	preparedStatement.setDouble(3, eColSpecific);
	preparedStatement.setDouble(4, radon);
	preparedStatement.setDouble(5, uranium);
		preparedStatement.setDate(6, recorDate);
		preparedStatement.setString(7, dataCollectedCase);
    preparedStatement.executeUpdate();
}
}
//update well characteristics
private static void updateWellcharactesitics(Connection conn,double hConductivity,double transmissivity,double storativity,double wellEfficiency,
		String wellProductivity,int  well_id) throws Exception {
	String updateCasing="update gw_db_schema.well_characteristics set well_efficiency="+wellEfficiency+",hydraulicconduct="+hConductivity+","
			+ "transmissivity="+transmissivity+",storativity="+storativity+",wellProductivity='"+wellProductivity+"' where well_id="+well_id+"";
	try(PreparedStatement preparedSatament = conn.prepareStatement(updateCasing)){
		preparedSatament.executeUpdate();
	}
}
//Update Well Status gwCell id
private static void udateWellStatus(Connection conn,int gwCell_id,int chCell_id) throws Exception {
	String updateCasing="update gw_db_schema.constwell_status set gw_id="+gwCell_id+" where gw_id is NULL and cdt_id="+chCell_id+"";
	try(PreparedStatement preparedSatament = conn.prepareStatement(updateCasing)){
		preparedSatament.executeUpdate();
	}
}
//Update Well Status repo id
private static void udateWellStatusRepoId(Connection conn,int repoCell_id) throws Exception {
	String updateCasing="update gw_db_schema.constwell_status set repo_id="+repoCell_id+" where repo_id is NULL";
	try(PreparedStatement preparedSatament = conn.prepareStatement(updateCasing)){
		preparedSatament.executeUpdate();
	}
}
private static byte[] readFileToByteArray(InputStream file) throws IOException {
	byte[] buffer = null;
    try {
        buffer = new byte[(int) file.available()];
        file.read(buffer);
    }catch (Exception e) {
    	e.printStackTrace();
		// TODO: handle exception
	}
    return buffer;
}
private static String calculateSHA256(byte[] operationaldataStream) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] hash = digest.digest(operationaldataStream); 
    StringBuilder hexString = new StringBuilder();
    for (byte b : hash) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) hexString.append('0');
        hexString.append(hex);
    }
    return hexString.toString();
}

private static byte[] readFileToByteArrayB(InputStream file) throws IOException {
	byte[] buffer = null;
    try {
        buffer = new byte[(int) file.available()];
        file.read(buffer);
    }catch (Exception e) {
    	e.printStackTrace();
		// TODO: handle exception
	}
    return buffer;
}
private static byte[] readFileToByteyPGDoc(InputStream file) throws IOException {
	byte[] buffer = null;
    try {
    	if(file!=null) {
    		 buffer = new byte[(int) file.available()];
    	        file.read(buffer);
    	}
    }catch (Exception e) {
    	e.printStackTrace();
		// TODO: handle exception
	}
    return buffer;
}

private static String calculateSHA256B(byte[] operationaldataStream) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] hash = digest.digest(operationaldataStream); 
    StringBuilder hexString = new StringBuilder();
    for (byte b : hash) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) hexString.append('0');
        hexString.append(hex);
    }
    return hexString.toString();
}
private static String calculateSHA256WellRepo(byte[] operationaldataStream) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] hash = digest.digest(operationaldataStream); 
    StringBuilder hexString = new StringBuilder();
    for (byte b : hash) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) hexString.append('0');
        hexString.append(hex);
    }
    return hexString.toString();
}
private static byte[] readFileToByteyPWellRepo(InputStream file) throws IOException {
	byte[] buffer = null;
    try {
    	if(file!=null) {
    		 buffer = new byte[(int) file.available()];
    	        file.read(buffer);
    	}
    }catch (Exception e) {
    	e.printStackTrace();
		// TODO: handle exception
	}
    return buffer;
}
//check Consultant email if exists
	private static boolean conscheckWellReportDoc(Connection connection, String hashvalue) throws Exception {
      String queryemailCkeck = "select count(*) from gw_db_schema.wellreport where hash_value='"+hashvalue+"'";
      try (PreparedStatement preparedStatement = connection.prepareStatement(queryemailCkeck)){
          ResultSet resultSet = preparedStatement.executeQuery();
          if (resultSet.next()) {
              return resultSet.getInt(1) > 0;
          }
      }
      return false;
  }
//spatial data save
public static List<File> savespatialfile(int gwdataId,int db_userID,int lcationId,int wellFieldId,int geographicLocId,String wellcode,String wellIndex,
		double wellDepth,String mainAcquifer,String wellType,java.sql.Date const_date,int wellOwnerID,
		String wellOwnerCata,String wellStatus,
		String drillingPermit,String casingMaterName,double largeCasingID,double telescopCasingID,
		java.sql.Date recorDate,List<MultipartFile> abandata,/*  Screening Info...*
		/*Abandoned well info*/String wellAbanReason,int repoId,String sealedYN,java.sql.Date sealedDate/**/,String productiveWellCond,
		String functionalWellCond,/*Productive well status*/String proWellPowerId,String availableGId,String generatorStatusId,
		double genePowerMeasureId, String SCADAId,String SCADAStatusId,int enameId,
		double pumpCapName,double pumpHead,double yieldLs,double SWL,double DWL,java.sql.Date pumpInstallDate,double pumpPositionName,double dischargeRate,
		java.sql.Date pumpReplacedDate,String pumpStatusName,
		String pumpFilename,String pumpFileType,int pumpfileSize,InputStream pumpdataStream/**/,/*gW parameters*/double SpecificCapacity,
		String wellreportName,String wellReportType,int wellRepoSize,InputStream wellRepoInputStream)throws Throwable{
	    System.out.println("Well Report file name Sent from Controller is "+wellreportName);
	//local Variables
	int wellRepoId=0,well_id=0,pro_id=0,char_id=0,wellrepo_id=0,cws_id=0,pg_id=0,changeFrequency=0,obCell_id=0,scadaCell_id=0,powCell_id=0,
			constD_id=0,geolocCheck;
	String well_code="";
	List<File> uploadReport=new ArrayList<File>();
		try {
			conn=connection.dbconnection();
			String re_sultcheckGeo="select geoloc_id from gw_db_schema.water_well where geoloc_id="+geographicLocId+"";
			Statement stmntcheckGeo=conn.createStatement();
			ResultSet rs1checkGeo=stmntcheckGeo.executeQuery(re_sultcheckGeo);
			if(rs1checkGeo.next()){
				geolocCheck=rs1checkGeo.getInt(1);
	            	File ff= new File(0,"The Cordinate Point (Lat, Long) You Provided For Well Index is Registered By Other Well Index",
	            			"<font color=#8B8000>Warning,</font>");
	            	uploadReport.add(ff);
			}else {
				String re_sult="select well_index from gw_db_schema.water_well where SIMILARITY(well_index,'"+wellIndex+"')=1";
				Statement stmnt=conn.createStatement();
				ResultSet rs1=stmnt.executeQuery(re_sult);
				if(rs1.next()){
					well_code=rs1.getString(1);
		            	File ff= new File(0,"The Submitted Drilling Pump Test History Data is Available in the Repository",
		            			"<font color=#8B8000>Warning,</font>");
		            	uploadReport.add(ff);
				}
				else{
		PreparedStatement prestatment=conn.prepareStatement("insert into gw_db_schema.water_well(gwdata_id,local_id,wf_id,geoloc_id,wellcode,well_index,"
				+ "well_depth,main_aquifer,well_type,cntn_year,owned_by,wellownercatgory,data_colln_case,record_date,db_userid,drilling_license)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
						prestatment.setInt(1, 2);
						prestatment.setInt(2, lcationId);
						prestatment.setInt(3, wellFieldId);
						prestatment.setInt(4, geographicLocId);
						prestatment.setString(5,wellcode);
						prestatment.setString(6,wellIndex);
						prestatment.setDouble(7,wellDepth);
						prestatment.setString(8,mainAcquifer);
						prestatment.setString(9, wellType);
						prestatment.setDate(10, const_date);
						prestatment.setInt(11, wellOwnerID);
						prestatment.setString(12,wellOwnerCata);
						prestatment.setString(13,"Well Completion Report");
						prestatment.setDate(14,recorDate);
						prestatment.setInt(15, db_userID);
						prestatment.setString(16,drillingPermit);
						prestatment.executeUpdate();
						prestatment.getUpdateCount();
						prestatment.close();
				String querytoSelect="select Ww.well_id from gw_db_schema.water_well as Ww,gw_db_schema.geo_location as gl where "
						+ "Ww.geoloc_id=gl.geoloc_id and Ww.well_index='"+wellIndex+"' and gl.geoloc_id="+geographicLocId+"";
				Statement stmntwellID=conn.createStatement();
				ResultSet rswellID = stmntwellID.executeQuery(querytoSelect);
				while(rswellID.next()) {
					well_id=rswellID.getInt(1);
				}
				//Insert Casing Information
				PreparedStatement prepstateCase=conn.prepareStatement("insert into gw_db_schema.casing_info(casing_mtrl,totl_scrin_l,"
				+ "top_scrin_l,botm_scrin_l,largecasing,well_id,telescopedcasing,caseinstall_date)values(?,?,?,?,?,?,?,?)");
				prepstateCase.setString(1, casingMaterName);
				prepstateCase.setDouble(2, 0);
				prepstateCase.setDouble(3, 0);
				prepstateCase.setDouble(4, 0);
				prepstateCase.setDouble(5, largeCasingID);
				prepstateCase.setInt(6, well_id);
				prepstateCase.setDouble(7, telescopCasingID);
				prepstateCase.setDate(8, null);
				prepstateCase.executeUpdate();
				prepstateCase.getUpdateCount();
				prepstateCase.close();
				//Insert into Well characteristics
				//Insert Casing Information
				PreparedStatement prepstatewellChar=conn.prepareStatement("insert into gw_db_schema.well_characteristics(well_id,specific_capacity,"
						+ "record_date,data_colln_case)values(?,?,?,?)");
				prepstatewellChar.setInt(1, well_id);
				prepstatewellChar.setDouble(2, SpecificCapacity);
				prepstatewellChar.setDate(3, recorDate);
				prepstatewellChar.setString(4,"Well Completion Report");
				prepstatewellChar.executeUpdate();
				prepstatewellChar.getUpdateCount();
				prepstatewellChar.close();
				
				String querywellCharacter="select ch.ch_id from gw_db_schema.well_characteristics as ch,gw_db_schema.water_well as Ww where "
						+ "Ww.well_id=ch.well_id and Ww.well_id="+well_id+" and ch.data_colln_case='Well Completion Report'";
				Statement stmntCharId=conn.createStatement();
				ResultSet rsCharId = stmntCharId.executeQuery(querywellCharacter);
				while(rsCharId.next()) {
					char_id=rsCharId.getInt(1);
					//System.out.println("Well Id is "+well_id);
				}
				if(pumpdataStream!=null) {
					byte filecontent[]= readFileToByteyPGDoc(pumpdataStream);
					String hashvalue = calculateSHA256B(filecontent);
					String querycheckPgdoc="select pgd.pg_id from gw_db_schema.pum_generatordoc as pgd where hashvalue='"+hashvalue+"'"
							+ "group by pgd.pg_id";
					Statement stmntcheckPgdoc=conn.createStatement();
					ResultSet rscheckPgDoc = stmntcheckPgdoc.executeQuery(querycheckPgdoc);
					if(rscheckPgDoc.next()) {
						pg_id=rscheckPgDoc.getInt(1);
					}else {
					PreparedStatement pumpGeneratorDoc=conn.prepareStatement("insert into gw_db_schema.pum_generatordoc(filename,filetype,hashvalue,"
							+ "fcontent,ch_id)values(?,?,?,?,?)");
					pumpGeneratorDoc.setString(1, pumpFilename);
					pumpGeneratorDoc.setString(2, pumpFileType);
					pumpGeneratorDoc.setString(3, hashvalue);
					pumpGeneratorDoc.setBytes(4, filecontent);
					pumpGeneratorDoc.setInt(5, char_id);
					pumpGeneratorDoc.executeUpdate();
					pumpGeneratorDoc.getUpdateCount();
					pumpGeneratorDoc.close();
					String querywellpumpGenDoc="select pgd.pg_id from gw_db_schema.pum_generatordoc as pgd where pgd.filename='"+pumpFilename+"'";
					Statement stmntpumpGenDoc=conn.createStatement();
					ResultSet rspumpGenDoc = stmntpumpGenDoc.executeQuery(querywellpumpGenDoc);
					while(rspumpGenDoc.next()) {
						pg_id=rspumpGenDoc.getInt(1);
						//System.out.println("Well Id is "+well_id);
					}
				}	
			}
				String query = "insert into gw_db_schema.con_discharge_test(yield_lps,swl_m,dwl_m,ch_id,data_colln_case,recordate)values(?,?,?,?,?,?)";
				try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
					preparedStatement.setDouble(1, yieldLs);
					preparedStatement.setDouble(2, SWL);
					preparedStatement.setDouble(3, DWL);
					preparedStatement.setInt(4, char_id);
					preparedStatement.setString(5, "Well Completion Report");
					preparedStatement.setDate(6, recorDate);
				    preparedStatement.executeUpdate();
				}
				//Insert into Constructed Well
				String queryconstTest="select cdt_id from gw_db_schema.con_discharge_test where cdt_id=(select MAX(cdt_id) from gw_db_schema.con_discharge_test as cd,gw_db_schema.well_characteristics as ch,"
						+ "gw_db_schema.water_well as Ww where cd.ch_id=ch.ch_id and Ww.well_id=ch.well_id and ch.ch_id="+char_id+" "
						+ "and cd.data_colln_case='Well Completion Report')limit 1";
				Statement stmntconstTest=conn.createStatement();
				ResultSet rsConst = stmntconstTest.executeQuery(queryconstTest);
				while(rsConst.next()) {
					constD_id=rsConst.getInt(1);
					//System.out.println("Well Id is "+well_id);
				}
				if(wellRepoInputStream!=null) {
					byte filecontent[]= readFileToByteyPWellRepo(wellRepoInputStream);
					String hashvalue = calculateSHA256WellRepo(filecontent);
					if(!conscheckWellReportDoc(conn,hashvalue)){
						System.out.println("Well Report file name is "+wellreportName);
					PreparedStatement pumpGeneratorDoc=conn.prepareStatement("insert into gw_db_schema.wellreport(filename,filesize,filetype,"
							+ "reportdoc,hash_value)values(?,?,?,?,?)");
					pumpGeneratorDoc.setString(1, wellreportName);
					pumpGeneratorDoc.setInt(2, wellRepoSize);
					pumpGeneratorDoc.setString(3, wellReportType);
					pumpGeneratorDoc.setBytes(4, filecontent);
					pumpGeneratorDoc.setString(5, hashvalue);
					pumpGeneratorDoc.executeUpdate();
					pumpGeneratorDoc.close();
					String querywellpumpGenDoc="select repo_id from gw_db_schema.wellreport where hash_value='"+hashvalue+"'";
					Statement stmntpumpGenDoc=conn.createStatement();
					ResultSet rspumpGenDoc = stmntpumpGenDoc.executeQuery(querywellpumpGenDoc);
					while(rspumpGenDoc.next()) {
						wellRepoId=rspumpGenDoc.getInt(1);
						//System.out.println("Well Id is "+well_id);
					}
				}	
			}
				PreparedStatement prepstatewellStatus=conn.prepareStatement("insert into gw_db_schema.constwell_status(cdt_id,well_status,record_date,"
						+ "data_colln_case,enumid,productive_well_status,repo_id)values(?,?,?,?,?,?,?)");
				prepstatewellStatus.setInt(1, constD_id);
				prepstatewellStatus.setString(2, wellStatus);
				prepstatewellStatus.setDate(3, recorDate);
				prepstatewellStatus.setString(4,"Well Completion Report");
				prepstatewellStatus.setInt(5, enameId);
				prepstatewellStatus.setString(6, productiveWellCond);
				if(wellRepoId==0) {
					prepstatewellStatus.setNull(7, wellRepoId);	
				}else {
					prepstatewellStatus.setInt(7, wellRepoId);	
				}
				prepstatewellStatus.executeUpdate();
				prepstatewellStatus.close();
				String querywellstatus="select cws_id from gw_db_schema.constwell_status where cws_id=(select MAX(cws_id) from "
						+ "gw_db_schema.constwell_status as cws,gw_db_schema.con_discharge_test as cd,gw_db_schema.well_characteristics as ch,"
						+ "gw_db_schema.water_well as Ww where cws.cdt_id=cd.cdt_id and cd.ch_id=ch.ch_id and Ww.well_id=ch.well_id and "
						+ "cd.cdt_id="+constD_id+" and cws.well_status='Productive')limit 1";
				Statement stmntstatus=conn.createStatement();
				ResultSet rsstatus= stmntstatus.executeQuery(querywellstatus);
				while(rsstatus.next()) {
					cws_id=rsstatus.getInt(1);
					//System.out.println("Well Id is "+well_id);
				}
				//Select the frequency of pump change from pumping system table
				String querychangeafreq="select sum(ps.changefrequency) from gw_db_schema.pumping_status ps,gw_db_schema.well_characteristics as ch"
						+ ",gw_db_schema.water_well as ww where ps.ch_id=ch.ch_id and ch.well_id=ww.well_id and ch.ch_id="+char_id+" and "
						+ "pumpchangdate='"+pumpReplacedDate+"'";
				Statement stmntchangeFreq=conn.createStatement();
				ResultSet rschangeFreq= stmntchangeFreq.executeQuery(querychangeafreq);
				while(rschangeFreq.next()) {
					changeFrequency=rschangeFreq.getInt(1);
					//System.out.println("Well Id is "+well_id);
				}
				
		         //Insert into Pumping status
	           // Array sqlArray_pumpHeadName=conn.createArrayOf("float8",toObjectArrayHead(pumpHeadName));
	            //Array sqlArray_pumpDischargeR=conn.createArrayOf("float8",toObjectArraydischarge(pumpDischargeR));
	            PreparedStatement prepstatePumpingS=conn.prepareStatement("insert into gw_db_schema.pumping_status(ch_id,pump_capacity,"
	            		+ "pump_position,pump_status,pumphead,abstraction_rate,data_colln_case,recordate,pumpinstalldate,pumpchangdate,doc_id,"
	            		+ "changefrequency)values(?,?,?,?,?,?,?,?,?,?,?,?)");
	            prepstatePumpingS.setInt(1, char_id);
	            prepstatePumpingS.setDouble(2, pumpCapName);
	            prepstatePumpingS.setDouble(3, pumpPositionName);
	            prepstatePumpingS.setString(4, pumpStatusName);
	            prepstatePumpingS.setDouble(5, pumpHead);
	            prepstatePumpingS.setDouble(6, dischargeRate);
	            prepstatePumpingS.setString(7, "Well Completion Report");
	            prepstatePumpingS.setDate(8, recorDate);
	            prepstatePumpingS.setDate(9, pumpInstallDate);
	            prepstatePumpingS.setDate(10, pumpReplacedDate);
	            if (pg_id == 0){
	            	prepstatePumpingS.setNull(11, java.sql.Types.INTEGER); // Set to NULL for integer types
	                // For other types, use appropriate java.sql.Types, e.g., Types.VARCHAR, Types.DOUBLE
	            } else {
	            	prepstatePumpingS.setInt(11, pg_id);
	            }
	            java.util.Date convertRepDate= new SimpleDateFormat("yyyy-MM-dd").parse("1970-01-01");
		  		pumpReplacedDate= new java.sql.Date(convertRepDate.getTime());
	            if(pumpReplacedDate.equals(pumpReplacedDate)){
	            	prepstatePumpingS.setInt(12, changeFrequency);	
	            }else {
	            	prepstatePumpingS.setInt(12, changeFrequency+1);
	            }
	            prepstatePumpingS.executeUpdate();
	            prepstatePumpingS.getUpdateCount();
	            prepstatePumpingS.close();
		        //Insert abandoned information into database
						if(wellStatus.equals("Abandoned")) {
					PreparedStatement prepstateAabandoned=conn.prepareStatement("insert into gw_db_schema.abandoned_well(reported_by,"
							+ "sealed_yn,reason,date_sealed,cws_id)values(?,?,?,?,?)");
					prepstateAabandoned.setInt(1, repoId);
					prepstateAabandoned.setString(2, sealedYN);
					prepstateAabandoned.setString(3, wellAbanReason);
					prepstateAabandoned.setDate(4, sealedDate);
					prepstateAabandoned.setInt(5, cws_id);
					prepstateAabandoned.executeUpdate();
					prepstateAabandoned.getUpdateCount();
					prepstateAabandoned.close();
						}
					else if(wellStatus.equals("Productive")){
	                PreparedStatement prepstateProductive=conn.prepareStatement("insert into gw_db_schema.productive_well(cws_id,data_colln_case,"
	                		+ "record_date,wellhead_cond,poorwellh_reason,drainage_cond)"
	                		+ "values(?,?,?,?,?,?)");
	                prepstateProductive.setInt(1, cws_id);
	                prepstateProductive.setString(2, "Well Completion Report");
	                prepstateProductive.setDate(3, recorDate);
	                prepstateProductive.setString(4, "");
	                prepstateProductive.setString(5, "");
	                prepstateProductive.setString(6, "");
	                prepstateProductive.executeUpdate();
	                prepstateProductive.close();
	                String productiveWellId="select pro_id from gw_db_schema.productive_well where pro_id=(select MAX(pro_id)from "
	                		+ "gw_db_schema.productive_well as pw,gw_db_schema.constwell_status as cws,gw_db_schema.con_discharge_test as cd,"
	                		+ "gw_db_schema.well_characteristics as ch,gw_db_schema.water_well as Ww where pw.cws_id=cws.cws_id and cws.cdt_id=cd.cdt_id "
	                		+ "and cd.ch_id=ch.ch_id and ch.well_id=Ww.well_id and cws.cws_id="+cws_id+")limit 1";
	                Statement stateProduc=conn.createStatement();
	                ResultSet rsProduc=stateProduc.executeQuery(productiveWellId);
	                while(rsProduc.next()) {
	                	pro_id=rsProduc.getInt(1);
	                }	
					//Insert into Power system,SCADA system and Observation PIP
	                //Insert Into SCADA
	                PreparedStatement prepstateSCADA=conn.prepareStatement("insert into gw_db_schema.scada_systemc(pro_id,conn_avialable,scada_status)"
	                		+ "values(?,?,?)");
	                prepstateSCADA.setInt(1, pro_id);
	                prepstateSCADA.setString(2, SCADAId);
	                prepstateSCADA.setString(3, SCADAStatusId);
	                prepstateSCADA.executeUpdate();
	                prepstateSCADA.getUpdateCount();
	                prepstateSCADA.close();
	              
	                //Insert into Power system
	                PreparedStatement prepstatePowerSys=conn.prepareStatement("insert into gw_db_schema.power_systemc(pro_id,power_source,st_gen_avialable,"
	                		+ "gen_status,gen_power,doc_id)values(?,?,?,?,?,?)");
	                prepstatePowerSys.setInt(1, pro_id);
	                prepstatePowerSys.setString(2, proWellPowerId);
	                prepstatePowerSys.setString(3, availableGId);
	                prepstatePowerSys.setString(4, generatorStatusId);
	                prepstatePowerSys.setDouble(5, genePowerMeasureId);
	                if (pg_id == 0){
	                	prepstatePowerSys.setNull(6, java.sql.Types.INTEGER); // Set to NULL for integer types
	                    // For other types, use appropriate java.sql.Types, e.g., Types.VARCHAR, Types.DOUBLE
	                } else {
	                	prepstatePowerSys.setInt(6, pg_id);
	                }
	                prepstatePowerSys.executeUpdate();
	                prepstatePowerSys.getUpdateCount();
	                prepstatePowerSys.close();
	                String queryOb = "insert into gw_db_schema.obsrvation_pipc(pro_id,pip_avialable,pip_status)values(?,?,?)";
	                try (PreparedStatement preparedStatement = conn.prepareStatement(queryOb)) {
	                	preparedStatement.setInt(1, pro_id);
	                	preparedStatement.setString(2, "");
	                	preparedStatement.setString(3,"" );
	                	preparedStatement.executeUpdate();
	                }
					String scadaId="select sca_id from gw_db_schema.scada_systemc where sca_id=(select MAX(sca_id)from gw_db_schema.scada_systemc as scad,"
							+ "gw_db_schema.productive_well as pw,gw_db_schema.constwell_status as cws,gw_db_schema.con_discharge_test as cd,"
							+ "gw_db_schema.well_characteristics as ch,gw_db_schema.water_well as Ww where scad.pro_id=pw.pro_id and pw.cws_id=cws.cws_id "
							+ "and cws.cdt_id=cd.cdt_id and cd.ch_id=ch.ch_id and ch.well_id=Ww.well_id and scad.pro_id="+pro_id+")limit 1";
					Statement statescada=conn.createStatement();
					ResultSet rsscada=statescada.executeQuery(scadaId);
					while(rsscada.next()) {
						scadaCell_id=rsscada.getInt(1);
					}
					String powId="select pow_id from gw_db_schema.power_systemc where pow_id=(select MAX(pow_id)from gw_db_schema.power_systemc as pow,"
							+ "gw_db_schema.productive_well as pw,gw_db_schema.constwell_status as cws,gw_db_schema.con_discharge_test as cd,"
							+ "gw_db_schema.well_characteristics as ch,gw_db_schema.water_well as Ww where pow.pro_id=pw.pro_id and pw.cws_id=cws.cws_id "
							+ "and cws.cdt_id=cd.cdt_id and cd.ch_id=ch.ch_id and ch.well_id=Ww.well_id and pow.pro_id="+pro_id+")limit 1";
					Statement statepow=conn.createStatement();
					ResultSet rspow=statepow.executeQuery(powId);
					while(rspow.next()) {
						powCell_id=rspow.getInt(1);
					}
					String obpipId="select pip_id from  gw_db_schema.obsrvation_pipc where pip_id=(select MAX(pip_id)from gw_db_schema.obsrvation_pipc "
							+ "as obp,gw_db_schema.productive_well as pw,gw_db_schema.constwell_status as cws,gw_db_schema.con_discharge_test as cd,"
							+ "gw_db_schema.well_characteristics as ch,gw_db_schema.water_well as Ww where obp.pro_id=pw.pro_id and pw.cws_id=cws.cws_id "
							+ "and cws.cdt_id=cd.cdt_id and cd.ch_id=ch.ch_id and ch.well_id=Ww.well_id and obp.pro_id="+pro_id+")limit 1";
					Statement stateobPip=conn.createStatement();
					ResultSet rsobPip=stateobPip.executeQuery(obpipId);
					while(rsobPip.next()) {
						obCell_id=rsobPip.getInt(1);
					}
		             //Productive well status information Insertion Function call	
					addproductivewellStatusInfo(conn, powCell_id, scadaCell_id, obCell_id, pro_id,productiveWellCond,
							functionalWellCond,"","",null,"");
					}
		// Data from Excel sheet
	    if(null!=abandata && !abandata.isEmpty()){
	    	System.out.println("Transfered File Sile from the Main Controller is "+abandata.size());
		int contractorID=0,consultID=0,wellCell_id = 0,gwCell_id=0,casing_id=0,chCell_id = 0,fileSize = 0,checkwellIndex_id=0,wellconstanD_id=0;
		InputStream fileStream = null,streamingDatatoCheckinDB=null;
		String filetype = null, fileName = null,wellCellIndex = null,wellCellContemail = null,wellCellConsulemail = null,wellcellUsageStatus = null,nonPotableReason = null,
				wellCellProjectName = null,wellCellDataSource = null,wellCellIndexCheck,yieldsForSteps=null,permanentCasingName=null,
				wellCellContCN=null,wellCellConsultCN=null,wellProductivity=null,wellCell_totalScreen= null,wellCellTopScreen= null,
				wellCellBottomScreen= null;
		double wellCell_largeDrillId = 0,wellCell_TeleDrillId = 0,/*well georefer*/lat=0,longx=0,easting = 0,northing=0
				,wellcellDiameter = 0,hydrolicConductivity=0,transmissvity=0,storativity = 0;
		double /*steps draw down test Params*/noOfSteps = 0,eachStepDuration = 0,commulativeDD = 0,
		/*constant Discharge Parameter*/testDurationHrs = 0,dischargeTotalDD = 0,
		/*physical parameters*/temprature = 0,turbidity = 0,totalDissolvedSolid = 0,colorTrueColor = 0,electricConductivity = 0,wellEfficiency=0,
		/*Physical data Param*/pH = 0,total_Hardness_Ca_Mg_CaCO3=0,F_=0,Na_=0,Cl_=0,Ca_=0,Fe_=0,Mn_=0,K_=0,PO4_=0,Mg_=0,NO3PlusNO2_=0,SO4_2_=0,
		Hc03=0,No3=0,NH4PlusNH3=0,As=0,Pb=0,
		/*Micro Radio Params*/totalColiforms = 0,eColSpecific=0,radon=0,uranium=0;
		java.sql.Date wellCellCaseInstalDate=null;
		String listallWellIndex[] = null;
		double allyieldsForSteps[]=null;
		String drillingMethodArr[]=null;
		List<String> wellIndexList = new ArrayList<>();
		for (MultipartFile abanfile:abandata) {
			if(abanfile!=null && abanfile.getSize()>0){
				filetype= abanfile.getContentType();
				fileSize= (int) abanfile.getSize();
				fileName=abanfile.getOriginalFilename();
				streamingDatatoCheckinDB=abanfile.getInputStream();
				fileStream= abanfile.getInputStream();
				//System.out.println("File name Transfered from Main Controller is "+fileName);
			try (Workbook workbook = new XSSFWorkbook(fileStream)){
				List<String[]> excelCellData = new ArrayList<>();
				//System.out.println("Excel Sheet name is  "+sheet.getSheetName());
				String target="2.DrillingPumpTestHistoryData";
				Sheet matchingSheet = null;
	            double highestSimilarity = 0.0; 
		        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
	                String currentSheetName = workbook.getSheetName(i);
	                double similarity = calculateSimilarity(target, currentSheetName);
	                if (similarity > highestSimilarity) {
	                    highestSimilarity = similarity;
	                    matchingSheet = workbook.getSheetAt(i);
	                }
	            }
		        String specialCases[]= {"Trace","Trac","ND","nd","NULL","nill","nul","nu","NaN","nan","null","TR","tr","TR","trace","Nill",
						"Null","NALL","Nall","nall","nill","NILL","NONE","none","NON","Non","non","-","--","_","__","Decimal","Decimal "," Decimal",
						"_","Nil","ND","in","IN","iN","In"};
		        if (matchingSheet != null && highestSimilarity >0.80) { // 80% threshold
		    		for(int rowIndex=5;rowIndex<matchingSheet.getLastRowNum();rowIndex++){
		    			Row rowOpdata=matchingSheet.getRow(rowIndex);
		    			if(rowOpdata==null) {
		    				continue;
		    			}
		 String[] excelRowData = new String[87];
		 int dataColumnIndex = 0; // Index for the rowData array
		 boolean hasNonNullCell = false;
		for (int i=0;i<rowOpdata.getLastCellNum();i++) {
			Cell cell=rowOpdata.getCell(i);
			String value="";
			boolean result = false;
			//String regExforNumericandSigns = "^[-+]?\\d*\\.?\\d+([eE][-+]?\\d+)?$";
	            if (cell!= null && cell.getCellType()!= CellType.BLANK ){
	            	hasNonNullCell = true;
	            	switch (cell.getCellType()) {
	                case STRING:
	                	for(String elementA:specialCases) {
	                		if(elementA.equals(cell.getStringCellValue())){
			                    value="0";
			                    result=true;
			                    break;
		                    	}else {
			                    	String valueLocal=cell.getStringCellValue().trim();
			                    	String regex="(\\.)";
			                    	Pattern pattern = Pattern.compile(regex);
			                        Matcher matcher = pattern.matcher(valueLocal);
			                    	if(matcher.find()){
			                    		 // If a decimal is found, split the string at the first decimal
			                            String beforeFirstDecimal = valueLocal.substring(0, matcher.end());
			                            String afterFirstDecimal = valueLocal.substring(matcher.end());
			                            // Replace any subsequent decimal points in the "after" part
			                            String cleanedAfterPart = afterFirstDecimal.replaceAll("\\.", "");
				                             // Handle cases with multiple decimal points, e.g., take the first part
				                        value = beforeFirstDecimal + cleanedAfterPart;	
			                    	}else {
			                    		value=cell.getStringCellValue().trim();	
			                    	}
		                    	}
	                	}
	                    System.out.println("Cell Value is String: "+value);
	                    break;
	                case NUMERIC:
	                	if(DateUtil.isCellDateFormatted(cell)) {
	                		value=String.valueOf(cell.getDateCellValue()).trim();
	                	}else {
	                		 value=String.valueOf(cell.getNumericCellValue()).trim();
	                	}
	                	break;
	                case BOOLEAN:
	                	value=String.valueOf(cell.getBooleanCellValue()).trim();
	                	break;
	                case ERROR:
	                	value="0";
	                	break;
	                case _NONE:
	                	value="0";
	                	break;
	                case FORMULA:
	                	 // Evaluate formula to get the result
	                    FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
	                    CellValue cellValue = evaluator.evaluate(cell);
	                    switch (cellValue.getCellType()) {
	                        case STRING:
	                        	value= String.valueOf(cellValue.getStringValue()).trim();
	                            break;
	                        case NUMERIC:
	                        	value= String.valueOf(cellValue.getNumberValue()).trim();
	                            break;
	                        case BOOLEAN:
	                        	value= String.valueOf(cellValue.getBooleanValue()).trim();
	                            break;
	                        case BLANK:
	                        	value="0";
	                        	break;
	                        case ERROR:
	                        	value="0";
	                        	break;
	                        case _NONE:
	                        	value="0";
	                        	break;
	                        default:
	                        	value="0";
	                        	break;
	                    }
	                    break;
	                default:
	                	break;
	            }
	            }else {
	            	value="0";
	            }
	            if(dataColumnIndex<87){
	            	excelRowData[dataColumnIndex++]=value;   
	            }
	            	//WOrking With Further well Information       	
		    			    }
		    				if(hasNonNullCell) {
		    				excelCellData.add(excelRowData);
		    				System.out.println("The first Cell Data is "+excelRowData[1]);	
		    				}
		    				if(excelRowData[1]!=null) {
		    					String queryCheck="select count(*) from gw_db_schema.water_well as Ww where SIMILARITY(Ww.well_index,'"+rowOpdata.getCell(1)+"')=1";
		    		        	Statement stmntCheck=conn.createStatement();
		    		        	ResultSet rsCheckwellIndex=stmntCheck.executeQuery(queryCheck);
		    		        	rsCheckwellIndex.next();
		    		        	int checkWellIndexId=rsCheckwellIndex.getInt(1);
		    		        	if(checkWellIndexId>0){
		    		        		String drilledMethod=rowOpdata.getCell(36, MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
		    						if(drilledMethod.isEmpty()) {
		    							drillingMethodArr= new String[]{""};
		    							}
		    						else {drillingMethodArr= drilledMethod.split(",");
		    						}
		    						//step drawdown and 
		    						String value="";
		    						boolean result = false;
		    						Cell cell2=rowOpdata.getCell(47);
		    						 if (cell2!= null && cell2.getCellType()!= CellType.BLANK ){
				    						switch (cell2.getCellType()) {
				    						case STRING:
				    							for(String elementA:specialCases) {
				    		                		if(elementA.equals(cell2.getStringCellValue())){
				    				                    value="0";
				    				                    result=true;
				    				                    break;
				    			                    	}else {
				    				                    	String valueLocal=cell2.getStringCellValue().trim();
				    				                    	String regex="(\\.)";
				    				                    	Pattern pattern = Pattern.compile(regex);
				    				                        Matcher matcher = pattern.matcher(valueLocal);
				    				                    	if(matcher.find()){
				    				                    		 // If a decimal is found, split the string at the first decimal
				    				                            String beforeFirstDecimal = valueLocal.substring(0, matcher.end());
				    				                            String afterFirstDecimal = valueLocal.substring(matcher.end());
				    				                            // Replace any subsequent decimal points in the "after" part
				    				                            String cleanedAfterPart = afterFirstDecimal.replaceAll("\\.", "");
				    					                             // Handle cases with multiple decimal points, e.g., take the first part
				    					                        value = beforeFirstDecimal + cleanedAfterPart;	
				    				                    	}else {
				    				                    		value=cell2.getStringCellValue().trim();	
				    				                    	}
				    			                    	}
				    		                	}
				    		                    System.out.println("String Array Value before converting to Double: "+value);
				    		                    break;
				    						case NUMERIC:
				    		                	if(DateUtil.isCellDateFormatted(cell2)) {
				    		                		value=String.valueOf(cell2.getDateCellValue()).trim();
				    		                		System.out.println("Date Array Value before converting to Double: "+value);
				    		                	}else {
				    		                		 value=String.valueOf(cell2.getNumericCellValue()).trim();
				    		                		 System.out.println("Numeric Array Value before converting to Double: "+value);
				    		                	}
				    		                	break;
				    		                case BOOLEAN:
				    		                	value=String.valueOf(cell2.getBooleanCellValue()).trim();
				    		                	break;
				    		                case ERROR:
				    		                	value="0.0";
				    		                	break;
				    		                case _NONE:
				    		                	value="0.0";
				    		                	break;
				    		                case FORMULA:
				    		                	 // Evaluate formula to get the result
				    		                    FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
				    		                    CellValue cellValue = evaluator.evaluate(cell2);
				    		                    switch (cellValue.getCellType()) {
				    		                        case STRING:
				    		                        	value= String.valueOf(cellValue.getStringValue()).trim();
				    		                            break;
				    		                        case NUMERIC:
				    		                        	value= String.valueOf(cellValue.getNumberValue()).trim();
				    		                            break;
				    		                        case BOOLEAN:
				    		                        	value= String.valueOf(cellValue.getBooleanValue()).trim();
				    		                            break;
				    		                        case BLANK:
				    		                        	value="0.0";
				    		                        	break;
				    		                        case ERROR:
				    		                        	value="0.0";
				    		                        	break;
				    		                        case _NONE:
				    		                        	value="0.0";
				    		                        	break;
				    		                        default:
				    		                        	value="0.0";
				    		                        	break;
				    		                    }
				    		                    break;
				    		                default:
				    		                	break;
				    						} 
		    						 }
		    						if(value.isEmpty()) {
		    							allyieldsForSteps= new double[] {0.0};
		    							}
		    						else {
		    							String[] stringNumbers = value.split(",");
		    							allyieldsForSteps=new double[stringNumbers.length];
		    							for(int i=0;i<stringNumbers.length;i++) {
		    								allyieldsForSteps[i]=Double.parseDouble(stringNumbers[i]);
		    								}
		    							}	
		    		        	}	
		    				}
		    		}
	            }
		        else {
                	//Error,Datasheet Mismatch
				File ff= new File(0001,"Datasheet Mismatch. The Datasheet Name Must Similar to  <html><b>'"+target+"'</html>","<font color=#FF0000>Error,</font>");
				uploadReport.add(ff);
		        	//System.out.println("Wrong Sheet! The sheet you Should store will like "+target);
                }
				//String commaSpardata=rowData.stream().collect(Collectors.joining(",")
				for (int h=0;h<excelCellData.size();h++) {
					String rowData[]=excelCellData.get(h);
					System.out.println("elements in the Array is "+rowData[1]);
					String queryCheck="select count(*) from gw_db_schema.water_well as Ww where SIMILARITY(Ww.well_index,'"+rowData[1]+"')=1";
	            	Statement stmntCheck=conn.createStatement();
	            	ResultSet rsCheckwellIndex=stmntCheck.executeQuery(queryCheck);
	            	rsCheckwellIndex.next();
	            	int checkWellIndexId=rsCheckwellIndex.getInt(1);
	            	if(checkWellIndexId>0){
	            		String regex="^[+-]?\\d*(\\.\\d+)?$";
                    	Pattern pattern = Pattern.compile(regex);
		            for (int i=0;i<rowData.length;i++) {
		            	 Matcher matcher = pattern.matcher(rowData[i]);
	                    	if(matcher.find()){
	                    		rowData[i]=rowData[i].replaceAll("\\s+", "");
	                    	}
		            		wellCellIndex=rowData[1];
		            		System.out.println("Well Index inside Array After Trim is "+wellCellIndex);
							wellCell_largeDrillId=Double.parseDouble(rowData[3]);
							wellCell_TeleDrillId=Double.parseDouble(rowData[4]);
							longx= Double.parseDouble(rowData[16]);
							lat= Double.parseDouble(rowData[17]);
							easting= Double.parseDouble(rowData[18]);
							northing= Double.parseDouble(rowData[19]);
							wellCellProjectName=rowData[21];
							wellCellContCN=rowData[26];
							wellCellContemail=rowData[27];
							wellCellConsultCN=rowData[29];
							wellCellConsulemail=rowData[30];
							wellCellDataSource=rowData[32];
							permanentCasingName=rowData[37];
							//System.out.println("permanentCasingName inside Array is "+permanentCasingName);
							wellCell_totalScreen=rowData[39];
							wellCellTopScreen=rowData[40];
							wellCellBottomScreen=rowData[41];
							noOfSteps=Double.parseDouble(rowData[45]);
							eachStepDuration=Double.parseDouble(rowData[46]);
							commulativeDD=Double.parseDouble(rowData[48]);
							testDurationHrs=Double.parseDouble(rowData[49]);
							dischargeTotalDD=Double.parseDouble(rowData[53]);
							wellEfficiency=Double.parseDouble(rowData[55]);
							hydrolicConductivity=Double.parseDouble(rowData[56]);
							transmissvity=Double.parseDouble(rowData[57]);
							storativity=Double.parseDouble(rowData[58]);
							wellProductivity=rowData[59];
							//System.out.println("storativity inside Array is "+storativity);
							temprature=Double.parseDouble(rowData[60].strip());
							//System.out.println("temprature inside Array is "+temprature);
							turbidity=Double.parseDouble(rowData[61]);
							//System.out.println("turbidity inside Array is "+turbidity);
							totalDissolvedSolid=Double.parseDouble(rowData[62]);
							colorTrueColor=Double.parseDouble(rowData[63]);
							electricConductivity=Double.parseDouble(rowData[64]);
							pH=Double.parseDouble(rowData[65]);
							total_Hardness_Ca_Mg_CaCO3=Double.parseDouble(rowData[66]);
							F_=Double.parseDouble(rowData[67]);
							Na_=Double.parseDouble(rowData[68]);
							Cl_=Double.parseDouble(rowData[69]);
							Ca_=Double.parseDouble(rowData[70]);
							Fe_=Double.parseDouble(rowData[71]);
							Mn_=Double.parseDouble(rowData[72]);
							K_=Double.parseDouble(rowData[73]);
							PO4_=Double.parseDouble(rowData[74]);
							Mg_=Double.parseDouble(rowData[75]);
							NO3PlusNO2_=Double.parseDouble(rowData[76]);
							SO4_2_=Double.parseDouble(rowData[77]);
							Hc03=Double.parseDouble(rowData[78]);
							No3=Double.parseDouble(rowData[79]);
							NH4PlusNH3=Double.parseDouble(rowData[80]);
							As=Double.parseDouble(rowData[81]);
							Pb=Double.parseDouble(rowData[82]);
							totalColiforms=Double.parseDouble(rowData[83]);
							eColSpecific=Double.parseDouble(rowData[84]);
							radon=Double.parseDouble(rowData[85]);
							uranium=Double.parseDouble(rowData[86]);
		            	}
		            System.out.println("Well index named "+wellCellIndex+" is Check for null value and Data type");
		            System.out.println("Large Hydrolic Conductivity ID is also check and converted from String "+hydrolicConductivity);
		            System.out.println("Large Transimissivity is also check and converted from String "+transmissvity);
					//WOrking With Further well Information
						//collect All Well Index Name
						wellIndexList.add(wellCellIndex);
						//WOrking With Further well Information
						String queryselectwellIDforreport="select Ww.well_id from gw_db_schema.water_well as Ww where "
								+ "SIMILARITY(Ww.well_index,'"+wellCellIndex+"')=1";
						Statement stmntwellIdforReport=conn.createStatement();
						ResultSet rswellIDforRepo = stmntwellIdforReport.executeQuery(queryselectwellIDforreport);
						while(rswellIDforRepo.next()){
							wellCell_id=rswellIDforRepo.getInt(1);
						}
						//System.out.println("Well index is "+wellCellIndex+" and Hydrolic Conductivity is "+hydrolicConductivity);
						updateWellcharactesitics(conn, hydrolicConductivity,transmissvity,storativity,wellEfficiency,wellProductivity,wellCell_id);
						
						//Insert extra well Information
						String queryselectUserId="select id from gw_db_schema.db_user where SIMILARITY(orgname,'"+wellCellContCN+"')>0.85 and "
								+ "usertype='Contractor'";
						Statement stmntUserID=conn.createStatement();
						ResultSet rsUserId = stmntUserID.executeQuery(queryselectUserId);
						while(rsUserId.next()) {
							contractorID=rsUserId.getInt(1);
							//System.out.println("Contructor Id to Insert into Extra Information table "+contractorID+"");
						}
						//Insert extra well Information
						String queryselectConsultUserId="select id from gw_db_schema.db_user where SIMILARITY(orgname,'"+wellCellConsultCN+"')>0.85 and "
								+ "usertype='Consultant'";
						Statement stmntConsultUserId=conn.createStatement();
						ResultSet rsConsultUserId = stmntConsultUserId.executeQuery(queryselectConsultUserId);
						while(rsConsultUserId.next()) {
							consultID=rsConsultUserId.getInt(1);
						}
						//Water Well Extra or Further Information
						String querytocontrolDuplicate="select count(*) from gw_db_schema.extrawellinfo as ewi,gw_db_schema.water_well as ww where "
								+"ww.well_id=ewi.well_id and ewi.well_id="+wellCell_id+"";
						Statement stmntControlDup=conn.createStatement();
						ResultSet rsControlDup=stmntControlDup.executeQuery(querytocontrolDuplicate);
						rsControlDup.next();
						int duplicateValue=rsControlDup.getInt(1);
						if(duplicateValue>0) {
							//System.out.println("Well Id "+wellCell_id+" is already Exists in Extra Information Table.");
						}else {
							addwellextraInfo(conn, wellCell_id, contractorID, consultID, wellCell_largeDrillId, wellCell_TeleDrillId, wellcellDiameter,
									wellCellProjectName,wellCellDataSource,drillingMethodArr);	
						}
						 //Ground water Data insertion function call
						int duplicateValueGW;
						String querytocontrolDuplicateGW="select count(*) from gw_db_schema.gw_data as gw,gw_db_schema.water_well as ww where "
								+ "ww.well_id=gw.well_id and gw.well_id="+wellCell_id+"";
						Statement stmntControlDupGW=conn.createStatement();
						ResultSet rsControlDupGW=stmntControlDupGW.executeQuery(querytocontrolDuplicateGW);
						rsControlDupGW.next();
						duplicateValueGW=rsControlDupGW.getInt(1);
						if(duplicateValueGW>0) {
						System.out.println("Well Id "+wellCell_id+" is Already Exists in gw_data Table.");	
						}else {
						addgroundwaterData(conn, wellCell_id, wellcellUsageStatus,nonPotableReason,"Well Completion Report",recorDate,db_userID);
						System.out.println("Well Id "+wellCell_id+" is Added to DB in gw_data Table.");
						}
						//Working on Water Characteristics Data
						String checkCharWell="select ch_id from gw_db_schema.well_characteristics where ch_id=(select MAX(ch_id)from "
								+ "gw_db_schema.well_characteristics as ch,gw_db_schema.water_well as Ww where ch.well_id=Ww.well_id and "
								+ "SIMILARITY(Ww.well_index,'"+wellCellIndex+"')>0.98)limit 1";
						Statement statementCheckcharcter=conn.createStatement();
						ResultSet rsCheckWellcharct=statementCheckcharcter.executeQuery(checkCharWell);
						if(rsCheckWellcharct.next()) {
							chCell_id=rsCheckWellcharct.getInt(1);
							}
						//Search Water Characteristics Data
						String checkCharConst="select cdt_id from gw_db_schema.con_discharge_test where cdt_id=(select MAX(cdt_id) from "
								+ "gw_db_schema.con_discharge_test as cd,gw_db_schema.well_characteristics as ch,gw_db_schema.water_well as Ww where "
								+ "cd.ch_id=ch.ch_id and Ww.well_id=ch.well_id and ch.ch_id="+chCell_id+" and "
								+ "cd.data_colln_case='Well Completion Report')limit 1";
						Statement statementconstD=conn.createStatement();
						ResultSet rsCheckconst=statementconstD.executeQuery(checkCharConst);
						if(rsCheckconst.next()) {
							wellconstanD_id=rsCheckconst.getInt(1);
							}
						//Insert into step draw down test and Constant discharge table
						insertStepDrawDownTest(conn,(int)noOfSteps,(int)eachStepDuration,allyieldsForSteps,commulativeDD,"Well Completion Report",
								recorDate,chCell_id);
						updateconstantDischargeTest(conn,(int)testDurationHrs,dischargeTotalDD,"",chCell_id);
						
						//Working on groundwater data
						String checkGWWell="select gw.gw_id from gw_db_schema.gw_data as gw,gw_db_schema.water_well as Ww "
								+ "where gw.well_id = Ww.well_id and SIMILARITY(Ww.well_index,'"+wellCellIndex+"')=1";
						Statement statementCheckGW=conn.createStatement();
						ResultSet rsCheckGW=statementCheckGW.executeQuery(checkGWWell);
						if(rsCheckGW.next()) {
							gwCell_id=rsCheckGW.getInt(1);
							}
						//Insert into Chemical, Physical and MicroBilogical Test Data
						String querytocontrolDuplicateWQE="select count(*) from gw_db_schema.physical_da as pd,gw_db_schema.gw_data as gw,"
								+ "gw_db_schema.water_well as ww where pd.gw_id=gw.gw_id and ww.well_id=gw.well_id and pd.gw_id="+gwCell_id+"";
						Statement stmntControlDupWQE=conn.createStatement();
						ResultSet rsControlDupWQE=stmntControlDupWQE.executeQuery(querytocontrolDuplicateWQE);
						rsControlDupWQE.next();
						int duplicateValueWQE=rsControlDupWQE.getInt(1);
						if(duplicateValueWQE>0) {
							//System.out.println("gw_Id "+gwCell_id+" is already Exists in gw_data Table.");
						}
						else {
						insertToPhysicalTable(conn,gwCell_id,temprature,turbidity,totalDissolvedSolid,colorTrueColor,electricConductivity,
								"Well Completion Report",recorDate);
						insertToChemicalTable(conn,gwCell_id,recorDate,pH,total_Hardness_Ca_Mg_CaCO3,F_,Na_,Cl_,Ca_,Fe_,Mn_,K_,PO4_,Mg_,NO3PlusNO2_,
								SO4_2_,Hc03,No3,NH4PlusNH3,As,Pb,"Well Completion Report");
						insertToMicroRadioTable(conn,gwCell_id,totalColiforms,eColSpecific,radon,uranium,"Well Completion Report",recorDate);	
						}
						//update Constructed well status table
						udateWellStatus(conn,gwCell_id,wellconstanD_id);
						//Update Casing Information
						String querycasing="select ca.ci_id from gw_db_schema.casing_info as ca,gw_db_schema.water_well as Ww where "
								+ "Ww.well_id=ca.well_id and SIMILARITY(Ww.well_index,'"+wellCellIndex+"')>0.98";
						Statement stmntcasing=conn.createStatement();
						ResultSet rscasing = stmntcasing.executeQuery(querycasing);
						while(rscasing.next()) {
							casing_id=rscasing.getInt(1);
							//System.out.println("Well Id is "+well_id);
						}
						//Calling Well Casing Information Update Function 
						updateWellcasingInfo(conn,wellCell_totalScreen,wellCellTopScreen,wellCellBottomScreen,wellCellCaseInstalDate,permanentCasingName,
								casing_id);	  
				}else {
					System.out.println("No Well Index in the DB");
	        	}
					}		
			byte[] fileContent = readFileToByteArrayB(streamingDatatoCheckinDB);
			String hashvalue = calculateSHA256B(fileContent);
			String re_wellreport="select repo_id from gw_db_schema.wellreport where hash_value='"+hashvalue+"' group by repo_id";
			Statement stmntwellReport=conn.createStatement();
			ResultSet rswellReport=stmntwellReport.executeQuery(re_wellreport);
		if(rswellReport.next()){
			wellrepo_id=rswellReport.getInt(1);
			//System.out.println("What is wrong with this result statement..."+wellrepo_id);
			}
			else {
				listallWellIndex=wellIndexList.toArray(new String[1]);
				//Store well report that contains all the wells stored in the DB
				Array sqlArray_listallwellIndex=conn.createArrayOf("text",listallWellIndex);
				PreparedStatement prepstatewellReport=conn.prepareStatement("insert into gw_db_schema.wellreport(wellindexlist,recordate,"
						+ "filename,filesize,filetype,reportdoc,hash_value)values(?,?,?,?,?,?,?)");
				prepstatewellReport.setArray(1,sqlArray_listallwellIndex);
				prepstatewellReport.setDate(2, recorDate);
				prepstatewellReport.setString(3, fileName);
				prepstatewellReport.setInt(4, fileSize);
				prepstatewellReport.setString(5, filetype);
				prepstatewellReport.setBytes(6,fileContent);
				prepstatewellReport.setString(7, hashvalue);
				prepstatewellReport.executeUpdate();
				prepstatewellReport.getUpdateCount();
				prepstatewellReport.close();
				String querywellRepotoInsert="select repo_id from gw_db_schema.wellreport where SIMILARITY(filename, '"+fileName+"')>0.88";
				Statement stmntwellRepo=conn.createStatement();
				ResultSet rswellRepo=stmntwellRepo.executeQuery(querywellRepotoInsert);
				while(rswellRepo.next()){
					wellrepo_id=rswellRepo.getInt(1);
					}
				//Calling well characteristics updating function
				udateWellStatusRepoId(conn, wellrepo_id);
			}
			}
			}
		}       
		}	
		//Insertion Report
		String sqlWaterWell ="select well_id,wellcode,well_index from gw_db_schema.water_well where SIMILARITY(well_index,'"+wellIndex+"')=1";
		Statement stmntwell=conn.createStatement();
		ResultSet rsWaterWell = stmntwell.executeQuery(sqlWaterWell);
		while (rsWaterWell.next()){
			int well_idSelect=rsWaterWell.getInt(1);
			String wellCode=rsWaterWell.getString(2);
		    String wellInde = rsWaterWell.getString(3);
		    System.out.println("Well Index Added into DB are: "+wellInde);
		 File ff= new File(well_idSelect,"Drilling Pump Test History Data is Successfully Stored in the Repository",
				 "<font color=#06402B>Success,</font>");
		 uploadReport.add(ff);
		}			
				stmnt.close();
				conn.close();
					
			}
			}
} catch (Exception e) {
		System.out.println("Error...."+e);
	}
	return uploadReport; 
}
//Helper method to convert primitive double[] to Double[]
private static Double[] allYesldsTObjectArray(double[] primitiveArray) {
 Double[] objectArray = new Double[primitiveArray.length];
 for (int i = 0; i < primitiveArray.length; i++) {
     objectArray[i] = primitiveArray[i];
 }
 return objectArray;
}
//save other doc
public static List<File> saveotherofficefile(List<MultipartFile> operationalDocuments,int db_userID){
	List<File> operationalFiles=new ArrayList<File>();
	String operationalFilename,operationalFileType;
	int operationalfileSize,operationrepo_id = 0,wellCell_id = 0,enumId = 0,wellCharacter_id = 0,GW_id=0,constructedWell_id=0,productiveWell_id=0,
			SCADAsystem_id = 0,power_id = 0,observationPiP_id = 0,checkWellindex = 0,SCADAsystemID=0,
			wellconstanD_id=0,well_idCheckUpdateStatus=0,contruct_id=0,duplicateValueGW=0,constDischargeTestId=0,pumpingStatusId=0,GWStatusId=0,
			physicalID=0,chemicalId=0,constWellId=0,productiveWellIdforUpdate=0,SCADAId=0;
	InputStream operationaldataStream=null,operationInputStreamWorkBook;
	String[] SensorTypes = null;
	//variable deffinition
	String wellCellIndex = null,enumratorCampanyN = null,enumratorName = null,enumratoremail = null,enumratorPhone = null,currentWellStatus = null,
			reasonNonFunctioning = null,functioningwellCondn = null,inActiveReason=null,wellHeadCondition = null,poorWellHeadReason=null,drainageCondition=null,
			powerSource=null,standByGeneratorAvialable=null,generatorStatus=null,observationPiPAvialable=null,statusOfObservationPIP=null,
			SCADAConnection=null,SCADAstatus=null,monitoringSencorIns=null,typesOfSensors=null,waterColor = null,
			waterOdor=null,Cyield_Dyield = null,gwPotableStatus = null,nonPotableReason = null,waterUsageLicence = null,
			extrnalpolun=null,goodwtrquality=null,poorwaterQuality_mineral=null,poorwaterqualityrsn=null,highdrawdown=null,yielddeclining=null,
			rapidyielddecline=null,goodcondition=null,changepumpcapacity=null,loyerpumpposition=null,wellcleaning=null,wellProductivity=null,
			comprehensive_reha=null,qualitytest_disinf=null,mantaindrainage=null,headmantainreconst=null,abandonmentseal=null,abandonmentreason=null
			,waterquality=null,qltyproblem=null,remark=null,rehabconducted=null,rehabCompanyName=null,rehabEmail=null,rehabPhone=null,pumpHead=null,
			dataCategory=null,dataStatus=null;
	java.sql.Date dataRecordedDate = null,dateOfFunctionStoped=null,operationStartedDate=null,rehabdate=null;
	double generatorPower = 0,staticWL = 0,dynamicWL=0,abstractionRate = 0,elecConductivity=0,pHValue=0,temprature=0,pumpPosition = 0,
			pumpCapacity = 0,salinity=0,swl_before=0,dwl_before=0,swl_after=0,dwl_after=0,pump_pos=0,pump_power=0,yield=0,spfc_capacity=0,
					effciency = 0,hconductivity=0,transimitivity=0,storativity=0;
	String listallWellIndex[] = null;
	List<String> wellIndexList = new ArrayList<>();
	conn=connection.dbconnection();
	if(null!=operationalDocuments && operationalDocuments.size()>0){
	 for(MultipartFile pumpfile:operationalDocuments){
		 operationalFilename=pumpfile.getOriginalFilename();
		 operationalFileType=pumpfile.getContentType();
		 operationalfileSize=(int) pumpfile.getSize();
		 try {
			operationaldataStream=pumpfile.getInputStream();
			operationInputStreamWorkBook=pumpfile.getInputStream();
			try (Workbook workbook = new XSSFWorkbook(operationInputStreamWorkBook)) {
				//System.out.println("Contents in Work Book is "+workbook);
				//Sheet sheet = workbook.getSheetAt(3);
				List<String[]> excelCellData = new ArrayList<>();
				String target="3-DetaiOperations Well Inventor";
				String DPTHDTarget="2.DrillingPumpTestHistoty Data";
				Sheet matchingSheet = null;
				Sheet matchingDPTHDataSheet = null;
	            double highestSimilarity = 0.0; 
	            double highestDPTHDataSimilarity = 0.0; 
		        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
	                String currentSheetName = workbook.getSheetName(i);
	                double similarity = calculateSimilarity(target, currentSheetName);
	                double DPTHDataSimilarity=calculateSimilarity(DPTHDTarget, currentSheetName);
	                if (similarity > highestSimilarity) {
	                    highestSimilarity = similarity;
	                    matchingSheet = workbook.getSheetAt(i);
	                }
	                if(DPTHDataSimilarity>highestDPTHDataSimilarity){
	                	highestDPTHDataSimilarity=DPTHDataSimilarity;
	                	matchingDPTHDataSheet=workbook.getSheetAt(i);
	                }
	            }
		        if (matchingSheet != null && highestSimilarity > 0.80) { // 80% threshold
		    		for(int rowIndex=5;rowIndex<matchingSheet.getLastRowNum();rowIndex++){
		    			Row rowOpdata=matchingSheet.getRow(rowIndex);
				if(rowOpdata==null) {
					continue;}
				 String[] excelRowData = new String[113];
				 int dataColumnIndex = 0; // Index for the rowData array
				 boolean hasNonNullCell = false;
				for (int i=0;i<rowOpdata.getLastCellNum();i++) {
					Cell cell=rowOpdata.getCell(i);
					String value="";
					String specialCases[]= {"Trace","Trac","ND","nd","NULL","nill","nul","nu","NaN","nan","null","tr","TR","trace","Nill",
							"Null","NALL","Nall","nall","nill","NILL","NONE","none","NON","Non","non","-","--","_","__","Decimal","Decimal ",
							" Decimal","_","Nil","ND"};
					boolean result = false;
					//String regExforNumericandSigns = "^[-+]?\\d*\\.?\\d+([eE][-+]?\\d+)?$";
					//String regExforStringoranySpecialChar = "^(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9])(?=.*\\d).*$";
					//Pattern checkInput= Pattern.compile(regExforNumericandSigns);
			            if (cell!= null && cell.getCellType()!= CellType.BLANK ){
			            	 hasNonNullCell = true;
			            	switch (cell.getCellType()) {
			                case STRING:
			                	for(String elementA:specialCases) {
		                		if(elementA.equals(cell.getStringCellValue())){
				                    value="0";
				                    result=true;
				                    break;
			                    	}else {
			                    	String valueLocal=cell.getStringCellValue().trim();
			                    	String regex="(\\.)";
			                    	Pattern pattern = Pattern.compile(regex);
			                        Matcher matcher = pattern.matcher(valueLocal);
			                    	if(matcher.find()){
			                    		 // If a decimal is found, split the string at the first decimal
			                            String beforeFirstDecimal = valueLocal.substring(0, matcher.end());
			                            String afterFirstDecimal = valueLocal.substring(matcher.end());
			                            // Replace any subsequent decimal points in the "after" part
			                            String cleanedAfterPart = afterFirstDecimal.replaceAll("\\.", "");
				                             // Handle cases with multiple decimal points, e.g., take the first part
				                        value = beforeFirstDecimal + cleanedAfterPart;	
			                    	}else {
			                    		value=cell.getStringCellValue().trim();	
			                    	}
			                    	}
			                	}
			                    break;
			                case NUMERIC:
			                	if(DateUtil.isCellDateFormatted(cell)) {
			                		value=String.valueOf(cell.getLocalDateTimeCellValue()).trim();
			                	}else{
			                		value=String.valueOf(cell.getNumericCellValue()).trim();
			                	}
			                	break;
			                case BOOLEAN:
			                	value=String.valueOf(cell.getBooleanCellValue()).trim();
			                	break;
			                case FORMULA:
			                	 // Evaluate formula to get the result
                               FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                               CellValue cellValue = evaluator.evaluate(cell);
                               switch (cellValue.getCellType()) {
                                   case STRING:
                                   	value= String.valueOf(cellValue.getStringValue()).trim();
                                       break;
                                   case NUMERIC:
                                   	value= String.valueOf(cellValue.getNumberValue()).trim();
                                       break;
                                   case BOOLEAN:
                                   	value= String.valueOf(cellValue.getBooleanValue()).trim();
                                       break;
                                   case BLANK:
                                	   value="0";
                                	   break;
                                   case ERROR:
                                	   value="0";
                                	   break;
                                   case _NONE:
                                	   value="0";
                                	   break;
                                   default:
                                   	break;
                               }
                               break;
			                default:
			                	break;
			            }
			            }else {
			            	value="0";
			            }
			            if(dataColumnIndex<113){
			            	excelRowData[dataColumnIndex++]=value;   
			            }
			            	//WOrking With Further well Information       	
			    }
				if(hasNonNullCell) {
					excelCellData.add(excelRowData);	
				}
			//System.out.println("The first Cell Data is "+rowOpdata.getCell(1));
			String queryCheck="select count(*) from gw_db_schema.water_well as Ww where SIMILARITY(Ww.well_index,'"+rowOpdata.getCell(1)+"')=1";
           	Statement stmntCheck=conn.createStatement();
           	ResultSet rsCheckwellIndex=stmntCheck.executeQuery(queryCheck);
           	rsCheckwellIndex.next();
           	int checkWellIndexId=rsCheckwellIndex.getInt(1);
           	if(checkWellIndexId>0){
           		typesOfSensors=rowOpdata.getCell(46, MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
				if(typesOfSensors.isEmpty()) {
					SensorTypes= new String[] {""};
				}else {
					SensorTypes = typesOfSensors.split(",");
				}
				}
				}
		    	}else {
                	//Error,Datasheet Mismatch
				File ff= new File(00001,"<font color=#FF0000>Error,</font>","Datasheet Mismatch. The Datasheet Name Must Similar to  <html><b>'"+target+"'</html>","",0);
		        	operationalFiles.add(ff);
		        	//System.out.println("Wrong Sheet! The sheet you Should store will like "+target);
                }
				//Access Excel file
				//String commaSpardata=rowData.stream().collect(Collectors.joining(",")
				for (String []rowData: excelCellData) {
					//System.out.println("Well Index for DB availablity checking is "+rowData[1]);
					String queryCheck="select count(*) from gw_db_schema.water_well as Ww where SIMILARITY(Ww.well_index,'"+rowData[1]+"')=1";
	            	Statement stmntCheck=conn.createStatement();
	            	ResultSet rsCheckwellIndex=stmntCheck.executeQuery(queryCheck);
	            	rsCheckwellIndex.next();
	            	int checkWellIndexId=rsCheckwellIndex.getInt(1);
	            	if(checkWellIndexId>0){
	            		String regex="^[+-]?\\d*(\\.\\d+)?$";
                    	Pattern pattern = Pattern.compile(regex);
		            for (int i=0;i<rowData.length;i++) {
		            	if(rowData[i]!=null) {
		            		Matcher matcher = pattern.matcher(rowData[i]);
	                    	if(matcher.find()){
	                    		rowData[i]=rowData[i].replaceAll("\\s+", "");
	                    	}	
		            	}
		            		wellCellIndex=rowData[1];
		            		waterUsageLicence=rowData[23];
		            		String recordDateS;
		            		if(rowData[24].equals("0") || rowData[24].equals("")) {
		            			recordDateS="1970-01-01";
		            		}else {
		            			recordDateS=rowData[24];	
		            		}
		            		java.util.Date recordDate=new SimpleDateFormat("yyyy-MM-dd").parse(recordDateS);
		            		dataRecordedDate= new java.sql.Date(recordDate.getTime());
		            		enumratorCampanyN=rowData[25];
		            		enumratorName=rowData[26];
		            		enumratoremail=rowData[27];
		            		enumratorPhone=rowData[28];
		            		currentWellStatus=rowData[29];
		            		String functionStopDate;
		            		if(rowData[30].equals("0")) {
		            			functionStopDate="1970-01-01";
		            		}else {
		            			functionStopDate=rowData[30];	
		            		}
		            		java.util.Date functionCheckDate=new SimpleDateFormat("yyyy-MM-dd").parse(functionStopDate);
		            		dateOfFunctionStoped=new java.sql.Date(functionCheckDate.getTime());
		            		reasonNonFunctioning=rowData[31];
		            		functioningwellCondn=rowData[32];
		            		inActiveReason=rowData[33];
		            		wellHeadCondition=rowData[34];
		            		poorWellHeadReason=rowData[35];
		            		drainageCondition=rowData[36];
		            		powerSource=rowData[37];
		            		standByGeneratorAvialable=rowData[38];
		            		generatorStatus=rowData[39];
		            		generatorPower=Double.parseDouble(rowData[40]);
		            		observationPiPAvialable=rowData[41];
		            		statusOfObservationPIP=rowData[42];
		            		SCADAConnection=rowData[43];
		            		SCADAstatus=rowData[44];
		            		monitoringSencorIns=rowData[45];
		            		staticWL=Double.parseDouble(rowData[47]);
		            		dynamicWL=Double.parseDouble(rowData[48]);
		            		abstractionRate=Double.parseDouble(rowData[49]);
		            		elecConductivity=Double.parseDouble(rowData[50]);
		            		pHValue=Double.parseDouble(rowData[51]);
							temprature=Double.parseDouble(rowData[52]);
							waterColor=rowData[53];
							waterOdor=rowData[54];
							String operationStartDate;
		            		if(rowData[55].equals("0")) {
		            			operationStartDate="1970-01-01";
		            		}else {
		            			operationStartDate=rowData[55];	
		            		}
							java.util.Date funStopejavaDate=new SimpleDateFormat("yyyy-MM-dd").parse(operationStartDate);
							operationStartedDate = new java.sql.Date(funStopejavaDate.getTime());
							pumpPosition=Double.parseDouble(rowData[56]);
							pumpCapacity=Double.parseDouble(rowData[60]);
							pumpHead=rowData[61];
							Cyield_Dyield=rowData[63];
							gwPotableStatus=rowData[64];
							nonPotableReason=rowData[65];
							wellProductivity=rowData[66];
							goodcondition=rowData[67];
							goodwtrquality=rowData[68];
							highdrawdown=rowData[69];
							yielddeclining=rowData[70];
							rapidyielddecline=rowData[71];
							poorwaterQuality_mineral=rowData[72];
							extrnalpolun=rowData[73];
							changepumpcapacity=rowData[74];
							loyerpumpposition=rowData[75];
							wellcleaning=rowData[76];
							comprehensive_reha=rowData[77];
							qualitytest_disinf=rowData[78];
							mantaindrainage=rowData[79];
							headmantainreconst=rowData[80];
							abandonmentseal=rowData[81];
							abandonmentreason=rowData[82];
							rehabconducted=rowData[92];
							String rehabdateString;
		            		if(rowData[93].equals("0") || rowData[93].equals("")) {
		            			rehabdateString="1970-01-01";
		            		}else {
		            			rehabdateString="1970-01-01";	
		            		}
		            		java.util.Date rehabdatejavaDate=new SimpleDateFormat("yyyy-MM-dd").parse(rehabdateString);
		            		rehabdate = new java.sql.Date(rehabdatejavaDate.getTime());
		            		rehabCompanyName=rowData[94];
							rehabEmail=rowData[95];
							rehabPhone=rowData[96];
							swl_before=Double.parseDouble(rowData[97]);
							swl_after=Double.parseDouble(rowData[98]);
							dwl_before=Double.parseDouble(rowData[99]);
							dwl_after=Double.parseDouble(rowData[100]);
							pump_pos=Double.parseDouble(rowData[101]);
							pump_power=Double.parseDouble(rowData[102]);
							yield=Double.parseDouble(rowData[103]);
							spfc_capacity=Double.parseDouble(rowData[104]);
							effciency=Double.parseDouble(rowData[105]);
							hconductivity=Double.parseDouble(rowData[106]);
							transimitivity=Double.parseDouble(rowData[107]);
							storativity=Double.parseDouble(rowData[108]);
							waterquality=rowData[109];
							qltyproblem=rowData[110];
							remark=rowData[112];
		            	}
		            String querycheckWellIndexPresents="select count(*) from gw_db_schema.con_discharge_test as cdt,gw_db_schema.well_characteristics as ch,"
		            		+ "gw_db_schema.chemical_da as chd,gw_db_schema.gw_data as gw,gw_db_schema.water_well as ww where cdt.ch_id=ch.ch_id and "
		            		+ "chd.gw_id=gw.gw_id and gw.well_id=ww.well_id and ch.well_id=ww.well_id and cdt.recordate='"+dataRecordedDate+"' and "
		            		+ "cdt.data_colln_case='Operational' and SIMILARITY(ww.well_index,'"+wellCellIndex+"')>0.98";
					Statement stmntwellIndex=conn.createStatement();
					ResultSet rswellIndex = stmntwellIndex.executeQuery(querycheckWellIndexPresents);
					rswellIndex.next();
					checkWellindex=rswellIndex.getInt(1);
					if(checkWellindex>0){
						//Error,Well index is not in the Data Repository.No Update is take placed
						File ff= new File(0,"<font color=#8B8000>Warning,</font>","Detail Operation Well Inventory Data is Updated Previously",0);
			        	operationalFiles.add(ff);
					}else {
						//collect All Well Index Name
						wellIndexList.add(wellCellIndex.trim());
						//WOrking With Further well Information
						String queryselectwellIDforreport="select Ww.well_id from gw_db_schema.water_well as Ww where "
								+ "SIMILARITY(Ww.well_index,'"+wellCellIndex+"')>0.98";
						Statement stmntwellIdforReport=conn.createStatement();
						ResultSet rswellIDforRepo = stmntwellIdforReport.executeQuery(queryselectwellIDforreport);
						if(rswellIDforRepo.next()){
							wellCell_id=rswellIDforRepo.getInt(1);
						}
				//check and Update Groundwater data
				 String querycheckGWstatus="select gw.gw_id from gw_db_schema.gw_data as gw,gw_db_schema.water_well as ww where gw.well_id=ww.well_id"
				 		+ " and gw.recordate < ? and gw.data_colln_case='Operational' and gw.datacondtion='Current' and ww.well_id="+wellCell_id+"";
				  // Use PreparedStatement to prevent SQL injection and handle date types correctly
			        try (PreparedStatement pstmt = conn.prepareStatement(querycheckGWstatus)) {
			            // Set the user input date as a parameter
			            pstmt.setObject(1, dataRecordedDate); // The pgJDBC driver handles LocalDate mapping
			            try (ResultSet rs = pstmt.executeQuery()) {
			                if (rs.next()) {
			                	GWStatusId=rs.getInt(1);
			                    String updatePumpCurrent="update gw_db_schema.gw_data set datacondtion='Historical' where "
			                    		+ "gw_id="+GWStatusId+"";
			                    try (PreparedStatement preparedStatement = conn.prepareStatement(updatePumpCurrent)){
			                		preparedStatement.executeUpdate();
			                	}
			                }
			            }
			        }
					 //Ground water Data insertion function call
					addGroundwaterData(conn, wellCell_id, gwPotableStatus,nonPotableReason,"Operational",dataRecordedDate,db_userID,"Current");
					//Search Ground Water Data
					String searchGWData="select gw.gw_id from gw_db_schema.gw_data as gw where gw.gw_id=(SELECT MAX(gw.gw_id) from gw_db_schema.gw_data as "
							+ "gw,gw_db_schema.water_well as Ww where gw.well_id = Ww.well_id and gw.well_id="+wellCell_id+" and "
							+ "gw.data_colln_case='Operational')limit 1";
					Statement statementGWD=conn.createStatement();
					ResultSet rsCheckGWD=statementGWD.executeQuery(searchGWData);
					if(rsCheckGWD.next()) {
						GW_id=rsCheckGWD.getInt(1);
						}
					//check and Update water chemistry data
					 String querycheckWchemistrystatus="select phd.pdata_id,wch.chda_id from gw_db_schema.physical_da as phd,gw_db_schema.chemical_da"
					 		+ " as wch,gw_db_schema.gw_data as gw,gw_db_schema.water_well as ww where phd.gw_id=gw.gw_id and wch.gw_id=gw.gw_id and "
					 		+ "gw.well_id=ww.well_id and (phd.recordate < ? OR wch.recordate < ?) and (phd.data_colln_case='Operational' OR "
					 		+ "wch.data_colln_case='Operational') and (phd.datacondtion='Current' OR wch.datacondtion='Current') and gw.gw_id="+GW_id+"";
					  // Use PreparedStatement to prevent SQL injection and handle date types correctly
				        try (PreparedStatement pstmt = conn.prepareStatement(querycheckWchemistrystatus)) {
				            // Set the user input date as a parameter
				            pstmt.setObject(1, dataRecordedDate); // The pgJDBC driver handles LocalDate mapping
				            pstmt.setObject(2, dataRecordedDate); // The pgJDBC driver handles LocalDate mapping
				            try (ResultSet rs = pstmt.executeQuery()) {
				                if (rs.next()) {
				                	physicalID=rs.getInt(1);
				                	chemicalId=rs.getInt(2);
				                    String updatePumpCurrent="update gw_db_schema.physical_da set datacondtion='Historical' where "
				                    		+ "pdata_id="+physicalID+";"
				                    		+ "update gw_db_schema.chemical_da set datacondtion='Historical' where chda_id="+chemicalId+"";
				                    try (PreparedStatement preparedStatement = conn.prepareStatement(updatePumpCurrent)){
				                		preparedStatement.executeUpdate();
				                	}
				                }
				            }
				        }
					//Insert into Chemical, Physical and MicroBilogical Test Data
					addPhysicaldata(conn,GW_id,temprature,elecConductivity,dataRecordedDate,"Operational",waterColor,waterOdor,"Current");
					addChemicaldata(conn,GW_id,dataRecordedDate,pHValue,"Operational","Current");
					//Search Water Characteristics Data
					String checkCharWell="select ch_id from gw_db_schema.well_characteristics where ch_id=(select MAX(ch_id)from "
							+ "gw_db_schema.well_characteristics as ch,gw_db_schema.water_well as Ww where ch.well_id=Ww.well_id and "
							+ "ch.well_id="+wellCell_id+")limit 1";
					Statement statementCheckcharcter=conn.createStatement();
					ResultSet rsCheckWellcharct=statementCheckcharcter.executeQuery(checkCharWell);
					if(rsCheckWellcharct.next()) {
						wellCharacter_id=rsCheckWellcharct.getInt(1);
						}
					//check and Update Constant discharge test data
					 String querycheckdate="select cdt.cdt_id from gw_db_schema.con_discharge_test as cdt,gw_db_schema.well_characteristics as ch,"
					 		+ "gw_db_schema.water_well as ww where cdt.ch_id=ch.ch_id and ch.well_id=ww.well_id and cdt.recordate < ? and "
					 		+ "cdt.data_colln_case='Operational' and cdt.datacondtion='Current' and ch.ch_id="+wellCharacter_id+"";
					  // Use PreparedStatement to prevent SQL injection and handle date types correctly
				        try (PreparedStatement pstmt = conn.prepareStatement(querycheckdate)) {
				            // Set the user input date as a parameter
				            pstmt.setObject(1, dataRecordedDate); // The pgJDBC driver handles LocalDate mapping
				            try (ResultSet rs = pstmt.executeQuery()) {
				                if (rs.next()) {
				                	constDischargeTestId=rs.getInt(1);
				                    String updatePreCurrent="update gw_db_schema.con_discharge_test set datacondtion='Historical' where "
				                    		+ "cdt_id="+constDischargeTestId+"";
				                    try (PreparedStatement preparedStatement = conn.prepareStatement(updatePreCurrent)){
				                		preparedStatement.executeUpdate();
				                	}
				                }
				            }
				        }
			        //check and Update Constant discharge test data
					 String querycheckPumpstatus="select ps.pump_id from gw_db_schema.pumping_status as ps,gw_db_schema.well_characteristics as ch,"
					 		+ "gw_db_schema.water_well as ww where ps.ch_id=ch.ch_id and ch.well_id=ww.well_id and ps.recordate < ? and "
					 		+ "ps.data_colln_case='Operational' and ps.datacondtion='Current' and ch.ch_id="+wellCharacter_id+"";
					  // Use PreparedStatement to prevent SQL injection and handle date types correctly
				        try (PreparedStatement pstmt = conn.prepareStatement(querycheckPumpstatus)) {
				            // Set the user input date as a parameter
				            pstmt.setObject(1, dataRecordedDate); // The pgJDBC driver handles LocalDate mapping
				            try (ResultSet rs = pstmt.executeQuery()) {
				                if (rs.next()) {
				                	pumpingStatusId=rs.getInt(1);
				                    String updatePumpCurrent="update gw_db_schema.pumping_status set datacondtion='Historical' where "
				                    		+ "pump_id="+pumpingStatusId+"";
				                    try (PreparedStatement preparedStatement = conn.prepareStatement(updatePumpCurrent)){
				                		preparedStatement.executeUpdate();
				                	}
				                }
				            }
				        }
					addConstantDischargeTestData(conn,staticWL,dynamicWL,wellCharacter_id,"Operational",dataRecordedDate,"Current");
					//Pumping Status
					addPumpingStatusData(conn,wellCharacter_id,pumpCapacity,pumpPosition,"",pumpHead,abstractionRate,Cyield_Dyield,"Operational",
							dataRecordedDate,"Current");
					//Select Constant Discharge Test
					//Search Water Characteristics Data
					String checkCharConst="select cdt_id from gw_db_schema.con_discharge_test where cdt_id=(select MAX(cdt_id) from "
							+ "gw_db_schema.con_discharge_test as cd,gw_db_schema.well_characteristics as ch,gw_db_schema.water_well as Ww where "
							+ "cd.ch_id=ch.ch_id and Ww.well_id=ch.well_id and ch.ch_id="+wellCharacter_id+" and cd.data_colln_case='Operational')limit 1";
					Statement statementconstD=conn.createStatement();
					ResultSet rsCheckconst=statementconstD.executeQuery(checkCharConst);
					if(rsCheckconst.next()) {
						wellconstanD_id=rsCheckconst.getInt(1);
						}
								//Enumerator Registration
		            String query_checkEnumeratorEmail="select id from gw_db_schema.db_user where SIMILARITY(fullname,'"+enumratorName+"')>0.80 "
		            		+ "and usertype='Enumerator'";
					Statement stmntcheckEnumEmail=conn.createStatement();
					ResultSet rstcheckEnumEmail=stmntcheckEnumEmail.executeQuery(query_checkEnumeratorEmail);
					if (rstcheckEnumEmail.next()){
					int checkEmail=rstcheckEnumEmail.getInt(1);
						enumId=checkEmail;
					}else {
					PreparedStatement prepstateDataEnum=conn.prepareStatement("insert into gw_db_schema.db_user(fullname,orgname,email,phone,usertype,"
							+ "wellindex)values('"+enumratorName+"','"+enumratorCampanyN+"',?,'"+enumratorPhone+"',"
									+ "'Enumerator','"+wellCellIndex+"')");
					if(enumratoremail.equals("0") || enumratoremail.equals("non")|| enumratoremail.equals("None")||enumratoremail.equals("none")) {
						prepstateDataEnum.setNull(1,java.sql.Types.VARCHAR);
					}else {
						prepstateDataEnum.setString(1, enumratoremail);	
					}
					prepstateDataEnum.executeUpdate();
					String query_dataEnum="select id from gw_db_schema.db_user where SIMILARITY(fullname,'"+enumratorName+"')>0.85 and "
							+ "usertype='Enumerator'";
					Statement stmntDataEnum=conn.createStatement();
					ResultSet rsDataEnum=stmntDataEnum.executeQuery(query_dataEnum);
					while(rsDataEnum.next()){
						enumId=rsDataEnum.getInt(1);
					}	
					}
			//check and Update Constant discharge test data
			 String querycheckConstWellstatus="select cws.cws_id from gw_db_schema.constwell_status as cws,gw_db_schema.con_discharge_test as cdt,"
			 		+ "gw_db_schema.well_characteristics as ch,gw_db_schema.water_well as ww where cws.cdt_id=cdt.cdt_id and cdt.ch_id=ch.ch_id and"
			 		+ " ch.well_id=ww.well_id and cws.record_date < ? and cws.data_colln_case='Operational' and cws.datacondtion='Current' and "
			 		+ "cdt.cdt_id="+wellconstanD_id+"";
			  // Use PreparedStatement to prevent SQL injection and handle date types correctly
		        try (PreparedStatement pstmt = conn.prepareStatement(querycheckConstWellstatus)) {
		            // Set the user input date as a parameter
		            pstmt.setObject(1, dataRecordedDate); // The pgJDBC driver handles LocalDate mapping
		            try (ResultSet rs = pstmt.executeQuery()) {
		                if (rs.next()) {
		                	constWellId=rs.getInt(1);
		                    String updatePumpCurrent="update gw_db_schema.constwell_status set datacondtion='Historical' where "
		                    		+ "cws_id="+constWellId+"";
		                    try (PreparedStatement preparedStatement = conn.prepareStatement(updatePumpCurrent)){
		                		preparedStatement.executeUpdate();
		                	}
		                }
		            }
		        }
					addConstractedWellStatusdata(conn,wellconstanD_id,GW_id,currentWellStatus,dataRecordedDate,"Operational",enumId,"Current");
					//Productive Well Data Access
					//Insert to productive well status info
					String checkProWell="select cws_id from gw_db_schema.constwell_status where cws_id=(select MAX(cws_id)from "
							+ "gw_db_schema.constwell_status as cws,gw_db_schema.con_discharge_test as cd,gw_db_schema.well_characteristics as ch,"
							+ "gw_db_schema.gw_data as gw,gw_db_schema.water_well as Ww where cd.cdt_id=cws.cdt_id and cd.ch_id=ch.ch_id and "
							+ "cws.gw_id=gw.gw_id and ch.well_id=Ww.well_id and gw.well_id=Ww.well_id and cws.well_status='Productive' and "
							+ "gw.gw_id="+GW_id+" and cd.cdt_id="+wellconstanD_id+")limit 1";
					Statement statementCheckwellStatus=conn.createStatement();
					ResultSet rsCheckWellSatatus=statementCheckwellStatus.executeQuery(checkProWell);
					if(rsCheckWellSatatus.next()) {
						constructedWell_id=rsCheckWellSatatus.getInt(1);
					}
					//check and Update Productive well data
					 String querycheckProducstatus="select pro.pro_id from gw_db_schema.productive_well as pro,gw_db_schema.constwell_status as cws,"
					 		+ "gw_db_schema.con_discharge_test as cdt,gw_db_schema.well_characteristics as ch,gw_db_schema.water_well as ww where "
					 		+ "pro.cws_id=cws.cws_id and cws.cdt_id=cdt.cdt_id and cdt.ch_id=ch.ch_id and ch.well_id=ww.well_id and "
					 		+ "pro.record_date < ? and pro.data_colln_case='Operational' and pro.datacondtion='Current' and "
					 		+ "cws.cws_id="+constructedWell_id+"";
					  // Use PreparedStatement to prevent SQL injection and handle date types correctly
				        try (PreparedStatement pstmt = conn.prepareStatement(querycheckProducstatus)) {
				            // Set the user input date as a parameter
				            pstmt.setObject(1, dataRecordedDate); // The pgJDBC driver handles LocalDate mapping
				            try (ResultSet rs = pstmt.executeQuery()) {
				                if (rs.next()) {
				                	productiveWellIdforUpdate=rs.getInt(1);
				                    String updatePumpCurrent="update gw_db_schema.productive_well set datacondtion='Historical' where "
				                    		+ "pro_id="+productiveWellIdforUpdate+"";
				                    try (PreparedStatement preparedStatement = conn.prepareStatement(updatePumpCurrent)){
				                		preparedStatement.executeUpdate();
				                	}
				                }
				            }
				        }
							addProductiveWellData(conn,constructedWell_id,"Operational",dataRecordedDate,waterUsageLicence,wellHeadCondition,
							poorWellHeadReason,drainageCondition,operationStartedDate,"Current");
						
				     String productiveWellId="select pro_id from gw_db_schema.productive_well where pro_id=(select MAX(pw.pro_id) from "
				     		+ "gw_db_schema.productive_well as pw,gw_db_schema.constwell_status as cws,gw_db_schema.con_discharge_test as cd,"
				     		+ "gw_db_schema.well_characteristics as ch,gw_db_schema.gw_data as gw,gw_db_schema.water_well as Ww where "
				     		+ "pw.cws_id=cws.cws_id and cws.cdt_id=cd.cdt_id and cd.ch_id=ch.ch_id and cws.gw_id=gw.gw_id and ch.well_id=Ww.well_id "
				     		+ "and gw.well_id=Ww.well_id and cws.cws_id="+constructedWell_id+")limit 1";
					 Statement stateProduc=conn.createStatement();
					 ResultSet rsProduc=stateProduc.executeQuery(productiveWellId);
					 if(rsProduc.next()) {
					 	productiveWell_id=rsProduc.getInt(1);
					 }
					//check and Update Constant discharge test data
					 String querycheckSCADAstatus="select scd.sca_id from gw_db_schema.scada_systemc as scd,gw_db_schema.productive_well as pro,"
					 		+ "gw_db_schema.constwell_status as cws,gw_db_schema.con_discharge_test as cdt,gw_db_schema.well_characteristics as ch,"
					 		+ "gw_db_schema.water_well as ww where scd.pro_id=pro.pro_id and pro.cws_id=cws.cws_id and cws.cdt_id=cdt.cdt_id and "
					 		+ "cdt.ch_id=ch.ch_id and ch.well_id=ww.well_id and scd.recordate < ? and scd.data_colln_case='Operational' and "
					 		+ "scd.datacondtion='Current' and pro.pro_id="+productiveWell_id+"";
					  // Use PreparedStatement to prevent SQL injection and handle date types correctly
				        try (PreparedStatement pstmt = conn.prepareStatement(querycheckSCADAstatus)) {
				            // Set the user input date as a parameter
				            pstmt.setObject(1, dataRecordedDate); // The pgJDBC driver handles LocalDate mapping
				            try (ResultSet rs = pstmt.executeQuery()) {
				                if (rs.next()) {
				                	SCADAId=rs.getInt(1);
				                    String updatePumpCurrent="update gw_db_schema.scada_systemc set datacondtion='Historical' where "
				                    		+ "sca_id="+SCADAId+"";
				                    try (PreparedStatement preparedStatement = conn.prepareStatement(updatePumpCurrent)){
				                		preparedStatement.executeUpdate();
				                	}
				                }
				            }
				        }
							// Update SCADA status
						addSCADASystemData(conn, productiveWell_id,SCADAConnection,SCADAstatus,monitoringSencorIns,SensorTypes,"Operational",
								dataRecordedDate, "Current");
						//power system status data
						addPowerSystemData(conn, productiveWell_id, powerSource,standByGeneratorAvialable, generatorStatus, generatorPower);
						//Observation PIP data
						addObservationPIPData(conn, productiveWell_id, observationPiPAvialable, statusOfObservationPIP);
					 //Productive well status information Insertion Function call
					String scadaId="select sca_id from gw_db_schema.scada_systemc where sca_id=(select MAX(sca_id)from gw_db_schema.scada_systemc as scad,"
							+ "gw_db_schema.productive_well as pw,gw_db_schema.constwell_status as cws,gw_db_schema.gw_data as gw,"
							+ "gw_db_schema.con_discharge_test as cd,gw_db_schema.well_characteristics as ch,gw_db_schema.water_well as Ww where "
							+ "scad.pro_id=pw.pro_id and pw.cws_id=cws.cws_id and cws.cdt_id=cd.cdt_id and cd.ch_id=ch.ch_id and "
							+ "cws.gw_id=gw.gw_id and ch.well_id=Ww.well_id and gw.well_id=Ww.well_id and scad.pro_id="+productiveWell_id+")limit 1";
					Statement statescada=conn.createStatement();
					ResultSet rsscada=statescada.executeQuery(scadaId);
					if(rsscada.next()) {
						SCADAsystem_id=rsscada.getInt(1);
					}
					String powId="select pow_id from gw_db_schema.power_systemc where pow_id=(select MAX(pow_id)from gw_db_schema.power_systemc as pow,"
							+ "gw_db_schema.productive_well as pw,gw_db_schema.constwell_status as cws,gw_db_schema.gw_data as gw,"
							+ "gw_db_schema.con_discharge_test as cd,gw_db_schema.well_characteristics as ch,gw_db_schema.water_well as Ww where "
							+ "pow.pro_id=pw.pro_id and pw.cws_id=cws.cws_id and cws.cdt_id=cd.cdt_id and cd.ch_id=ch.ch_id and "
							+ "cws.gw_id=gw.gw_id and ch.well_id=Ww.well_id and gw.well_id=Ww.well_id and pow.pro_id="+productiveWell_id+")limit 1";
					Statement statepow=conn.createStatement();
					ResultSet rspow=statepow.executeQuery(powId);
					while(rspow.next()) {
						power_id=rspow.getInt(1);
					}
					String obpipId="select pip_id from gw_db_schema.obsrvation_pipc where pip_id=(select MAX(pip_id)from gw_db_schema.obsrvation_pipc "
							+ "as obp,gw_db_schema.productive_well as pw,gw_db_schema.constwell_status as cws,gw_db_schema.gw_data as gw,"
							+ "gw_db_schema.con_discharge_test as cd,gw_db_schema.well_characteristics as ch,gw_db_schema.water_well as Ww where "
							+ "obp.pro_id=pw.pro_id and pw.cws_id=cws.cws_id and cws.cdt_id=cd.cdt_id and cd.ch_id=ch.ch_id and cws.gw_id=gw.gw_id and "
							+ "ch.well_id=Ww.well_id and gw.well_id=Ww.well_id and obp.pro_id="+productiveWell_id+")limit 1";
					Statement stateobPip=conn.createStatement();
					ResultSet rsobPip=stateobPip.executeQuery(obpipId);
					if(rsobPip.next()) {
						observationPiP_id=rsobPip.getInt(1);
					}
					int CheckProIDDUP=0;
					String querytochechproIDDupl="select count(*) from gw_db_schema.productive_well_status where prws_id="
					 		+ "(select MAX(prws_id)from gw_db_schema.productive_well_status as pws,gw_db_schema.productive_well "
					 		+ "as pw,gw_db_schema.constwell_status as cws,gw_db_schema.con_discharge_test as cd,"
					 		+ "gw_db_schema.well_characteristics as ch,gw_db_schema.water_well as Ww where pws.pro_id=pw.pro_id and "
					 		+ "pw.cws_id=cws.cws_id and cws.cdt_id=cd.cdt_id and cd.ch_id=ch.ch_id and ch.well_id=Ww.well_id and "
					 		+ "pw.data_colln_case='Operational' and pw.record_date='"+dataRecordedDate+"' and pws.pro_id="+productiveWell_id+")limit 1";
							Statement stmntselectproIDDUP=conn.createStatement();
							ResultSet rsselectProIDDUP=stmntselectproIDDUP.executeQuery(querytochechproIDDupl);
							rsselectProIDDUP.next();
							CheckProIDDUP=rsselectProIDDUP.getInt(1);
							if(CheckProIDDUP>0){
								System.out.println("Duplicated Productive well id from productive well status table");
							}else {
								addproductivewellStatusInfon(conn,power_id ,SCADAsystem_id, observationPiP_id,productiveWell_id,currentWellStatus,
										functioningwellCondn,inActiveReason,reasonNonFunctioning,dateOfFunctionStoped,"Current");	
							}
						//select from productive well status to insert into water quality trend
						int pwsID=0,wct_id=0,wqt_id=0,recomand_id=0,rehabconduc_Id = 0;
						 String querytoselectIdfromPWS="select prws_id from gw_db_schema.productive_well_status where prws_id="
						 		+ "(select MAX(prws_id)from gw_db_schema.productive_well_status as pws,gw_db_schema.productive_well "
						 		+ "as pw,gw_db_schema.constwell_status as cws,gw_db_schema.con_discharge_test as cd,"
						 		+ "gw_db_schema.well_characteristics as ch,gw_db_schema.water_well as Ww where pws.pro_id=pw.pro_id and "
						 		+ "pw.cws_id=cws.cws_id and cws.cdt_id=cd.cdt_id and cd.ch_id=ch.ch_id and ch.well_id=Ww.well_id and "
						 		+ "pw.data_colln_case='Operational' and pw.record_date='"+dataRecordedDate+"' and pws.pro_id="+productiveWell_id+")limit 1";
								Statement stmntselectPWSId=conn.createStatement();
								ResultSet rsselectPWSId=stmntselectPWSId.executeQuery(querytoselectIdfromPWS);
								while(rsselectPWSId.next()) {
									pwsID=rsselectPWSId.getInt(1);
								}
								addwaterQualityTrend(conn,pwsID,salinity,temprature,extrnalpolun,goodwtrquality,poorwaterQuality_mineral,
								poorwaterqualityrsn="Not Defined");
								addwellcoditionTrend(conn,pwsID,highdrawdown,yielddeclining,rapidyielddecline,goodcondition);
					String querytoselectWQTId="select wqt_id from gw_db_schema.waterqualitytrend where wqt_id=(select MAX(wqt_id)from "
							+ "gw_db_schema.waterqualitytrend as wqt,gw_db_schema.productive_well_status as pws,gw_db_schema.productive_well as pw,"
							+ "gw_db_schema.constwell_status as cws,gw_db_schema.con_discharge_test as cd,gw_db_schema.well_characteristics as ch,"
							+ "gw_db_schema.water_well as Ww where wqt.pws_id=pws.prws_id and pws.pro_id=pw.pro_id and pw.cws_id=cws.cws_id and "
							+ "cws.cdt_id=cd.cdt_id and cd.ch_id=ch.ch_id and ch.well_id=Ww.well_id and wqt.pws_id="+pwsID+")limit 1";
							Statement stmntselectWQTId=conn.createStatement();
							ResultSet rsselectWQTId=stmntselectWQTId.executeQuery(querytoselectWQTId);
							while(rsselectWQTId.next()) {
								wqt_id=rsselectWQTId.getInt(1);
							}
				String querytoselectWCT="select wellco_id from gw_db_schema.wellcoditiontrend where wellco_id=(select MAX(wellco_id)from "
						+ "gw_db_schema.wellcoditiontrend as wct,gw_db_schema.productive_well_status as pws,gw_db_schema.productive_well as pw,"
						+ "gw_db_schema.constwell_status as cws,gw_db_schema.con_discharge_test as cd,gw_db_schema.well_characteristics as ch,"
						+ "gw_db_schema.water_well as Ww where wct.pws_id=pws.prws_id and pws.pro_id=pw.pro_id and pw.cws_id=cws.cws_id and "
						+ "cws.cdt_id=cd.cdt_id and cd.ch_id=ch.ch_id and ch.well_id=Ww.well_id and wct.pws_id="+pwsID+")limit 1";
						Statement stmntselectWCTId=conn.createStatement();
						ResultSet rsselectWCTId=stmntselectWCTId.executeQuery(querytoselectWCT);
						while(rsselectWCTId.next()) {
							wct_id=rsselectWCTId.getInt(1);
						}
						addRecomendation(conn,wct_id,wqt_id,changepumpcapacity,loyerpumpposition,wellcleaning,comprehensive_reha,qualitytest_disinf,
						mantaindrainage,headmantainreconst,abandonmentseal,abandonmentreason);	
				String querytoselectRecomId="select recom_id from gw_db_schema.recomendation where recom_id=(select MAX(recom_id)from "
						+ "gw_db_schema.recomendation as rec,gw_db_schema.wellcoditiontrend as wct,gw_db_schema.productive_well_status as pws,"
						+ "gw_db_schema.productive_well as pw,gw_db_schema.constwell_status as cws,gw_db_schema.con_discharge_test as cd,"
						+ "gw_db_schema.well_characteristics as ch,gw_db_schema.water_well as Ww where rec.wellco_id=wct.wellco_id and wct.pws_id="
						+ "pws.prws_id and pws.pro_id=pw.pro_id and pw.cws_id=cws.cws_id and cws.cdt_id=cd.cdt_id and cd.ch_id=ch.ch_id and "
						+ "ch.well_id=Ww.well_id and rec.wellco_id="+wct_id+")limit 1";
						Statement stmntselectRecomId=conn.createStatement();
						ResultSet rsselectRecomId=stmntselectRecomId.executeQuery(querytoselectRecomId);
						while(rsselectRecomId.next()) {
							recomand_id=rsselectRecomId.getInt(1);
						}
						addRehabConducted(conn,recomand_id,rehabconducted,rehabdate,0);
			   String querytoselectRCondId="select rc_id from gw_db_schema.rehabconducted where rc_id=(select MAX(rc_id)from "
			   		+ "gw_db_schema.rehabconducted as rcon,gw_db_schema.recomendation as rec,gw_db_schema.wellcoditiontrend as wct,"
			   		+ "gw_db_schema.productive_well_status as pws,gw_db_schema.productive_well as pw,gw_db_schema.constwell_status as cws,"
			   		+ "gw_db_schema.con_discharge_test as cd,gw_db_schema.well_characteristics as ch,gw_db_schema.water_well as Ww where "
			   		+ "rcon.recom_id=rec.recom_id and rec.wellco_id=wct.wellco_id and wct.pws_id=pws.prws_id and pws.pro_id=pw.pro_id and "
			   		+ "pw.cws_id=cws.cws_id and cws.cdt_id=cd.cdt_id and cd.ch_id=ch.ch_id and ch.well_id=Ww.well_id and rcon.recom_id="
			   		+ ""+recomand_id+")limit 1";
					Statement stmntselectRCondId=conn.createStatement();
					ResultSet rsselectRCondId=stmntselectRCondId.executeQuery(querytoselectRCondId);
					while(rsselectRCondId.next()) {
						rehabconduc_Id=rsselectRCondId.getInt(1);
					}
					addRehabProgram(conn,contruct_id,swl_before,swl_after,dwl_before,dwl_after,pump_pos,pump_power,waterquality,
							qltyproblem,yield,spfc_capacity,effciency,hconductivity,transimitivity,storativity,rehabconduc_Id,remark);
					
					//Update report
					//Insertion Report
					String sqlWaterWell ="select well_id,well_index from gw_db_schema.water_well where "
							+ "SIMILARITY(well_index,'"+wellCellIndex+"')>0.98";
					Statement stmntwell=conn.createStatement();
					ResultSet rsWaterWell = stmntwell.executeQuery(sqlWaterWell);
					while (rsWaterWell.next()) {
						well_idCheckUpdateStatus=rsWaterWell.getInt(1);
					String wellInde = rsWaterWell.getString(2);
					System.out.println("Well Index Added into DB are "+wellInde+ "and Well Index is "+well_idCheckUpdateStatus);
					File ff= new File(well_idCheckUpdateStatus,"<font color=#06402B>Success,</font>",
							"Detail Operation Well Inventory Data is Successfully Updated",0);
					operationalFiles.add(ff);
					}
					}
		            }
	            	else if(well_idCheckUpdateStatus==0){
	            		//Error,Well index is not in the Data Repository.No Update is take placed
						File ff= new File(0001,"<font color=#FF0000>Error,</font>","Unkown Well Index or Well Index is Not Available "
								+ "to Update Datasets from Detail Operation Well Inventory Data",0);
			        	operationalFiles.add(ff);
	            	}
		            }
				
			 byte[] fileContent = readFileToByteArray(operationaldataStream);
			String hashvalue = calculateSHA256(fileContent);
		//check if file is already in DB
			String re_wellreport="select repo_id from gw_db_schema.wellreport where hash_value='"+hashvalue+"'";
			Statement stmntwellReport=conn.createStatement();
			ResultSet rswellReport=stmntwellReport.executeQuery(re_wellreport);
			if(rswellReport.next()) {
				operationrepo_id=rswellReport.getInt(1);
			}
			else {
				if(matchingDPTHDataSheet!= null && matchingSheet!= null && highestSimilarity > 0.80 && highestDPTHDataSimilarity>0.80) {
					dataCategory="Drilling Pumping Test History and Detail Operation Well Data";
				}else if(matchingSheet!= null && highestSimilarity > 0.80){
					dataCategory="Detail Operation Well Inventory Data";
				}
				//Insert Into Report file
				if(wellIndexList.toArray(new String[0])!=null && wellIndexList.toArray(new String[0]).length>0) {
					listallWellIndex=wellIndexList.toArray(new String[0]);
					//Store well report that contains all the wells stored in the DB
					Array sqlArray_listallwellIndex=conn.createArrayOf("text",listallWellIndex);
					PreparedStatement prepstatewellReport=conn.prepareStatement("insert into gw_db_schema.wellreport(wellindexlist,recordate,"
							+ "filename,filesize,filetype,reportdoc,hash_value,dataCategory)values(?,?,?,?,?,?,?,?)");
					prepstatewellReport.setArray(1,sqlArray_listallwellIndex);
					prepstatewellReport.setDate(2, dataRecordedDate);
					prepstatewellReport.setString(3, operationalFilename);
					prepstatewellReport.setInt(4, operationalfileSize);
					prepstatewellReport.setString(5, operationalFileType);
					prepstatewellReport.setBytes(6, fileContent);
					prepstatewellReport.setString(7, hashvalue);
					prepstatewellReport.setString(8, dataCategory);
					prepstatewellReport.executeUpdate();
					prepstatewellReport.getUpdateCount();
					prepstatewellReport.close();
					System.out.println("All Well indexs from the array List are "+listallWellIndex.length);
					String querywellRepotoInsert="select repo_id from gw_db_schema.wellreport where hash_value='"+hashvalue+"' and "
							+ "SIMILARITY(filename, '"+operationalFilename+"')>0.80";
					Statement stmntwellRepo=conn.createStatement();
					ResultSet rswellRepo=stmntwellRepo.executeQuery(querywellRepotoInsert);
					if(rswellRepo.next()){
						operationrepo_id=rswellRepo.getInt(1);
						}
					//Calling well characteristics updating function
					udateWellStatusRepoId(conn, operationrepo_id);
				}}}
			conn.close();
				}catch (Exception e) {
						e.printStackTrace();
					}
			 }
		  }
	
	return operationalFiles; 
}
//Insert into GW data
private static void addGroundwaterData(Connection connection, int well_id,String wellcellUsageStatus,String nonPotableReason,String datacollectCase,
		java.sql.Date recordDate,int db_UserID,String dataCondition)throws Exception {
String query = "insert into gw_db_schema.gw_data(well_id,potability_status,non_potable_reason,data_colln_case,recordate,db_userid,datacondtion)"
		+ "values(?,?,?,?,?,?,?)";
try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
	    preparedStatement.setInt(1, well_id);
		preparedStatement.setString(2, wellcellUsageStatus);
		preparedStatement.setString(3, nonPotableReason);
		preparedStatement.setString(4, datacollectCase);
		preparedStatement.setDate(5, recordDate);
		preparedStatement.setInt(6, db_UserID);
		preparedStatement.setString(7, dataCondition);
        preparedStatement.executeUpdate();
}
}
//Insert into Constructed Well status
private static void addConstractedWellStatusdata(Connection connection, int ch_id,int gw_id,String wellStatus,java.sql.Date recordDate,
		String datacollectCase,int enumId,String dataCondition)throws Exception {
String query = "insert into gw_db_schema.constwell_status(cdt_id,gw_id,well_status,record_date,data_colln_case,enumid,"
		+ "datacondtion,productive_well_status)values(?,?,?,?,?,?,?,?)";
try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
	    preparedStatement.setInt(1, ch_id);    
	    preparedStatement.setInt(2, gw_id);
		preparedStatement.setString(3, "Productive");
		preparedStatement.setDate(4, recordDate);
		preparedStatement.setString(5, datacollectCase);
		preparedStatement.setInt(6, enumId);
		preparedStatement.setString(7, dataCondition);
		preparedStatement.setString(8, wellStatus);
        preparedStatement.executeUpdate();
}
}
//Insert into Pumping Status
private static void addPumpingStatusData(Connection connection,int ch_id,double pump_capacity,double pump_position,String pumpStstus,
		String pump_head,double abstraction_rate,String currentY_designY,String data_colln_case,java.sql.Date recordDate,String dataCondtion)
		throws Exception {
String query = "insert into gw_db_schema.pumping_status(ch_id,pump_capacity,pump_position,pump_status,pumphead,abstraction_rate,currenty_designy,"
		+ "data_colln_case,recordate,datacondtion)values(?,?,?,?,?,?,?,?,?,?)";
try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	preparedStatement.setInt(1, ch_id);
	preparedStatement.setDouble(2, pump_capacity);
	preparedStatement.setDouble(3, pump_position);
	preparedStatement.setString(4, pumpStstus);
	preparedStatement.setString(5, pump_head);
	preparedStatement.setDouble(6, abstraction_rate);
	preparedStatement.setString(7, currentY_designY);
	preparedStatement.setString(8, data_colln_case);
	preparedStatement.setDate(9, recordDate);
	preparedStatement.setString(10, dataCondtion);	
  preparedStatement.executeUpdate();
}
}
//Insert into Constant Discharge Rate
private static void addConstantDischargeTestData(Connection connection,double dischargeTestSWL,double dischargeTestDWL, int chCell_id,
		String dataCollectionCases,java.sql.Date recordDate,String dataCondtion)
		throws Exception {
String query = "insert into gw_db_schema.con_discharge_test(swl_m,dwl_m,ch_id,data_colln_case,recordate,datacondtion)values(?,?,?,?,?,?)";
try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	preparedStatement.setDouble(1, dischargeTestSWL);
	preparedStatement.setDouble(2, dischargeTestDWL);
	preparedStatement.setInt(3, chCell_id);
	preparedStatement.setString(4, dataCollectionCases);
	preparedStatement.setDate(5, recordDate);
	preparedStatement.setString(6, dataCondtion);	
    preparedStatement.executeUpdate();
}
}
//Insert to Physical Table
private static void addPhysicaldata(Connection connection,int gwCell_id,double temprature,double electricConductivity,java.sql.Date recorDate,
		String dataCollectedCase,String waterColor,String waterOdor,String dataCondition)
		throws Exception {
String query = "insert into gw_db_schema.physical_da(gw_id,temprature,elec_condu,recordate,data_colln_case,water_color,water_odor,datacondtion)"
		+ "values(?,?,?,?,?,?,?,?)";
try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		preparedStatement.setInt(1, gwCell_id);
		preparedStatement.setDouble(2, temprature);
		preparedStatement.setDouble(3, electricConductivity);
		preparedStatement.setDate(4, recorDate);
		preparedStatement.setString(5, dataCollectedCase);
		preparedStatement.setString(6, waterColor);
		preparedStatement.setString(7, waterOdor);
		preparedStatement.setString(8, dataCondition);
		preparedStatement.executeUpdate();
}
}
//Insert into Chemical Table
private static void addChemicaldata(Connection connection,int gwCell_id,java.sql.Date recorDate,double pH,String dataCollectedCase,
		String dataCondition)
		throws Exception {
String query = "insert into gw_db_schema.chemical_da(gw_id,recordate,ph,data_colln_case,datacondtion)values(?,?,?,?,?)";
try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	preparedStatement.setInt(1, gwCell_id);
	preparedStatement.setDate(2, recorDate);
	preparedStatement.setDouble(3, pH);
	preparedStatement.setString(4, dataCollectedCase);
	preparedStatement.setString(5, dataCondition);
    preparedStatement.executeUpdate();
}
}
//Productive Well Data
private static void addProductiveWellData(Connection connection,int cws_id,String dataCollectedCase,java.sql.Date recorDate,String waterUseLicense,
		String wellhead_cond,String poorwellh_reason,String drainage_cond,java.sql.Date pumping_operen_date,String dataCondition)
		throws Exception {
String query = "insert into gw_db_schema.productive_well(cws_id,data_colln_case,record_date,wateruse_license,wellhead_cond,poorwellh_reason,"
		+ "drainage_cond,pumping_operen_date,datacondtion)values(?,?,?,?,?,?,?,?,?)";
try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	preparedStatement.setInt(1, cws_id);
	preparedStatement.setString(2, dataCollectedCase);
	preparedStatement.setDate(3, recorDate);
	preparedStatement.setString(4, waterUseLicense);
	preparedStatement.setString(5, wellhead_cond);
	preparedStatement.setString(6, poorwellh_reason);
	preparedStatement.setString(7, drainage_cond);
	preparedStatement.setDate(8, pumping_operen_date);
	preparedStatement.setString(9, dataCondition);
  preparedStatement.executeUpdate();
}
}
//SCADA system data
private static void addSCADASystemData(Connection connection,int proWellID,String SCADACOnnection,String SCADAStatus,String mon_sensor_instal,
		String sensor_type[],String data_colln_case,java.sql.Date recorDate,String datacondtion)
		throws Exception {
	Array sqlArray_sensorType=conn.createArrayOf("text",sensor_type);
String query = "insert into gw_db_schema.scada_systemc(pro_id,conn_avialable,scada_status,mon_sensor_instal,sensor_type,data_colln_case,recordate,"
		+ "datacondtion)values(?,?,?,?,?,?,?,?)";
try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	preparedStatement.setInt(1, proWellID);
	preparedStatement.setString(2, SCADACOnnection);
	preparedStatement.setString(3, SCADAStatus);
	preparedStatement.setString(4, mon_sensor_instal);
	preparedStatement.setArray(5,sqlArray_sensorType);
	preparedStatement.setString(6, data_colln_case);
	preparedStatement.setDate(7, recorDate);
	preparedStatement.setString(8, datacondtion);
    preparedStatement.executeUpdate();
  
}
}
//Power system data
private static void addPowerSystemData(Connection connection,int pro_id,String power_source,String st_gen_avialable,String gen_status,
		double gen_power)
		throws Exception {
String query = "insert into gw_db_schema.power_systemc(pro_id,power_source,st_gen_avialable,gen_status,gen_power)values(?,?,?,?,?)";
try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	preparedStatement.setInt(1, pro_id);
	preparedStatement.setString(2, power_source);
	preparedStatement.setString(3,st_gen_avialable );
	preparedStatement.setString(4, gen_status);
	preparedStatement.setDouble(5, gen_power);
    preparedStatement.executeUpdate();

}
}
//Power system data
private static void addObservationPIPData(Connection connection,int pro_id,String pip_avialable,String pip_status)
		throws Exception {
String query = "insert into gw_db_schema.obsrvation_pipc(pro_id,pip_avialable,pip_status)values(?,?,?)";
try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	preparedStatement.setInt(1, pro_id);
	preparedStatement.setString(2, pip_avialable);
	preparedStatement.setString(3,pip_status );
	preparedStatement.executeUpdate();
}
}
//Productive Well Statistical Data
private static void addproductivewellStatusInfon(Connection connection,int pow_id,int scada_id,int obpip_id,int pro_id,
		String wellfunstatus,String func_condition,String reason_inactive,String reason_nonfun,java.sql.Date funStopeDate,
		String dataCondition)
		throws Exception {
String query = "insert into gw_db_schema.productive_well_status(pow_id,sca_id,pip_id,pro_id,wellfunstatus,func_condition,"
		+ "reason_inactive,reason_nonfun,fun_stoped_date,datacondtion)values(?,?,?,?,?,?,?,?,?,?)";
try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
	preparedStatement.setInt(1,pow_id );
	preparedStatement.setInt(2,scada_id );
	preparedStatement.setInt(3, obpip_id);
	preparedStatement.setInt(4, pro_id);
	preparedStatement.setString(5,wellfunstatus);
	preparedStatement.setString(6,func_condition);
	preparedStatement.setString(7, reason_inactive);
	preparedStatement.setString(8, reason_nonfun);
	preparedStatement.setDate(9,funStopeDate);
	preparedStatement.setString(10, dataCondition);
	preparedStatement.executeUpdate();
}
}
//Productive water Quality trend
private static void addwaterQualityTrend(Connection connection,int pws_id,double salinity,double temprature,
		String extrnalpolun,String goodwtrquality,String poorwaterQuality,String poorwaterqualityrsn)
		throws Exception {
String query = "insert into gw_db_schema.waterqualitytrend(pws_id,salinity,temprature,extrnalpolun,goodwtrquality,"
		+ "poorwaterquality,poorwaterqualityrsn)values(?,?,?,?,?,?,?)";
try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
	preparedStatement.setInt(1,pws_id );
	preparedStatement.setDouble(2,salinity );
	preparedStatement.setDouble(3, temprature);
	preparedStatement.setString(4,extrnalpolun);
	preparedStatement.setString(5,goodwtrquality);
	preparedStatement.setString(6, poorwaterQuality);
	preparedStatement.setString(7, poorwaterqualityrsn);
	preparedStatement.executeUpdate();
}
}
/// Productive Well condition trend
private static void addwellcoditionTrend(Connection connection,int pws_id,String highdrawdown,String yielddeclining,String rapidyielddecline,
		String goodcondition)
		throws Exception {
String query = "insert into gw_db_schema.wellcoditiontrend(pws_id,highdrawdown,yielddeclining,rapidyielddecline,goodcondition)values(?,?,?,?,?)";
try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
	preparedStatement.setInt(1,pws_id );
	preparedStatement.setString(2,highdrawdown);
	preparedStatement.setString(3,yielddeclining);
	preparedStatement.setString(4, rapidyielddecline);
	preparedStatement.setString(5, goodcondition);
preparedStatement.executeUpdate();
}
}
//Productive Well Statistical Data
private static void addRecomendation(Connection connection,int wct_id,int wqct_id,String changepumpcapacity,String loyerpumpposition,
		String wellcleaning,String comprehensive_reha,String qualitytest_disinf,String mantaindrainage,String headmantainreconst,
		String abandonmentseal,String abandonmentreason)
		throws Exception {
String query = "insert into gw_db_schema.recomendation(wellco_id,wqt_id,changepumpcapacity,loyerpumpposition,"
		+ "wellcleaning,comprehensive_reha,qualitytest_disinf,mantaindrainage,headmantainreconst,abandonmentseal,abandonmentreason)"
		+ "values(?,?,?,?,?,?,?,?,?,?,?)";
try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
	preparedStatement.setInt(1,wct_id );
	preparedStatement.setInt(2,wqct_id );
	preparedStatement.setString(3,changepumpcapacity);
	preparedStatement.setString(4,loyerpumpposition);
	preparedStatement.setString(5,wellcleaning);
	preparedStatement.setString(6,comprehensive_reha);
	preparedStatement.setString(7,qualitytest_disinf);
	preparedStatement.setString(8,mantaindrainage);
	preparedStatement.setString(9,headmantainreconst);
	preparedStatement.setString(10,abandonmentseal);
	preparedStatement.setString(11,abandonmentreason);
	preparedStatement.executeUpdate();
}
}
//Productive Well condition trend
private static void addRehabConducted(Connection connection,int recomand_id,String rehabconducted,java.sql.Date rehabdate,int rehabCycle)
		throws Exception {
String query = "insert into gw_db_schema.rehabconducted(recom_id,conducted,redate,rehab_cycle)values(?,?,?,?)";
try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
	preparedStatement.setInt(1,recomand_id);
	preparedStatement.setString(2,rehabconducted);
	preparedStatement.setDate(3, rehabdate);
	preparedStatement.setInt(4,rehabCycle );
preparedStatement.executeUpdate();
}
}
//Productive Well Statistical Data
private static void addRehabProgram(Connection connection,int contruct_id,double swl_before,double swl_after,double dwl_before,
		double dwl_after,double pump_pos,double pump_power,String waterquality,String qltyproblem,double yield,double spfc_capacity,
		double effciency,double hconductivity,double transimitivity,double storativity,int rehabconduc_Id,String remark)
		throws Exception {
String query = "insert into gw_db_schema.rehabprogram(contruct_id,swl_before,dwl_before,pump_pos,pump_power,waterquality,"
		+ "qltyproblem,yield,spfc_capacity,effciency,hconductivity,transimitivity,storativity,rc_id,remark,swl_after,dwl_after)"
		+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
	preparedStatement.setInt(1,contruct_id);
	preparedStatement.setDouble(2,swl_before);
	preparedStatement.setDouble(3, dwl_before);
	preparedStatement.setDouble(4,pump_pos);
	preparedStatement.setDouble(5, pump_power);
	preparedStatement.setString(6, waterquality);
	preparedStatement.setString(7, qltyproblem);
	preparedStatement.setDouble(8,yield);
	preparedStatement.setDouble(9,spfc_capacity);
	preparedStatement.setDouble(10,effciency);
	preparedStatement.setDouble(11,hconductivity);
	preparedStatement.setDouble(12,transimitivity);
	preparedStatement.setDouble(13,storativity);
	preparedStatement.setInt(14,rehabconduc_Id);
	preparedStatement.setString(15,remark);
	preparedStatement.setDouble(16,swl_after);
	preparedStatement.setDouble(17,dwl_after);
	preparedStatement.executeUpdate();
}
}
//Productive Update WCS
public static List<File> updateWCRData(int user_id,int well_id,int geolocID,String wellCode,double easting,double northing,double longT,
		double lat,double elevation,java.sql.Date constYear,String dirillingL,double wellDepth,double wellYield,double SWL,double DWL,
		double specificCapacity,String wellOwnerName,String wellOwnerEmail,String wellOwnerHpone,int dbUserId,
		String CRSOwnerCatName,double CRlargeCasingName,double CRtelCasingName,String CRCasingMaterName,String mainAquifer,String CRwellTypeName,
		String CRabanRWellName,String CRsealedYNName,java.sql.Date CRSealedDateName,String CRfunWellConName,String CRpumpstatusName,
		java.sql.Date CRpumpInstalledDateName,double CRpumpCapacityName,String CRpumpHeadName,double CRpumpPosiName,
		double CRdischargeRateName,String CRgeneratorStaName,double CRcapacityCapaName,String SCADAName,String SCADACStatusName)
		throws Exception {
	int chID = 0,constDischargeId = 0,casingId=0,pumpID=0,SCADAsystemID=0;
	double longtCheck=0,latCheck=0,eastingCheck=0,northingCheck=0,l_elevation=0,well_depthCheck=0,largecasingCheck=0,telescopedcasingCheck=0,
			pump_capacityCheck=0,pump_positionCheck=0,abstraction_rateCheck=0,yield_lpsCheck=0,swl_mCheck=0,dwl_mCheck=0,specific_capacityCheck=0;
	String wellcodeCheck="",well_typeCheck="",main_aquiferCheck="",drilling_licenseCheck="",casing_mtrlCheck="",wellownercatgoryCheck="",
			pump_statusCheck="",pumpheadCheck="",conn_avialableCheck="",scada_statusCheck="",emailCheck="",orgnameCheck="",phoneCheck="",cntn_yearCheck=null;
	LocalDateTime timedateNow= LocalDateTime.now(ZoneId.systemDefault());
	String wellIndex = "";
	File noValue=null;
	List<File> updateRe=new ArrayList<File>();
	conn=connection.dbconnection();
	String querywellCha="select cdt.cdt_id,ch.ch_id,ww.well_index,ps.pump_id from gw_db_schema.water_well as ww,gw_db_schema.pumping_status as ps,"
			+ "gw_db_schema.well_characteristics as ch,gw_db_schema.con_discharge_test as cdt where ch.well_id=ww.well_id and ps.ch_id=ch.ch_id and "
			+ "cdt.ch_id=ch.ch_id and cdt.data_colln_case='Well Completion Report' and ps.data_colln_case='Well Completion Report' and "
			+ "ww.well_id="+well_id+"";
	Statement stmntWellCH=conn.createStatement();
	ResultSet rswellCh=stmntWellCH.executeQuery(querywellCha);
	while(rswellCh.next()){
		constDischargeId=rswellCh.getInt(1);
		chID=rswellCh.getInt(2);
		wellIndex=rswellCh.getString(3);
		pumpID=rswellCh.getInt(4);
	}
	//Productive well status information Insertion Function call
	String scadaUpdate="select sca_id from gw_db_schema.scada_systemc where sca_id=(select MAX(sca_id)from gw_db_schema.scada_systemc as scad,"
			+ "gw_db_schema.productive_well as pw,gw_db_schema.constwell_status as cws,gw_db_schema.gw_data as gw,gw_db_schema.con_discharge_test as cd,"
			+ "gw_db_schema.well_characteristics as ch,gw_db_schema.water_well as Ww where scad.pro_id=pw.pro_id and pw.cws_id=cws.cws_id and "
			+ "cws.cdt_id=cd.cdt_id and cd.ch_id=ch.ch_id and cws.gw_id=gw.gw_id and ch.well_id=Ww.well_id and gw.well_id=Ww.well_id and "
			+ "Ww.well_id="+well_id+")limit 1";
	Statement statescadaUpdate=conn.createStatement();
	ResultSet rsscadaUpdate=statescadaUpdate.executeQuery(scadaUpdate);
	while(rsscadaUpdate.next()) {
		SCADAsystemID=rsscadaUpdate.getInt(1);
	}
	String querycasing="select ci.ci_id from gw_db_schema.water_well as ww,gw_db_schema.casing_info as ci "
			+ "where ci.well_id=ww.well_id and ww.well_id="+well_id+"";
	Statement stmntcasing=conn.createStatement();
	ResultSet rscasing=stmntcasing.executeQuery(querycasing);
	while(rscasing.next()){
		casingId=rscasing.getInt(1);
	}
	//Productive well status information Insertion Function call
	 List<ValuePair>list_checkPair=new ArrayList<>();
	String checkElements_ifUpdated ="select gl.longt,gl.lat,gl.easting,gl.northing,gl.l_elevation,ww.wellcode,ww.well_type,ww.well_depth,"
			+ "extract(year from ww.cntn_year),ww.main_aquifer,ww.wellownercatgory,ww.drilling_license,ci.largecasing,ci.telescopedcasing,"
			+ "ci.casing_mtrl,ps.pump_capacity,ps.pump_position,ps.pump_status,ps.pumphead,ps.abstraction_rate,cd.yield_lps,cd.swl_m,cd.dwl_m,"
			+ "ch.specific_capacity,sc.conn_avialable,sc.scada_status,dbu.email,dbu.orgname,dbu.phone from gw_db_schema.scada_systemc as sc,"
			+ "gw_db_schema.productive_well as pw,gw_db_schema.constwell_status as cws,gw_db_schema.pumping_status as ps,gw_db_schema.con_discharge_test "
			+ "as cd,gw_db_schema.db_user as dbu,gw_db_schema.well_characteristics as ch,gw_db_schema.casing_info as ci,gw_db_schema.water_well as ww,"
			+ "gw_db_schema.geo_location as gl where sc.pro_id=pw.pro_id and pw.cws_id=cws.cws_id and cws.cdt_id=cd.cdt_id and cd.ch_id=ch.ch_id and "
			+ "ps.ch_id=ch.ch_id and ch.well_id=Ww.well_id and gl.geoloc_id=ww.geoloc_id and dbu.id=ww.owned_by and ci.well_id=ww.well_id and "
			+ "cd.data_colln_case='Well Completion Report' and ps.data_colln_case='Well Completion Report' and ww.well_id="+well_id+"";
	Statement stmtcheckIfUpdated=conn.createStatement();
	ResultSet rscheckIfUpdated=stmtcheckIfUpdated.executeQuery(checkElements_ifUpdated);
	while(rscheckIfUpdated.next()) {
		longtCheck=rscheckIfUpdated.getDouble(1);
		latCheck=rscheckIfUpdated.getDouble(2);
		eastingCheck=rscheckIfUpdated.getDouble(3);
		northingCheck=rscheckIfUpdated.getDouble(4);
		l_elevation=rscheckIfUpdated.getDouble(5);
		wellcodeCheck=rscheckIfUpdated.getString(6);
		well_typeCheck=rscheckIfUpdated.getString(7);
		well_depthCheck=rscheckIfUpdated.getDouble(8);
		cntn_yearCheck=rscheckIfUpdated.getString(9);
		main_aquiferCheck=rscheckIfUpdated.getString(10);
		wellownercatgoryCheck=rscheckIfUpdated.getString(11);
		drilling_licenseCheck=rscheckIfUpdated.getString(12);
		largecasingCheck=rscheckIfUpdated.getDouble(13);
		telescopedcasingCheck=rscheckIfUpdated.getDouble(14);
		casing_mtrlCheck=rscheckIfUpdated.getString(15);
		pump_capacityCheck=rscheckIfUpdated.getDouble(16);
		pump_positionCheck=rscheckIfUpdated.getDouble(17);
		pump_statusCheck=rscheckIfUpdated.getString(18);
		pumpheadCheck=rscheckIfUpdated.getString(19);
		abstraction_rateCheck=rscheckIfUpdated.getDouble(20);
		yield_lpsCheck=rscheckIfUpdated.getDouble(21);
		swl_mCheck=rscheckIfUpdated.getDouble(22);
		dwl_mCheck=rscheckIfUpdated.getDouble(23);
		specific_capacityCheck=rscheckIfUpdated.getDouble(24);
		conn_avialableCheck=rscheckIfUpdated.getString(25);
		scada_statusCheck=rscheckIfUpdated.getString(26);
		emailCheck=rscheckIfUpdated.getString(27);
		orgnameCheck=rscheckIfUpdated.getString(28);
		phoneCheck=rscheckIfUpdated.getString(29);
		ValuePair valleuP=new ValuePair(longtCheck, latCheck, eastingCheck, northingCheck, l_elevation, well_depthCheck, largecasingCheck,
				telescopedcasingCheck, pump_capacityCheck, pump_positionCheck, abstraction_rateCheck, yield_lpsCheck, swl_mCheck, dwl_mCheck, 
				specific_capacityCheck, wellcodeCheck, well_typeCheck, main_aquiferCheck, drilling_licenseCheck, casing_mtrlCheck, wellownercatgoryCheck,
				pump_statusCheck, pumpheadCheck, conn_avialableCheck, scada_statusCheck, emailCheck, orgnameCheck, phoneCheck, cntn_yearCheck);
		list_checkPair.add(valleuP);
	}
	for(ValuePair value: list_checkPair) {
		System.out.println("What is the invoked Value of Orgnamecheck is " +value.orgnameCheck+" is = to "+ wellOwnerName);
		  // Compare user input string with the item's name
        if ((value.longtCheck!=longT) || (value.latCheck!=lat) || (value.eastingCheck!=easting) || (value.northingCheck!=northing) || 
        		(value.l_elevation!=elevation)){
        	String query ="update gw_db_schema.geo_location set easting="+easting+",northing="+northing+",longt="+longT+",lat="+lat+",l_elevation="+elevation+" "
        			+ "where geoloc_id= "+geolocID+";"
        			+"insert into gw_db_schema.database_log(well_id,user_id,actiontook,datehapped,datasheetype)values("+well_id+","+dbUserId+","
        			+ "'Contents: geo_location (Location Coordinate) table Updated',?,'Well Completion Report')";
        	try (PreparedStatement preparedStatement = conn.prepareStatement(query)){
        		preparedStatement.setObject(1,timedateNow);
        		preparedStatement.executeUpdate();
        		File updateWCR=new File(well_id,"<font color=#06402B>Success,</font>","Succefully Updated",noValue);
        		updateRe.add(updateWCR);
        	}catch (Exception e) {
        		e.printStackTrace();
        		File updateWCR=new File(0,"<font color=#8B8000>Warning,</font>",""+wellIndex+" Well Completion Report Data in the Repository is Not Affected"
        				+ " by this Updation Query",noValue); 
        		updateRe.add(updateWCR);
        	}
        }else if((!value.wellcodeCheck.equals(wellCode))|| (value.well_depthCheck!=wellDepth) || (!value.main_aquiferCheck.equals(mainAquifer))||
        		(!value.wellownercatgoryCheck.equals(CRSOwnerCatName))|| (!value.drilling_licenseCheck.equals(dirillingL)) || 
        		(!value.well_typeCheck.equals(CRwellTypeName))){
        	String query = "update gw_db_schema.water_well set wellcode='"+wellCode+"',well_depth="+wellDepth+",cntn_year=?,main_aquifer='"+mainAquifer+"',"
        			+ "wellownercatgory='"+CRSOwnerCatName+"',drilling_license='"+dirillingL+"',well_type='"+CRwellTypeName+"' where well_id="+well_id+";"
        			+ "insert into gw_db_schema.database_log(well_id,user_id,actiontook,datehapped,datasheetype)values("+well_id+","+dbUserId+","
        			+ "'Contents: water_well (Water Well) Data table Updated',?,'Well Completion Report')";
        	try (PreparedStatement preparedStatement = conn.prepareStatement(query)){
        		preparedStatement.setDate(1, constYear);
        		preparedStatement.setObject(2,timedateNow);
        		preparedStatement.executeUpdate();
        		File updateWCR=new File(well_id,"<font color=#06402B>Success,</font>","Succefully Updated",noValue);
        		updateRe.add(updateWCR);
        	}catch (Exception e) {
        		e.printStackTrace();
        		File updateWCR=new File(0,"<font color=#8B8000>Warning,</font>",""+wellIndex+" Well Completion Report Data in the Repository is Not Affected"
        				+ " by this Updation Query",noValue); 
        		updateRe.add(updateWCR);
        	}
        }else if((!value.casing_mtrlCheck.equals(CRCasingMaterName)) || (value.largecasingCheck!=CRlargeCasingName) || 
        		(value.telescopedcasingCheck!=CRtelCasingName)) {
        	String query = "update gw_db_schema.casing_info set largecasing="+CRlargeCasingName+",telescopedcasing="+CRtelCasingName+","
        			+ "casing_mtrl='"+CRCasingMaterName+"' where ci_id="+casingId+";"
        			+ "insert into gw_db_schema.database_log(well_id,user_id,actiontook,datehapped,datasheetype)values("+well_id+","+dbUserId+","
        			+ "'Contents: casing_info (Well Casing Information) table Updated',?,'Well Completion Report')";
        	try (PreparedStatement preparedStatement = conn.prepareStatement(query)){
        		preparedStatement.setObject(1,timedateNow);
        		preparedStatement.executeUpdate();
        		File updateWCR=new File(well_id,"<font color=#06402B>Success,</font>","Succefully Updated",noValue);
        		updateRe.add(updateWCR);
        	}catch (Exception e) {
        		e.printStackTrace();
        		File updateWCR=new File(0,"<font color=#8B8000>Warning,</font>",""+wellIndex+" Well Completion Report Data in the Repository is Not Affected"
        				+ " by this Updation Query",noValue); 
        		updateRe.add(updateWCR);
        	}
        }else if(value.pump_capacityCheck!=CRpumpCapacityName || value.pump_positionCheck!=CRpumpPosiName || 
        		(!value.pump_statusCheck.equals(CRpumpstatusName)) || (!value.pumpheadCheck.equals(CRpumpHeadName)) || 
        		value.abstraction_rateCheck!=CRdischargeRateName) {
        	String query = "update gw_db_schema.pumping_status set pump_capacity="+CRpumpCapacityName+",pump_position="+CRpumpPosiName+","
        			+ "pump_status='"+CRpumpstatusName+"',pumphead='"+CRpumpHeadName+"',abstraction_rate="+CRdischargeRateName+" where pump_id="+pumpID+";"
        			+ "insert into gw_db_schema.database_log(well_id,user_id,actiontook,datehapped,datasheetype)values("+well_id+","+dbUserId+","
        			+ "'Contents: pumping_status(Pumping System Status) data table Updated',?,'Well Completion Report')";
        	try (PreparedStatement preparedStatement = conn.prepareStatement(query)){
        		preparedStatement.setObject(1,timedateNow);
        		preparedStatement.executeUpdate();
        		File updateWCR=new File(well_id,"<font color=#06402B>Success,</font>","Succefully Updated",noValue);
        		updateRe.add(updateWCR);
        	}catch (Exception e) {
        		e.printStackTrace();
        		File updateWCR=new File(0,"<font color=#8B8000>Warning,</font>",""+wellIndex+" Well Completion Report Data in the Repository is Not Affected"
        				+ " by this Updation Query",noValue); 
        		updateRe.add(updateWCR);
        	}
        }else if(value.yield_lpsCheck!=wellYield || value.swl_mCheck!=SWL || value.dwl_mCheck!=DWL){
        	String query = "update gw_db_schema.con_discharge_test set yield_lps="+wellYield+",swl_m="+SWL+",dwl_m="+DWL+" where "
        			+ "cdt_id="+constDischargeId+";"
        			+ "insert into gw_db_schema.database_log(well_id,user_id,actiontook,datehapped,datasheetype)values("+well_id+","+dbUserId+","
        			+ "'Contents: con_discharge_test(Constant discharge Test) data table Updated',?,'Well Completion Report')";
        	try (PreparedStatement preparedStatement = conn.prepareStatement(query)){
        		preparedStatement.setObject(1,timedateNow);
        		preparedStatement.executeUpdate();
        		File updateWCR=new File(well_id,"<font color=#06402B>Success,</font>","Succefully Updated",noValue);
        		updateRe.add(updateWCR);
        	}catch (Exception e) {
        		e.printStackTrace();
        		File updateWCR=new File(0,"<font color=#8B8000>Warning,</font>",""+wellIndex+" Well Completion Report Data in the Repository is Not Affected"
        				+ " by this Updation Query",noValue); 
        		updateRe.add(updateWCR);
        	}	
        }else if(value.specific_capacityCheck!=specificCapacity){
        	String query = "update gw_db_schema.well_characteristics set specific_capacity="+specificCapacity+" where ch_id="+chID+";"
        			+ "insert into gw_db_schema.database_log(well_id,user_id,actiontook,datehapped,datasheetype)values("+well_id+","+dbUserId+","
        			+ "'Contents: well_characteristics (Specific Well Charactersitics) table Updated',?,'Well Completion Report')";
        	try (PreparedStatement preparedStatement = conn.prepareStatement(query)){
        		preparedStatement.setObject(1,timedateNow);
        		preparedStatement.executeUpdate();
        		File updateWCR=new File(well_id,"<font color=#06402B>Success,</font>","Succefully Updated",noValue);
        		updateRe.add(updateWCR);
        	}catch (Exception e) {
        		e.printStackTrace();
        		File updateWCR=new File(0,"<font color=#8B8000>Warning,</font>",""+wellIndex+" Well Completion Report Data in the Repository is Not Affected"
        				+ " by this Updation Query",noValue); 
        		updateRe.add(updateWCR);
        	}		
        }else if((!value.scada_statusCheck.equals(SCADACStatusName)) || (!value.conn_avialableCheck.equals(SCADAName))) {
        	String query = "update gw_db_schema.scada_systemc set conn_avialable='"+SCADAName+"',scada_status='"+SCADACStatusName+"' where "
        			+ "sca_id="+SCADAsystemID+";"
        			+ "insert into gw_db_schema.database_log(well_id,user_id,actiontook,datehapped,datasheetype)values("+well_id+","+dbUserId+","
        			+ "'Contents: scada_systemc(SCADA Information) Table Updated',?,'Well Completion Report')";
        	try (PreparedStatement preparedStatement = conn.prepareStatement(query)){
        		preparedStatement.setObject(1,timedateNow);
        		preparedStatement.executeUpdate();
        		File updateWCR=new File(well_id,"<font color=#06402B>Success,</font>","Succefully Updated",noValue);
        		updateRe.add(updateWCR);
        	}catch (Exception e) {
        		e.printStackTrace();
        		File updateWCR=new File(0,"<font color=#8B8000>Warning,</font>",""+wellIndex+" Well Completion Report Data in the Repository is Not Affected"
        				+ " by this Updation Query",noValue); 
        		updateRe.add(updateWCR);
        	}	 	
        }else if((!value.orgnameCheck.equals(wellOwnerName)) || (!value.phoneCheck.equals(wellOwnerHpone))){
        	System.out.println("User ID for Update is "+user_id);
        	String query = "update gw_db_schema.db_user set email='"+wellOwnerEmail+"',orgname='"+wellOwnerName+"',phone='"+wellOwnerHpone+"'"
        			+ " where id="+user_id+";"
        			+ "insert into gw_db_schema.database_log(well_id,user_id,actiontook,datehapped,datasheetype)values("+well_id+","+dbUserId+","
        			+ "'Contents: db_user(database User Management) Table Updated',?,'Well Completion Report')";
        	try (PreparedStatement preparedStatement = conn.prepareStatement(query)){
        		preparedStatement.setObject(1,timedateNow);
        		preparedStatement.executeUpdate();
        		File updateWCR=new File(well_id,"<font color=#06402B>Success,</font>","Succefully Updated",noValue);
        		updateRe.add(updateWCR);
        	}catch (Exception e) {
        		e.printStackTrace();
        		File updateWCR=new File(0,"<font color=#8B8000>Warning,</font>",""+wellIndex+" Well Completion Report Data in the Repository is Not Affected"
        				+ " by this Updation Query",noValue); 
        		updateRe.add(updateWCR);
        	}	
        }else {	
        	File updateWCR=new File(0,"<font color=#8B8000>Warning,</font>",""+wellIndex+" Non table data is Affected"
    				+ " by this Update Query",noValue); 
    		updateRe.add(updateWCR);
        }
	}

return updateRe;
}
//Productive Well Statistical Data
public static List<File> updateGWOData(int userID,int wellID,String wellIndex,String waterUserLName,java.sql.Date wellOpernStartDate,
		String enumCompanyName,String enumEmailName,String enumEmailPhoneName)
		throws Exception {
	int proId = 0;
	List<File> updateRe=new ArrayList<File>();
	conn=connection.dbconnection();
	String querywellCha="select pro.pro_id from gw_db_schema.water_well as ww,gw_db_schema.well_characteristics as ch,"
			+ "gw_db_schema.con_discharge_test as cdt,gw_db_schema.constwell_status as cws,gw_db_schema.productive_well as pro where "
			+ "ch.well_id=ww.well_id and cdt.ch_id=ch.ch_id and cdt.cdt_id=cws.cdt_id and cws.cws_id=pro.cws_id and cws.data_colln_case="
			+ "'Operational' and ww.well_id="+wellID+"";
	Statement stmntWellCH=conn.createStatement();
	ResultSet rswellCh=stmntWellCH.executeQuery(querywellCha);
	while(rswellCh.next()){
		proId=rswellCh.getInt(1);
	}
String query = "update gw_db_schema.productive_well set wateruse_license='"+waterUserLName+"',pumping_operen_date=? where pro_id="+proId+";"
		+ "update gw_db_schema.db_user set email='"+enumEmailName+"',orgname='"+enumCompanyName+"',phone='"+enumEmailPhoneName+"' "
		+ "where id="+userID+"";
try (PreparedStatement preparedStatement = conn.prepareStatement(query)){
	preparedStatement.setDate(1, wellOpernStartDate);
	preparedStatement.executeUpdate();
	double noValue=0.0;
	File updateWCR= new File(wellID,"<font color=#06402B>Success,</font>","Succefully Updated",noValue);
	updateRe.add(updateWCR);
}catch (Exception e) {
	e.printStackTrace();
	double noValue=0.0;
	File updateWCR=new File(0,"<font color=#8B8000>Error,</font>",""+wellIndex+" Well's Groundwater Operational Data in the Repository is Not "
			+ "Affected by this Updation Query",noValue); 
	updateRe.add(updateWCR);
}
return updateRe;
}
//ask request
public static List<File>ask_request(int center,int process,int projcet_n,String Desi,String userId,String fname,String lname){
		List<File>files=new ArrayList<File>();
		try {
			int user_id=0;
			int doc_id=0;
			 byte []bb = null;
			// String doc_name="";
								conn=connection.dbconnection();
					String user="select Ln.l_id from hydrogeos.login11 as Ln where Ln.user1='"+userId+"' and Ln.f_name='"+fname+"'";
								Statement stmn=conn.createStatement();
								ResultSet rs1=stmn.executeQuery(user);
								while(rs1.next()){
									user_id=rs1.getInt(1);
								}
			String sql="select As1.cat_id,dc.cat_name,As1.l_id,Ln.user1 from hydrogeos.national_cor_center as Na,hydrogeos.eco_process as Pr,hydrogeos.project_data as Cl,"
					+ "hydrogeos.doc_cat as dc,hydrogeos.login11 as Ln,hydrogeos.askprivlage as As1 where Na.na_id=Pr.na_id and Pr.pro_id=dc.process_id and "
					+ "Cl.pro_def_id=dc.pro_id and dc.cat_id=As1.cat_id and Ln.l_id=As1.l_id and Na.na_id="+center+" and Pr.pro_id="+process+" and "
							+ "Cl.pro_def_id="+projcet_n+" and dc.cat_id="+Desi+" and Ln.user1='"+userId+"' and Ln.f_name='"+fname+"'";
						Statement stmt = conn.createStatement();
						 ResultSet rs = stmt.executeQuery(sql);
						  if(rs.next()) {
							  doc_id=rs.getInt(1);
							  //doc_name=rs.getString(2);
							  user_id=rs.getInt(3);
							  //String username=rs.getString(4); 
	File ffile=new File(doc_id, "<font color=#FF0000>"+"Documents Request is Already Sent !! "+ " </font>", bb); 
							  files.add(ffile);							  
		//System.out.println("Request is Already Asked with Id= "+doc_id+" and name= "+doc_name+" and UserId= "+user_id+" and Username= "+username);  
						  }
						  else{	 
							  LocalDateTime now;
							  DateTimeFormatter timeformat;
							  timeformat= DateTimeFormatter.ofPattern("yyyy/MM/dd");
							  now=LocalDateTime.now();	
String quercenter="insert into hydrogeos.askprivlage(process_id,cat_id,l_id,asked_date)values("+process+","+Desi+","+user_id+",'"+timeformat.format(now)+"')";
				 PreparedStatement statmnt = conn.prepareStatement(quercenter);
				 statmnt.executeUpdate(); 
				 File ffile=new File(doc_id, "Your request is Succefully sent!!", bb);
				  files.add(ffile);	
							  }
		        
		} catch (Exception e) {
		e.printStackTrace();
		}
		return files;
	}

}
