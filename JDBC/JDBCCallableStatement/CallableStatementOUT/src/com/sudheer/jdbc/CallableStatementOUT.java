package com.sudheer.jdbc;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
 
public class CallableStatementOUT {
 
	private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String DB_CONNECTION = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String DB_USER = "system";
	private static final String DB_PASSWORD = "oracle";
 
	public static void main(String[] argv) {
 
		try {
 
			callOracleStoredProcOUTParameter();
 
		} catch (SQLException e) {
 
			System.out.println(e.getMessage());
 
		}
 
	}
 
	private static void callOracleStoredProcOUTParameter() throws SQLException {
 
		Connection dbConnection = null;
		CallableStatement callableStatement = null;
 
		String getDBUSERByUserIdSql = "{call getDBUSERByUserId(?,?,?,?)}";
 
		try {
			dbConnection = getDBConnection();
			callableStatement = dbConnection.prepareCall(getDBUSERByUserIdSql);
 
			callableStatement.setInt(1, 1);
			callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);
			callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
			callableStatement.registerOutParameter(4, java.sql.Types.DATE);
 
			// execute getDBUSERByUserId store procedure
			callableStatement.executeUpdate();
 
			String userName = callableStatement.getString(2);
			String createdBy = callableStatement.getString(3);
			Date createdDate = callableStatement.getDate(4);
 
			System.out.println("UserName : " + userName);
			System.out.println("CreatedBy : " + createdBy);
			System.out.println("CreatedDate : " + createdDate);
 
		} catch (SQLException e) {
 
			System.out.println(e.getMessage());
 
		} finally {
 
			if (callableStatement != null) {
				callableStatement.close();
			}
 
			if (dbConnection != null) {
				dbConnection.close();
			}
 
		}
 
	}
 
	private static Connection getDBConnection() {
 
		Connection dbConnection = null;
 
		try {
 
			Class.forName(DB_DRIVER);
 
		} catch (ClassNotFoundException e) {
 
			System.out.println(e.getMessage());
 
		}
 
		try {
 
			dbConnection = DriverManager.getConnection(
				DB_CONNECTION, DB_USER,DB_PASSWORD);
			return dbConnection;
 
		} catch (SQLException e) {
 
			System.out.println(e.getMessage());
 
		}
 
		return dbConnection;
 
	}
 
}
