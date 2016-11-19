package lauzhack.client.keyboard;

import com.logitech.gaming.LogiLED;

public class Printer {
	
	private int pendingNb;

	public Printer() {
		LogiLED.LogiLedInit();
		pendingNb = 0;
	}   
	
	public boolean printMessage(char[] m) throws InterruptedException {
		LogiLED.LogiLedSetLighting(0, 0, 100);
		for (int i = 0; i < m.length; i++) {
			int key = charToKey(m[i]);
			printKey(key, 100, 0, 0);
			Thread.sleep(1000);
			LogiLED.LogiLedSetLighting(0, 0, 100);
		}
		return true;
	}
	
	public boolean printKey(int key, int r, int g, int b) {
		return LogiLED.LogiLedSetLightingForKeyWithScanCode(key, r, g, b);
	}
	
	private void drawPendingNb() {
		 
	}
	
	public void updatePending(int pending) {
				
	}
	
	private int charToKey(char c) {
		switch (c) {
		case 'a':
			return 0x1e;
		case 'b':
			return 0x30;
		case 'c':
			return 0x2e;
		case 'd':
			return 0x20;
		case 'e':
			return 0x12;
		case 'f':
			return 0x21;
		case 'g':
			return 0x22;
		case 'i':
			return 0x17;
		case 'j':
			return 0x24;
		case 'k':
			return 0x25;
		case 'l':
			return 0x26;
		case 'm':
			return 0x32;
		case 'n':
			return 0x31;
		case 'o':
			return 0x18;
		case 'p':
			return 0x19;
		case 'q':
			return 0x10;
		case 'r':
			return 0x13;
		case 's':
			return 0x1f;
		case 't':
			return 0x14;
		case 'u':
			return 0x16;
		case 'v':
			return 0x2f;
		case 'h':
			return 0x23;
		case 'w':
			return 0x11;
		case 'x':
			return 0x2d;
		case 'y':
			return 0x15;
		case 'z':
			return 0x2c;
		case ' ':
			return 0x39;
		default:
			return 0;
		}
	}
}
