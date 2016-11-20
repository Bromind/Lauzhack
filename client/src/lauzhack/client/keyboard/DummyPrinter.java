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
		System.out.print("Colored: [");
		for(Color col: c)
			System.out.print(col);
		System.out.println("]");
		
		for(char c2: m) {
			System.out.print(c2);
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

	@Override
	public void italia() {
		// TODO Auto-generated method stub
		System.out.println("Italia.");
	}

	@Override
	public void french() {
		// TODO Auto-generated method stub
		System.out.println("France.");
	}

}
