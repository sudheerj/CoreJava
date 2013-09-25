package com.sudheer.jdbc;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
 
import oracle.jdbc.OracleTypes;
 
public class CallableStatementCURSOR {
 
	private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String DB_CONNECTION = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String DB_USER = "system";
	private static final String DB_PASSWORD = "oracle";
 
	public static void main(String[] argv) {
 
		try {
 
			callOracleStoredProcCURSORParameter();
 
		} catch (SQLException e) {
 
			System.out.println(e.getMessage());
 
		}
 
	}
 
	private static void callOracleStoredProcCURSORParameter()
			throws SQLException {
 
		Connection dbConnection = null;
		CallableStatement callableStatement = null;
		ResultSet rs = null;
 
		String getDBUSERCursorSql = "{call getDBUSERCursor(?,?)}";
 
		try {
			dbConnection = getDBConnection();
			callableStatement = dbConnection.prepareCall(getDBUSERCursorSql);
 
			callableStatement.setString(1, "sudheer");
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
 
			// execute getDBUSERCursor store procedure
			callableStatement.executeUpdate();
 
			// get cursor and cast it to ResultSet
			rs = (ResultSet) callableStatement.getObject(2);
 
			while (rs.next()) {
				String userid = rs.getString("USER_ID");
				String userName = rs.getString("USERNAME");
				String createdBy = rs.getString("CREATED_BY");
				String createdDate = rs.getString("CREATED_DATE");
 
				System.out.println("UserName : " + userid);
				System.out.println("UserName : " + userName);
				System.out.println("CreatedBy : " + createdBy);
				System.out.println("CreatedDate : " + createdDate);
			}
 
		} catch (SQLException e) {
 
			System.out.println(e.getMessage());
 
		} finally {
 
			if (rs != null) {
				rs.close();
			}
 
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
