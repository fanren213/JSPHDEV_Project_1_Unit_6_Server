package database;

import java.io.*;
import java.sql.*;
import java.util.*;
import database.SQLConnection;

public class CreateTable {
	public void createTable(){
		SQLConnection conn = new SQLConnection();
		Connection connection = null;
		Statement statement = null;
		try {
			connection = conn.getConnection("jdbc:mysql://localhost:3306/");
			statement = connection.createStatement();
			Scanner scanner = new Scanner(new File("CreateTable.txt"));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				statement.executeUpdate(line);
			}
			scanner.close();
			if(statement != null)
				statement.close();
			if(connection != null)
				conn.closeConnection(connection);
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
