package lauzhack.client;

import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lauzhack.client.keyboard.Printer;
import lauzhack.client.keyboard.PrinterInterface;
import lauzhack.gui.GUI;


public class Client {
	
	private static final boolean VERBOSE = false;

	public static void main(String[] args) {
		System.out.println("Starting client.");
		
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

        CommunicationWrapper cw = new CommunicationWrapper(pushing, ne);
        new GUI(cw);
	}
}
