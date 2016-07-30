/**
 * @author: Bonan Cao <bonanc@andrew.cmu.edu>
 * @Andrew ID: bonanc
 * @date: @{date}
 */
package server;

import java.io.IOException;
import java.net.*;
public class Multithread {
	ServerSocket ss;
	public Multithread(int port, BuildCarModelOptions autoServer) throws IOException{
		ss = new ServerSocket(port);
		while(true){
			Socket socket = ss.accept();
			DefaultSocketClient d = new DefaultSocketClient(socket, autoServer);
			d.start();
		}
	}
}
