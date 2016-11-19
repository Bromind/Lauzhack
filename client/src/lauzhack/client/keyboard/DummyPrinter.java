package lauzhack.client.keyboard;

import lauzhack.client.Color;

public class DummyPrinter implements PrinterInterface {

	@Override
	public boolean printMessage(char[] m) {
		System.out.println(m.toString());
		return false;
	}

	@Override
	public boolean printMessage(char[] m, Color[] c) {
		System.out.println("Colored " + m.toString());
		return false;
	}

	@Override
	public void updatePending(int pending) {
		System.out.println("Now " + pending + " pending messages.");		
	}

}
