package lauzhack.client.keyboard;

import com.logitech.gaming.LogiLED;
import com.sun.xml.internal.ws.dump.LoggingDumpTube;

import lauzhack.client.Color;

public class Printer implements PrinterInterface {

	private int pending;
	private Color backgroundColor;
	private Color pendingColor;
	private Color defaultMessageColor;                                                                                                                                                                                

	public Printer(Color backgroundColor, Color pendingColor, Color defaultMessageColor) {
		LogiLED.LogiLedInit();
		pending = 0;
		this.backgroundColor = backgroundColor;
		this.pendingColor = pendingColor;
		this.defaultMessageColor = defaultMessageColor;
		LogiLED.LogiLedSetTargetDevice(LogiLED.LOGI_DEVICETYPE_PERKEY_RGB);
		clear();
	} 

	public Printer() {
		LogiLED.LogiLedInit();
		pending = 0;
		this.backgroundColor = new Color(0, 0, 100);
		this.pendingColor = new Color(0, 100, 0);
		this.defaultMessageColor = new Color(100, 0, 0);
		LogiLED.LogiLedSetTargetDevice(LogiLED.LOGI_DEVICETYPE_PERKEY_RGB);
		clear();
	} 

	public boolean printMessage(char[] m) {
		clear();
		for (int i = 0; i < m.length; i++) {
			int key = charToKey(m[i]);
			updateWithKey(key);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			clear();
		}
		return true;
	}

	public boolean printMessage(char[] m, Color[] c) {
		clear();
		for (int i = 0; i < m.length; i++) {
			int key = charToKey(m[i]);
			updateWithKey(key, c[i]);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			clear();
		}
		return true;
	}

	private void clear() {
		LogiLED.LogiLedSetLighting(
				backgroundColor.getRed(), 
				backgroundColor.getGreen(),
				backgroundColor.getBlue());
		drawPendingNb();
	}

	private void printKey(int key) {
		printKey(key, defaultMessageColor);
	}

	private void printKey(int key, Color c) {
		LogiLED.LogiLedSetLightingForKeyWithScanCode(
				key,
				c.getRed(),
				c.getGreen(),
				c.getBlue()
				);
	}

	private void updateWithKey(int key, Color c) {
		printKey(key, c);
		drawPendingNb();
	}

	private void updateWithKey(int key) {
		printKey(key);
		drawPendingNb();
	}

	public void drawPendingNb() {
		int c = pending;
		if (c > 9) {
			c = 9;
			printKey(0x0d, pendingColor);
		}
		int key = intToKey(c);
		printKey(key, pendingColor);
		
		LogiLED.LogiLedSetTargetDevice(LogiLED.LOGI_DEVICETYPE_RGB);
		if (pending > 0) {
			for (int i = 0; i < 100; i++) {
				LogiLED.LogiLedSetLighting(0, i, 0);
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
				}
			}
		} else {
			LogiLED.LogiLedSetLighting(0, 0, 0);
		}
		LogiLED.LogiLedSetTargetDevice(LogiLED.LOGI_DEVICETYPE_PERKEY_RGB);
	}

	public void updatePending(int pending) {
		this.pending = pending;		
		clear();
	}

	private int intToKey(int v) {
		if(v == 0) {
			return 0x0b;
		}

		return v+1;
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
		case ',':
			return 0x33;
		case '.':
			return 0x34;
		default:
			return 0;
		}
	}

	public void rainbow() {
		try {
			for(int j = 0; j < 2; j++) {
				for (int i = 0; i < 100; i++) {
					LogiLED.LogiLedSetLighting(100, i, 0);
					Thread.sleep(5);
				}
				for (int i = 0; i < 100; i++) {
					LogiLED.LogiLedSetLighting(100-i, 100, 0);
					Thread.sleep(5);
				}
				for (int i = 0; i < 100; i++) {
					LogiLED.LogiLedSetLighting(0, 100, i);
					Thread.sleep(5);
				}
				for (int i = 0; i < 100; i++) {
					LogiLED.LogiLedSetLighting(0, 100-i, 100);
					Thread.sleep(5);
				}
				for (int i = 0; i < 100; i++) {
					LogiLED.LogiLedSetLighting(i, 0, 100);
					Thread.sleep(5);
				}
			}
			clear();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void french() {
		char[] blueMessage = {'q', 'a', 'w', 's', 'e', 'd', 'z', 'x', 'd', 'c'};
		char[] whiteMessage = {'r', 't', 'y', 'f', 'g', 'h', 'v', 'b', 'n'};
		char[] redMessage = {'u', 'i', 'o', 'j', 'k', 'l', 'm', ',', '.'};
		Color blue = new Color(0, 0, 100);
		Color white = new Color(100, 100, 100);
		Color red = new Color(100, 0, 0);
		
		for (int i = 0; i < 10; i++) {
			LogiLED.LogiLedSetLighting(0, 0, 0);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (char m : blueMessage) {
				printKey(charToKey(m), blue);
			}
			for (char m : whiteMessage) {
				printKey(charToKey(m), white);
			}
			for (char m : redMessage) {
				printKey(charToKey(m), red);
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void italia() {
		char[] greenMessage = {'q', 'a', 'w', 's', 'e', 'd', 'z', 'x', 'd', 'c'};
		char[] whiteMessage = {'r', 't', 'y', 'f', 'g', 'h', 'v', 'b', 'n'};
		char[] redMessage = {'u', 'i', 'o', 'j', 'k', 'l', 'm', ',', '.'};
		
		Color green = new Color(0, 100, 0);
		Color white = new Color(100, 100, 100);
		Color red = new Color(100, 0, 0);
		for (int i = 0; i < 10; i++) {
			LogiLED.LogiLedSetLighting(0, 0, 0);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (char m : greenMessage) {
				printKey(charToKey(m), green);
			}
			for (char m : whiteMessage) {
				printKey(charToKey(m), white);
			}
			for (char m : redMessage) {
				printKey(charToKey(m), red);
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
