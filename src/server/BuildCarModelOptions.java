package server;

import java.util.LinkedHashMap;

import adapter.BuildAuto;
import exception.AutoException;
import model.Automotive;

public class BuildCarModelOptions{
	BuildAuto buildAuto = new BuildAuto();
	public void addAutoFromClient(Object props) {
		buildAuto.addAutoFromClient(props);
	}
	public String modelsList(){
		StringBuffer modelsList = new StringBuffer();
		LinkedHashMap<String, Automotive> autos = buildAuto.getAutos();
		for(String key:autos.keySet()){
			modelsList.append(autos.get(key).getMake() + " - ");
			modelsList.append(key + "\n");
		}
		if(modelsList.toString().equals("")){
			modelsList.append("No automobiles in the server.\n");
		}
		return modelsList.toString();
	}
	public Object sendObjectToClient(String modelName){
		try{
			LinkedHashMap<String, Automotive> autos = buildAuto.getAutos();
			if(!autos.containsKey(modelName)){
				throw new AutoException(10);
			}
			else{
				return autos.get(modelName);
			}
		}
		catch(AutoException e){
			e.log();
			System.out.println("Error -- " + e.toString());
			return null;
		}
	}
}
