package driver;

import java.sql.SQLException;

import adapter.BuildAuto;
import database.CreateTable;
import database.DBOperation;
import exception.AutoException;

public class MainDriver {

	public static void main(String[] args) throws AutoException, SQLException {
		CreateTable createTable = new CreateTable();
		createTable.createTable();
		DBOperation d = new DBOperation();
		BuildAuto buildAuto = new BuildAuto();
		buildAuto.buildAuto("1.properties", "properties");
		buildAuto.buildAuto("2.properties", "properties");
		System.out.println("Print the auto list:");
		System.out.println(d.getAutoList());
		System.out.println("Update the option price:");
		System.out.println(d.getOptionList("Power Moonroof","Focus Wagon ZTW"));
		buildAuto.updateOptionPrice("Focus Wagon ZTW", "Power Moonroof", "selected", 1000);
		System.out.println(d.getOptionList("Power Moonroof","Focus Wagon ZTW"));
		System.out.println("Update the option set name:");
		System.out.println(d.getOptionSetList("Focus Wagon ZTW"));
		buildAuto.updateOptionSetName("Focus Wagon ZTW", "Power Moonroof", "Moonroof");
		System.out.println(d.getOptionSetList("Focus Wagon ZTW"));
		d.deleteAuto("Focus Wagon ZTW");
		System.out.println("Auto deleted:");
		System.out.println(d.getAutoList());
		
	}

}
