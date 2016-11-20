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
		System.out.print("Colored ");
		for(char c2: m) {
			System.out.println(c2);
		}
		System.out.println("");
		
		return false;
	}

	@Override
	public void updatePending(int pending) {
		System.out.println("Now " + pending + " pending messages.");		
	}

	@Override
	public void rainbow() {
		// TODO Auto-generated method stub
		System.out.println("Rainbow.");
	}

}
