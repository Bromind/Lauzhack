package lauzhack.client;

import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Client {

	public static void main(String[] args) {
		NetworkInterface ne;
		List<Message> pending_messages = new LinkedList<>();
		Lock lock = new ReentrantLock(true);
		
		/* Init stuff */
		try {
			ne = new NetworkInterface("My name", "http://127.0.0.1:8000", "GET");
		} catch (MalformedURLException e) {
			System.err.println("U noob 4");
			System.err.println(e.getMessage());
			return;
		}
		
		PullingWorker pw = new PullingWorker(ne, pending_messages, lock);
        (new Thread(pw)).start();

		Color colors[] = { new Color(0, 0, 0) };

		// Example

		ne.sendInitMessage();
		ne.sendMessage("Wololo", colors, "dest");

	}


}
