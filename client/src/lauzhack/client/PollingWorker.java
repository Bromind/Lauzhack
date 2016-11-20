package lauzhack.client;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.Lock;

import lauzhack.client.keyboard.PrinterInterface;

class PollingWorker implements Runnable {

	List<Message> pending_messages;
	PrinterInterface printer;
	NetworkInterface ne;
	Lock lock;

	public PollingWorker(NetworkInterface ne, PrinterInterface printer, List<Message> pending, Lock lock) {
		this.ne = ne;
		this.pending_messages = pending;
		this.lock = lock;
		this.printer = printer;
	}

	public void run() {
		int pending = 0;
		while (true) {
			List<String> new_message;

			try {
				new_message = ne.getNextMessage();
				for (String line : new_message) {
					lock.lock();
					pending_messages.add(new Message(line));
					lock.unlock();
				}
				if(pending != pending_messages.size()) {
					printer.updatePending(pending_messages.size());
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