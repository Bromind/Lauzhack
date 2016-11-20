package lauzhack.client;

import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


import lauzhack.client.keyboard.DummyPrinter;
import lauzhack.client.keyboard.PrinterInterface;


public class Client {

	public static void main(String[] args) {
		System.out.println("Starting Client");
		
		NetworkInterface ne;
		List<Message> pending_messages = new LinkedList<>();
		Lock lock = new ReentrantLock(true);
		PrinterInterface printer = (PrinterInterface) new DummyPrinter();
		
		/* Init stuff */
		try {
			ne = new NetworkInterface("My name", "http://localhost:8000", "POST");
		} catch (MalformedURLException e) {
			System.err.println("U noob 4");
			System.err.println(e.getMessage());
			return;
		}
		
		PollingWorker pw = new PollingWorker(ne, printer, pending_messages, lock);
        (new Thread(pw)).start();
        PushingWorker pushing = new PushingWorker(printer, pending_messages, lock);
        (new Thread(pushing)).start();
	}
}
