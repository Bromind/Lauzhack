package lauzhack.client;

import java.net.MalformedURLException;

public class client {

	public static void main(String[] args) {
		NetworkInterface ne;
		try{
			ne = new NetworkInterface("My name", "http://127.0.0.1:8000", "GET");
		} catch (MalformedURLException e) {
			System.err.println("U noob 4");
			System.err.println(e.getMessage());
			return;
		}
		
		Color colors[] = {new Color(0, 0, 0)};
		
		// Example
		
		ne.sendInitMessage();
		ne.sendMessage("Wololo", colors, "dest");
		
	}

}
