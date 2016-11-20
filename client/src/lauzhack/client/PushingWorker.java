package lauzhack.client;

import java.util.List;
import java.util.concurrent.locks.Lock;

import lauzhack.client.keyboard.PrinterInterface;

public class PushingWorker {

	List<Message> pending_messages;
	Lock listLock;
	Lock kbLock;
	PrinterInterface printer;

	public PushingWorker(PrinterInterface printer, List<Message> pending, Lock listLock, Lock kbLock) {
		this.listLock = listLock;
		this.kbLock = kbLock;
		this.pending_messages = pending;
		this.printer = printer;
	}

	public void displayNextMessage() {
		listLock.lock();
		int size = pending_messages.size();
		Message m;
		if (size != 0) {
			m = pending_messages.remove(0);
			listLock.unlock();
			kbLock.lock();
			
			if(m.getMessageAsString().equals("\\rainbow")) {
				printer.rainbow();
				kbLock.unlock();
				return;
			}
			
			if(m.getMessageAsString().equalsIgnoreCase("\\italia")) {
				printer.italia();
				kbLock.unlock();
				return;
			}
			
			if(m.getMessageAsString().equals("\\france")){
				printer.french();
				kbLock.unlock();
				return;
			}
			
			if(m.getMessageAsString().equals("\\suisse")){
				printer.suisse();
				kbLock.unlock();
				return;
			}
			
			printer.updatePending(pending_messages.size());
			if (m.getColor() == null) {
				printer.printMessage(m.getMessage());
			} else {
				printer.printMessage(m.getMessage(), m.getColor());
			}
			kbLock.unlock();
		} else {
			listLock.unlock();
		}

	}

}
