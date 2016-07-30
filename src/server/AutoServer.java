package server;

import model.Automotive;

public interface AutoServer {
	public void addAutoFromClient(Object props);
	public String modelsList();
	public Object sendObjectToClient(String modelName);
}
