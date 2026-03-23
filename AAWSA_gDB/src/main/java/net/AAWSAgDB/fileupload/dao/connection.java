package net.AAWSAgDB.fileupload.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import org.postgresql.ds.PGConnectionPoolDataSource;
import org.postgresql.ds.PGPoolingDataSource;

/**
 * Servlet implementation class connection
 */
public class connection {
	public static final String url="jdbc:postgresql://localhost:5432/AAWSA-gDB";
	public static final String Uname="postgres";
	public static final String Pword="MmumDdostyab";
	public static Connection conn = null;
	
	public static Connection dbconnection(){
		PGConnectionPoolDataSource  dataSource= new PGConnectionPoolDataSource();
		dataSource.setServerNames(new String[] {
				"localhost:5432"
		});
		dataSource.setDatabaseName("AAWSA-gDB");
		dataSource.setUser(Uname);
		dataSource.setPassword(Pword);
		try {
			Class.forName("org.postgresql.Driver");
			conn=dataSource.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
		}
	}