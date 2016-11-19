package lauzhack.client;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.Lock;

import lauzhack.client.keyboard.Printer;
import lauzhack.client.keyboard.PrinterInterface;

class PullingWorker implements Runnable {

	List<Message> pending_messages;
	PrinterInterface printer = (PrinterInterface) new Printer();
	NetworkInterface ne;
	Lock lock;

	public PullingWorker(NetworkInterface ne, List<Message> pending, Lock lock) {
		this.ne = ne;
		this.pending_messages = pending;
		this.lock = lock;
	}

	public void run() {
		while (true) {
			List<String> new_message;

			try {
				new_message = ne.getNextMessage();
				for (String line : new_message) {
					lock.lock();
					pending_messages.add(new Message(line));
					lock.unlock();
				}
				printer.updatePending(pending_messages.size());
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