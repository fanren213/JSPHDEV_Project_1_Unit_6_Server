/**
 * @author      Bonan Cao <bonanc@andrew.cmu.edu>
 * @Andrew ID   bonanc
 * @Date 		Oct. 07. 2015
 * 
 */
package server;

import java.io.IOException;
import java.net.*;

import adapter.BuildAuto;
import constants.ClientConstants;

public class ServerDriver {
	public static void main (String args[]) throws IOException{
		String strLocalHost = new String();
		try{
			strLocalHost = InetAddress.getLocalHost().getHostName();
		}
		catch (UnknownHostException e){
			System.err.println ("Unable to find local host");
		}
		BuildCarModelOptions buildCarModelOptions = null;
		buildCarModelOptions = new BuildCarModelOptions();
		new Multithread(ClientConstants.iDAYTIME_PORT, buildCarModelOptions);
	}
}
