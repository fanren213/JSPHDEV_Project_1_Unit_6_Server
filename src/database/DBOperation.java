package database;

import java.sql.*;
import java.util.*;

import adapter.EditAutoDB;
import constants.DBConstants;
import exception.AutoException;
import model.Automotive;
import util.FileIO;

public class DBOperation implements EditAutoDB{
	FileIO fileIO = new FileIO();
	SQLConnection sqlConnection = new SQLConnection();
	Properties props = null;
	public DBOperation() throws AutoException{
		String fileName = "SQL.txt";
		props = fileIO.readPropertiesFile(fileName);
	}
	@Override
	public void addAuto(Automotive auto) throws SQLException {
		
		Connection conn = sqlConnection.getConnection(DBConstants.URL);
		StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb, Locale.US);
		f.format(props.getProperty("addAuto"), 
				auto.getMake(),
				auto.getModel(),
				auto.getBasePrice());
		Statement st = conn.createStatement();
		st.executeUpdate(sb.toString(), Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = st.getGeneratedKeys();
		int auto_id = -1;
		if(rs.next()){
			auto_id = rs.getInt(1);
		}
		if(auto_id != -1){
			for(int i = 0; i < auto.getOptionSets().size(); i++){
				sb = new StringBuilder();
				f = new Formatter(sb, Locale.US);
				f.format(props.getProperty("addOptionSet"), auto.getOptionSetName(i), auto_id);
				f.close();
				st.executeUpdate(sb.toString(), Statement.RETURN_GENERATED_KEYS);
				rs = st.getGeneratedKeys();
				int optionset_id = -1;
				if(rs.next()){
					optionset_id = rs.getInt(1);
				}
				if(optionset_id != -1){
					for(int j = 0; j < auto.getOptions(i).size(); j++){
						sb = new StringBuilder();
						f = new Formatter(sb, Locale.US);
						f.format(props.getProperty("addOption"), auto.getOptionName(i, j),
										 auto.getOptionPrice(i, j), optionset_id);
						st.executeUpdate(sb.toString(), Statement.RETURN_GENERATED_KEYS);
						f.close();
					}
				}
			}
		}
		sqlConnection.closeConnection(conn);
		sqlConnection.closeStatement(st);
	}

