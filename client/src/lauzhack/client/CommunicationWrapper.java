package lauzhack.client;

import java.util.List;

public class CommunicationWrapper {

	NetworkInterface ne;
	PushingWorker pw;
	public CommunicationWrapper(PushingWorker pw, NetworkInterface ne){
		this.ne = ne;
		this.pw = pw;
	}
	
	public void sendMessage(String message, String dest, List<Color> colors) {
		if (colors.size() >= 1) {
			int lastIndex = colors.size() - 1;
			for (int i = colors.size(); i < message.length(); i++) {
				colors.add(colors.get(lastIndex));
			}
		}
		
		Color[] c_array = new Color[message.length()];
		for(int i = 0; i < message.length(); i++)
			c_array[i] = colors.get(i);
		
		ne.sendMessage(message, c_array, dest);
	}
	
	public void displayNext(){
		pw.displayNextMessage();
	}
	
	public void newName(String name) {
		ne.setSrc(name);
	}
}
