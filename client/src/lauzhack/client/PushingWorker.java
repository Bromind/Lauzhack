package lauzhack.client;

import java.util.List;
import java.util.concurrent.locks.Lock;

import lauzhack.client.keyboard.PrinterInterface;

public class PushingWorker implements Runnable{

	List<Message> pending_messages;
	Lock lock;
	PrinterInterface printer;
	
	public PushingWorker(PrinterInterface printer, List<Message> pending, Lock lock) {
		this.lock = lock;
		this.pending_messages = pending;
		this.printer = printer;
	}
	
	@Override
	public void run() {
		while(true){
			lock.lock();
			int size = pending_messages.size();
			Message m;
			if(size != 0) {
				m = pending_messages.remove(0);
				lock.unlock();
				printer.printMessage(m.getMessage(), m.getColor());
			} else {
				lock.unlock();
			}
		}
		
	}

}
