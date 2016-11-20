package lauzhack.client;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.Lock;

import lauzhack.client.keyboard.PrinterInterface;

class PollingWorker implements Runnable {

	List<Message> pending_messages;
	PrinterInterface printer;
	NetworkInterface ne;
	Lock listLock, kbLock;

	public PollingWorker(NetworkInterface ne, PrinterInterface printer, List<Message> pending, Lock listLock, Lock kbLock) {
		this.ne = ne;
		this.pending_messages = pending;
		this.listLock = listLock;
		this.printer = printer;
		this.kbLock = kbLock;
	}

	public void run() {
		int pending = -1;
		while (true) {
			List<String> new_message;

			try {
				new_message = ne.getNextMessage();
				for (String line : new_message) {
					listLock.lock();
					pending_messages.add(new Message(line));
					listLock.unlock();
				}
				if(pending != pending_messages.size()) {
					kbLock.lock();
					printer.updatePending(pending_messages.size());
					kbLock.unlock();
					pending = pending_messages.size();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NoMessageException e) {
				try {
					wait(1000);
				} catch (InterruptedException e1) {

				}
			}
		}
	}

}