	@Override
	public void updateOptionSetName(String newName, String optionSetName, String modelName) throws SQLException {
		Connection conn = sqlConnection.getConnection(DBConstants.URL);
		StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb, Locale.US);
		f.format(props.getProperty("findAuto"), modelName);
		f.close();
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sb.toString());
		int auto_id = -1;
		while(rs.next()){
			auto_id = rs.getInt("id");
		}
		sb = new StringBuilder();
		f = new Formatter(sb, Locale.US);
		f.format(props.getProperty("updateOptionSetName"), newName, auto_id, optionSetName);
		st.executeUpdate(sb.toString());
		f.close();
		sqlConnection.closeConnection(conn);
		sqlConnection.closeStatement(st);
	}

	@Override
	public void updateOptionPrice(Double optionPrice, String optionName, String optionSetName, String modelName) throws SQLException {
		// TODO Auto-generated method stub
		Connection conn = sqlConnection.getConnection(DBConstants.URL);
		StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb, Locale.US);
		f.format(props.getProperty("findAuto"), modelName);
		f.close();
		Statement st = null;
		st = conn.createStatement();
		ResultSet rs = st.executeQuery(sb.toString());
		int auto_id = -1;
		while(rs.next()){
			auto_id = rs.getInt("id");
		}
		sb = new StringBuilder();
		f = new Formatter(sb, Locale.US);
		f.format(props.getProperty("findOptionSet"), auto_id, optionSetName);
		f.close();
		rs = st.executeQuery(sb.toString());
		int optionset_id = -1;
		while(rs.next()){
			optionset_id = rs.getInt("id");
		}
		sb = new StringBuilder();
		f = new Formatter(sb, Locale.US);
		f.format(props.getProperty("updateOptionPrice"), optionPrice, optionset_id, optionName);
		f.close();
		st.executeUpdate(sb.toString());
		sqlConnection.closeConnection(conn);
		sqlConnection.closeStatement(st);
	}

	@Override
	public void deleteAuto(String modelName) throws SQLException {
		Connection conn = sqlConnection.getConnection(DBConstants.URL);
		StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb, Locale.US);
		f.format(props.getProperty("deleteAuto"), modelName);
		Statement st = null;
		st = conn.createStatement();
		st.executeUpdate(sb.toString());
		sqlConnection.closeConnection(conn);
		sqlConnection.closeStatement(st);
		f.close();
	}
	@Override
	public String getAutoList() throws SQLException {
		Connection conn = sqlConnection.getConnection(DBConstants.URL);
		StringBuilder sb = new StringBuilder();
		Statement st = conn.createStatement();
		String s = props.getProperty("getModelList");
		ResultSet rs = st.executeQuery(s);
		while(rs.next()){
			sb.append(rs.getString("id") + "\n");
			sb.append(rs.getString("model") + "\n");
			sb.append(rs.getString("make") + "\n");
			sb.append(rs.getString("base_price") + "\n");
		}
		rs.close();
		sqlConnection.closeConnection(conn);
		sqlConnection.closeStatement(st);
		return sb.toString();
	}
	@Override
	public String getOptionSetList(String modelName) throws SQLException {
		Connection conn = sqlConnection.getConnection(DBConstants.URL);
		StringBuilder sb = new StringBuilder();
		Statement st = conn.createStatement();
		Formatter f = new Formatter(sb, Locale.US);
		f.format(props.getProperty("findAuto"), modelName);
		ResultSet rs = st.executeQuery(sb.toString());
		f.close();
		int auto_id = -1;
		while(rs.next()){
			auto_id = rs.getInt("id");
		}
		sb = new StringBuilder();
		f = new Formatter(sb, Locale.US);
		f.format(props.getProperty("getOptionSetList"), auto_id);
		f.close();
		rs = st.executeQuery(sb.toString());
		sb = new StringBuilder();
		while(rs.next()){
			sb.append(rs.getString("id") + "\n");
			sb.append(rs.getString("name") + "\n");
		}
		rs.close();
		sqlConnection.closeConnection(conn);
		sqlConnection.closeStatement(st);
		return sb.toString();
	}
	@Override
	public String getOptionList(String optionSetName, String modelName) throws SQLException {
		Connection conn = sqlConnection.getConnection(DBConstants.URL);
		StringBuilder sb = new StringBuilder();
		Statement st = conn.createStatement();
		Formatter f = new Formatter(sb, Locale.US);
		f.format(props.getProperty("findAuto"), modelName);
		f.close();
		ResultSet rs = st.executeQuery(sb.toString());
		int auto_id = -1;
		while(rs.next()){
			auto_id = rs.getInt("id");
		}
		sb = new StringBuilder();
		f = new Formatter(sb, Locale.US);
		f.format(props.getProperty("findOptionSet"), auto_id, optionSetName);
		f.close();
		rs = st.executeQuery(sb.toString());
		int optionset_id = -1;
		while(rs.next()){
			optionset_id = rs.getInt("id");
		}
		sb = new StringBuilder();
		f = new Formatter(sb, Locale.US);
		f.format(props.getProperty("getOptionList"), optionset_id);
		f.close();
		rs = st.executeQuery(sb.toString());
		sb = new StringBuilder();
		while(rs.next()){
			sb.append(rs.getString("id") + "\n");
			sb.append(rs.getString("name") + "\n");
			sb.append(rs.getString("price") + "\n");
		}
		rs.close();
		sqlConnection.closeConnection(conn);
		sqlConnection.closeStatement(st);
		return sb.toString();
	}
	
}
