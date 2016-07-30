package database;

import java.sql.*;
import constants.DBConstants;
public class SQLConnection {
	public Connection getConnection(String host) throws SQLException{
//		try{
//			Class.forName(DBConstants.JDBC);
//		}
//		catch(ClassNotFoundException e){
//		}
		Connection conn = DriverManager.getConnection(host, DBConstants.USER, DBConstants.PASSWORD);
		return conn;
	}
	public void closeConnection(Connection conn) throws SQLException{
		conn.close();
	}
	public void closeStatement(Statement statement) throws SQLException{
		statement.close();
	}
}
