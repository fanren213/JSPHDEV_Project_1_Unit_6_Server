/**
 * @author      Bonan Cao <bonanc@andrew.cmu.edu>
 * @Andrew ID   bonanc
 * @Date 		Oct. 07. 2015
 * 
 */
package server;

import java.io.*;
import java.net.*;

public class DefaultSocketClient extends Thread {
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Socket socket;
	private String strHost;
	private int iPort;
	private BuildCarModelOptions autoServer;
	private ServerSocket serverSocket;
	public DefaultSocketClient(Socket socket, BuildCarModelOptions autoServer) {
		setSocket(socket);
		setAutoServer(autoServer);
	}
	
	private void setSocket(Socket socket) {
		this.socket = socket;
		
	}
	public void run(){
		if (openConnection()){
			handleSession();
			closeSession();
		}
	}
	private void closeSession() {
		try {
			in = null;
			out = null;
			socket.close();
		}
		catch (IOException e){
			System.err.println("Error closing socket to " + strHost);
		}
	}
	private void handleSession() {
			while(true){
				int commandCode;
				Object command = new Object();
					command = handleInput();
				if(command instanceof Integer){
					commandCode = Integer.valueOf(command.toString());
					switch(commandCode){
					case(1): // Get an object from client and add to the linkedhashmap
						Object propsObject = new Object();
						propsObject = handleInput();
						autoServer.addAutoFromClient(propsObject);
						System.out.println("Object built.(Output by server)");
						sendOutput(5);
						break;
					case(2): // Provide the models list in string
						String modelsList = autoServer.modelsList();
						sendOutput(modelsList);
						break;
					case(3): // Return the Automotive object as requested
						String modelName = new String();
						modelName = (String)handleInput();
						Object auto = autoServer.sendObjectToClient(modelName);
						sendOutput(auto);
						break;
					case(0): //exit
						System.out.println("Quit the server.(Output by server)");
						return;
					}
				}
			}
	}
	private boolean openConnection() {
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(socket.getInputStream());
		}
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	private void setHost(String strHost) {
		this.strHost = strHost;
	}
	private void setPort(int iPort) {
		this.iPort = iPort;
	}
	private void setAutoServer(BuildCarModelOptions autoServer){
		this.autoServer = autoServer;
	}
	/* Get input from stream */
	private Object handleInput(){
		Object object = null;
       	try {
			object = in.readObject();
		} 
       	catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
       	catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
    	return object;
	}
	/* Send output with stream. */
	private void sendOutput(Object object){
		if(object != null){
			try {
				out.writeObject(object);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
