package lauzhack.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;



public class NetworkInterface {
	
	private String src;
	private String server;
	private String method;
	private boolean verbose;
	
	public void setSrc(String src){
		this.src = src;
	}
	
	public NetworkInterface(String sourceName, String serverAddress, String method, boolean verbose) throws MalformedURLException {
		src = sourceName;
		server= serverAddress;
		this.method = method;
		this.verbose = verbose;
		
	}
	
	public List<String> getNextMessage() throws NoMessageException, IOException {
		HttpURLConnection connection;

		URL serverAddress = new URL(server + "/receive");
		connection = (HttpURLConnection) serverAddress.openConnection();
		connection.setRequestMethod(method);
		connection.setRequestProperty("Content-Length", Integer.toString(src.getBytes().length));
		connection.setUseCaches(false);
		connection.setDoOutput(true);

		if(verbose){
			System.out.println("[NetworkInterface/getNextMessage] Polling");
		}
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.writeBytes(src);
		wr.close();

		InputStream is = connection.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));

		List<String> response = new LinkedList<String>();
		String line;
		while ((line = rd.readLine()) != null) {
			response.add(line);
		}
		rd.close();
		return response;
	}

	public boolean sendMessage(String message, Color colors[], String dest) {
		if(message.length() > 20)
			message = message.substring(0, 20);
		
		Message m = new Message(message.toCharArray(), colors, src, dest);
		String json_str = m.toJSON();
		
		HttpURLConnection connection;
		
		try{
			URL serverAddress = new URL(server + "/send");
			connection = (HttpURLConnection) serverAddress.openConnection();
			connection.setRequestMethod(method);
			connection.setRequestProperty("Content-Length", 
					Integer.toString(json_str.getBytes().length));
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			if(verbose) {
				System.out.println("[NetworkInterface/sendMessage] Sending message.");
			}
			wr.writeBytes(json_str);
			wr.close();
		} catch (IOException e) {
			System.err.println("Error in sending message");
			System.err.println(e.getMessage());
			return false;
		}
		
		try{
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			
		    StringBuffer response = new StringBuffer();
		    String line;
		    while ((line = rd.readLine()) != null) {
		      response.append(line);
		      response.append('\r');
		    }
		    rd.close();
		    return (response.toString() == "ACK");
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}
}
