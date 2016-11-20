package lauzhack.client.keyboard;

import lauzhack.client.Color;

public interface PrinterInterface {
	public boolean printMessage(char[] m);
	public boolean printMessage(char[] m, Color[] c);
	public void updatePending(int pending);
	public void rainbow();
	public void italia();
	public void french();
}
