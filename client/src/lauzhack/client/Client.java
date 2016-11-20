package lauzhack.client;

import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lauzhack.client.keyboard.Printer;
import lauzhack.client.keyboard.PrinterInterface;


public class Client {
	
	private static final boolean VERBOSE = false;

	public static void main(String[] args) {
		System.out.println("Starting Anal-plug-and-play client, provided to you by the Fist-in, Fist-out team.");
		
		NetworkInterface ne;
		List<Message> pending_messages = new LinkedList<>();
		Lock listLock = new ReentrantLock(true);
		Lock kbLock = new ReentrantLock(true);
		PrinterInterface printer = (PrinterInterface) new Printer();

		
		/* Init stuff */
		try {
			ne = new NetworkInterface("My name", "http://localhost:8000", "POST", VERBOSE);
		} catch (MalformedURLException e) {
			System.err.println("U noob 4");
			System.err.println(e.getMessage());
			return;
		}
		
		PollingWorker pw = new PollingWorker(ne, printer, pending_messages, listLock, kbLock);
        (new Thread(pw)).start();
        PushingWorker pushing = new PushingWorker(printer, pending_messages, listLock, kbLock);
        (new Thread(pushing)).start();
        Color[] color = {new Color(100, 0, 0)};
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ne.sendMessage("prout", color, "My name");
	}
}
