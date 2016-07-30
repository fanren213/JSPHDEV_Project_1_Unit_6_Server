package adapter;
import java.sql.SQLException;

import model.Automotive;
public interface EditAutoDB {
	public void addAuto(Automotive auto) throws SQLException;
	public void updateOptionSetName(String newName, String optionSetName, String modelName) throws SQLException;
	public void updateOptionPrice(Double optionPrice, String optionName, String optionSetName, String modelName)
			throws SQLException;
	public void deleteAuto(String modelName) throws SQLException;
	public String getAutoList() throws SQLException;
	public String getOptionSetList(String modelName) throws SQLException;
	public String getOptionList(String optionSetName, String modelName) throws SQLException;
}
